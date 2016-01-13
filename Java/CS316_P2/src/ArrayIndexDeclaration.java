
public class ArrayIndexDeclaration
{
	RangeList rangeList;
	
	public ArrayIndexDeclaration( RangeList rl ) 
	{
		rangeList = rl;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <array index declaration>");
		rangeList.printParseTree(indent1);
	}
	
	
}
