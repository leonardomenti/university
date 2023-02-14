public class C {

    public String called() {return "C";}

    @call(calledClass = "C", method = "c")
    public String c() {return called();}

    @call(calledClass = "B", method = "b")
    public String b() {return new B().called();}

    @call(calledClass = "A", method = "a")
    public String a() {return new A().called();}
}
