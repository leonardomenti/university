import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyHandler extends NestedCalls implements InvocationHandler {

    private int level = 0;
    private INestedCalls proxy;

    public MyHandler() {
        proxy = (INestedCalls) Proxy.newProxyInstance(
                MyHandler.class.getClassLoader(),
                new Class[]{INestedCalls.class},
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method superm = NestedCalls.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
        level++;
        System.out.println("#".repeat(level) + " " + method.getName());
        final MethodHandle h = MethodHandles.lookup().unreflectSpecial(superm, super.getClass());
        Object res = h.bindTo(this).invokeWithArguments(args);
        level--;
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

    @Override
    public int d() {
        return proxy.d();
    }
}