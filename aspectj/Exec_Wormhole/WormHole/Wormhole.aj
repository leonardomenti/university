import java.lang.reflect.*;

aspect Wormhole percflow(contextCall(Context)) {
    private Context cont;
    
    pointcut contextCall(Context c): execution(* Context.*(..)) && this(c);
    pointcut bCall(B b): execution(* *..*(..)) && this(b) && cflowbelow(contextCall(Context));

    before(Context c) : contextCall(c) {
        System.out.println("[Aspect] Called method " + thisJoinPoint.getSignature().toLongString());
        if(c != null)
            cont = c;
    }

    before(B b): bCall(b){
        try {
            Field f = B.class.getDeclaredField("c");
            f.setAccessible(true);
            f.set(b,cont);
        } catch(Exception e){e.printStackTrace();}
    }


}