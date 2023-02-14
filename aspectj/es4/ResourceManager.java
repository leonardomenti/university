import java.util.*;

public class ResourceManager implements ResourcePool{

	private HashMap <String, List<Resource>> resources = new HashMap <>();

	public Resource getResource(String type){
		if (resources.get(type) != null) {
			return resources.get(type).get(0);
		}
		return null;
	}

  	public void releaseResource(String type, Resource r){
  		List<Resource> l;
  		if ((l = resources.get(type)) != null) {
  			l.remove(0);
  			//l.forEach(e -> System.out.print(e+";"));
  			if (l.size()==0)
  				resources.replace(type, null);
  			else
  				resources.replace(type, l);
		}
  	}

  	public void putResource(String type, Resource res){
  		List<Resource> l;
  		if ((l = resources.get(type)) != null)
  			l.add(0,res);
  		else
  			l = new ArrayList<Resource>();
  			l.add(res);
  		resources.put(type, l);
  	}
}