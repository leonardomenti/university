import java.lang.reflect.Method; 

public interface SmartMessageSending {
	default public Object receive(String selector, Object[] args) throws Exception { 
		Method mth = null;
		Class<?>[] classes = null;
		if (args != null) {
			classes = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++){
				classes[i] = args[i].getClass();
			}
		}
		mth = this.getClass().getMethod(selector, classes); // preleva il metodo con nome "selector" e con i parametri "classes"
		return mth.invoke(this, args);
	} 
}

// esempio con Person:	
// zack.receive("setName", new Object[]{"Cody"})

// args != null quindi entro nell if
// classes è un array di dimensione 1, ossia la lunghezza dell'array di Object passato come argomento
// classes[0] = java.lang.String
// mth = Person.setName(String name)
// viene invocato il metodo mth sull oggetto this (zack) passando come argomento args, ossia {"Cody"}