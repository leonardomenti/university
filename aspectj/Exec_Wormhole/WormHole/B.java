public class B {
    private Context c;

    public void bar() {
        if(c.isPrivileged()){
            System.out.println("Bar");
        } else {
            System.out.println("Unprivileged");
        }
    }
}