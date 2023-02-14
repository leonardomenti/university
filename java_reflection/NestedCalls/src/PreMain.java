import javassist.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class PreMain {
    public static void main(String[] args) throws Throwable {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("NestedCalls");

        cc.addField(CtField.make("private int counter = 0;", cc));
        Arrays.stream(cc.getDeclaredMethods())
                .filter(m -> !m.getName().equals("main"))
                .forEach(cm -> {
                    try {
                        instrument(cm);
                    } catch (CannotCompileException cce) {
                        cce.printStackTrace();
                    }
                });
        Loader l = new Loader(cp);
        l.run("NestedCalls", new String[]{});

    }

    private static void instrument(CtMethod cm) throws CannotCompileException {
        cm.insertBefore("System.out.println(\"#\".repeat(++counter) + \" \"\"" + cm.getName() + "\");");
        cm.insertAfter("counter--;");
    }
}
