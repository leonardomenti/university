public aspect Caching {

	private ResourceManager rm = new ResourceManager();

	pointcut constr(): call(Resource+.new());
	pointcut destroy(Resource r): call(void Resource+.destroy()) && target(r);

	Object around(): constr(){
		String type = thisJoinPoint.getSignature().getDeclaringType().toString();
		Resource r;
		if ( (r = rm.getResource(type)) != null){
			System.out.println("[Aspect] Getting resource " + type);
			return r;
		}
		else{
			r = (Resource) proceed();
			rm.putResource(type, r);
			System.out.println("[Aspect] Putting resource " + type);
			return r;
		}
	}

	before(Resource r): destroy(r){
		String type = r.getClass().toString();
		System.out.println("[Aspect] Destroying resource " + type);
		rm.releaseResource(type, r);
	}
}