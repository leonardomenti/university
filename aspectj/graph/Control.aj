public aspect Control{

	pointcut callMethod(): call(* *..*(..)) && !within(Control) && !call(* java..*(..));

	before(): callMethod(){
		System.out.println("[Aspect] " + thisJoinPoint.getSignature().toShortString());
	}
}