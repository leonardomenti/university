trait Debug{

	var commands = List[String]()

	def add_command(s:String) = {
		commands = commands:::List(s)
	}

}