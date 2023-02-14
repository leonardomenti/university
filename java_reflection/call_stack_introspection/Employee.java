class Employee{
	
	private String name;
	
	public Employee(){
		this.name="Anon";
	}
	public Employee(String name){
		this.name=name;
	}
	
	public String toString(){
		return "Employee: "+this.name;
	}
}