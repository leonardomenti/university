package desk.gramm

import scala.util.parsing.combinator._

class DeskParser extends JavaTokenParsers{

	var variables = Map[String,Int]()
	var res = 0
	
	def programm:Parser[Int] = "print" ~> expression <~ "where" <~ inits ^^ {
		case items => calc(items)
	}
	
	def expression:Parser[List[Any]] = repsep(term, "+") ^^ {case l => l}
	
	def term = 	variable ^^ {case v => v}| 
				wholeNumber ^^ {_.toInt}
	
	def variable = """[A-Za-z]+""".r ^^ {case v => v}
	
	def inits:Parser[List[Unit]] = repsep(init, ",")
	
	def init:Parser[Unit] = ("""[A-Za-z]+""".r <~ "=") ~ wholeNumber ^^ {
		case v~n => variables = variables++Map(v->n.toInt)	
	}
	
	def calc(l:List[Any]):Int = {
		l match{
			case h::tl =>	if (h.isInstanceOf[String]){
								val h1 = h.asInstanceOf[String]
								if (variables contains h1)
									res = res+variables(h1)
								else 
									throw new VariableNotFound("variabile "+h+" non definita")
							}
							else
								res = res + h.asInstanceOf[Int]
							calc(tl)
			case _ => res
		}
	}
}

class VariableNotFound(s:String) extends Exception(s) {
	def this() = this(null:String)
}
