public class FigureFactory {
	public Line makeLine(Point p1, Point p2) { return new Line(p1, p2); }
	public Point makePoint(int x, int y) { return new Point(x, y); } 
}