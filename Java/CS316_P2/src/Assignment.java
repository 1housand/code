//import java.util.*;

class Assignment extends Statement
{
	Var var; 	// variable on the left side of the assignment
	Expr expr;	// expression on the right side of the assignment

	Assignment(Var v, Expr e)
	{
		var = v;
		expr = e;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent1 + indent1.length() + " <assignment>");
		var.printParseTree(indent1);
		expr.printParseTree(indent1);
	}
	
//	void M(HashMap<String,Val> state) // function to interpret this assignment
//	{
//		Val eVal = e.Eval(state); // evaluate expression e
//		if ( eVal != null )
//			state.put(id, eVal); // assign the value eVal to id
//		
//		/* For practical reason of efficiency, the error state is not implemented.
//		   Rather, the error state is implicitly assumed whenever Eval returns null representing
//		   the runtime error value. */
//	}
//	
//	void emitInstructions()
//	{
//		e.emitInstructions();
//		IO.displayln("pop " + id);
//	}
}
