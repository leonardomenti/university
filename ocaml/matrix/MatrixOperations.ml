open MatrixOperationsADT
module MatrixOperations:MatrixOperationsADT = 

struct
	type matrixList = int array array
	type matrix = {mutable m: matrixList}
	exception ImpossibleOperation

	let size x = ( (Array.length x),(Array.length x.(0) ))

	let equals a b = if (size a.m)=(size b.m) then (a.m=b.m) else raise ImpossibleOperation

	let copy a b =  if (size a.m)=(size b.m) then (b.m <- a.m) else raise ImpossibleOperation

	let addition a b c =
		if (size a.m)=(size b.m) then (
			let x = a.m in 
			let y = b.m in
			let rec add x y i j r c=

				
	
		)
		else raise ImpossibleOperation


end