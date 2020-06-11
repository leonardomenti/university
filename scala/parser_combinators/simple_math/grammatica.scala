package equazioni.gramm

import scala.util.parsing.combinator._

class MyParser extends JavaTokenParsers{
	
	def expr:Parser[Int] ={
		(term <~"+") ~ expr ^^ {case l~r => l+r} |
		(term <~"-") ~ expr ^^ {case l~r => l-r} |
		term
	}
	
	def term:Parser[Int] = {
		(factor <~ "*") ~ term ^^ {case l~r => l*r} |
		(factor <~ "/") ~ term ^^ {case l~r => l/r} |
		factor
	}
	
	def factor:Parser[Int] = "("~> expr <~ ")" | """[0-9]+""".r ^^ {_.toInt}
}

/*
object EquazioniParserCombinator extends JavaTokenParsers{

	def readExpression(input: String) : Option[()=>Int] = {
		parseAll(expr, input) match {
			case Success(result, _) => Some(result)
		  	case other =>println(other)
		    None
		}
	}
	
	private def expr : Parser[()=>Int] = {
		(term<~"+")~expr ^^ { case l~r => () => l() + r() } |
		(term<~"-")~expr ^^ { case l~r => () => l() - r() } |
		term
	}
	 
	private def term : Parser[()=>Int] = {
		(factor<~"*")~term ^^ { case l~r => () => l() * r() } |
		(factor<~"/")~term ^^ { case l~r => () => l() / r() } |
		factor
	}
	 
	private def factor : Parser[()=>Int] = {
		"("~>expr<~")" |
		"\\d+".r ^^ { x => () => x.toInt } |
		failure("Expected a value")
	}
				
}
*/
