-module(client).
-export([is_prime/1, send/1, close/0]).

is_prime(N) -> send(N).

send(N) -> 	{server, sif@leo} ! {new, N, self()},
			wait_response().

close() -> {server, sif@leo} ! {quit, self()},
			wait_response().

wait_response() -> 	receive
						{result, R} -> R
					end.