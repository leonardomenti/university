public class B{

	public void b1(){
		System.out.print("b->");
		new C().c1();
	}

	public void b2(){
		System.out.print("b->");
		new A().a2();
	}
}