class DebEditor(s:String,i:Int) extends Editor(s,i) with Debug{

	override def show() = {
		super.show()
		println("COMMANDS")
		commands.foreach(println(_))
	}

	override def x() = {
		super.x()
		this.add_command("x")
	}

	override def dw() ={
		super.dw()
		this.add_command("dw")
	}

	override def i(c:Char) = {
		super.i(c)
		this.add_command("i: "+c)
	}

	override def iw(s:String) = {
		super.iw(s)
		this.add_command("iw: "+s)
	}

	override def l(n:Int) = {
		super.l(n)
		this.add_command("l: "+n)
	}

	override def l() = {
		super.l()
		this.add_command("l")
	}

	override def h(n:Int) = {
		super.h(n)
		this.add_command("h: "+n)
	}

	override def h() = {
		super.h()
		this.add_command("h")
	}

}