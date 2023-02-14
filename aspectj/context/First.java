import java.lang.Math;

public class First{
		private int x;
		private String s;
		private int n;

		public First(int x, String s){
			this.x = x;
			this.s = s;
			this.n = (int) (Math.random() * 10);
		}

		public void firstCall(){
			System.out.println("First");
			new Middle1().middle1Call();
		}

		public int getX(){return x;}
		public int getN(){return n;}
		public String getS(){return s;}
	}