
public class UnaryOperator
{
	String string;
	
	public UnaryOperator(String s)
	{
		string = s;
	}
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
//		IO.displayln(indent + indent.length() + " <unary operator>");
		IO.displayln(indent + indent.length() + " " + string);
	}
}
