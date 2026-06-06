(ns url-shortener.core
  (:require [url-shortener.server :as server]
            [url-shortener.cli :as cli])
  (:gen-class))

(defn -main [& args]
  (if (and (not-empty args) (= (first args) "8080"))
    (server/start-server 8080)
    (cli/start-cli)))