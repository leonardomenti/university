public class Person{

	private int age;
	private String name;

	public Person(int age, String name){
		this.age = age;
		this.name = name;
	}

	public int getAge(){
		return this.age;
	}

	public void setAge(int n){
		age = n;
	}
}