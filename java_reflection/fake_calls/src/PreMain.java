import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.reflect.Method;
import java.util.Arrays;

public class PreMain {
    public static void main(String[] args) throws NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("MyOriginalClass");
        CtConstructor ccr_original = cc.getDeclaredConstructors()[0];
        /*
        Arrays.stream(cc.getDeclaredMethods()).forEach(m -> {
            try {
                m.insertBefore("return new MyFakeClass()." + m.getName() + "();");
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        });


        CtClass cc_fake = cp.get("MyFakeClass");
        Arrays.stream(cc.getDeclaredMethods()).forEach(m -> m.setName("old_" + m.getName()));

        String fake1_body = "public void a() { return new MyFakeClass().a();}";
        String fake2_body = "public String b() { return new MyFakeClass().b();}";
        CtMethod fake1 = CtMethod.make(fake1_body, cc);
        CtMethod fake2 = CtMethod.make(fake2_body, cc);
        cc.addMethod(fake1);
        cc.addMethod(fake2);
         */

        CtClass cc_fake = cp.get("MyFakeClass");
        CtClass main = cp.get("Main");
        CodeConverter conv = new CodeConverter();
        conv.replaceNew(cc, cc_fake);
        main.instrument(conv);

        // cc.toClass();
        main.toClass();
        Main.main(new String[]{});
    }
}
