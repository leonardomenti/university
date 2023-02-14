public class Middle2{

		private First context;
		
		public void middle2Call(){
			System.out.println("Middle 2");
			new Target().targetCall();
		}
	}