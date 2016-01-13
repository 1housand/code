
public class BinaryOperatorApplication extends OperatorApplication
{
	BinaryOperator binaryOperator;
	Expr expr1;
	Expr expr2;
	
	public BinaryOperatorApplication(BinaryOperator bo, Expr e1, Expr e2) 
	{
		binaryOperator = bo;
		expr1 = e1;
		expr2 = e2;
	}
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		binaryOperator.printParseTree(indent1);
		expr1.printParseTree(indent1);
		expr2.printParseTree(indent1);
	}
}
