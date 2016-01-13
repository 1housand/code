//import java.util.*;

class Id extends IdList
{
	String id;

	Id(String ident)
	{
		id = ident;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent1 + indent1.length() + " " + id);
	}

//	Val Eval(HashMap<String,Val> state)
//	{
//		Val idVal = state.get(id);
//		if ( idVal != null )
//			return idVal.cloneVal();
//		else
//		{
//			System.out.println( "variable "+id+" does not have a value" );
//			return null;
//		}
//	}
//	
//	void emitInstructions()
//	{
//		IO.displayln("push " + id);
//	}
}
