import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        //Test.main(new String[]{});
        //new NestedCalls().a();

        StringBuilder sb = new StringBuilder("test");
        sb.append(new char[10000000]);
        System.out.println("Main Cl: " + Main.class.getClassLoader());
    }
}
