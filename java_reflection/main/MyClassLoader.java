import javassist.*;
import java.lang.reflect.*;

public class MyClassLoader extends ClassLoader{

	private ClassPool cp;

	public MyClassLoader(ClassPool  cp){
		this.cp = cp;
	}

	@Override
	public Class loadClass(String name) throws ClassNotFoundException{
		if (name == "Main")
			return findClass(name);
		else
			return super.loadClass(name);

	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException{
		CtClass cc = null;
		byte[] bytecode = null;
		try{
			cc = cp.get(name);
			bytecode = cc.toBytecode();
		} catch(Exception e){
			e.printStackTrace();
		}
		return defineClass(name, bytecode, 0, bytecode.length);
	}
}