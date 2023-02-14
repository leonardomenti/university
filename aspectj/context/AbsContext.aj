import java.lang.reflect.*;

public abstract aspect AbsContext{

	protected interface IFirst {}

	private IFirst savedContext;

	abstract pointcut saveContext(IFirst f); 
	abstract pointcut getContext(Object obj); 

	before(IFirst f): saveContext(f){
		System.out.println("[Aspect] Saving context");
		savedContext = f;
	}

	before(Object obj): getContext(obj){
		System.out.println("[Aspect] Getting context for " + thisJoinPoint.getSignature().toShortString());
		try{
			Field f = obj.getClass().getDeclaredField("context");
			f.setAccessible(true);
			f.set(obj, savedContext);
		} catch(Exception e){
			System.out.println("catch");
			e.printStackTrace();
		}
	}
}