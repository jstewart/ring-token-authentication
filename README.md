# ring-token-authentication

Ring middleware to authenticate requests against HTTP token authentication

## Installation

Add the following to your project's `:dependencies`:

    [ring-token-authentication "0.1.0"]


## Usage

The `wrap-token-authentication` middleware will secure any HTTP request against a function that accepts an access token.

    (use 'ring.middleware.token-authentication)

    (defn authenticated? [token]
       (= token "letmein"))

    (def app (-> routes
             ..
             (wrap-token-authentication authenticated?))


The headers of the request must contain the proper authorization header:

    Authorization: Token token=letmein

On authentication failure, the `:body` will be "unauthorized" and the `:status` will be 401.
To override this behavior, a map can be passed as a second argument to `wrap-token-authentication`:

    (wrap-token-authentication authenticated? {:body "you're in serious trouble now"})

## License

Copyright © 2014 Jason Stewart

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
