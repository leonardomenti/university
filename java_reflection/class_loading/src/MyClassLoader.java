import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader{

    private int userCount = 0;
    private int systemCount = 0;

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(
                name.replace('.', '/') + ".class");
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int next;
        try{
            while ((next=is.read()) != -1)
                byteStream.write(next);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buffer = byteStream.toByteArray();
        return defineClass(name, buffer, 0, buffer.length);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.print("Loading class: " + name);
        if (name.startsWith("java.")){
            System.out.println("   [system]");
            return getParent().loadClass(name);
        }
        else {
            userCount++;
            System.out.println("   [user]");
        }
        return findClass(name);
    }

    public int getUserCount() {
        return userCount;
    }

    public int getSystemCount() {
        return systemCount;
    }
}
