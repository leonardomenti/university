public class Person implements SmartFieldAccess, ReflectiveCloning, SmartMessageSending{
    private String name;
    private int age;

    public Person(){
        this.name = "";
        this.age = 0;
    }
    public Person(String name){
        this.name = name;
    }
    
    public int getAge(){
        return this.age;
    }
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    private int pvt(){
        return 1;
    }

    public int test_exeption(int n) throws ArithmeticException, ArrayIndexOutOfBoundsException{
        return 1;
    }
}