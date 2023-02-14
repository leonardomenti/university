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
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        String finalTargetName = this.target.replaceAll("\\.", "/");
        if (!className.equals(finalTargetName))
            return byteCode;
        else{
            ClassPool cp = ClassPool.getDefault();
            try {
                System.out.println("Agent Cl: " + getClass().getClassLoader());
                CtClass cc = cp.get(target);
                CtMethod cm_append = cc.getDeclaredMethod("append", new CtClass[]{cp.get("char[]")});
                cm_append.addLocalVariable("start", CtClass.longType);
                cm_append.addLocalVariable("end", CtClass.longType);
                cm_append.insertBefore("start = System.currentTimeMillis();");
                cm_append.insertAfter("end = System.currentTimeMillis();");
                cm_append.insertAfter("System.out.println(\"The method " + cm_append.getName() + " lasted \" +(end - start)+ \" milliseconds\");");
                byteCode = cc.toBytecode();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return byteCode;
        }
    }
}
