## Start application
    $clj --main cljs.main --compile hello-world.core --repl

### Attributes
```
 --main invokes a Clojure function -> cljs.main
 --compile compiles the namespace -> hello-world.core
 --repl launches REPL when compilation completes
```

### Starting simple web serve
    $clj -m cljs.main --serve
