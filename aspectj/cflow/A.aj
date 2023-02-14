aspect A  {
    pointcut fooPC(): execution(void Test.foo());
    pointcut gooPC(): execution(void Test.goo());
    pointcut printPC(): call(void java.io.PrintStream.println(String));

    before(): cflow(fooPC()) && cflow(gooPC()) && printPC() && !within(A) {
        System.out.println("should occur");
    }
    
    before(): cflow(fooPC() && gooPC()) && printPC() && !within(A) {
        System.out.println("should not occur");
    } 
}