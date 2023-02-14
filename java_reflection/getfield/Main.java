import java.lang.reflect.*;

public class Main{
	public static void main(String[] args) throws Throwable{
		A a = new A();
		Field f = a.getClass().getDeclaredField("a");
		f.setAccessible(true);
		System.out.println("a: " + f.get(a));
		a.incrm();
		System.out.println("a: " + f.get(a));
	}
}