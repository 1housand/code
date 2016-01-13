class VarDec extends VarDecList
{
	Type type;
	IdList idList;

	VarDec(Type t, IdList i)
	{
		type = t;
		idList = i;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent + indent.length() + " <var dec>");
		type.printParseTree(indent1);
		idList.printParseTree(indent1);
	}
	
	boolean buildTypeMap()
	{
		boolean varDecError = false;

		Valid_Type typeVal = type.typeVal(); // converts "type" to the corresponding "typeVal"
		if ( typeVal == null ) // an error found in array index bounds
		{
			return true;
		}

		// associate variables in "idList" with "typeVal" and enter them into type map

		String ident;

		IdList p = idList;
		while ( p.getClass() == MultipleId.class )
		{
			ident = ((MultipleId)p).id.id;
			if ( TypeChecker.typeMap.containsKey(ident) )
			{
				varDecError = true;
				IO.displayln("Error: variable "+ident+" already declared");
			}
			else
			{
				TypeChecker.typeMap.put(ident, typeVal);
				if ( typeVal.getClass() == Array_Type.class ) 
				{
					TypeChecker.addressMap.put(ident, TypeChecker.address);
					int[] r = ((Array_Type)typeVal).rangeList;
					int d = ((Array_Type)typeVal).dimension;
					int a = 0;
					for(int i=0; i<d*2; i+=2)
					{
						a = a * (Math.abs(r[i+1]-r[i])+1) + Math.abs(r[i+1]-r[i]);
					}
					TypeChecker.address += a + 1;
				}
				else
				{
					TypeChecker.addressMap.put(ident, TypeChecker.address++);
				}
			}
			p = ((MultipleId)p).idList;
		}
		ident = ((SingleId)p).id.id; // p is the last SingleId
		if ( TypeChecker.typeMap.containsKey(ident) )
		{
			varDecError = true;
			IO.displayln("Error: variable "+ident+" already declared");
		}
		else
		{
			TypeChecker.typeMap.put(ident, typeVal);
			if ( typeVal.getClass() == Array_Type.class ) 
			{
				TypeChecker.addressMap.put(ident, TypeChecker.address);
				int[] r = ((Array_Type)typeVal).rangeList;
				int d = ((Array_Type)typeVal).dimension;
				int a = 0;
				for(int i=0; i<d*2; i+=2)
				{
					a = a * Math.abs(r[i+1]-r[i]+1) + Math.abs(r[i+1]-r[i]);
				}
				TypeChecker.address += a + 1;
//				if ( d == 1) 
//				{
//					TypeChecker.address += Math.abs(r[1]-r[0])+1; 
//				}
//				else if (d == 2) 
//				{
//					TypeChecker.address = 
//							Math.abs(TypeChecker.address + ((r[1]-r[0])*(r[3]-r[2]+1)+(r[3]-r[2]))) + 1; 
//				}
			}
			else
			{
				TypeChecker.addressMap.put(ident, TypeChecker.address++);
			}
		}
		return varDecError; 
	}
}
