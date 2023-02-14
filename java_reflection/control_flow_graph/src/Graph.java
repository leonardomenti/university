import java.util.Arrays;

public class Graph {
    public static void main(String[] args) {
        Arrays.stream(args).forEach(c -> {
            try {
                Class<?> cls = Class.forName(c);
                Arrays.stream(cls.getDeclaredMethods()).forEach(m -> {
                    if (m.isAnnotationPresent(call.class)){
                        call current_call = m.getAnnotation(call.class);
                        printAnnotation(c, current_call);
                    }
                    if (m.isAnnotationPresent(calls.class)) {
                        calls current_calls = m.getAnnotation(calls.class);
                        Arrays.stream(current_calls.calls()).forEach(current_call -> {
                            printAnnotation(c, current_call);
                        });
                    }
                });
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static void printAnnotation(String caller, call annotation) {
        System.out.println(caller + " -> " + annotation.method() + "() -> " + annotation.calledClass());
    }
}

