import javassist.*;

public class PreMain{
	public static void main(String[] args) throws Throwable{

		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("NestedCalls");

		try{
			CtField cf = cc.getField("$$_isModified_$$");
		} catch (NotFoundException nfe){
			
			cc.addField(CtField.make("private int counter = 0;", cc));

			CtMethod[] cms = cc.getDeclaredMethods();
			for(CtMethod cm: cms){
				cm.insertBefore("System.out.println(\"#\".repeat(counter) + \" \" \"" + cm.getName() + "\");");
				cm.insertBefore("counter++;");
				cm.insertAfter("counter--;");
			}
			cc.addField(CtField.make("private boolean $$_isModified_$$ = true;", cc));
			cc.writeFile();
		}	
	}
}