class MultipleExpr extends EList
{
	Expr expr;
	EList eList;

	MultipleExpr(Expr e, EList el)
	{
		expr = e;
		eList = el;
	}

	void emitInstructions() 
	{
		expr.emitInstructions();
		eList.emitInstructions();
	}
}
