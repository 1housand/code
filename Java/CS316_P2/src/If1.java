
public class If1 extends If
{
	Expr expr;
	Statement statement;
	
	public If1( Expr e, Statement s )
	{
		expr = e;
		statement = s;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent1 + " ";
		String indent3 = indent2 + " ";
		
		IO.displayln(indent1 + indent1.length() + " <if1>");
		expr.printParseTree(indent1);
		IO.displayln(indent2 + indent2.length() + " <statement>");
		statement.printParseTree(indent3);
	}
}
