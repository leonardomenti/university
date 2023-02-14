public class Main{
	public static void main(String[] args){

		System.out.println("### Main");
		System.out.println("dupl(2): " + dupl(2));
		System.out.println("addOne(2): " + addOne(2));

		System.out.println("\n### Main2");
		Main2.main(new String[]{});
	}

	public static int dupl(int x){
		return 2*x;
	}

	public static int addOne(int x){
		return 1+x;
	}
}