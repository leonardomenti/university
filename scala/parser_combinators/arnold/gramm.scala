package arnold.gramm
import scala.util.parsing.combinator._

class ArnoldParser extends JavaTokenParsers{

	var variables = Map[String,Int]()
	
	def prog = "IT'S SHOWTIME" ~> opt(body) <~ "YOU HAVE BEEN TERMINATED"
	
	def body:Parser[Any] = 	print ~ opt(body) | 
				declaration ~ opt(body) | 
				assignement ~ opt(body) |
				conditional ~ opt(body) |
				loop ~ opt(body)
	
	def print = "TALK TO THE HAND" ~> """[A-Za-z0-9]+""".r  ^^ {
		case v => {
			if(variables contains v)
				println(v+": "+variables(v))
			else
				throw new VariableNotFound("variabile "+v+" inesistente")
		}
	}| "TALK TO THE HAND" ~> wholeNumber ^^ {case x => println(x.toInt)}
				
	def declaration = "HEY CHRISTMAS TREE" ~> ("""[A-Za-z0-9]+""".r <~ "YOU SET US UP") ~ wholeNumber ^^ {
		case v~n => variables = variables++Map(v->n.toInt)
	}
	
	def assignement = "GET TO THE CHOPPER" ~> ("[A-Za-z0-9]+".r <~ "HERE IS MY INVITATION") ~ operand ~ rep(operations) <~ "ENOUGH TALK" ^^ {
		case a~op~items => variables = variables++Map(a->calc(op,items))
	}
	
	def calc(res:Int, items:List[Map[String,Int]]):Int = {
		items match{
			case h::tl =>{
				val r = eval(res,h)
				calc(r,tl)
			}
			case _ => res
		}
	}
	
	def eval(p:Int, map:Map[String,Int]):Int = {
		var x = p
		var y = ""
		var z = 0
		for((k:String,v:Int) <- map){
			y = k
			z = v
		}
		(x,y,z) match{
			case (x,"+",o) => x+o
			case (x,"-",o) => x-o
			case (x,"*",o) => x*o
			case (x,"/",o) => x/o
			case (z,"==",y) if(z==y) => 1
			case (_,"==",_) => 0
			case (x,">",o) => {
				var r = 0
				if(x>o) r=1
				r
			}
			case (0,"||",1) | (1,"||",0) | (1,"||",1) => 1
			case (0,"||",0) => 0
			case (_,"||",_) => throw new ImpossibleOperation("parametri || errati")
			case (0,"&&",1) | (1,"||",0) | (0,"||",0) => 0
			case (1,"&&",1) => 1
			case (_,"&&",_) => throw new ImpossibleOperation("parametri && errati")
			case _ => throw new ImpossibleOperation("operazione inesistente")
		}	 
	}
	
	def operand:Parser[Int] = 	wholeNumber ^^ {_.toInt}| 
					"[A-Za-z0-9]+".r ^^ {
						case v =>{
							if(variables contains v)
								variables(v)
							else
								throw new VariableNotFound("variabile "+v+" inesistente")
						}
					}
 	
	def operations = plus | minus | mul | div | equals | gt | or | and
	
	def plus = "GET UP " ~> operand ^^ {case o => Map("+"->o)}
	def minus = "GET DOWN" ~> operand ^^ {case o => Map("-"->o)}
	def mul = "YOU'RE FIRED" ~> operand ^^ {case o => Map("*"->o)}
	def div = "HE HAD TO SPLIT" ~> operand ^^ {case o => Map("/"->o)}
	def equals = "YOU ARE NOT YOU YOU ARE ME" ~> operand ^^ {case o => Map("=="->o)}
	def gt = "LEFT OFF SOME STREAM BENNET" ~> operand ^^ {case o => Map(">"->o)}
	def or = "CONSIDER THAT A DIVORCE" ~> operand ^^ {case o => Map("||"->o)}
	def and = "KNOCK KNOCK" ~> operand ^^ {case o => Map("&&"->o)}
	
	def conditional = {
		"BECAUSE I'M GOING TO SAY PLEASE" ~> (operand <~ "[") ~ (stm <~ "] BULLSHIT [") ~ (stm <~ "] YOU HAVE NO RESPECT FOR LOGIC") ^^ {
			case 1~s1~s2 => my_parse(s1)
			case 0~s1~s2 => my_parse(s2)
			case _~_~_ => throw new ImpossibleOperation("parametri if-else errati")
		}
	} 
	
	def stm = """[A-Z0-9a-z\n ]+""".r ^^ {case s=>s}
	
	def loop:Parser[Unit]= "STICK AROUND" ~ """[A-Z0-9a-z]+""".r ~ "[" ~ stm ~ "] CHILL" ^^ {
		case "STICK AROUND"~v~"["~s~"] CHILL" =>{
			if(variables contains v){
				if(variables(v)==1){
					my_parse(s)
					my_loop("STICK AROUND "+v+" ["+s+"] CHILL")
				}
			}
		} 
	}
	
	def my_parse(stm:String):Unit = {
		this.parseAll(body,stm) match{
			case this.Success(_,_) => Unit
			case f => println(f.toString)
		}
	}
	
	def my_loop(stm:String) = {
		this.parseAll(loop,stm) match{
			case this.Success(_,_) => Unit
			case f => println(f.toString)
		}
	}
	
}

class VariableNotFound(msg:String) extends Exception(msg){
	def this() = this(null:String)
}

class ImpossibleOperation(msg:String) extends Exception(msg){
	def this() = this(null:String)
}
