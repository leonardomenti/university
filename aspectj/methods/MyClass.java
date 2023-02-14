public class MyClass{

	public void a(){
		System.out.println("a");
	}

	public void b(){
		System.out.println("b");
		a();
	}
	public void c(){
		System.out.println("c");
			a();
			d();
		
	}
	public void d(){
		System.out.println("d");
		b();
	}
}