public class B {
	private int c;

	public B(){}
	
	public B(int a){
		c=a*5;
	}

	public boolean equals(Object obj) {
		return (((B)obj).c == c); 
	}
}
