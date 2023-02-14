import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyHandler implements InvocationHandler {

    private Object baseObject;

    public MyHandler(Object baseObject) {
        this.baseObject = baseObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.printf("*** Instance state before calling '%s()' ***\n", method.getName());
        Arrays.stream(baseObject.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            try {
                Object obj = f.get(baseObject);
                System.out.print(f.getName() + ": ");
                if (obj.getClass().isArray()) {
                    Arrays.stream((Object[]) obj).forEach(n -> {
                        System.out.print(n + "   ");
                    });
                    System.out.println();
                }
                else
                    System.out.println(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        System.out.println("___________________________");
        return method.invoke(baseObject, args);
    }
}
