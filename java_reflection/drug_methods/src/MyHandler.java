import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Random;

public class MyHandler extends Methods implements InvocationHandler {

    private Object baseObject;

    public MyHandler(Object baseObject) {
        this.baseObject = baseObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method[] mths = baseObject.getClass().getDeclaredMethods();
        int rand = new Random().nextInt(mths.length);
        while (mths[rand].getName().equals(method.getName())) {
            rand = new Random().nextInt(mths.length);
        }
        Object res = mths[rand].invoke(baseObject, args);
        return res;
    }
}
