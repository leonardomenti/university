import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class MyHandler implements InvocationHandler {

    Object[] parents;

    public MyHandler(Class<?> ... parents) {
        this.parents = Arrays.stream(parents).map(p -> {
            try {
                return p.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                return null;
            }
        }).filter(Objects::nonNull).toArray(Object[]::new);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = Arrays.stream(parents).filter(p ->
                Arrays.stream(p.getClass().getDeclaredMethods()).anyMatch(m -> {
                if (m.getParameterCount()!=method.getParameterCount())
                    return false;
                Class<?>[] orginalParams = method.getParameterTypes();
                Class<?>[] targetParams = m.getParameterTypes();
                for (int i=0; i<m.getParameterCount(); i++) {
                    if (!orginalParams[i].getName().equals(targetParams[i].getName()))
                        return false;
                }
                return m.getName().equals(method.getName());
            })
        ).findFirst().get();
        return method.invoke(target, args);
    }
}
