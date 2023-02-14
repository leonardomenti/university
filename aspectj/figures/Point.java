class Point implements FigureElement {
	private int x = 0, y = 0;

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public int getY() { return y; }

	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }

	public void moveBy(int dx, int dy) { x += dx; y += dy; }

	public String getPoint(){
		return "("+x+";"+y+")";
	}
}