public aspect PointBoundsPreCondition { 

	before(int newX):
		call(void Point.setX(int)) && args(newX) { 
			myassert(newX >= MIN_X, newX);
			myassert(newX <= MAX_X, newX);
			System.out.println("[Aspect - Before] new X ok");
		}

	before(int newY):
		call(void Point.setY(int)) && args(newY) { 
			myassert(newY >= MIN_Y, newY);
			myassert(newY <= MAX_Y, newY);
			System.out.println("[Aspect - Before] new Y ok");
		}
	
	private void myassert(boolean v, int a) {
		if ( !v ) throw new RuntimeException("wrong value: " + a);
	} 
	
	private int MIN_X = 0;
	private int MIN_Y = 0;
	private int MAX_X = 100;
	private int MAX_Y = 100;
}