
public class ArrayVar extends Var
{
	VarId varId;
	ArrayIndex arrayIndex;

	ArrayVar( VarId i, ArrayIndex ai )
	{
		varId = i;
		arrayIndex = ai;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent1 + indent1.length() + " <array var>");
		varId.printParseTree(indent1);
		arrayIndex.printParseTree(indent1);
	}
}
