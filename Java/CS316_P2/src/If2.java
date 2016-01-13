
public class If2 extends If
{
	Expr expr;
	Statement statement1;
	Statement statement2;
	
	public If2( Expr e, Statement s1, Statement s2 )
	{
		expr = e;
		statement1 = s1;
		statement2 = s2;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent1 + " ";
		
		IO.displayln(indent1 + indent1.length() + " <if2>");
		expr.printParseTree(indent1);
		IO.displayln(indent2 + indent2.length() + " <statement>");
		statement1.printParseTree(indent2);
		IO.displayln(indent2 + indent2.length() + " <statement>");
		statement2.printParseTree(indent2);
	}
}
