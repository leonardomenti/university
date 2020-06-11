-module(permutations).
-export([start/2, loop/3, combinator/2, generator/6, create_slaves/3]).

start(N,M) -> register(master, spawn(permutations, combinator, [N,M])).

combinator(N,M) -> 	process_flag(trap_exit, true),
					create_slaves(N,M,N-1),
					loop(N,M,N-1).

create_slaves(_,_,-1) -> void; 
create_slaves(N,M,C) -> P = trunc(math:pow(M,C)), Tot = trunc(math:pow(M,N)),
						Pid = spawn(permutations, generator, [M,P,P,Tot,[],1]),
						Num = lists:flatten(io_lib:format("~p",[C])),
						Slave = "generator"++Num,
						register(list_to_atom(Slave), Pid),
						io:format("~p created.~n",[Slave]),
						create_slaves(N,M,C-1).

loop(0,M,N1) -> 	create_table(M,N1);
loop(K,M,N1) -> 	receive
						{C, L} -> put(C,L),loop(K-1,M,N1) 
					end.

create_table(_,-1) -> io:format("Finished.~n",[]);
create_table(M,N1) -> 	P = trunc(math:pow(M,N1)),
						L = get(P),
						io:format("~p~n",[L]),
						create_table(M,N1-1).

generator(_,_,C,0,L,_) ->  master ! {C,L};
generator(M,0,C,Tot,L,Num) -> Num2 = (Num+1) rem M,
							case Num2 of
								0 -> generator(M,C,C,Tot,L,M);
								Other -> generator(M,C,C,Tot,L,Other)
							end;						
generator(M,P,C,Tot,L,Num) -> 	L2 = lists:append(L,[Num]),
								generator(M,P-1,C,Tot-1,L2,Num).
