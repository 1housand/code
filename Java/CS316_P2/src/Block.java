
public class Block extends Statement
{
	SList sList;
	
	public Block( SList sl)
	{
		sList = sl;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <block>");
		IO.displayln(indent1 + indent1.length() + " <statement>");
		sList.printParseTree(indent1);
	}
}
