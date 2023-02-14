import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        int x = 100000;
        DynamicCacheP dcp = new DynamicCacheP();
        IDynamicCache dcp_th = (IDynamicCache) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[]{IDynamicCache.class},
                new MyHandler(dcp)
        );

        long start = System.currentTimeMillis();
        for (int i=0;i<x;i++)
            dcp_th.isPrime((long) i);
        long end = System.currentTimeMillis();
        System.out.println(end-start);

        long start1 = System.currentTimeMillis();
        for (int i=0;i<x;i++)
            dcp_th.isPrime((long) i);
        long end1 = System.currentTimeMillis();
        System.out.println(end1-start1);
    }
}
