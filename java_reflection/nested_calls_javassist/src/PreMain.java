import javassist.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PreMain {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("NestedCalls");
        CtField counter = CtField.make("private int counter = 0;", cc);
        cc.addField(counter);
        Arrays.stream(cc.getDeclaredMethods()).forEach(m -> {
            try {
                m.insertBefore("Counter.getInstance().printCounter(++counter, \"" + m.getName() + "\");");
                m.insertAfter("Counter.getInstance().printCounter(counter--, \"" + m.getName() + "\");");
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        });
        cc.toClass();
        Main.main(new String[]{});
/*
        MyClassLoader mcl = new MyClassLoader(PreMain.class.getClassLoader().getParent());
        mcl.loadClass("NestedCalls");
        Class<?> mainClass = mcl.loadClass("Main");
        Method mth = mainClass.getDeclaredMethod("main", String[].class);
        mth.invoke(null, (Object) new String[]{});
 */
    }
/*
    public static class MyClassLoader extends ClassLoader {
        ClassPool cp;

        public MyClassLoader(ClassLoader parent) {
            super(parent);
            this.cp = ClassPool.getDefault();
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            CtClass cc = null;
            try {
                cc = cp.get(name);
                if (name.equals("NestedCalls")) {
                    CtField counter = CtField.make("private int counter = 0;", cc);
                    cc.addField(counter);
                    Arrays.stream(cc.getDeclaredMethods()).forEach(m -> {
                        try {
                            m.insertBefore("Counter.getInstance().printCounter(++counter, \"" + m.getName() + "\");");
                            m.insertAfter("Counter.getInstance().printCounter(counter--, \"" + m.getName() + "\");");
                        } catch (CannotCompileException e) {
                            e.printStackTrace();
                        }
                    });
                }
                return cc.toClass();
            } catch (NotFoundException | CannotCompileException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
*/
}
