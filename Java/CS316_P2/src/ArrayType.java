
public class ArrayType extends Type
{
	PrimitiveType primitiveType;
	ArrayIndexDeclaration arrayIndexDeclaration;
	
	public ArrayType(PrimitiveType pt, ArrayIndexDeclaration aid) 
	{
		primitiveType = pt;
		arrayIndexDeclaration = aid;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <array type>");
		primitiveType.printParseTree(indent1);
		arrayIndexDeclaration.printParseTree(indent1);
	}
	
}
