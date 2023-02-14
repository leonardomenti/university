import javassist.*;
import java.lang.reflect.*;

public class Main{
	public static void main(String[] args) throws Throwable{
		
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("Main");
		CtMethod cm = cc.getDeclaredMethod("main");
		ClassLoader cl = new MyClassLoader(cp);
		

		if (args.length < 2){
			Object[] objs =  null;
			if (args.length==0){
				System.out.println("I'm Main");
				System.out.println("Inserting mod 1");
				cm.insertAfter("System.out.println(\"I'm modified main 1\");");
				objs = new Object[]{new String[]{"ciao"}};
			}
			else{
				cc.defrost();
				System.out.println("Inserting mod 2");
				cm.insertAfter("System.out.println(\"I'm modified main 2\");");
				objs = new Object[]{new String[]{"ciao", "ciao"}};
			}
			System.out.println("inserted (maybe)");
			cc.writeFile();
			Class<?> newMain = cl.loadClass("Main");
			Method newmain = newMain.getDeclaredMethod("main", new Class<?>[]{args.getClass()});
			newmain.invoke(null, objs);
			System.out.println("Invoked");
		}
		System.out.println("End\n");	
	}
}