为加深对数据库的理解，了解SQL调优的细节。 对presto的源码进行研究，探索Query Engine的实现细节。自行定义了project, 命名为db-practice。

v1.0版本的功能特性如下：
1. 支持select field from table语法。 
2. 支持string字段类型。
3. 支持csv文件作为数据源，读取指定字段。

具体的操作方式如下：
1. 对SqlBase.g4的antlr4语法文件进行最大限度的裁剪，明确主线，避免陷入整个presto sql的细节中。 实际上，db-practice的g4文件相比presto的原版，行数只有其1/7。 实现了最大限度的裁剪。
2. 基于需要从presto源码中提取相关类，对于当前不需要的功能进行删除即可。 比如本版本，我们就移除了AstVisitor。 实际上该类特别重要，是我们访问语法树的模板。

所谓细节是魔鬼，实际操作中踩了如下的坑：
1. antlr4的版本要一致，比如代码中用的是4.6，那么按官网文档安装命令行时，版本号也要是4.6，不然就会报错。这里主要是因为官网的版本是4.9。
2. antlr4的的规则要考虑顺序。因为优先级是基于规则定义的顺序来的。

具体地，定义了SelectBase.g4文件后，通过如下的命令生成代码：
```shell
antlr4 -package org.example.antlr -no-listener -visitor .\SqlBase.g4
```
(注：antlr4命令来源于https://github.com/antlr/antlr4/blob/master/doc/getting-started.md操作指南)

通常对于语法树，visitor模式比listener模式开发更简单一些。

接下来就创建几个核心类：
1. 功能入口类：SqlParser，该类最核心的方法是`invokeParser()`, 我们基于该类进行扩张。
2. 节点基类：Node，这里为了简化开发成本，我们移除了`accept()`方法。

antlr4的核心功能就是解析查询语句生成语法树。通过解析查询语句获得Statement后，我们已经获得查询数据所需要的必要元素。
1. 查询的表Table。 
2. 查询的字段SelectItem。

为了简化实现，可以做出如下的约定：

整个业务流程如下：
s1: 获取查询的数据表以及字段。
s2: 通过数据表名称定为到数据文件，并读取数据文件数据。
s3: 格式化输出字段名称到命令行。
s4: 格式化输出字段内容到命令行。

