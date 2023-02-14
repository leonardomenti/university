public class Main {
    public static void main(String[] args) {
        IMyClass myclass = new MyOriginalClass();
        myclass.a();
        System.out.println(myclass.b());
    }
}
