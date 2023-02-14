import java.lang.reflect.*;

public class Test{
	public static void main(String[] args){

		IPoint p1 = new Point(10,20);
		System.out.println("*** Regular calls ***\n" + p1 + "\n" + p1.getX());

		IPoint th_p1 = (IPoint) Proxy.newProxyInstance(p1.getClass().getClassLoader(), p1.getClass().getInterfaces(), new TraceHandler(p1));
		System.out.println("\n*** Traced calls ***\n");
		System.out.println(th_p1.toString() + "\n");
		System.out.println(th_p1.getX() + "\n");

		InvariantHandler ih = new InvariantHandler(new Point (0,7));
		IPoint ih_p2 = (IPoint) Proxy.newProxyInstance(Point.class.getClassLoader(), Point.class.getInterfaces(), ih);
		System.out.println("\n*** InvariantHandler ***\n");
		System.out.println(ih_p2);

		TraceHandler th = new TraceHandler(ih_p2);
		System.out.println(th);

		IPoint th_ih_p3 = (IPoint) Proxy.newProxyInstance(Point.class.getClassLoader(), Point.class.getInterfaces(), th);
		System.out.println("\n*** TraceHandler with InvariantHandler ***\n");
		System.out.println(th_ih_p3 + "\n");

		th_ih_p3.setX(25);
		System.out.println(th_ih_p3 + "\n");

		th_ih_p3.setY(-7);
		System.out.println(th_ih_p3 + "\n");
	}
}