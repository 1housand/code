
public class Range extends RangeList
{
	Int start;
	Int end;
	
	public Range( Int s, Int e ) 
	{
		start = s;
		end = e;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <range>");
		start.printParseTree(indent);
		end.printParseTree(indent);
	}
}
