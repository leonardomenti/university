import primaryschool.gramm._

object Main{
	def main(args:Array[String]):Unit={
		val p = new PrimarySchoolParser()
		println("""Please input the expressions. Type "q" to quit.""")
		var input: String = ""
		
		var res = 0
		
		while(input!="q"){
			input = readLine("> ")
			p.parseAll(p.adds_subs, input) match{
				case p.Success(("OK",x:Int),_) => println("Risultato corretto: "+x)
				case p.Success(("NO",_),_) => println("Formato errato")
				case p.Success(("ERR",_),_) => println("Risultato errato")
				case p.Success(_,_) => Unit
				case f => println(f)
			}
		}
	}
}
