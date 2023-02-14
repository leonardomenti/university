public class Main{
	public static void main(String[] args){
		d();
		d();
	}

	public static void a(){
		 System.out.println("a()");
		 b();
	}

	public static void b(){
		 System.out.println("b()");
		 c();
	}

	public static void c(){
		 System.out.println("c()");
		 new Z().z();
	}

	public static void d(){
		System.out.println("d()");
		int rand = (int)(Math.random() * 10);
		if (rand < 5)
			d();
		else
			a();
	}

}