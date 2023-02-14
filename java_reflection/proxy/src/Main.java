import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        TestingFields tf = new TestingFields(7, 1.);
        MyHandler handler = new MyHandler(tf);
        ITestingFields itf = (ITestingFields) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[]{ITestingFields.class},
                handler
        );
        itf.message();
        itf.setAnswer(100);
        itf.message();
    }
}
