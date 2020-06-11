open Queue
module MyQueue:Queue = struct

	type 'a queue = Empty | Element of 'a * 'a queue
	exception QueueIsEmpty

	let empty () = Empty

	let is_empty q = q=Empty

	let first q = 
		match q with
			| Empty -> raise QueueIsEmpty
			| Element(f,tl) -> f

	let enqueue x q = Element (x,q)

	let dequeue q = 
		match q with
		| Empty -> raise QueueIsEmpty
		| Element(_,tl) -> tl

end