import java.io.*;
import java.util.ArrayList;

public class Loader extends ClassLoader{

    int userCls = 0;
    int systemCls = 0;
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException{
        if (name.startsWith("java") && findLoadedClass(name)==null){
            systemCls++;
            return super.loadClass(name);
        }
        else{
            userCls++;
            return findClass(name);
        }
    }

    @Override
    protected Class<?> findClass(String name){

        String className = "/" + name.replace('.', ',') + ".class";
        int classSize = (int) new File(name).length();
        byte[] bytecode = new byte[classSize];
        try{
            FileInputStream fis = new FileInputStream(className);
            fis.read(bytecode);
            fis.close();
        }catch (Exception e){
            System.out.println("Cannot read file " + className);
        }
        return defineClass(name, bytecode, 0, bytecode.length);
    }
}
