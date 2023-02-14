aspect ExceptionLogging {
	pointcut excpt(): execution(* *.*(..));
	
	after() throwing(Throwable e): excpt() { 
		System.out.println("EXCT: "+e);
	
	StackTraceElement[] elems = e.getStackTrace();
	for(int i=0;i<elems.length;i++)
		System.out.println("[CLASS]	"+elems[i].getClassName()+"	[METHOD]  "+elems[i].getMethodName());
	}
} 