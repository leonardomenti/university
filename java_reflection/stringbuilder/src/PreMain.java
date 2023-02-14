import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class PreMain {

    public static void premain(String args, Instrumentation i) throws UnmodifiableClassException, ClassNotFoundException {
        String className = "java.lang.StringBuilder";
        Class<?> cls = Class.forName(className);
        i.addTransformer(new MyTransformer(className), true);
        i.retransformClasses(cls);
    }

    static class MyTransformer implements ClassFileTransformer{
        private String target;
        public MyTransformer(String className) {
            this.target = className.replaceAll("\\.", "/");
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            byte[] byteCode = classfileBuffer;
            if (target.equals(className)) {
                System.out.println(className);
                String finalTarget = target.replaceAll("/", "\\.");
                ClassPool cp = ClassPool.getDefault();
                try {
                    CtClass cc = cp.get(finalTarget);
                    CtMethod cm_append = cc.getDeclaredMethod("append", new CtClass[]{cp.get("char[]")});
                    CtMethod cm_insert = cc.getDeclaredMethod("insert", new CtClass[]{CtClass.intType, cp.get("java.lang.String")});
                    instrumentMethod(cm_append);
                    instrumentMethod(cm_insert);

                    byteCode = cc.toBytecode();
                } catch (NotFoundException | CannotCompileException | IOException e) {
                    e.printStackTrace();
                }
            }
            return byteCode;
        }
    }

    private static void instrumentMethod(CtMethod cm) throws CannotCompileException, NotFoundException {
        cm.addLocalVariable("start", CtClass.longType);
        cm.addLocalVariable("end", CtClass.longType);
        cm.insertBefore("start = System.nanoTime();");
        cm.insertAfter("end = System.nanoTime();");
        cm.insertAfter("System.out.println(\"The method " + cm.getName() + " lasts \" + (end - start) + \" nanoseconds\");");
    }
}
