public class Middle1{

		private First context;
		
		public void middle1Call(){
			System.out.println("Middle 1");
			new Middle2().middle2Call();
		}

	}