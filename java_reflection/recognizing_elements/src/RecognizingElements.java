import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class RecognizingElements {
    public static void main(String[] args) {

        String[] classNames = {"Person", "Animal"};
        String[] fieldsMethodsNames = {
                "name",
                "surname",
                "age",
                "salary",
                "getAge",
                "calcAnnaualSalary",
                "setName",
                "name",
                "type",
                "age",
                "setAge",
                "getAge",
                "toString",
        };

        boolean arePresent = Arrays.stream(fieldsMethodsNames).allMatch(x ->
                Arrays.stream(classNames).anyMatch(clsName -> {
                try {
                    Class<?> cls = Class.forName(clsName);
                    AtomicInteger mod = new AtomicInteger();
                    AtomicReference<String> retTypeName = new AtomicReference<>();
                    boolean isMethod = Arrays.stream(cls.getDeclaredMethods()).anyMatch(m -> {
                        boolean exists = x.equals(m.getName());
                        if (exists) {
                            mod.set(m.getModifiers());
                            retTypeName.set(m.getReturnType().getName());
                        }
                        return exists;
                    });

                    if (!isMethod){
                        Field f = cls.getDeclaredField(x);
                        mod.set(f.getModifiers());
                    }
                    System.out.printf("2) " +
                            (Modifier.isPrivate(mod.get())?"private ":"public ") +
                            (isMethod? retTypeName : "") +
                            " %s.%s" +
                            (isMethod? "()" : "") + "\n", clsName, x);
                    return true;
                } catch (ClassNotFoundException | NoSuchFieldException e) {
                    return false;
                }})
        );

        if (arePresent)
            System.out.println("\n1) All the methods and fields and present in the classes");
        else
            System.out.println("\n1) Not all the stringss are present");
    }
}
