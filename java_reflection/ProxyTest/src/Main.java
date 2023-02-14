import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Person leo = new Person("leo");
        MyHandler handler = new MyHandler(leo);
        IPerson leo_proxy = (IPerson) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[]{IPerson.class},
                handler);
        leo_proxy.printName();
    }
}
