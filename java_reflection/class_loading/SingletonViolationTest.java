import java.lang.reflect.*;

public class SingletonViolationTest{
	public static void main (String[] args) throws Exception{

		SimpleClassLoader CL1 = new SimpleClassLoader("testclasses");
		Class<?> c1 = CL1.loadClass("Singleton");
		System.out.println("Loaded class Singleton via the CL1 class loader");

		Field flag = c1.getDeclaredField("runOnce");
		flag.setAccessible(true);
		System.out.println("Let’s instatiate Singleton@CL1 ### runOnce :- "+flag.get(null));
		Object x = c1.getDeclaredConstructor().newInstance();
		System.out.println("### runOnce :- "+flag.get(null)); 

		try{
			System.out.println("\nLet’s re-instantiate Singleton@CL1 ### runOnce :- "+flag.get(null));
			Object y = c1.getDeclaredConstructor().newInstance();
			throw new RuntimeException("Test Fails!!!");
		} catch (Exception e) { 
			System.out.println("Re-initiation of Singleton !!!");
		}

		SimpleClassLoader CL2 = new SimpleClassLoader("testclasses");
		Class<?> c2 = CL2.loadClass("Singleton");
		System.out.println("\nLoaded class Singleton via the CL2 class loader"); 
		Field flag2 = c2.getDeclaredField("runOnce");
		flag2.setAccessible(true);
		System.out.println("Let’s instatiate Singleton@CL2 ### runOnce :- "+flag2.get(null));
		Object z = c2.getDeclaredConstructor().newInstance();
		System.out.println("### runOnce :- "+flag.get(null));
	}
}