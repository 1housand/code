
public class MultipleId extends IdList 
{
	Id id;
	IdList idList;
	
	MultipleId( Id i, IdList il ) 
	{	
		id = i;
		idList = il;
	}

	void printParseTree(String indent)
	{
//		String indent1 = indent + " ";
				
//		IO.displayln(indent + indent.length() + " <var dec>");
		id.printParseTree(indent);
		idList.printParseTree(indent);
	}
	
}
