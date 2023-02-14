import java.lang.reflect.*;

public class TraceHandler implements InvocationHandler{
	private Object baseObject;
	
	public TraceHandler(Object base){
		baseObject = base;
	}

	public Object invoke(Object proxy, Method m, Object[] args){
		try {
			System.out.println("before " + m.getName());
			Object result = m.invoke(baseObject, args);
			System.out.println("after " + m.getName());
			return result;
		}
		catch (Exception e) {
			e.printStackTrace(); 
			return null;
		}
	}
	
	public String toString(){
		return "th :- "+this.baseObject;
	}
}