class If2 extends Statement
{
	Expr expr;
	Statement statement1;
	Statement statement2;

	If2(Expr e, Statement s1, Statement s2)
	{
		expr = e;
		statement1 = s1;
		statement2 = s2;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <if2>");
		expr.printParseTree(indent2);
		statement1.printParseTree(indent2);
		statement2.printParseTree(indent2);
	}
	
	TypeVal typeEval()
	{
		TypeVal eType = expr.typeEval();
		TypeVal st1Type = statement1.typeEval();
		TypeVal st2Type = statement2.typeEval();
		if ( eType == TypeChecker.typeError || st1Type == TypeChecker.typeError || st2Type == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( eType == TypeChecker.boolean_Type )
			return TypeChecker.typeCorrect;
		else
		{
			IO.displayln("Type Error: non-boolean expression found in if-else-statement");
			return TypeChecker.typeError;
		}
	}

	void emitInstructions() 
	{
		int g = TypeChecker.gt;
		TypeChecker.gt+=2;
		expr.emitInstructions();
		IO.displayln("      ifF goto " + (g));
		statement1.emitInstructions();
		IO.displayln("      goto " + (g+1));
		IO.displayln(g + ":");
		statement2.emitInstructions();
		IO.displayln((g+1) + ":");
	}
}

