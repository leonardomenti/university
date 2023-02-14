public class MyFakeClass implements IMyClass{
    @Override
    public void a() {
        System.out.println("Fake a");
    }

    @Override
    public String b() {
        return "Fake b";
    }
}
