open Queue
module MyGraph (Queue : Queue.Queue) = 

struct

	type person = string;;
	type relationship = Friendship |Kinship |FinancialExchange |Dislike |SexualRelationship |RelationshipOfBeliefs | 
						RelationshipOfKnowledge | RelationshipOfPrestige
	type node = ((person*person)*relationship)
	type graph = {mutable g: node list ; mutable p: (person*bool) list}

	let empty () = {g=[];p=[]};;

	let is_empty = function gr -> gr.g=[]

	let add_arc s1 s2 rel gr = gr.g <- [((s1,s2),rel)]@gr.g;;

	let add_relation s1 s2 rel g = add_arc s1 s2 rel g; add_arc s2 s1 rel g;;

	let rec is_in_graph n g = 
		match g with
		h::tl -> if (fst h = fst n) then true 
				else is_in_graph n tl
		|_ -> false 

	let rec already_in p gr = List.exists (fun x -> (fst x)=p) gr.p

	let add_persons p1 p2 gr = 	let first= (already_in p1 gr) in 
								let sec = (already_in p2 gr) in
								match first,sec with
								| false,false -> gr.p <- gr.p@[(p1,false)]@[(p2,false)]
								| false,true -> gr.p <- gr.p@[(p1,false)]
								| true,false -> gr.p <- gr.p@[(p2,false)]
								| true,true -> gr.p <- gr.p

	let add_node n g =	 add_relation (fst (fst n)) (snd (fst n)) (snd n) g; 
						let p1= fst (fst n) in
						let p2 = snd (fst n) in
						add_persons p1 p2 g ;;

	let print_node n = print_string ((fst (fst n)^"-"^(snd (fst n)))^"\n")

	let print_graph gr = List.iter (fun n -> print_node n) gr.g;;

	let init_person_list gr = 
		let rec fill l acc=
		match l with
			| h::tl -> fill tl (acc@[(fst h,false)])
		 	| _ -> acc
		in fill gr.p []

	let rec mark p g =
		match g with
		| h::tl -> if( (fst h)=p && (snd h)=false) then ([(p,true)]@tl) else ([h]@(mark p tl))
		| _ -> []

	let rec is_mark p l= 
		match l with
			| h::tl -> if( (fst h)=p && (snd h)=false) then true else is_mark p tl
			| _ -> false

	let vicini u gr=
		let rec foreach u fsList aux ris =
			match fsList with 
				| h::tl -> 	let x = fst (fst h) in
							let y = snd (fst h) in
							if(x=u && (is_mark y aux)=false) then foreach u tl (mark y aux) (ris@[y]) else foreach u tl aux ris
				| _ -> ris
		in foreach u gr.g gr.p []
(*
	let aux_updates u gr =
		let rec foreach u fsList aux =
			match fsList with 
				| h::tl -> 	let x = fst (fst h) in
							let y = snd (fst h) in
							if(x=u && (is_mark y aux)=false) then foreach u tl (mark y aux) else foreach u tl aux 
				| _ -> aux
		in foreach u gr.g gr.p

	let queue_update u gr = 
		let rec foreach u fsList aux q_ris =
			match fsList with 
				| h::tl -> 	let x = fst (fst h) in
							let y = snd (fst h) in
							if(x=u && (is_mark y aux)=false) then foreach u tl (mark y aux) (Queue.enqueue q_ris y) else foreach u tl aux q_ris
				| _ -> q_ris
		in foreach u gr.g gr.p Queue.Empty

	
	let dfs p gr =
		let () = gr.p <- init_person_list gr in
		let queue = Queue.empty() in
		let () = gr.p <- mark p gr.p in
		let queue = Queue.enqueue p queue in 
		let rec visit q l aux gr=
			match q with
				| Queue.Element(x,_) ->	let q = Queue.dequeue q in 
										visit (queue_update x gr) (vicini x gr) (aux_updates x gr) gr
				| Queue.Empty -> l
		in visit queue [p] gr.p gr

*)
end

open MyQueue
module G = MyGraph(MyQueue);;

