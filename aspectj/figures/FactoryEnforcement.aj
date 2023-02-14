aspect FactoryEnforcement{

	pointcut illegalNewFigElt():
		(call(Point.new(..)) || call(Line.new(..))) && !withincode(* FigureFactory.make*(..));

	/*
	before(): IllegalNewFigElt(){
		throw new Error("Use factory method instead.");
	}
	*/
	declare error: illegalNewFigElt(): "Use factory method instead.";

}