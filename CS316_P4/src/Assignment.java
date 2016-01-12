import java.awt.DisplayMode;

class Assignment extends Statement
{
	Var var;
	Expr expr;

	Assignment(Var v, Expr e)
	{
		var = v;
		expr = e;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <assignment>");
		var.printParseTree(indent2);
		expr.printParseTree(indent2);
	}
	
	TypeVal typeEval()
	{
		TypeVal eType = expr.typeEval();
		if ( eType == TypeChecker.typeError )
			return TypeChecker.typeError;
		else
		{
			TypeVal varType = var.typeEval();
			if ( varType == TypeChecker.typeError )
			{
				return TypeChecker.typeError;
			}
			else if ( varType == eType )
				return TypeChecker.typeCorrect;
			else
			{
				IO.displayln("Type Error: "+eType.toString()+" cannot be assigned to "+varType.toString());
				return TypeChecker.typeError;
			}
		}
	}

	void emitInstructions() 
	{
		if ( var.getClass() == ArrayVar.class) 
		{
			var.emitInstructions();
			if (expr.getClass() == Int.class || expr.getClass() == Id.class)
			{
				IO.display("      push  ");
			}
			expr.emitInstructions();
			IO.displayln("      storeArrayElem  " 
					+ Compiler.addressMap.get(((ArrayVar)var).id.id) + ", " 
					+ ((Array_Type)(Compiler.typeMap.get(((ArrayVar)var).id.id))).dimension);
		} 
		else 
		{
			if (expr.getClass() == Int.class || expr.getClass() == Floatp.class)
			{
				IO.display("      push  ");
			}
			expr.emitInstructions();
			IO.display("      pop  ");
			var.emitInstructions();
		}
	}
}
