class BooleanType extends PrimitiveType
{
	String getType()
	{
		return "boolean";
	}
	
	Boolean_Type typeVal()
	{
		return TypeChecker.boolean_Type;
	}
}