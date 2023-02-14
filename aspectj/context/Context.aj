import java.lang.reflect.*;
import java.io.PrintStream;

public aspect Context extends AbsContext{ 

	declare parents: First implements IFirst;

	pointcut saveContext(IFirst f): call(void First.firstCall()) && target(f);
	pointcut getContext(Object obj): 
		cflowbelow(call(void First.firstCall())) && 
		call(* Object.*(..)) && 
		target(obj) &&
		!call(void PrintStream.println(..)) &&
		!within(Context);

}