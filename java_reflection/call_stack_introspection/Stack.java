import java.lang.reflect.*;

public class Stack{
	public void a(){
		b();
	}
	public void b(){
		c();
	}
	public void c(){
		for(StackTraceElement f: new Throwable().getStackTrace()) 
			System.out.println(f);
	}

	public static void main(String[] args){
		new Stack().a();
	}
}