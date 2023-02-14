import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        IParent target = (IParent) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[]{IParent.class},
                new MyHandler(Parent1.class, Parent2.class)
        );

        target.methdodParent1();
        target.methodParent1(1);
        target.methdodParent2();
        target.methodParent1("ciao");
        target.methodParent1("ciao", "ciao");
    }
}
