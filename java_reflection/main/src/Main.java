import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws NotFoundException, ClassNotFoundException, CannotCompileException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        System.out.println("Main 0");

        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("Main");
        CtMethod cm = cc.getDeclaredMethod("main", new CtClass[]{cp.get("java.lang.String[]")});

        String[] newArgs = null;
        if (args.length == 0) {
            cm.insertBefore("System.out.println(\"Main 1\");");
            newArgs = new String[]{"ciao"};
        }
        if (args.length == 1) {
            cc.defrost();
            cm.insertBefore("System.out.println(\"Main 2\");");
            newArgs = new String[]{"ciao", "ciao"};
        }
        if (args.length < 2) {
            //cc.writeFile();

            MyClassLoader mcl = new MyClassLoader(cc);
            Class<?> cls = mcl.loadClass("Main");
            Method m = cls.getDeclaredMethod("main", args.getClass());
            m.invoke(null, (Object) newArgs);
        }
    }
}
