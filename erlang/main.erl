-module(main).
-export([start_server/1, is_prime_client/1]).

start_server(N) -> controller:start(N).

is_prime_client(N) -> client:is_prime(N).