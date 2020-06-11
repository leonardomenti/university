module GoldBach = struct
	
	exception NotPossible

	let prime n =
	    let rec checkZero x d = match d with
	        | 1 -> true    
	        | _ -> (x mod d <> 0) && checkZero x (d-1)
	    in match n with
	    | 0 | 1 -> false
	    | _ -> checkZero n (n-1) ;;

	let goldbach n = 
		if n>3 then (
			let rec goldbach1 n x y =
				let rec goldbach2 n x y =
					if (y>=n) then goldbach1 n (x+1) 2
					else(
						if(prime x && prime y) then(
							let ris = x+y in
							if (ris=n) then (x,y) else goldbach2 n x (y+1)
						)
						else goldbach2 n x (y+1)
					)
				in goldbach2 n x y 
			in goldbach1 n 2 2
		)
		else raise NotPossible

	let goldbach_list n m = 
		let rec goldbach_list n m i ris =	
			if(i>m) then ris else (goldbach_list n m (i+1) (ris@[goldbach i]))
		in goldbach_list n m n []
		
			
	
end