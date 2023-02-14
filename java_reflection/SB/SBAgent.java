import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class SBAgent {
    public static void premain(String agentArgs, Instrumentation i) throws ClassNotFoundException, UnmodifiableClassException {
        String className = "java.lang.StringBuilder";
        SBTransformer sbt = new SBTransformer(className);
        i.addTransformer(sbt, true);
        i.retransformClasses(Class.forName(className));
    }
}
