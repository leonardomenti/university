import com.sun.security.jgss.GSSUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InvokeUnknownMethod {
    public static void main(String[] args){
        try {
            Class<?> cls = Class.forName(args[0]);
            String mtdName = args[1];
            Arrays.stream(cls.getMethods()).forEach( m -> {
                    if (m.getName().equals(mtdName) && m.getParameterCount() == args.length-2) {

                        Class<?> argsType = m.getParameterTypes()[0];
                        //System.out.println(argsType);
                        String[] arguments = Arrays.copyOfRange(args, 2, args.length);
                        boolean integers = Arrays.stream(arguments).allMatch(a -> {
                            try {
                                Integer.parseInt(a);
                            } catch (Exception e) {
                                return false;
                            }
                            return true;
                        });

                        boolean doubles = Arrays.stream(arguments).allMatch(a -> {
                            try {
                                Double.parseDouble(a);
                            } catch (Exception e) {
                                return false;
                            }
                            return true;
                        });

                        if (integers)
                            doubles = false;

                        //System.out.println("integers? " + integers + "\n");
                        //System.out.println("doubles? " + doubles + "\n");

                        if (integers && argsType.getName().equals("int")) {
                            try {
                                int res = (int) m.invoke(new Calculator(), Arrays.stream(arguments).map(Integer::parseInt).toArray(Integer[]::new));
                                System.out.println(m.getName() + "(int): " + res);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                        if (doubles && argsType.getName().equals("double")) {
                            try {
                                double res = (double) m.invoke(new Calculator(), Arrays.stream(arguments).map(Double::parseDouble).toArray(Double[]::new));
                                System.out.println(m.getName() + "(double): " + res);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
