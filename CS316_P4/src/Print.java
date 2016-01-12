class Print extends Statement
{
	Expr expr;

	Print(Expr e)
	{
		expr = e;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <print>");
		expr.printParseTree(indent2);
	}
	
	TypeVal typeEval()
	{
		TypeVal eType = expr.typeEval();
		if ( eType == TypeChecker.typeError )
			return TypeChecker.typeError;
		else 
			return TypeChecker.typeCorrect;
	}

	void emitInstructions() 
	{
		if (expr.getClass() == ArrayVar.class) 
		{
			expr.emitInstructions();
			IO.displayln("      loadArrayElem  "
					+ Compiler.addressMap.get(((ArrayVar)expr).id.id) + ", " 
					+ ((Array_Type)(Compiler.typeMap.get(((ArrayVar)expr).id.id))).dimension);
		}
		else
		{
			if (expr.getClass() == Id.class) 
			{
				IO.display("      push  ");
			}
			expr.emitInstructions();
		}
		IO.displayln("      print");
	}
}

