import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Locale;

public class MyMain {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder x = new StringBuilder();
        char[] toAppend = new char[100000];
        Arrays.fill(toAppend, '0');
        x.append(toAppend);
        x.insert(x.length(), String.valueOf(toAppend));
    }
}
