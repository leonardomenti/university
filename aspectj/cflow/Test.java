public class Test {
    public static void main(String[] args) {
        foo(); 
    }
    static void foo() {
        goo();
    }
    static void goo() {
        System.out.println("hi");
    }
}
