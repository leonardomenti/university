module type Queue = 
sig

	type 'a queue 
	exception QueueIsEmpty

	val empty: unit-> 'a queue
	val is_empty: 'a queue -> bool
	val first:	'a queue -> 'a
	val enqueue: 'a -> 'a queue -> 'a queue
	val dequeue:  'a queue -> 'a queue
end