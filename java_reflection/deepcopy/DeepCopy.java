import java.lang.reflect.*;

public class DeepCopy{
	public static <T> T copy(T obj) throws Throwable{

		Class cls = obj.getClass();
		T res = (T) cls.newInstance();

		Field[] fields = cls.getDeclaredFields();
		Field.setAccessible(fields, true);
		for(Field f: fields){
			if (f.getType().isPrimitive()){
				f.set(res, f.get(obj));
			}
			else{
				f.set(res, DeepCopy.copy(f.get(obj)));
			}
		}
		return res;
	}
}