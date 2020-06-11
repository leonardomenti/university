package primaryschool.gramm

import scala.util.parsing.combinator._

class PrimarySchoolParser extends JavaTokenParsers{
	
	var res = new Operation(0,"+")
	var max = 0
	var fin = 0
	var set = false
	
	def adds_subs= {
		term ~ op ^^ {
			case t~o => {
				o match{
					case "+" | "-" => res = new Operation(res.eval(t),o)
					case "=" => fin = res.eval(t); res = new Operation(0,"+")
					
				}
			}
		} | line | result
	}	
	
	def op = "+" | "-" | "="
	
	def result =  wholeNumber ^^ {
		case s => {
			if(set){
				val x = s.toInt
				val Z = fin
				fin = 0
				set = false
				x match{
					case Z => Tuple2("OK",Z)
					case _ => Tuple2("ERR",-1)
				}
			}
		}
	} 
	
	def line = """[-]+""".r ^^ {
		case l => {
			set = true
			//print("Max: "+max)
			//println("   Lunghezza: "+l.length())
			val q = max
			max = 0
			if(l.length()!=q+2)
				Tuple2("NO",-1) 
		}
	}
	
	def term = wholeNumber ^^ {
		case s =>{	
			if(!set){
				max = max_length(s) 
				s.toInt
			}
			else
				-1
		}
	}
	
	def max_length(num:String):Int = {
		val x = num.length()
		//print("Max corrente: "+max)
		//println("   Length(): "+x)
		if(x>max) x
		else max
	} 
}

case class Operation(tot:Int,op:String){
	def eval(n:Int) ={
		op match{
			case "+" => tot+n
			case "-" => tot-n
		}
	}
}
