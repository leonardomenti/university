import java.util.*;
import java.lang.*;

public aspect counting {

	private HashMap <String,Integer> methods = new HashMap <>();
	private int count = 0;

	pointcut Ccount(): call(* MyClass.*(..))&& withincode(* MyClass.*(..));

	after(): Ccount(){
		count++;
		System.out.println(thisJoinPoint.getSignature().toShortString() + "called in ");
	}

	after(): execution(public static void Main.main(String[])){
		System.out.println("C count: " + count);
	}
}