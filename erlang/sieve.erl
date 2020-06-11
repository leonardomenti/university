-module(sieve).
-export([create/2,loop/2]).


create(N,[]) -> P = whereis(two), 
				case P of
					undefined -> io:format("sieve2 undefined~n",[]);
					Pid -> 	put('2',Pid), 
							io:format("I'm sieve~p (~p) -> my neighbour is ~p(~p)~n",[N,self(),2,Pid]),
							loop(N,Pid)
				end;
create(N,[H|TL]) ->	Pid = spawn(sieve, create, [H,TL]),
					Num = lists:flatten(io_lib:format("~p",[H])),
					put(list_to_atom(Num),Pid),
					io:format("I'm sieve~p (~p) -> my neighbour is ~p(~p)~n",[N,self(),H,Pid]),
					loop(N,Pid).

loop(Num,Pid) -> 	receive
						{new, N} -> R = N rem Num, 
									if R == 0 -> two ! {res, 0};
									true -> Pid ! {pass, N}
									end,loop(Num,Pid);
						{pass, _} when Num==2 -> server ! {res, 1},loop(Num,Pid);
						{pass, N} -> R = N rem Num, 
									if R == 0 -> two ! {res, 0};
									true -> Pid ! {pass, N}
									end,loop(Num,Pid);
						{res, 0} -> server ! {res, 0},loop(Num,Pid)		
					end.