import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class SBTransformer implements ClassFileTransformer {

    private String target;

    public SBTransformer(String target) {
        this.target = target;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String finalTargetName = target.replaceAll("\\.", "/");

        if (!className.equals(finalTargetName))
            return classfileBuffer;
        else{
            //System.out.println("[Agent] Modifying class " + finalTargetName);

            ClassPool cp = ClassPool.getDefault();
            try {
                CtClass cc = cp.get(target);
                CtMethod cm = cc.getDeclaredMethod("append", new CtClass[]{cp.get("char[]")});
                transformMethod(cm);
                cm = cc.getDeclaredMethod("insert", new CtClass[]{CtClass.intType, cp.get("java.lang.String")});
                transformMethod(cm);
            
                return cc.toBytecode();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }

    private void transformMethod(CtMethod cm) throws CannotCompileException {
        //System.out.println("[Agent] Modifying method " + cm);
        cm.addLocalVariable("start", CtClass.longType);
        cm.addLocalVariable("end", CtClass.longType);
        cm.insertBefore("start = System.currentTimeMillis();");
        cm.insertAfter("end = System.currentTimeMillis();");
        cm.insertAfter("System.out.println(\"The method " +cm.getName() + " lasted \" +(end - start)+ \" milliseconds\");");
    }

}
