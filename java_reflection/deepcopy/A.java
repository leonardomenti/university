public class A {
	private int aa=0;
	private B bb = new B(aa);

	public A() {}

	public A(int a) {
		aa=a;
		bb=new B(aa);
	} 

	public boolean equals(Object obj) {
		return ((this.aa == ((A)obj).aa) && (this.bb.equals(((A)obj).bb)));
} }
