public aspect Intercept{

	pointcut lastC(): call(* C.*(..)) && cflow(call(* B.*(..)));
	pointcut remove(): cflow(call(* B.*(..))) && !cflow(lastC()) && call(* A.*(..));

	after(): remove(){
		System.out.println("[Aspect] Remove this!");
	}
}