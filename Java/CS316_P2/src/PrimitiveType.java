
public class PrimitiveType extends Type
{
	protected String type;
	
	PrimitiveType( String s ) 
	{
		type = s;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " " + type);
	}
	
}
