
public class While extends Statement 
{
	Expr expr;
	Statement statement;
	
	public While(Expr e, Statement s)
	{
		expr = e;
		statement = s;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent1 + " ";
		
		IO.displayln(indent1 + indent1.length() + " <while>");
		expr.printParseTree(indent1);
		IO.displayln(indent2 + indent2.length() + " <statement>");
		statement.printParseTree(indent2 + " ");
	}
}
