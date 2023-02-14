public class Parent2 implements IParent2 {
    public void methdodParent2() {
        System.out.println("Method of parent 2");
    }

    public void methodParent1(String x) {
        System.out.println("Method of parent 2 with string");
    }

    @Override
    public void methodParent1(String x, String y) {
        System.out.println("Method of parent 2 with 2 strings");
    }
}
