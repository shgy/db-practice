package org.example.operator;

import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.block.RunLengthEncodedBlock;
import com.facebook.presto.spi.type.Type;
import com.google.common.annotations.VisibleForTesting;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.multiplyExact;
import static java.util.Objects.requireNonNull;

/**
 * 这里的实现直接用NestedLoopJoin,限制如下：
 * 1. 只实现两个Page的CrossJoin: 由于Cross Join的特性，两个Page JOIN
 * 2. Join条件的过滤实现由FilterOperator实现
 *
 */
public class JoinOperator implements Operator{

    private Page probePage, buildPage;

    private NestedLoopPageBuilder nestedLoopPageBuilder;

    public JoinOperator(TableScanOperator buildPageOperator) {
        this.buildPage = buildPageOperator.getOutput();
    }

    @Override
    public void addInput(Page page) {
        this.probePage = page;
    }

    @Override
    public Page getOutput() {
        /**
         * 由于Page是列式存储，所以需要考虑清楚CrossJoin对每一列的处理思路：复制
         * 这里处理的关键就是需要处理好映射关系，例如；
         * s={a,b},R={1,2}
         * 则：
         * a,1;
         * b,1;
         * a,2;
         * b,2;
         * 即每个block会进行扩展。 理解了原理后，直接可以复用presto的源码
         */

        if(nestedLoopPageBuilder==null){
            nestedLoopPageBuilder = new NestedLoopPageBuilder(probePage,buildPage);
        }else{
            if(nestedLoopPageBuilder.hasNext()){
                return nestedLoopPageBuilder.next();
            }
        }

        return null;
    }


    /**
     * This class takes one probe page(p rows) and one build page(b rows) and
     * build n pages with m rows in each page, where n = min(p, b) and m = max(p, b)
     */
    @VisibleForTesting
    static class NestedLoopPageBuilder
            implements Iterator<Page>
    {
        private final int numberOfProbeColumns;
        private final int numberOfBuildColumns;
        private final boolean buildPageLarger;
        private final Page largePage;
        private final Page smallPage;
        private final int maxRowIndex; // number of rows - 1

        private int rowIndex; // Iterator on the rows in the page with less rows.
        private final int noColumnShortcutResult; // Only used if select count(*) from cross join.

        NestedLoopPageBuilder(Page probePage, Page buildPage)
        {
            requireNonNull(probePage, "probePage is null");
            checkArgument(probePage.getPositionCount() > 0, "probePage has no rows");
            requireNonNull(buildPage, "buildPage is null");
            checkArgument(buildPage.getPositionCount() > 0, "buildPage has no rows");
            this.numberOfProbeColumns = probePage.getChannelCount();
            this.numberOfBuildColumns = buildPage.getChannelCount();

            // We will loop through all rows in the page with less rows.
            this.rowIndex = -1;
            this.buildPageLarger = buildPage.getPositionCount() > probePage.getPositionCount();
            this.maxRowIndex = Math.min(buildPage.getPositionCount(), probePage.getPositionCount()) - 1;
            this.largePage = buildPageLarger ? buildPage : probePage;
            this.smallPage = buildPageLarger ? probePage : buildPage;

            this.noColumnShortcutResult = calculateUseNoColumnShortcut(numberOfProbeColumns, numberOfBuildColumns, probePage.getPositionCount(), buildPage.getPositionCount());
        }

        private static int calculateUseNoColumnShortcut(
                int numberOfProbeColumns,
                int numberOfBuildColumns,
                int positionCountProbe,
                int positionCountBuild)
        {
            if (numberOfProbeColumns == 0 && numberOfBuildColumns == 0) {
                try {
                    // positionCount is an int. Make sure the product can still fit in an int.
                    return multiplyExact(positionCountProbe, positionCountBuild);
                }
                catch (ArithmeticException exception) {
                    // return -1 to disable the shortcut if overflows.
                }
            }
            return -1;
        }

        @Override
        public boolean hasNext()
        {
            return rowIndex < maxRowIndex;
        }

        @Override
        public Page next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (noColumnShortcutResult >= 0) {
                rowIndex = maxRowIndex;
                return new Page(noColumnShortcutResult);
            }

            rowIndex++;

            // Create an array of blocks for all columns in both pages.
            Block[] blocks = new Block[numberOfProbeColumns + numberOfBuildColumns];

            // Make sure we always put the probe data on the left and build data on the right.
            int indexForRleBlocks = buildPageLarger ? 0 : numberOfProbeColumns;
            int indexForPageBlocks = buildPageLarger ? numberOfProbeColumns : 0;

            // For the page with less rows, create RLE blocks and add them to the blocks array
            for (int i = 0; i < smallPage.getChannelCount(); i++) {
                Block block = smallPage.getBlock(i).getSingleValueBlock(rowIndex);
                blocks[indexForRleBlocks] = new RunLengthEncodedBlock(block, largePage.getPositionCount());
                indexForRleBlocks++;
            }

            // Put the page with more rows in the blocks array
            for (int i = 0; i < largePage.getChannelCount(); i++) {
                blocks[indexForPageBlocks + i] = largePage.getBlock(i);
            }

            return new Page(largePage.getPositionCount(), blocks);
        }
    }
}
