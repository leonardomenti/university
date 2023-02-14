import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;

import java.io.*;

public class MyClassLoader extends ClassLoader{

    public MyClassLoader(String name, ClassLoader parent) {
        super(name, parent);
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String file = name.replace('.', File.separatorChar) + ".class";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(file);
        int size = 0;
        try {
            size = stream.available();
            byte[] buff = new byte[size];
            DataInputStream in = new DataInputStream(stream);
            in.readFully(buff);
            in.close();
            return defineClass(name, buff, 0, buff.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println(name);
        if (name.equals("Methods") || name.equals("Main")) {
            return findClass(name);
            /*
            try {
                System.out.println("###1 " + MyClassLoader.class.getClassLoader());
                Class cls = ClassPool.getDefault().get(name).toClass();
                System.out.println("###2 " + cls.getClassLoader());
                return cls;
             */
        }
        else
            return super.loadClass(name);
    }
}
