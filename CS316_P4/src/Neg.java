class Neg extends UnaryOperatorApplication
{
	Neg(Expr e)
	{
		expr = e;
	}

	String getOp()
	{
		return "-";
	}

	TypeVal typeEval()
	{
		TypeVal eType = expr.typeEval();

		if ( eType == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( eType.isNumberType() )
			return eType;
		else
		{
			IO.displayln("Type Error: unary - operator cannot be applied to "+eType.toString());
			return TypeChecker.typeError;
		}
	}

	void emitInstructions() 
	{
		expr.emitInstructions();
	}
}
