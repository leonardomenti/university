import java.util.*;

public class Main {
    public static void main(String[] args){
        Random rand = new Random();
        boolean b = rand.nextBoolean();
        System.out.println(b);
        if(b){
            System.out.println("Creating privileged context");
            Context c = new Context(true);
            c.foo();
        } else {
            System.out.println("Creating unprivileged context");
            Context c = new Context(false);
            c.foo();
        }
    }

    public static void lol(){
        System.out.println("Lol");
        new B().bar();
    }

}