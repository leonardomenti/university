import org.aspectj.lang.*;

aspect SmartExc {
    pointcut anyCall(): call(* *..*(..)) || call(*..*.new(..)) && !within(SmartExc);

    declare soft : Exception : anyCall();

    after() throwing(SoftException ex) : execution(* *..*(..)) {
        Throwable t = ex.getWrappedThrowable();

        StackTraceElement[] els = ex.getStackTrace();
        StackTraceElement el = els[0];

        System.out.println(
            "[Aspect] Error: " + t.getClass().getName() + 
            " thrown at " + el.getClassName() + "." + el.getMethodName() + "(..):" +  
            " " + el.getFileName() + 
            " at " + el.getLineNumber());
        
        System.exit(-1);
    }
}