import java.lang.reflect.*;

public class InvariantHandler implements InvocationHandler{
	private Object target;
	private Method invariant;

	public InvariantHandler(Object target){
		this.target = target;
		try {
			invariant = target.getClass().getMethod("invariant", new Class<?>[]{});
			if (!invariant.getReturnType().equals(boolean.class))
				invariant = null;
		} catch (NoSuchMethodException ex) { 
			invariant = null;
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		this.invokeInvariant(method);
		Object retvalue = method.invoke(this.target, args); 
		this.invokeInvariant(method);
		return retvalue;
	}

	private void invokeInvariant(Method method) {
		if ((this.invariant == null) || (method.equals(this.invariant))) 
			return;
		try {
			Boolean passed = (Boolean) invariant.invoke(target, new Object[]{});
			if (!passed.booleanValue()) 
				throw new RuntimeException();
		} catch (Exception e) { 
			System.out.println("Failed invariant check!!!");
		}
	}
	
	public String toString() {
		return "ih :- "+this.target;
	}
}