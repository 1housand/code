
public class UnaryOperatorApplication extends OperatorApplication
{
	UnaryOperator unaryOperator;
	Expr expr1;
	
	public UnaryOperatorApplication(UnaryOperator uo, Expr e1) 
	{
		unaryOperator = uo;
		expr1 = e1;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
//		IO.displayln(indent + indent.length() + " <unary operator application>");
		unaryOperator.printParseTree(indent1);
		expr1.printParseTree(indent1);
	}
}
