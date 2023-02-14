import java.util.*;

public aspect caching{
	
	private HashMap <Pair,Object> cache = new HashMap <>();

	Object around(): call(* Main.*(..)) {
		String keyString = thisJoinPoint.getSignature().toShortString();
		Object[] keyargs = thisJoinPoint.getArgs();
		Pair key = new Pair (keyString, keyargs);

		if (cache.get(key) == null){
			Object res =  proceed();
			cache.put(key, res);
		}
		else
			System.out.print("[Cached]:	");
		return cache.get(key);
	}
}