module KWIC = 

struct
	
	type line = { mutable s:string ; line: int}
	type indexedLine = {mutable left: string ; mutable right:string ; line_number:int}

	let read_file filename =
			let ch = open_in filename in 
			let s = really_input_string ch (in_channel_length ch) in 
			close_in ch;
			s	
(*
	let create_list s = 
		let rec create_list l=
			match l with
			| h::k::tl when ()-> 
*)
	
	let remove_spaces record =
		let rec remove_spaces s i n ris iniz=
			if i<(n-1) then (
				let c = s.[i] in 
				let d = s.[(i+1)] in
				match (c,d) with
				| (' ',' ') -> remove_spaces s (i+1) n ris iniz
				| (' ', z) when(i<>0) -> if iniz then (remove_spaces s (i+1) n (ris^" ") iniz) else remove_spaces s (i+1) n ris iniz
				| (' ', z) when (i=0) -> remove_spaces s (i+1) n ris iniz
				| (x,y) -> remove_spaces s (i+1) n (ris^(String.make 1 x)) true
			)
			else (	let x=s.[i] in 
					if x=' ' then record.s <- ris 
					else record.s <- (ris^(String.make 1 x))
				)
		in remove_spaces record.s 0 (String.length record.s) "" false

	let create_record_list sl = 
		let rec create2 sl ris i=
			match sl with
			| h::tl -> create2 tl (ris@[{s=h;line=i}]) (i+1)
			| _ -> ris
		in create2 sl [] 1


	let create_list s = let first = Str.split (Str.regexp "[\n+]") s in
						let second = create_record_list first in 
						List.iter remove_spaces second; second

	let stringList_to_string sl =
		let rec stringList_to_string sl ris i= 
			match sl with
			| h::tl when i<>0 -> stringList_to_string tl (ris^" "^h) i
			| h::tl when i=0 -> stringList_to_string tl (ris^h) 1
			| _ -> ris
		in stringList_to_string sl "" 0

	let make_index_line line =
		let rec make_index_line sl ris dx= 
			match sl with
			| h::tl when (String.length h)>=3 -> make_index_line tl ( ris@[ {left=stringList_to_string sl ; right=dx ; line_number=line.line} ] ) (dx^" "^h)
			| h::tl when (String.length h)<3 -> make_index_line tl ris (dx^" "^h)
			| _ -> ris
		in make_index_line (Str.split (Str.regexp "[ +]") line.s) [] ""

	let make_index f =
		let rec make_index l ris =
			match l with
			| h::tl -> make_index tl (ris@(make_index_line h))
			| _ -> ris
		in make_index (create_list (read_file f)) []

	let make_spaces ver = 
		let rec make_spaces i ris= 
			match i with
			| 0 -> ris
			| _ -> make_spaces (i-1) ris^" "
		in make_spaces (20-String.length ver) ""

	let print_myver r = 	let n = make_spaces r.right in
							print_string (n^r.right^" "^(string_of_int r.line_number)^" "^r.left^"\n")
	
	let print_index_myver i = List.iter print_myver i

	let print r = 	let n = make_spaces r.right in
					let m = make_spaces r.left in
					print_string ("     "^(string_of_int r.line_number)^n^r.right^" "^r.left^m^".\n")
	
	let print_index i = List.iter print i
	


end