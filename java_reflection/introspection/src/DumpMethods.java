import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class DumpMethods {
    public static void main(String[] args) {
        try {
            Class cls = Class.forName(args[0]);
            System.out.println("Printing all methods of class: " + cls.getName());
            for (Method m: cls.getDeclaredMethods()){
                String mod = "";
                mod = Modifier.isPublic(m.getModifiers()) ? "public" : mod;
                mod = Modifier.isPrivate(m.getModifiers()) ? "private" : mod;
                System.out.println(mod + " " + m.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
