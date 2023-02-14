import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

public class MyClassLoader extends ClassLoader{
    private CtClass cc;

    public MyClassLoader(CtClass cc) {
        this.cc = cc;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.equals("Main")) {
            try {
                //byte[] byteCode = ClassPool.getDefault().get(name).toBytecode();
                byte[] byteCode = cc.toBytecode();
                return defineClass("Main", byteCode, 0, byteCode.length);
            } catch (CannotCompileException | IOException e){
                return null;
            }
        }
        else
            return super.loadClass(name);
    }
}
