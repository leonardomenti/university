public class Display {

	public void update(FigureElement fe){
		if (fe instanceof Line){
			Line l = (Line) fe;
			System.out.println(l.getLine() + " has been updated!");
		}
		if (fe instanceof Point){
			Point p = (Point) fe;
			System.out.println(p.getPoint() + " has been updated!");
		}
	}
}