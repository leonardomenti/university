aspect PointBoundsPostConditions {
	after(Point p, int newX) returning:
		call(void Point.setX(int)) && target(p) && args(newX) { 
			myassert(p.getX() == newX);
			System.out.println("[Aspect - After] new X ok");
		}

	after(Point p, int newY) returning:
		call(void Point.setY(int)) && target(p) && args(newY) { 
			myassert(p.getY() == newY);
			System.out.println("[Aspect - After] new Y ok");
		}

	private void myassert(boolean v) {
		if ( !v ) throw new RuntimeException();
	} 
}