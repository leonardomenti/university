import equazioni.gramm._

object Main {
	def main(args: Array[String]) {
		println("""Please input the expressions. Type "q" to quit.""")
	    	var input: String = ""

		val p = new MyParser()
	 
	    	do {
	      		input = readLine("> ")
	      		if (input != "q") {
					p.parseAll(p.expr,input) match{
						case p.Success(x,_) => println(x)
						case f => println(f)
					}
					//EquazioniParserCombinator.readExpression(input).foreach(f => println(f()))
	      		}
	    	} while (input != "q")
  	}
}
