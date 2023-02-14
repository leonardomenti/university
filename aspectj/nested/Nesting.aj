public aspect Nesting{

	private int NestedCalls.counter = 0;

	int around(NestedCalls nc): call(* NestedCalls.*(..)) && target(nc){
		nc.counter++;
		System.out.println("#".repeat(nc.counter));
		int res = proceed(nc);
		nc.counter--;
		return res;
	}
}