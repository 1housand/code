
public class MultipleStatement extends Statement 
{
	Statement statement;
	SList sList;
	
	public MultipleStatement( Statement s, SList sl )
	{
		statement = s;
		sList = sl;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
//		IO.displayln(indent + indent.length() + " <statement>");
		statement.printParseTree(indent);
		IO.displayln(indent + indent.length() + " <statement>");
		sList.printParseTree(indent);
	}
}
