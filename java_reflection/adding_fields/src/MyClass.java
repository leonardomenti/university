public class MyClass {
    public int x;
    private String y;

    public MyClass(int x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getY() {
        return y;
    }

    @Override
    public String toString() {
        return "MyClass: " + x + "   " + y;
    }
}
