import javassist.*;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public class PreMain {
    static String json_str = "{\n" +
            "  \"type\": \"MyClass\",\n" +
            "  \"attrs\": {\n" +
            "    \"a\": 1,\n" +
            "    \"b\": 2,\n" +
            "    \"c\": 3\n" +
            "  },\n" +
            "  \"methods\": {\n" +
            "    \"toString\": \"a=1, b=2, c=3\",\n" +
            "    \"second\": 3\n" +
            "  }\n" +
            "}";
    static JSONObject json = new JSONObject(json_str);

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NotFoundException, CannotCompileException {

        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("MyClass");

        JSONObject methods = (JSONObject) json.get("methods");
        Iterator<String> keys = methods.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            Object obj = methods.get(key);
            String body = "return " + obj + ";";
            try {
                CtMethod cm = cc.getDeclaredMethod(key);
                cm.insertBefore(body);
            } catch (NotFoundException nfe) {
                CtClass returnType = getReturnType(obj, cp);
                if (returnType.getName().equals("java.lang.String"))
                    body = "return \"" + obj + "\";";
                if (returnType != null) {
                    CtMethod newMethod = new CtMethod(returnType, key, new CtClass[]{}, cc);
                    newMethod.setBody(body);
                    cc.addMethod(newMethod);
                }
            }
        }
        cc.toClass();

        CtClass cmain = cp.get("MyMain");
        CtMethod main = cmain.getDeclaredMethod("main");

        main.addLocalVariable("mc", cc);

        main.insertBefore("mc = new MyClass();");
        
        main.insertAfter("PreMain.modifyFields(mc);");
        main.insertAfter("System.out.println(mc.toString());");
        main.insertAfter("System.out.println(mc.first());");
        main.insertAfter("System.out.println(mc.second());");

        cmain.toClass();

        MyMain.main(new String[]{});
    }

    private static CtClass getReturnType(Object obj, ClassPool cp) {
        try {
            Integer i = (Integer) obj;
            return cp.get("Integer");
        } catch (Exception e) {
            try {
                String x = (String) obj;
                return cp.get("java.lang.String");
            } catch (Exception e1) {
                return null;
            }
        }

    }

    public static void modifyFields(Object obj) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        JSONObject attrs = (JSONObject) json.get("attrs");
        Iterator<String> keys1 = attrs.keys();
        while(keys1.hasNext()) {
            String key = keys1.next();
            Field f = obj.getClass().getDeclaredField(key);
            f.setAccessible(true);
            f.set(obj, attrs.get(key));
        }
    }
}
