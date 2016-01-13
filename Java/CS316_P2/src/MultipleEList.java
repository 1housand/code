
public class MultipleEList extends EList
{
	Expr expr;
	EList eList;
	
	public MultipleEList( Expr e, EList el ) 
	{
		expr = e;
		eList = el;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
//		IO.displayln(indent + indent.length() + " <var dec list>");
		expr.printParseTree(indent);
		eList.printParseTree(indent);
	}
}
