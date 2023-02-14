public class Animal {

    public String name;
    public String type;
    private int age;

    public Animal(int age) {
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "I'm an animal called " + name;
    }
}
