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
		T__0=1, T__1=2, T__2=3, T__3=4, AND=5, AS=6, FROM=7, FALSE=8, JOIN=9, 
		INNER=10, NOT=11, OR=12, ON=13, SELECT=14, TRUE=15, WHERE=16, EQ=17, NEQ=18, 
		LT=19, LTE=20, GT=21, GTE=22, STRING=23, INTEGER_VALUE=24, IDENTIFIER=25, 
		WS=26, UNRECOGNIZED=27;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "AND", "AS", "FROM", "FALSE", "JOIN", 
		"INNER", "NOT", "OR", "ON", "SELECT", "TRUE", "WHERE", "EQ", "NEQ", "LT", 
		"LTE", "GT", "GTE", "DIGIT", "LETTER", "STRING", "INTEGER_VALUE", "IDENTIFIER", 
		"WS", "UNRECOGNIZED"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'.'", "'('", "')'", "'AND'", "'AS'", "'FROM'", "'FALSE'", 
		"'JOIN'", "'INNER'", "'NOT'", "'OR'", "'ON'", "'SELECT'", "'TRUE'", "'WHERE'", 
		"'='", null, "'<'", "'<='", "'>'", "'>='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "AND", "AS", "FROM", "FALSE", "JOIN", "INNER", 
		"NOT", "OR", "ON", "SELECT", "TRUE", "WHERE", "EQ", "NEQ", "LT", "LTE", 
		"GT", "GTE", "STRING", "INTEGER_VALUE", "IDENTIFIER", "WS", "UNRECOGNIZED"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\35\u00b9\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\5\23\u0085\n\23\3\24\3\24\3\25\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\7\32\u0099"+
		"\n\32\f\32\16\32\u009c\13\32\3\32\3\32\3\33\6\33\u00a1\n\33\r\33\16\33"+
		"\u00a2\3\34\3\34\5\34\u00a7\n\34\3\34\3\34\3\34\7\34\u00ac\n\34\f\34\16"+
		"\34\u00af\13\34\3\35\6\35\u00b2\n\35\r\35\16\35\u00b3\3\35\3\35\3\36\3"+
		"\36\2\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\2\61\2\63\31\65\32\67\33"+
		"9\34;\35\3\2\7\3\2\62;\3\2C\\\3\2))\5\2<<BBaa\5\2\13\f\17\17\"\"\u00bf"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\3=\3\2\2\2\5?\3\2"+
		"\2\2\7A\3\2\2\2\tC\3\2\2\2\13E\3\2\2\2\rI\3\2\2\2\17L\3\2\2\2\21Q\3\2"+
		"\2\2\23W\3\2\2\2\25\\\3\2\2\2\27b\3\2\2\2\31f\3\2\2\2\33i\3\2\2\2\35l"+
		"\3\2\2\2\37s\3\2\2\2!x\3\2\2\2#~\3\2\2\2%\u0084\3\2\2\2\'\u0086\3\2\2"+
		"\2)\u0088\3\2\2\2+\u008b\3\2\2\2-\u008d\3\2\2\2/\u0090\3\2\2\2\61\u0092"+
		"\3\2\2\2\63\u0094\3\2\2\2\65\u00a0\3\2\2\2\67\u00a6\3\2\2\29\u00b1\3\2"+
		"\2\2;\u00b7\3\2\2\2=>\7.\2\2>\4\3\2\2\2?@\7\60\2\2@\6\3\2\2\2AB\7*\2\2"+
		"B\b\3\2\2\2CD\7+\2\2D\n\3\2\2\2EF\7C\2\2FG\7P\2\2GH\7F\2\2H\f\3\2\2\2"+
		"IJ\7C\2\2JK\7U\2\2K\16\3\2\2\2LM\7H\2\2MN\7T\2\2NO\7Q\2\2OP\7O\2\2P\20"+
		"\3\2\2\2QR\7H\2\2RS\7C\2\2ST\7N\2\2TU\7U\2\2UV\7G\2\2V\22\3\2\2\2WX\7"+
		"L\2\2XY\7Q\2\2YZ\7K\2\2Z[\7P\2\2[\24\3\2\2\2\\]\7K\2\2]^\7P\2\2^_\7P\2"+
		"\2_`\7G\2\2`a\7T\2\2a\26\3\2\2\2bc\7P\2\2cd\7Q\2\2de\7V\2\2e\30\3\2\2"+
		"\2fg\7Q\2\2gh\7T\2\2h\32\3\2\2\2ij\7Q\2\2jk\7P\2\2k\34\3\2\2\2lm\7U\2"+
		"\2mn\7G\2\2no\7N\2\2op\7G\2\2pq\7E\2\2qr\7V\2\2r\36\3\2\2\2st\7V\2\2t"+
		"u\7T\2\2uv\7W\2\2vw\7G\2\2w \3\2\2\2xy\7Y\2\2yz\7J\2\2z{\7G\2\2{|\7T\2"+
		"\2|}\7G\2\2}\"\3\2\2\2~\177\7?\2\2\177$\3\2\2\2\u0080\u0081\7>\2\2\u0081"+
		"\u0085\7@\2\2\u0082\u0083\7#\2\2\u0083\u0085\7?\2\2\u0084\u0080\3\2\2"+
		"\2\u0084\u0082\3\2\2\2\u0085&\3\2\2\2\u0086\u0087\7>\2\2\u0087(\3\2\2"+
		"\2\u0088\u0089\7>\2\2\u0089\u008a\7?\2\2\u008a*\3\2\2\2\u008b\u008c\7"+
		"@\2\2\u008c,\3\2\2\2\u008d\u008e\7@\2\2\u008e\u008f\7?\2\2\u008f.\3\2"+
		"\2\2\u0090\u0091\t\2\2\2\u0091\60\3\2\2\2\u0092\u0093\t\3\2\2\u0093\62"+
		"\3\2\2\2\u0094\u009a\7)\2\2\u0095\u0099\n\4\2\2\u0096\u0097\7)\2\2\u0097"+
		"\u0099\7)\2\2\u0098\u0095\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009c\3\2"+
		"\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3\2\2\2\u009c"+
		"\u009a\3\2\2\2\u009d\u009e\7)\2\2\u009e\64\3\2\2\2\u009f\u00a1\5/\30\2"+
		"\u00a0\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3"+
		"\3\2\2\2\u00a3\66\3\2\2\2\u00a4\u00a7\5\61\31\2\u00a5\u00a7\7a\2\2\u00a6"+
		"\u00a4\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00ad\3\2\2\2\u00a8\u00ac\5\61"+
		"\31\2\u00a9\u00ac\5/\30\2\u00aa\u00ac\t\5\2\2\u00ab\u00a8\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2"+
		"\2\2\u00ad\u00ae\3\2\2\2\u00ae8\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b2"+
		"\t\6\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\b\35\2\2\u00b6:\3\2\2\2"+
		"\u00b7\u00b8\13\2\2\2\u00b8<\3\2\2\2\13\2\u0084\u0098\u009a\u00a2\u00a6"+
		"\u00ab\u00ad\u00b3\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}