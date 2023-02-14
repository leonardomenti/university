import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        NestedCalls nc_h = new MyHandler();
        nc_h.a();
    }
}
