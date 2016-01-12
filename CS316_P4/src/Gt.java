class Gt extends BinaryOperatorApplication
{
	Gt(Expr e1, Expr e2)
	{
		expr1 = e1;
		expr2 = e2;
	}

	String getOp()
	{
		return ">";
	}

	TypeVal typeEval()
	{
		TypeVal e1Type = expr1.typeEval();
		TypeVal e2Type = expr2.typeEval();

		if ( e1Type == TypeChecker.typeError || e2Type == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( e1Type.isNumberType() && e2Type.isNumberType() )
			return TypeChecker.boolean_Type;
		else if ( !e1Type.isNumberType() )
		{
			IO.displayln("Type Error: > operator cannot be applied to "+e1Type.toString());
			return TypeChecker.typeError;
		}
		else // !e2Type.isNumberType()
		{
			IO.displayln("Type Error: > operator cannot be applied to "+e2Type.toString());
			return TypeChecker.typeError;
		}
	}

	void emitInstructions() 
	{
		if (expr1.getClass() == ArrayVar.class) 
		{
			expr1.emitInstructions();
			IO.displayln("      loadArrayElem  "
					+ Compiler.addressMap.get(((ArrayVar)expr1).id.id) + ", " 
					+ ((Array_Type)(Compiler.typeMap.get(((ArrayVar)expr1).id.id))).dimension);
		}
		else if (BinaryOperatorApplication.class.isAssignableFrom(expr1.getClass()))
		{
			expr1.emitInstructions();
		}
		else 
		{
			IO.display("      push  ");
			expr1.emitInstructions();
		}
		if (expr2.getClass() == ArrayVar.class) 
		{
			expr2.emitInstructions();
			IO.displayln("      loadArrayElem  "
					+ Compiler.addressMap.get(((ArrayVar)expr2).id.id) + ", " 
					+ ((Array_Type)(Compiler.typeMap.get(((ArrayVar)expr2).id.id))).dimension);
		}
		else if (BinaryOperatorApplication.class.isAssignableFrom(expr2.getClass()))
		{
			expr2.emitInstructions();
		}
		else
		{
			IO.display("      push  ");
			expr2.emitInstructions();
		}
		IO.displayln("      gt");
	}
}
