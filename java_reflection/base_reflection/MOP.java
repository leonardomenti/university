import java.util.*;
import java.util.stream.*;
import java.lang.reflect.*;

public class MOP{
	public static void main(String args[]){
		try {
		    Class c = Class.forName(args[0]);
		    Method m[] = c.getDeclaredMethods();

		    System.out.println("\n*** get declared methods ***");
		    for (int i = 0; i < m.length; i++)
		    	System.out.println(m[i].toString());
		    
		    Class<?>[] superClasses = getAllSuperClasses(c);
		    System.out.println("\n*** List of all super classes ***");
		    for (Class<?> obj: superClasses)
		    	System.out.println(obj);

		    // let's take one method of the class 
		    //var ms = Arrays.asList(m).stream().filter(s->s.getName()=="regionMatches").findFirst().get();
		    var ms = Arrays.asList(m).stream().filter(s->s.getName()=="test_exeption").findFirst().get();

		    System.out.println("\n*** expand a Class array into an arg list ***\n" + formalParametersToString(ms));

		    System.out.println("\n*** return the signature of another method ***\n" + headerSuffixToString(ms));

		    // Fields
		    System.out.println("\n*** Access to the fields ***\n");
		    Person mike = new Person("Mike");
		    Field name = Person.class.getDeclaredField("name"); 
		    name.setAccessible(true);
			System.out.println("Value of name: "+ name.get(mike));
			name.set(mike, "Eleonor");
			System.out.println("Changed value of name: " + name.get(mike));

			// Smart Reflective Access to Fields
			Person jake = new Person("jake");
			jake.instVarAtPut("name", "John");
			System.out.println("\n*** Smart Reflective Access to Fields ***\n" + jake.instVarAt("name"));

			Person mary = new Person("Mary");
			Object mary_clone = mary.copy();
			Person mary_clone_person = new Person("clone");
			if (mary_clone instanceof Person)
				mary_clone_person = (Person) mary_clone;
			System.out.println("\n*** Cloning a class ***\n" + mary_clone_person.getName());

			Person zack = new Person("Zack");
			System.out.println("\n*** Smart Message Sending ***\n" + zack.receive("getName", null));
			zack.receive("setName", new Object [] {"Cody"});
			System.out.println(zack.getName());

		}
		catch (Throwable e) {
			System.err.println(e);
		}
	}

	public static String classNameToString(Class<?> cls){
		if (!cls.isArray())
			return cls.getName();
		else 
			return cls.getComponentType().getName() + "[]";
	}

	public static String classArrayToString(Class<?> [] clsArray){
		String res = "";
		for (Class<?> cls: clsArray)
			res += classNameToString(cls) + ", ";
		return res.substring(0, res.length()-2);
	}

	// Let’s code a method to return super class hierarchy of a class
	public static Class<?>[] getAllSuperClasses(Class<?> cls) {
		List<Class<?>> result = new ArrayList<Class<?>>();
		for (Class<?> x = cls; x != null; x = x.getSuperclass()) 
			result.add(x);
		return result.toArray(Class<?>[]::new);
	}

	// Let’s code a method to expand a Class array into an arg list
	public static String formalParametersToString(Method m) {
		var mt = Arrays.asList(m.getParameterTypes());
		return IntStream.range(0, m.getParameterCount()).boxed()
		.map(i -> {
			return MOP.classNameToString(mt.get(i))+" p"+(i+1);
		}).collect(Collectors.joining(", "));
	}

	// Let’s code a method to return the signature of another method.
	public static String headerSuffixToString(Method m) {
		String result = MOP.classNameToString( m.getReturnType() ) + " " + m.getName() + "(" + MOP.formalParametersToString( m ) + ")";
		Class<?>[] exs = m.getExceptionTypes();
		if (exs.length > 0) 
			result += " throws " + MOP.classArrayToString(exs); 
		return result;
	}
}








