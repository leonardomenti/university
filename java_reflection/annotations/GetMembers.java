import java.lang.reflect.*;

class Test{
	
	@TODO(
		value="implement",
		assignedTo="Leonardo Menti"
	)
	public int mth(){
		return 1;
	}
}

public class GetMembers{
	public static void main(String [] args) throws NoSuchMethodException{
		Method m = Test.class.getMethod("mth");
		TODO todo = m.getAnnotation(TODO.class);
		String assignedTo = todo.assignedTo();
		System.out.println("TODO item on Test is assigned to: ’" + assignedTo + ".");
	}
}