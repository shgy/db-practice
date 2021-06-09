// Generated from .\SqlBase.g4 by ANTLR 4.6
package org.example.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SqlBaseLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, AND=3, FROM=4, FALSE=5, NOT=6, OR=7, SELECT=8, TRUE=9, 
		WHERE=10, EQ=11, NEQ=12, LT=13, LTE=14, GT=15, GTE=16, STRING=17, INTEGER_VALUE=18, 
		IDENTIFIER=19, WS=20, UNRECOGNIZED=21;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "AND", "FROM", "FALSE", "NOT", "OR", "SELECT", "TRUE", 
		"WHERE", "EQ", "NEQ", "LT", "LTE", "GT", "GTE", "DIGIT", "LETTER", "STRING", 
		"INTEGER_VALUE", "IDENTIFIER", "WS", "UNRECOGNIZED"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'.'", "'AND'", "'FROM'", "'FALSE'", "'NOT'", "'OR'", "'SELECT'", 
		"'TRUE'", "'WHERE'", "'='", null, "'<'", "'<='", "'>'", "'>='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "AND", "FROM", "FALSE", "NOT", "OR", "SELECT", "TRUE", 
		"WHERE", "EQ", "NEQ", "LT", "LTE", "GT", "GTE", "STRING", "INTEGER_VALUE", 
		"IDENTIFIER", "WS", "UNRECOGNIZED"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public SqlBaseLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SqlBase.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\27\u0098\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\5\rd\n"+
		"\r\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\24\3\24\7\24x\n\24\f\24\16\24{\13\24\3\24\3\24\3\25\6"+
		"\25\u0080\n\25\r\25\16\25\u0081\3\26\3\26\5\26\u0086\n\26\3\26\3\26\3"+
		"\26\7\26\u008b\n\26\f\26\16\26\u008e\13\26\3\27\6\27\u0091\n\27\r\27\16"+
		"\27\u0092\3\27\3\27\3\30\3\30\2\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21"+
		"\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\2%\2\'\23)\24+\25-\26"+
		"/\27\3\2\7\3\2\62;\3\2C\\\3\2))\5\2<<BBaa\5\2\13\f\17\17\"\"\u009e\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2"+
		"\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\5\63\3\2\2\2"+
		"\7\65\3\2\2\2\t9\3\2\2\2\13>\3\2\2\2\rD\3\2\2\2\17H\3\2\2\2\21K\3\2\2"+
		"\2\23R\3\2\2\2\25W\3\2\2\2\27]\3\2\2\2\31c\3\2\2\2\33e\3\2\2\2\35g\3\2"+
		"\2\2\37j\3\2\2\2!l\3\2\2\2#o\3\2\2\2%q\3\2\2\2\'s\3\2\2\2)\177\3\2\2\2"+
		"+\u0085\3\2\2\2-\u0090\3\2\2\2/\u0096\3\2\2\2\61\62\7.\2\2\62\4\3\2\2"+
		"\2\63\64\7\60\2\2\64\6\3\2\2\2\65\66\7C\2\2\66\67\7P\2\2\678\7F\2\28\b"+
		"\3\2\2\29:\7H\2\2:;\7T\2\2;<\7Q\2\2<=\7O\2\2=\n\3\2\2\2>?\7H\2\2?@\7C"+
		"\2\2@A\7N\2\2AB\7U\2\2BC\7G\2\2C\f\3\2\2\2DE\7P\2\2EF\7Q\2\2FG\7V\2\2"+
		"G\16\3\2\2\2HI\7Q\2\2IJ\7T\2\2J\20\3\2\2\2KL\7U\2\2LM\7G\2\2MN\7N\2\2"+
		"NO\7G\2\2OP\7E\2\2PQ\7V\2\2Q\22\3\2\2\2RS\7V\2\2ST\7T\2\2TU\7W\2\2UV\7"+
		"G\2\2V\24\3\2\2\2WX\7Y\2\2XY\7J\2\2YZ\7G\2\2Z[\7T\2\2[\\\7G\2\2\\\26\3"+
		"\2\2\2]^\7?\2\2^\30\3\2\2\2_`\7>\2\2`d\7@\2\2ab\7#\2\2bd\7?\2\2c_\3\2"+
		"\2\2ca\3\2\2\2d\32\3\2\2\2ef\7>\2\2f\34\3\2\2\2gh\7>\2\2hi\7?\2\2i\36"+
		"\3\2\2\2jk\7@\2\2k \3\2\2\2lm\7@\2\2mn\7?\2\2n\"\3\2\2\2op\t\2\2\2p$\3"+
		"\2\2\2qr\t\3\2\2r&\3\2\2\2sy\7)\2\2tx\n\4\2\2uv\7)\2\2vx\7)\2\2wt\3\2"+
		"\2\2wu\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z|\3\2\2\2{y\3\2\2\2|}\7)"+
		"\2\2}(\3\2\2\2~\u0080\5#\22\2\177~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\177"+
		"\3\2\2\2\u0081\u0082\3\2\2\2\u0082*\3\2\2\2\u0083\u0086\5%\23\2\u0084"+
		"\u0086\7a\2\2\u0085\u0083\3\2\2\2\u0085\u0084\3\2\2\2\u0086\u008c\3\2"+
		"\2\2\u0087\u008b\5%\23\2\u0088\u008b\5#\22\2\u0089\u008b\t\5\2\2\u008a"+
		"\u0087\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u0089\3\2\2\2\u008b\u008e\3\2"+
		"\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d,\3\2\2\2\u008e\u008c"+
		"\3\2\2\2\u008f\u0091\t\6\2\2\u0090\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092"+
		"\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0095\b\27"+
		"\2\2\u0095.\3\2\2\2\u0096\u0097\13\2\2\2\u0097\60\3\2\2\2\13\2cwy\u0081"+
		"\u0085\u008a\u008c\u0092\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}