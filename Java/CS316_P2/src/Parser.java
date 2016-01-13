//import java.lang.reflect.Array;
//
//import javax.print.attribute.standard.RequestingUserName;

/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<assignment list> --> <assignment> | <assignment> <assignment list>
<assignment> --> <id> = <E> ";"
<E> --> <term> | <term> + <E> | <term> - <E>
<term> --> <primary> | <primary> * <term> | <primary> / <term>
<primary> --> <id> | <int> | <float> | <floatE> | "(" <E> ")" 

The definitions of the tokens are given in the lexical analyzer class file "LexArithArray.java". 

The following variables/functions of "IO"/"LexArithArray" classes are used:

static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

static void setLex()
static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token

An explicit parse tree is constructed in the form of nested class objects.
 
The main function will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks. 

**/


public abstract class Parser extends LexAnalyzer
{
	static boolean errorFound = false;
	static int position;

	public static Program program()
	
	// <program> --> <var dec list> <statement> 
	
	{
		VarDecList varDecList = varDecList();
		Statement statement = statement();
		return new Program(varDecList, statement);
	}
	
	public static VarDecList varDecList()

	// <var dec list> --> <var dec> ";" | <var dec> ";" <var dec list> 
	
	{
		VarDec varDec = varDec();
		if ( state == State.Semicolon )
		{
			getToken();
			System.out.println("line 63: " + t);
			if ( primitiveType() != null ) {
				VarDecList varDecList = varDecList();
				return new MultipleVarDec(  varDec, varDecList );
			}
		}
		else
			errorMsg(4);
		return varDec;
	}
	
	public static VarDec varDec() 
	
	// <var dec> --> <type> <id list> 
	
	{
		Type type = type();
		IdList idList = idList();
		return new VarDec( type, idList );
	}

	public static Type type()
	
	// <type> --> <primitive type> | <array type> 
	
	{
		PrimitiveType primitiveType = primitiveType();
		getToken();
		System.out.println("line 98: " + t);
		if ( state == State.Id )
			return primitiveType;
		else
		{

			// <array type> --> <primitive type> <array index declaration> 
			
			ArrayIndexDeclaration arrayIndexDeclaration = arrayIndexDeclaration();
			return new ArrayType( primitiveType, arrayIndexDeclaration );
		}
	}
	
	public static PrimitiveType primitiveType()
	
	// <primitive type> --> "int" | "float" | "boolean" 
	
	{
/*		if ( state.toString().equals( "Keyword_int" ) 
				|| state.toString().equals( "Keyword_float" ) 
				|| state.toString().equals( "Keyword_boolean" ) )
*/		
		if ( state.ordinal() >= 33 && state.ordinal() <= 35)
			return new PrimitiveType( t );
		else
			return null;
	}

	public static ArrayIndexDeclaration arrayIndexDeclaration()
	
	// <array index declaration> --> "[" <range list> "]"
	
	{
		if ( state == State.LBracket )
		{
			getToken();
			System.out.println("line 169: " + t);
			RangeList rangeList = rangeList();
//			getToken();
//			System.out.println("line 169: " + t);
			if ( state == State.RBracket ) 
			{
				getToken();
				System.out.println("line 176: " + t);
				return new ArrayIndexDeclaration( rangeList );
			}
			else
			{
				errorMsg(6);
				return null;
			}
		}
		else return null;
	}
	
	public static RangeList rangeList()
	
	// <range list> --> <range> | <range> "," <range list>
	
	{
		Range range = range();
		getToken();
		System.out.println("line 189: " + t);
		if ( state == State.Comma )
		{
			getToken();
			System.out.println("line 187: " + t);
			RangeList rangeList = rangeList();
			return new MultipleRange( range, rangeList );
		}
		else
			return range;
	}
	
	public static Range range()
	
	// <range> --> <int> ":" <int> 
	
	{
		if ( state == State.Int )
		{
			Int int1 = new Int(Integer.parseInt( t ) );
			getToken();
			System.out.println("line 205: " + t);
			if ( state == State.Colon )
			{
				getToken();
				System.out.println("line 214: " + t);
				if ( state == State.Int )
				{
					Int int2 = new Int(Integer.parseInt( t ) );
					return new Range( int1, int2 );
				}
				else return null;
			}
			else return null;
		}
		else return null;
	}
	
	public static IdList idList()
	
	// <id list> --> <id> | <id> "," <id list> 
	
	{
		if ( state == State.Id )
		{
			Id id = new Id( t );
			getToken();
			System.out.println("line 118: " + t);
			if ( state == State.Comma )
			{
				getToken();
				System.out.println("line 122: " + t);
				IdList idList = idList();
				return new MultipleId(id, idList);
			}
			else
				return id;
		}
		else
		{
			errorMsg(4);
			return null;
		}
	}
	
	public static Statement statement()
	
	// <statement> --> <assignment> | <block> | <if1> | <if2> | <while> | <print> 
	
	{
		System.out.println("line 215: in statement()");
		switch ( state )
		{
			case Id:
				return assignment();
				
			case LBrace:
				
				// <block> --> "{" <s list> "}" 
				
				System.out.println("line 225: case LBrace");
				getToken();
				SList sList = sList();
//				getToken();
				System.out.println("line 227: " + t);
				if ( state == State.RBrace )
					return new Block(sList);
				else 
					//error msg
					return null;

			case Keyword_if:
				
				If if1 = If();
//				getToken();
				return if1;
				
			case Keyword_while:
				return While();
				
			case Keyword_print:
				return print(); 
				
			default:
				errorMsg(2);
				return null;
		}
	}

	public static Assignment assignment()
	
	// <assignment> --> <var> "=" <expr>; 
	
	{
		Var var = var();
//		getToken();
		if ( state == State.Eq )
		{
			getToken();
			System.out.println("line 260: " + t);
			Expr expr = expr();
//			getToken();
			if ( state == State.Semicolon )
			{
				return new Assignment(var, expr);
			}
			else
				errorMsg(4);
		}
		else
			errorMsg(3);
		return null;
	}

	public static Var var()
	
	// <var> --> <id> | <array var> 
	
	{
		VarId id = new VarId( t );
		getToken();
		if ( state == State.LBracket )
			
		// <array var> --> <id> <array index>
			
		{
			return new ArrayVar( id, arrayIndex() );
		}
		else 
			return id;
	}
	
	public static ArrayIndex arrayIndex()
	
	// <array index> --> "[" <e list> "]" 
	
	{
		if ( state == State.LBracket)
		{
			getToken();
			EList eList = eList();
//			getToken();
			if ( state == State.RBracket )
			{
				getToken();
				return new ArrayIndex(eList);
			}
			else
			{
				errorMsg(6);
				return null;
			}
		}
		else
			//error msg
			return null;
	}
	
	public static EList eList()
	
	// <e list> --> <expr> | <expr> "," <e list> 
	
	{
		Expr expr = expr();
//		getToken();
		if ( state == State.Comma )
		{
			getToken();
			EList eList = eList();
			return new MultipleEList( expr, eList );
		}
		else
			return expr;
	}
	
	public static SList sList()
	
	// <s list> --> <statement> | <statement> <s list> 
	
	{
		Statement statement = statement();
		if ( !(statement instanceof If1) ) {
			getToken();
		}
		if ( state == State.RBrace )
			return statement;
		else 
			if ( statement != null )
		{
			SList sList = sList();
			return new MultipleStatement( statement, sList );
		}
		else
			return statement;
	}
	
	public static If If() 
	
	// <if1> --> "if" <expr> <statement> 
	// <if2> --> "if" <expr> <statement> "else" <statement> 
	
	{
		getToken();
		Expr expr = expr();
		if ( expr != null ) 
		{
//			getToken();
			Statement statement = statement();
			if ( statement != null )
			{
				getToken();
				if ( state == State.Keyword_else )
				{
					getToken();
					return new If2( expr, statement, statement());
				}
				else
					return new If1( expr, statement );
			}
			else
				return null;
		} 
		else
			return null;
	}

	public static While While()
	
	// <while> --> "while" <expr> <statement>
	
	{
		getToken();
		return new While( expr(), statement() );
	}
	
	public static Print print() 
	
	// <print> --> "print" <expr>; 
	
	{
		getToken();
		return new Print( expr() );
	}
	
	public static Expr expr()

	// <expr> --> <var> | <int> | <float> | <floatE> | <floatF> | "false" | "true" | "(" <operator application> ")" 

	{
		switch ( state )
		{
			case Id:
				
				return var();
//				getToken();
				
			case Int:

//				Int intExpr = ;
				Int i = new Int(Integer.parseInt(t));
				getToken();
				return i; 

			case Float: case FloatE:

				FloatE floate = new FloatE(Float.parseFloat(t));
				getToken();
				return floate;

			case Keyword_false:
				
				getToken();
				return new Bool( false );
				
			case Keyword_true:
			
				getToken();
				return new Bool( true );
				
			case LParen:
				
				getToken();
				OperatorApplication operatorApplication = operatorApplication();
				if ( state == State.RParen )
				{
					getToken();
					return operatorApplication;
				}
				else
				{
					errorMsg(1);
					return null;
				}

			default:

				errorMsg(2);
				return null;
		}
	}
	
	public static OperatorApplication operatorApplication() 
	
	// <operator application> --> <binary operator application> | <unary operator application> 
	
	{
		if ( state.ordinal() >= 0 && state.ordinal() <= 12) 
		{
			String operator = t;
			getToken();
			Expr expr1 = expr();
			if ( state == State.RParen )
				
				// <unary operator application> --> <unary operator> <expr>
				
				return new UnaryOperatorApplication( new UnaryOperator(operator), expr1 );

//			getToken();
			else
				
				// <binary operator application> --> <binary operator> <expr> <expr>
			
			{
				Expr expr2 = expr();
				return new BinaryOperatorApplication( new BinaryOperator(operator), expr1, expr2 );
			}
		}
		else
			return null;
	}
	
	public static void errorMsg(int i)
	{
		System.out.print("errorMsg");
		
		errorFound = true;
		
		if ( i > 0 )
		{
			errorMsgExt(i);
		}
		else if ( position == a ) 
		{
			displayln(t + "  -- unexpected symbol");
		}
	}
	
	public static void errorMsgExt(int i)
	{
		position = a;
		
		display(t + " : Syntax Error, unexpected symbol where");
		
		switch( i )
		{
		case 1:	displayln(" ) expected"); return;
		case 2: displayln(" id, {, if, while, or print expected"); return;
		case 3:	displayln(" = expected"); return;
		case 4:	displayln(" ; expected"); return;
		case 5:	displayln(" id expected"); return;	
		case 6: displayln(" ] expected"); return;
		default: displayln(" attention is needed"); return;
		}
	}

	public static void main(String argv[])
	{
		// argv[0]: input file containing an assignment list
		// argv[1]: output file displaying the parse tree
		
		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		System.out.println("line 368: " + t);
		Program program = program(); // build a parse tree
		if ( ! errorFound )
			program.printParseTree(""); // print the parse tree in linearly indented form in argv[1] file
		else if ( ! t.isEmpty() )
			errorMsg(0);

		closeIO();
	}
}

