import javassist.*;
import javassist.bytecode.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Field;
import java.security.ProtectionDomain;

public class MyAgent {
    public static void premain(String agentArgs, Instrumentation i) throws ClassNotFoundException, UnmodifiableClassException {
        String className = "DynamicCache";
        MyTransformer mt = new MyTransformer(className);
        i.addTransformer(mt);
        i.retransformClasses(Class.forName(className));
    }

    private static class MyTransformer implements ClassFileTransformer {
        private String target;

        public MyTransformer(String className) {
            this.target = className;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            byte[] bytecode = classfileBuffer;
            String finalTarget = this.target.replaceAll("\\.", "/");
            if (!finalTarget.equals(className))
                return bytecode;
            else {
                ClassPool cp = ClassPool.getDefault();

                // Class.forName(target);
                // Usando anche reflection mi lancia un eccezione
                // java.lang.LinkageError: loader 'app' attempted duplicate class definition for DynamicCache.
                // (DynamicCache is in unnamed module of loader 'app')

                CtClass cc = null;
                try {
                     cc = cp.get(target);
                }
                catch (NotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    CtField target_field = cc.getDeclaredField("cache");
                } catch (NotFoundException e) {
                    System.out.println("Cache non trovata");
                    try {

                        CtField cf = new CtField(cp.get("java.util.HashMap"), "cache", cc);
                        cf.setModifiers(Modifier.STATIC);
                        CtField.Initializer init = CtField.Initializer.byNew(cp.get("java.util.HashMap"));
                        cc.addField(cf, init);
                        CtMethod cm = cc.getDeclaredMethod("isPrime");

                        cm.insertBefore("if (cache.containsKey($1)) return ($r) cache.get($1);");
                        cm.insertAfter("cache.put($1, $_);");


                        bytecode = cc.toBytecode();
                    } catch (CannotCompileException | NotFoundException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
                return bytecode;
            }
        }
    }
}
