module type Graph =
  functor (Queue : Queue.Queue) ->
    
	sig
		type person = string
		type relationship = Friendship |Kinship |FinancialExchange |Dislike |SexualRelationship |RelationshipOfBeliefs | 
							RelationshipOfKnowledge | RelationshipOfPrestige
		type node = ((person*person)*relationship)
		type graph = {mutable g:node list ; mutable p: person list}

		val empty: unit -> graph
		val is_empty: graph -> bool
		(* val add_arc: person -> person -> relationship -> graph -> unit *)
		val add_relation: person -> person -> relationship -> graph -> unit
		(* val is_in_graph: node -> graph -> bool *)
		val already_in: person -> graph -> bool 
		val add_node: node -> graph -> unit
		val print_graph: graph -> unit

	end