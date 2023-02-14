import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent {
    public static void premain(String argName, Instrumentation i) {
        MyTransformer mt = new MyTransformer(new MyFakeClass());
        i.addTransformer(mt);
    }

    public static class MyTransformer implements ClassFileTransformer {
        public MyTransformer(MyFakeClass myFakeClass) {
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            return ClassFileTransformer.super.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        }
    }
}
