
public class MultipleVarDec extends VarDecList
{
	VarDec varDec;
	VarDecList varDecList;
	
	public MultipleVarDec(VarDec v, VarDecList vdl) 
	{
		varDec = v;
		varDecList = vdl;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		if ( varDec != null ) 
		{
			if ( !(varDec.type instanceof ArrayType) )
				IO.displayln(indent + indent.length() + " <var dec list>");
		}
		if (varDec.type instanceof ArrayType)
		{
			varDec.printParseTree(indent);
			varDecList.printParseTree(indent1);
		}
		else
		{
			varDec.printParseTree(indent1);
			varDecList.printParseTree(indent1);
		}
	}
}
