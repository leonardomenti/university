import java.lang.reflect.*;

public class SelectiveAccessibilityCheck {
	public static void main(String[] args) throws Exception {
		System.setSecurityManager(new SecurityManager() {
			public void checkPermission(Permission p) {
				if (p instanceof ReflectPermission && "suppressAccessChecks".equals(p.getName()))
					for (StackTraceElement e : Thread.currentThread().getStackTrace())
						if ("SelectiveAccessibilityCheck".equals(e.getClassName()) && "setName".equals(e.getMethodName())) 
							throw new SecurityException();
			}
		});

		Employee eleonor = new Employee("Eleonor", "Runedottir");
		System.out.println(eleonor);
		setSurname(eleonor, "Odindottir");
		System.out.println(eleonor);
		setName(eleonor, "Angela");
		System.out.println(eleonor);
	}

	private static void setName(Employee e, String n) throws Exception{
		Field name = Employee.class.getDeclaredField("name");
		name.setAccessible(true);
		name.set(e, n);
	}
	private static void setSurname(Employee e, String s) throws Exception{
		Field surname = Employee.class.getDeclaredField("surname");
		surname.setAccessible(true);
		surname.set(e, s);
	}
}