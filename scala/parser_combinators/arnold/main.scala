import arnold.gramm._
import scala.io.Source

object ArnoldEvaluator{
	def main(args:Array[String]):Unit = {
		val filename = "print-10.arnoldc"
		var str = ""
		for(c <- Source.fromFile(filename)){
			str+=c	
		}
		println(str)
		val p = new ArnoldParser()
		
		p.parseAll(p.prog,str) match{
			case p.Success(x,_) => println("OK")
			case f => println(f.toString)
		}
		
	}
}
