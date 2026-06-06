(defproject url-shortener "0.1.0-SNAPSHOT"
  :description "REST API для сокращения url-адресов"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.10.0"]
                 [ring/ring-jetty-adapter "1.10.0"]
                 [compojure "1.7.0"]
                 [clj-http "3.12.3"]
                 [org.xerial/sqlite-jdbc "3.41.2.2"]
                 [org.clojure/java.jdbc "0.7.12"]]
  :main ^:skip-aot url-shortener.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})