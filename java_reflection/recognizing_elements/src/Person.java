public class Person {

    public String name;
    public String surname;
    private int age;
    private double salary;

    public Person(String name, String surname, int age, double salary) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.salary = salary;
    }

    public int getAge() {
        return this.age;
    }

    public double calcAnnaualSalary() {
        return salary*12.0;
    }

    private void setName(String name) {
        this.name = name;
    }



}
