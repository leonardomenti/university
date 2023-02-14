import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        //MyClassLoader cl = new MyClassLoader(Main.class.getClassLoader());

        Person leo = new Person("leo");
        String s = "ciao";
        BigInteger bg = new BigInteger("100");

        System.out.println("[Main]");

        //System.out.println(cl.getSystemCount());
        //System.out.println(cl.getUserCount());
    }
}
