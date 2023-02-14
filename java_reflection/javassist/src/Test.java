import javassist.*;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("NestedCalls");

        try {
            CtField cf = cc.getDeclaredField("$$_isModified_$$");
        } catch (NotFoundException nfe) {
            cc.addField(CtField.make("private int counter = 0;", cc));
            cc.addField(CtField.make("private boolean $$_isModified_$$ = true;", cc));

            for (CtMethod cm: cc.getDeclaredMethods()) {
                cm.insertBefore("System.out.println(\"#\".repeat(++counter) + \" \" + \"" + cm.getName() + "()\");");
                cm.insertAfter("System.out.println(\"#\".repeat(counter--) + \" \" + \"" + cm.getName() + "()\");");
            }
            cc.writeFile();
        }
    }
}
