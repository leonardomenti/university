public class Main{
	public static void main(String[] args){
		int x = 1;
		System.out.println(mydouble(x));
		System.out.println(mydouble(x+1));
		System.out.println(mydouble(x+1));
	}
	public static int mydouble(int x){
		return 2*x;
	}
}