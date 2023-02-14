aspect Nesting{

	pointcut int2int(int x):  
		args(x)
		&& call(int *..*.*(int)) 
		&& !within(Nesting);

	int around(int x): int2int(x) {
		System.out.println("[Aspect] Nesting aspect on method " + thisJoinPoint.getSignature().toShortString());
		return proceed(proceed(x));
	}
}