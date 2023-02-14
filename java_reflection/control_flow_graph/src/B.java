public class B {

    public String called() {return "B";}

    @call(calledClass = "B", method = "b")
    public String b() {return called();}

    @call(calledClass = "A", method = "a")
    public String a() {return new A().called();}

    @call(calledClass = "C", method = "c")
    public String c() {return new C().called();}
}
