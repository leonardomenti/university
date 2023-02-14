import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeepCopy {
    public static <T> T copy (T obj) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class cls = obj.getClass();
        T res = (T) cls.getConstructor().newInstance();
        Arrays.stream(cls.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            if (field.getType().isPrimitive()) {
                try {
                    field.set(res, field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    field.set(res, DeepCopy.copy(field.get(obj)));
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
        return res;
    }

    public static void main(String[] args) {
        System.out.println(isPrim(new Integer(19)));
    }

    public static void main1(String[] args) {
        A a1 = new A("ciao", 10, new String[]{"a1", "a2"}, new B(20));
        A a2 = new A();
        System.out.println("Before deep copy");
        A finalA2_before = a2;
        Arrays.stream(a2.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                Object f = field.get(finalA2_before);
                System.out.println("\t" + f);
                String toString = (f==null) ? null : printField(f);
                System.out.println(toString);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        a2 = a1.deepCopy();
        System.out.println("After deep copy");
        A finalA2_after = a2;
        Arrays.stream(a2.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                Object f = field.get(finalA2_after);
                String toString = (f==null) ? null : printField(f);
                System.out.println(toString);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static String printField(Object obj) throws IllegalAccessException {
        System.out.println("printField");
        System.out.println(obj.getClass().isPrimitive());
        if (obj.getClass().isPrimitive())
            return (String) obj;
        else{
            String res = obj.getClass().getName() + "\n";
            for (Field f: obj.getClass().getDeclaredFields()) {
                res += f.getName() + ": " + printField(f.get(obj)) + "\n";
            }
            return res;
        }
    }

    public static boolean isPrim(Object obj) {
        try {
            System.out.println(obj.getClass());
            int obj1 = (int) obj;
            System.out.println("integer");
            return true;
        } catch (RuntimeException re) {
            try {
                double obj1 = (double) obj;
                System.out.println("Double");
                return true;
            } catch (RuntimeException re1) {
                try {
                    boolean obj1 = (boolean) obj;
                    System.out.println("Boolean");
                    return true;
                } catch (RuntimeException re2) {
                    return false;
                }
            }
        }
    }
}
