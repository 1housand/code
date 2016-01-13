
public class ArrayIndex 
{
	EList eList;
	
	public ArrayIndex( EList el )
	{
		eList = el;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent1 + indent1.length() + " <array index>");
		eList.printParseTree(indent1);
	}
}
