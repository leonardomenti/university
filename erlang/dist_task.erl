-module(dist_task).
-export([start/2, init/5, send_message/1, send_message/2 ,slave/4]).

start(0,_) -> io:format("Input errato~n",[]);
start(_,[]) -> io:format("Input errato~n",[]);
start(N,[H|TL]) ->  Pid = spawn(dist_task, init, [N-1,H,TL,N+1,1]),
                    register(slave1, Pid),
                    link(Pid),
                    io:format("I'm shell(~p), just created slave1(~p)~n",[self(), Pid]).

send_message(N) -> slave1 ! {loops, 1, N}.

send_message(N,Loops) -> slave1 ! {loops, Loops, N}.

init(0,Fun,_,_,Id) -> P = whereis(slave1), io:format("I'm ~p, just linked to ~p~n",[self(),P]), slave(Fun,P,Id,0);
init(N,Fun,[H|TL],L,Id) ->  Pid = spawn(dist_task, init, [N-1,H,TL,L,Id+1]),
                            Slave = create_slave_name(N,L),
                            register(Slave, Pid),
                            link(Pid),
                            put(Slave,Pid),
                            io:format("I'm ~p(~p) with fun:~p, just created ~p(~p)~n",[Id,self(),Fun,Slave, Pid]),
                            slave(Fun,Pid,Id,0).
                    
create_slave_name(N,M) ->   Num = lists:flatten(io_lib:format("~p", [abs(N-M)])),
                            Str = "slave"++Num,
                            list_to_atom(Str).
            
slave(Fun,Neigh,Id,L) -> receive
                            {calc, N} ->    Res = Fun(N), 
                                            io:format("~p made ~p passing to ~p~n",[Id,Res,Neigh]), 
                                            Neigh ! {calc, Res, Id},
                                            slave(Fun,Neigh,Id,L-1);
                            {calc, N, _From} when (Id==1) and (L==0)  ->  io:format("Result: ~p~n",[N]),
                                                                        slave(Fun,Neigh,Id,L-1);
                            {calc, N, _From} -> Res = Fun(N), 
                                                io:format("~p made ~p passing to ~p~n",[Id,Res,Neigh]),
                                                Neigh ! {calc, Res, Id},
                                                slave(Fun,Neigh,Id,L-1);
                            {stop} -> exit(stop);
                            {loops, Loops, N} -> Neigh ! {loops, Loops, N, Id}, slave(Fun, Neigh, Id, Loops);
                            {loops, Loops, N, _From} when Id==1-> self() ! {calc, N}, slave(Fun, Neigh, Id, Loops);
                            {loops, Loops, N, _From} -> Neigh ! {loops, Loops, N, Id}, slave(Fun, Neigh, Id, Loops)
                        end.
