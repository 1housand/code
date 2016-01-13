
public class MultipleRange extends RangeList
{
	Range range;
	RangeList rangeList;
	
	public MultipleRange( Range r, RangeList rl ) 
	{
		range = r;
		rangeList = rl;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
//		IO.displayln(indent + indent.length() + " <range>");
		range.printParseTree(indent);
		rangeList.printParseTree(indent);
	}
}
