-module(ring).
-export([start/1, send/2, init_master/1, create_ring/1, generate_messages/2, wait/1]).

start(N) -> 	register(master, spawn(ring,init_master,[N])),
				receive after 1000->true end.
				

send(_,0) -> 	io:format("-----------------------------------END----------------------------------~n",[]),
				master ! stop;
send(L,N) -> 	io:format("-----------------------------------LAP ~p----------------------------------~n",[N]),
				master ! L,
				receive after 1000->true end, 
				send(L,N-1).	

init_master(N) -> 	process_flag(trap_exit, true),
					create_ring(N).

create_ring(1) -> 	M = whereis(master),
					monitor(process,M),
					io:format("~p --> ~p~n",[self(),M]),
					wait(M);
create_ring(N) -> 	Pid = spawn(ring, create_ring, [N-1]),
					monitor(process,Pid),
					io:format("~p --> ~p~n",[self(),Pid]),
					wait(Pid).

wait(Pid) ->
	receive
		stop -> io:format("~p master dying~n", [self()]), receive after 500->true end;
		{'DOWN', _, process, _, _} -> io:format("~p slave dying~n", [self()]), receive after 1000->true end;
		{send, L} -> master ! L, wait(Pid);
		L -> 	io:format("Pid: ~p     Messages: ~p~n", [self(),L]),
				M = whereis(master),
				case Pid of
					M -> void, wait(Pid);
					P -> P ! L, wait(Pid)
				end
	end.

generate_messages(0,L) -> L;
generate_messages(M,L) ->	Num = lists:flatten(io_lib:format("~p", [M])),
							Message = "Message"++Num,
							L2 = L++[Message],
							generate_messages(M-1,L2).
