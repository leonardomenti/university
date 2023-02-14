import java.lang.reflect.*;

public interface ReflectiveCloning {

	default public Object copy() throws Exception {
		// new istance in questo caso si riferisce ad un costruttore senza argomenti, quindi la classe (this) che userà questo metodo
		// dovrà avere un costruttore di questo tipo, altrimenti sarà lanciata un eccezione NoSuchMethodException Person.init()
		Object tmp = this.getClass().getDeclaredConstructor().newInstance();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			fields[i].set(tmp, fields[i].get(this));
		}
		return tmp;
	}
}