class And extends BinaryOperatorApplication
{
	And(Expr e1, Expr e2)
	{
		expr1 = e1;
		expr2 = e2;
	}

	String getOp()
	{
		return "&";
	}

	TypeVal typeEval()
	{
		TypeVal e1Type = expr1.typeEval();
		TypeVal e2Type = expr2.typeEval();

		if ( e1Type == TypeChecker.typeError || e2Type == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( e1Type != TypeChecker.boolean_Type )
		{
			IO.displayln("Type Error: & operator cannot be applied to "+e1Type.toString());
			return TypeChecker.typeError;
		}
		else if ( e2Type != TypeChecker.boolean_Type )
		{
			IO.displayln("Type Error: & operator cannot be applied to "+e2Type.toString());
			return TypeChecker.typeError;
		}
		else
			return TypeChecker.boolean_Type;
	}

	void emitInstructions() 
	{
		expr1.emitInstructions();
		expr2.emitInstructions();
		IO.displayln("      and");
	}
}
