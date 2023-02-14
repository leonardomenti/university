aspect PointBoundsEnforcement { 
	
	void around(int newX):
		call(void Point.setX(int)) && args(newX) {
			proceed(clip(newX, MIN_X, MAX_X));
		}	
	
	void around(int newY):
		call(void Point.setY(int)) && args(newY) { 
			proceed(clip(newY, MIN_Y, MAX_Y));
		}

	private int clip(int val, int min, int max) { 
		return Math.max(min, Math.min(max, val));
	} 

	private int MIN_X = 0;
	private int MIN_Y = 0;
	private int MAX_X = 100;
	private int MAX_Y = 100;
}