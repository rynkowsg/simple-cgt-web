(defproject simplecgt-web "0.1.0-SNAPSHOT"
  :min-lein-version "2.9.0"

  :plugins [[lein-tools-deps "0.4.5"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  :lein-tools-deps/config {:config-files ["deps.edn"]}

  :main simplecgt.web.server.main
  :aot [simplecgt.web.server.main]

  :profiles {:uberjar {:uberjar-name "simplecgt-web-standalone.jar"}
             ;; jvm/native
             :jvm {:aot :all}
             :native {:lein-tools-deps/config {:aliases [:native]}
                      :jvm-opts ["-Dclojure.compiler.direct-linking=true"
                                 "-Dclojure.spec.skip-macros=true"]}
             ;; prod/env
             :prod-env {:lein-tools-deps/config {:aliases [:prod]}}
             :dev-env {:lein-tools-deps/config {:aliases [:dev]}}})

;; Sample run:
;; lein with-profiles "+prod-env" do clean, run
;; lein with-profiles "+dev-env" do clean, run

;; Run uberjar:
;lein with-profiles "+nonnative,+prod-env" classpath | tr ':' '\n' | sort
;lein with-profiles "+nonnative,+prod-env" deps :tree
;lein with-profiles "+nonnative,+prod-env" do clean, uberjar
;java -jar target/simplecgt-web-standalone.jar

;; Run native:
;lein with-profiles "+native,+prod-env" classpath | tr ':' '\n' | sort
;lein with-profiles "+native,+prod-env" deps :tree
;lein with-profiles "+native,+prod-env" do clean, uberjar
;./scripts/compile-native.bash ./target/simplecgt-web-standalone.jar ./target/simplecgt-web-standalone
;./target/simplecgt-web-standalone
