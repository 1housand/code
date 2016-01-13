class Mul extends BinaryOperatorApplication
{
	Mul(Expr e1, Expr e2)
	{
		expr1 = e1;
		expr2 = e2;
	}

	String getOp()
	{
		return "*";
	}

	TypeVal typeEval()
	{
		TypeVal e1Type = expr1.typeEval();
		TypeVal e2Type = expr2.typeEval();

		if ( e1Type == TypeChecker.typeError || e2Type == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( !e1Type.isNumberType() )
		{
			IO.displayln("Type Error: * operator cannot be applied to "+e1Type.toString());
			return TypeChecker.typeError;
		}
		else if ( !e2Type.isNumberType() )
		{
			IO.displayln("Type Error: * operator cannot be applied to "+e2Type.toString());
			return TypeChecker.typeError;
		}
		else if ( e1Type == TypeChecker.int_Type && e2Type == TypeChecker.int_Type )
			return TypeChecker.int_Type;
		else if ( e1Type == TypeChecker.float_Type && e2Type == TypeChecker.float_Type )
			return TypeChecker.float_Type;
		else
		{
			IO.displayln("Type Error: * operator cannot be applied to "+
			             " ("+e1Type.toString()+", "+e2Type.toString()+")");
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
		else
		{
			if (!expr1.getClass().isAssignableFrom(BinaryOperatorApplication.class)) 
			{
				IO.display("      push  ");
			}
			expr1.emitInstructions();
		}
		if (expr2.getClass() == ArrayVar.class) 
		{
			expr2.emitInstructions();
			IO.displayln("      loadArrayElem  "
					+ Compiler.addressMap.get(((ArrayVar)expr2).id.id) + ", " 
					+ ((Array_Type)(Compiler.typeMap.get(((ArrayVar)expr2).id.id))).dimension);
		}
		else
		{
//			if (!expr1.getClass().isAssignableFrom(BinaryOperatorApplication.class)) 
			{
				IO.display("      push  ");
			}
			expr2.emitInstructions();
		}
		IO.displayln("      mul");
	}
}
