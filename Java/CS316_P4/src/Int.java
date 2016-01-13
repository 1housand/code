class Int extends Expr
{
	int val;

	Int(int i)
	{
		val = i;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " " + val);
	}
	
	TypeVal typeEval()
	{
		return TypeChecker.int_Type;
	}

	void emitInstructions() 
	{
		IO.displayln("" + val);
	}
}