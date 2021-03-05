## Start application
    $ clj --main cljs.main --compile hello-world.core --repl

### Attributes
```
 --main invokes a Clojure function -> cljs.main
 --compile compiles the namespace -> hello-world.core
 --repl launches REPL when compilation completes
```

### Starting simple web server
    $ clj -m cljs.main --serve

### Building project with target node (to use nodejs)
    $ clj -m cljs.main --target node --output-to main.js -c hello-world.core
    $ node main.js
    $ clj -m cljs.main --repl-env node

### Interacting with REPL
```
(require '[hello-world.core :as hello] :reload)
```

### React
    $ clj -m cljs.main -c hello-world.core -r


#### Referência
https://clojurescript.org/guides/quick-start