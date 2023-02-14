public class Main {
    public static void main(String[] args) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append(new char[1000000]);
        System.out.println(sb1);

        StringBuilder sb2 = new StringBuilder();
        sb2.insert(0,"a".repeat(10000000));
    }
}
