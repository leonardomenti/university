import javassist.*;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class SBAgent {
    public static void premain(String agentArgs, Instrumentation i) throws ClassNotFoundException, UnmodifiableClassException {
        String className = "java.lang.StringBuilder";
        SBTransformer sbTransformer = new SBTransformer(className);
        i.addTransformer(sbTransformer, true);
        i.retransformClasses(Class.forName(className));
    }
}
