import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyHandler extends NestedCalls implements InvocationHandler {

    private NestedCallsI proxy;
    private int counter;

    public MyHandler() {
        this.proxy = (NestedCallsI) Proxy.newProxyInstance(
                MyHandler.class.getClassLoader(),
                new Class[]{NestedCallsI.class},
                this);
        this.counter = 0;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method superMethod = NestedCalls.class.getMethod(method.getName(), method.getParameterTypes());
        MethodHandle h = MethodHandles.lookup().unreflectSpecial(superMethod, getClass());
        System.out.println("#".repeat(++counter) + " " + method.getName() + "()");
        Object res = h.bindTo(this).invokeWithArguments(args);
        counter--;
        return res;
    }

    @Override
    public int a() {
        return proxy.a();
    }

    @Override
    public int b(int a) {
        return proxy.b(a);
    }

    @Override
    public int c(int a) {
        return proxy.c(a);
    }
}
