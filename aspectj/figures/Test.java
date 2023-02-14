public class Test{
	public static void main(String[] args){

		Display d = new Display();
		FigureElement[] shapes={new Point(-1,2),new Line(new Point(-5,3),new Point(1,2))};

		for(FigureElement f:shapes) 
			f.addObserver(f,d);

		for (FigureElement f:shapes) 
			f.moveBy(3,-5);

		/*
		Point p = new Point(0,0);
		p.setX(10);
		try{
			p.setY(200);
		} catch (Throwable e){}

		Line l = new Line(p,p);

		l.modifyP1(new Point(1,1));

		System.out.println(l.getLine());

		System.out.println("P1.x: " + l.getP1().getX());
		System.out.println("P1.y: " + l.getP1().getY());
		System.out.println("P2.x: " + l.getP2().getX());
		System.out.println("P2.y: " + l.getP2().getY());

		System.out.println("P1 was called " + l.getP1().counter + " times");
		

		l.moveBy(10,20);
		*/
	}
}