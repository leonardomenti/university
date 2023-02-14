public aspect Error{

	pointcut cfl(): cflow(call(void Person.a())) && call(* *..*(..)) && !call(* java..*(..)) && !within(Error);
	pointcut cflblw(): cflowbelow(call(void Person.a())) && call(* Person..*(..));

	before(): cfl(){
		System.out.println("[Aspect] CFLOW" + thisJoinPoint.getSignature().toShortString());
	}

	before(): cflblw(){
		System.out.println("[Aspect] CFLOWBELOW" + thisJoinPoint.getSignature().toShortString());
	}

	declare error: call(* *..*.println(..)) && !within(Error): "Not use println";

	declare error: 
		set(* *..*) 
		&& !withincode(public void *.set*(..))
		&& !withincode(public *.new(..)): "Use setter";
}