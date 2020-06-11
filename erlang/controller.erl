-module(controller).
-export([start/1, init/1, loop/1]).

start(N) -> register(server, spawn(controller, init, [N])).

init(N) -> 	Numbers = create_num_list(2,N+1,[]),
			Primes = [X || X<-Numbers, isPrime(X,X-1), X/=2],
			Pid = spawn(sieve, create, [2,Primes]),
			put('2',Pid),
			register(two,Pid),
			io:format("I'm controller (~p) -> my neighbour is 2 (~p)~n",[self(),Pid]),
			Max = max(Primes),
			loop(Max).

max([H|[]]) -> H;
max([_|TL]) -> max(TL).

loop(Max) -> receive
				{new, N, From} -> 	
									io:format("You ~p asked for: ~p~n",[From,N]),
									M = math:pow(Max,2),
									if (N < M) ->
										two ! {new,N},
											receive
												{res, R} -> if (R == 0) -> B=false;
															true -> B = true
															end,
															Result = lists:flatten(io_lib:format("is ~p prime? ~p",[N,B])), 
															From ! {result, Result}, 
															loop(Max)
											end;
									true -> Result = lists:flatten(io_lib:format("~p is uncheckable, too big value.",[N])),
											From ! {result, Result},
											loop(Max)
									end;
				{quit, From} -> io:format("Hey ~p, I'm closing~n",[From]), From ! {result, "The service is closed!!!"}
			end.


create_num_list(Last,Last,L) -> L;
create_num_list(First, Last, L) -> L2 = lists:append(L,[First]), create_num_list(First+1,Last,L2).

isPrime(0,_) -> false;
isPrime(1,_) -> false;
isPrime(_N,1) -> true;
isPrime(N,M) -> R = N rem M,
				if R == 0 -> false;
				true -> isPrime(N,M-1)
				end.