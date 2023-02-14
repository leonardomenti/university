import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        try {
            TestingFields tf = new TestingFields(7, 3.14);

            Class<?> cls = Class.forName("TestingFields");
            for (Field field: cls.getDeclaredFields()){
                field.setAccessible(true);
                if (!field.getType().isArray())
                    System.out.println(field.get(tf));
                else{
                    //Arrays.stream(new Object[]{field.get(tf)}).forEach(System.out::println);
                    // non capisco perchè inizializzando un nuovo array object non funziona
                    Arrays.stream((Object[]) field.get(tf)).forEach(System.out::println);
                }
            }
            Field string2change = cls.getDeclaredField("s");
            Field.setAccessible(new AccessibleObject[]{string2change}, true);
            string2change.set(tf, "changed!!!");
            System.out.println(string2change.get(tf));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
