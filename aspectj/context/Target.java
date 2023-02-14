public class Target{

		private First context;

		public void targetCall(){
			System.out.println("Target");
			System.out.println("Context: " + context.getX() + ", " + context.getN() + ", " + context.getS());
		}
	}