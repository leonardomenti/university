import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyHandler implements InvocationHandler {

    private Object baseObject;

    public MyHandler(Object baseObject) {
        this.baseObject = baseObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before: " + method.getName());
        Object res = method.invoke(baseObject, args);
        System.out.println("after: " + method.getName());
        return res;
    }
}
