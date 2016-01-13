
public class BinaryOperator
{
	String string;
	
	public BinaryOperator(String s) 
	{
		string = s;
	}
	
	void printParseTree(String indent)
	{
//		String indent1 = indent + " ";
		
		IO.displayln(indent + indent.length() + " " + string);
	}
}
