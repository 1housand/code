//import java.util.*;

class FloatE extends Expr
{
	float val;

	FloatE(float f)
	{
		val = f;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
//		IO.displayln(indent + indent.length() + " <float>");
		IO.displayln(indent1 + indent1.length() + " " + val);
	}

//	Val Eval(HashMap<String,Val> state)
//	{
//		return new FloatVal(val);
//	}
//	
//	void emitInstructions()
//	{
//		IO.displayln("push " + val);
//	}
}
