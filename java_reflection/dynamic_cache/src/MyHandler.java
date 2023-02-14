import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MyHandler implements InvocationHandler {

    DynamicCacheP baseObject;
    HashMap<Long, Boolean> cache;

    public MyHandler(DynamicCacheP dcp) {
        baseObject = dcp;
        cache = new HashMap<Long, Boolean>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (cache.containsKey(args[0]))
            return cache.get(args[0]);
        else {
            Object res = method.invoke(baseObject, args);
            cache.put((Long) args[0], (Boolean) res);
            return res;
        }
    }
}
