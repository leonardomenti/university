public class Context {
    private boolean privileged = false;

    public Context(boolean priv){
        this.privileged = priv;
    }

    public boolean isPrivileged(){
        return privileged;
    }

    public void foo(){
        System.out.println("Foo");
        Main.lol();
    }
}