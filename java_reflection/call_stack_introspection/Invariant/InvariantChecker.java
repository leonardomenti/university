public class InvariantChecker{

	public static void checkInvariant(InvariantSupporter obj){
		StackTraceElement[] ste = (new Throwable()).getStackTrace();

		// non va in loop perchè il for parte da 1, quindi controllo le funzioni che ci sono prima di questa
		for (int i = 1 ; i<ste.length; i++){
			if (ste[i].getClassName().equals("InvariantChecker") && ste[i].getMethodName().equals("checkInvariant"))
				return;
		}
		if (!obj.invariant()) 
			throw new IllegalStateException("invariant failure");
	}		
}