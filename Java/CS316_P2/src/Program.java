
class Program 
{
	VarDecList varDecList;
	Statement statement;
	
	public Program( VarDecList vdl, Statement s) 
	{
		varDecList = vdl;
		statement = s;
	}
	
	void printParseTree( String indent )
	{
		String indent1 = indent + " ";
		String indent2 = indent1 + " ";
		
		IO.displayln(indent + indent.length() + " <program>");
//		IO.displayln(indent1 + indent1.length() + " <var dec list>");
		varDecList.printParseTree(indent1);
		IO.displayln(indent1 + indent1.length() + " <statement>");
		statement.printParseTree(indent2);
	}
}
