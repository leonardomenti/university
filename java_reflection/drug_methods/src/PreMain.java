import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class PreMain {
    public static void main(String[] args) throws NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("Main");
        CtMethod cm = cc.getDeclaredMethod("main");

        cm.insertAt(4, "im = MyProxy.createProxy(im);");

        cc.toClass();
        Main.main(new String[]{});
    }
}
