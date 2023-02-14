import java.lang.reflect.Field;
import java.util.Arrays;

public class A {

    private String x;
    private int n;
    private static double d = 3.14;
    private String[] S;
    B b;

    public A(String x, int n, String[] S, B b) {
        this.x = x;
        this.n = n;
        this.S = S;
        this.b = b;
    }

    public A() {}

    public A deepCopy() {
        A a2 = new A ();
        Class cls = this.getClass();
        Arrays.stream(cls.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                field.set(a2, field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return a2;
    }

    public static void main(String[] args) {
        A a1 = new A("test", 10, new String[]{"a1", "a2"}, new B(1));
        A a2 = new A();
        System.out.println("Before deep copy");
        A finalA2_before = a2;
        Arrays.stream(a2.getClass().getDeclaredFields()).forEach(field -> {
            try {
                System.out.println(field.get(finalA2_before));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        a2 = a1.deepCopy();
        System.out.println("After deep copy");
        A finalA1 = a2;
        Arrays.stream(a2.getClass().getDeclaredFields()).forEach(field -> {
            try {
                System.out.println(field.get(finalA1));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
