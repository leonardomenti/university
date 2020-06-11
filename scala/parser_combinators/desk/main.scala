import desk.gramm._
import scala.io.Source

object DeskEvaluator{
	def main(args:Array[String]):Unit = {
		val filename = args(0)
		val line = Source.fromFile(filename).getLines
		var str = ""
		for(c<-line) str+=c

		val p = new DeskParser()
		
		p.parseAll(p.programm, str) match{
			case p.Success(x,_) => println("Result: "+x)
			case f => println(f.toString)
		}
		
	}
}
