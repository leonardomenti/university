import javassist.*;

public class PreMain {
    public static void main(String[] args) throws NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("Main");
        CtField newField = new CtField(cp.get("MyClass"), "myClass", cc);
        newField.setModifiers(Modifier.STATIC);
        cc.addField(newField);

        CtMethod cm = cc.getDeclaredMethod("main");
        cm.insertBefore("myClass = new MyClass(1, \"ciao\");");
        cm.insertAfter("System.out.println(myClass);");
        cc.toClass();
        Main.main(new String[]{});
    }
}
