public aspect SetterEnforcement{

	pointcut changeState():
		set(Point Line.*) && !withincode(void Line.set*(Point));

	declare error: changeState(): "use setter methods";
}