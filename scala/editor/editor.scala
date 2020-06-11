class Editor(input:String, in:Int){

	var line = input
	var length = line.length()
	var index = in
	
	def this(input:String) = this(input,0)

	def show() = println(line.substring(0,index+1)+"|"+line.substring(index+1))

	def x():Unit = if(index>0) {
		line = line.substring(0,index)+line.substring(index+1)
		length = line.length()
		index-=1
	}

	def dw():Unit = while(line.charAt(index) != ' ') this.x()

	def i(c:Char) = {
		line = line.substring(0,index+1)+c+line.substring(index+1)
		length = line.length()
		index+=1
	}

	def iw(s:String) = {
		line = line.substring(0,index+1)+s+" "+line.substring(index+1)
		length = line.length()
		index+=s.length()+1
	}
	
	def l(n:Int):Unit = {
		val x = index+n
		if(x>length-1) 
			index = length-1
		else
			index = x

	}

	def l():Unit = this.l(1)

	def h(n:Int):Unit = this.l(-n)

	def h():Unit = this.h(1)
	
}

object Editor{
	def apply(input:String, in:Int) = new Editor(input,in)
	def apply(input:String) = new Editor(input)
}