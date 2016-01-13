class ArrayIndex
{
	EList eList;

	ArrayIndex(EList el)
	{
		eList = el;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IO.displayln(indent + indent.length() + " <array index>");
		eList.printParseTree(indent1);
	}
	
	void emitInstructions() 
	{
		if ( eList.getClass() == MultipleExpr.class )
		{
			EList el = eList;
			do
			{
				Expr expr = ((MultipleExpr)el).expr;
				if (expr.getClass() == Id.class || expr.getClass() == Int.class) 
				{
					IO.display("      push  ");
					expr.emitInstructions();
				}
				el = ((MultipleExpr)el).eList;
			} 
			while (el.getClass() == MultipleExpr.class);
			
			if (el.getClass() == Id.class || el.getClass() == Int.class)
			{
				IO.display("      push  ");
				el.emitInstructions();
			}
		}
		else
		{
			if (eList.getClass() == Id.class  || eList.getClass() == Int.class)
			{
				IO.display("      push  ");
			}
			eList.emitInstructions();
		}
	}
}