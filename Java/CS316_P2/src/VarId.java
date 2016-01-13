
public class VarId extends Var
{
	String id;

	VarId(String ident)
	{
		id = ident;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent1 + indent1.length() + " " + id);
	}
	
}
