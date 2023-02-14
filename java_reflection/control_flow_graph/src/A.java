public class A {

    public String called() {return "A";}

    @call(calledClass = "A", method = "a")
    public String a() {return called();}

    @call(calledClass = "B", method = "b")
    public String b() {return new B().called();}

    @call(calledClass = "C", method = "c")
    public String c() {return new C().called();}

    @calls(calls = {
            @call(calledClass = "A", method = "ab"),
            @call(calledClass = "B", method = "ab")
    })
    public String ab() { return called() + new B().called();}
}
