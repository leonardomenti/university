public aspect DelegationCount {

	private int counter = 0;
	private int max = 0;

	pointcut mainMethods():  call(* *..*(..)) && !within(DelegationCount)  && !call(* java..*(..));

	before(): mainMethods(){
		counter++;
		max++;
	}
	
	after(): mainMethods(){
		counter--;
		System.out.println("[Aspect] The delegation level of " + thisJoinPoint.getSignature().toShortString() + " is " + (max-counter-1));
		if (counter == 0)
			max = 0;
	}
}