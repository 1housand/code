
public class Print extends Statement
{
	Expr expr;
	
	public Print( Expr e ) 
	{
		expr = e;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent1 + indent1.length() + " <print>");
		expr.printParseTree(indent1);
	}
}
