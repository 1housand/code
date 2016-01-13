
public class Bool extends Expr
{
	Boolean bool;

	Bool(Boolean b)
	{
		bool = b;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent1 + indent1.length() + " " + bool.toString());
	}

}
