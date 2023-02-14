public aspect Age {

	pointcut setage(Person p, int n): 
		call(public void Person.setAge(int))
		&& target(p)
		&& args(n);

	pointcut excpt(): execution(* *..*.*(..)); // ? neatly captures any possible method execution 

	before(Person p, int n): setage(p, n){
		System.out.println("[Before] Age: " + p.getAge());
	}

	after(Person p, int n) returning: setage(p, n){
		System.out.println("[After] Age: " + n);
	}
}