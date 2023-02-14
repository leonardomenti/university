import java.lang.reflect.Proxy;
import java.util.HashMap;

public class DynamicCache {

    public static void main(String[] args) {

        int x = 100000;

        long start = System.currentTimeMillis();
        for (Long i = Long.valueOf(0); i<x; i++)
            isPrime(i);
        long end = System.currentTimeMillis();
        System.out.println(end-start);

        long start2 = System.currentTimeMillis();
        for (Long i = Long.valueOf(0); i<x; i++)
            isPrime(i);
        long end2 = System.currentTimeMillis();
        System.out.println(end2-start2);
    }

    static Boolean isPrime(Long n) {
        Boolean res = true;
        if (n <= 1) {
            res = false;
            return res;
        }

        for (int i = 2; i < n; i++)
            if (n % i == 0) {
                res = false;
                return res;
            }
        return res;
    }
}
