module Coda = struct

	type 'a coda = 'a list
	exception Impossible

	let init ():'a coda = []

	let enqueue x c= c@[x]	

	let dequeue = function
		[] -> raise Impossible
		| h::tl -> h
end