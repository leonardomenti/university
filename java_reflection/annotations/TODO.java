import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface TODO{
	String value();
	String assignedTo();
}