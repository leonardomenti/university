import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // con una semplice writeFile in PreMain questo non funziona
        // la prima volta funziona e la writeFile modifica il .class
        // la seconda volta la ClassPool prende il bytecode prima modificato
        // quando faccio cc.addField(counter) il programma crasha perchè è gia presente nel bytecode

        // PreMain.main(new String[]{});
        new NestedCalls().a();
    }
}
