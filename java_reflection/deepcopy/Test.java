public class Test {
    public static void main(String[] args) throws Throwable{ 
        A a = new A(7);
        A b = DeepCopy.copy(a);
        
        
        if (a.equals(b))
            System.out.println("a is equal to b");
        else
            System.out.println("a is not equal to b"); 
        if (a == b)
            System.out.println("a is b");
        else
            System.out.println("a is not b");
        
} }
