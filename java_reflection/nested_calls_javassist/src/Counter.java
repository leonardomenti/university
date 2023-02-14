public class Counter {
    private static Counter instance = null;

    public static Counter getInstance() {
        if (instance == null)
            instance = new Counter();
        return instance;
    }

    public static void printCounter(int n, String mthName) {
        System.out.println("#".repeat(n) + " " + mthName + "()");
    }
}
