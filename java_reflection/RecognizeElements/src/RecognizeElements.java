import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RecognizeElements {
    public static void main(String[] args) {

        int separator = new ArrayList<String>(List.of(args)).indexOf(",");
        String[] clsNames = Arrays.copyOfRange(args, 0, separator);

        Class[] cls = Arrays.stream(clsNames)
                .map(n -> {
                    try {
                        return Class.forName(n);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .toArray(Class[]::new);



        String[] fms = Arrays.copyOfRange(args, separator+1, args.length);
       /*
        IntStream.range(0, separator).forEach(i -> System.out.println(cls[i]));
        IntStream.range(separator+1, args.length).forEach(i -> System.out.println(fms[i - separator - 1]));
        */

        Arrays.stream(fms).forEach(fm -> searchDeclaration(fm, cls));
    }

    private static void searchDeclaration(String fm, Class[] cls) {
        AtomicBoolean found = new AtomicBoolean(false);
        Arrays.stream(cls).forEach(c -> {
            try {
                Field f = c.getDeclaredField(fm);
                System.out.println("Field " + f.toGenericString() + " found in " + c);
                found.set(true);
            } catch (NoSuchFieldException e) {
                try {
                    Method m = c.getDeclaredMethod(fm);
                    System.out.println("Method " + m.toGenericString() + " found in " + c);
                    found.set(true);
                } catch (NoSuchMethodException ex) {
                    return;
                }
            }
        });
        if (!found.get())
            System.out.println(fm + " not found in any class");
    }
}
