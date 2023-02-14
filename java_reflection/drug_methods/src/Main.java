public class Main {
    public static void main(String[] args) {
        IMethods im = new Methods();
        System.out.println(im.getClass().getClassLoader());
        for(int i=0;i<5;i++){
            System.out.println("Round " + i);
            im.a();
            im.b();
            im.c();
            im.d();
            im.e();
            im.f();
            im.g();
            im.h();
            im.i();
            im.l();
            System.out.println("\n");
        }
    }
}
