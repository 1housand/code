abstract class Expr extends EList
{
	abstract TypeVal typeEval();
	abstract void emitInstructions();
}
