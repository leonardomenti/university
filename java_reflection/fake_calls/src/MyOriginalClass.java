public class MyOriginalClass implements IMyClass{
    @Override
    public void a() {
        System.out.println("Original a");
    }

    @Override
    public String b() {
        return ("Original b");
    }
}
