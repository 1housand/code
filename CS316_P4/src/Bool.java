class Bool extends Expr
{
	boolean val;

	Bool(boolean b)
	{
		val = b;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " " + val);
	}
	
	TypeVal typeEval()
	{
		return TypeChecker.boolean_Type;
	}

	void emitInstructions() 
	{
		IO.displayln("" + val);
	}
}