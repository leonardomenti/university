import java.lang.reflect.Proxy;

public class MyProxy {
    public static IMethods createProxy(IMethods im) {
        return (IMethods) Proxy.newProxyInstance(
                MyProxy.class.getClassLoader(),
                new Class[]{IMethods.class},
                new MyHandler(im)
        );
    }
}
