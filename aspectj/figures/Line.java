class Line implements FigureElement{
	
	private Point p1, p2;

	public Line(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
	}

	public Point getP1() { return p1; }
	public Point getP2() { return p2; }

	public void modifyP1(Point p){ this.p1 = p; }

	public void setP1(Point p1) { this.p1 = p1; }
	public void setP2(Point p2) { this.p2 = p2; }

	public void moveBy(int dx, int dy) { p1.moveBy(dx, dy); p2.moveBy(dx, dy); }

	public String getLine(){
		return "Line: " + p1.getPoint() + ", " + p2.getPoint();
	}
}