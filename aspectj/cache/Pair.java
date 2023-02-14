public class Pair{

	private String keyString;
	private Object[] params;

	public Pair(String keyString, Object[] params){
		this.keyString = keyString;
		this.params = params;
	}

	public String print(){
		StringBuilder sb = new StringBuilder();
		for(Object obj: params)
			sb.append(obj+", ");
		return "("+keyString+";"+ sb+")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Pair){
			Pair p2 = (Pair) o; 
			return this.print().equals(p2.print());
		}
		return false;
		
	}

	@Override
	public int hashCode(){
		return keyString.length()+params.length;
	}
}