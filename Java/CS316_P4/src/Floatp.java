class Floatp extends Expr
{
	float val;

	Floatp(float f)
	{
		val = f;
	}
	
	void printParseTree(String indent)
	{
		LexAnalyzer.displayln(indent + indent.length() + " " + val);
	}
	
	TypeVal typeEval()
	{
		return TypeChecker.float_Type;
	}

	void emitInstructions() 
	{
		IO.displayln("" + val);
	}
}