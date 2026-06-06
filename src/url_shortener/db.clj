(ns url-shortener.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "url_shortener.db"})

(defn init-db []
  (try
    (jdbc/db-do-commands db-spec
                         (jdbc/create-table-ddl :urls
                                                [[:short_url "TEXT PRIMARY KEY"]
                                                 [:normal_url "TEXT"]]))
    (catch Exception _ nil))) ; Игнорируем ошибку, если таблица уже существует

(defn insert-url [short-url normal-url]
  (jdbc/insert! db-spec :urls {:short_url short-url :normal_url normal-url}))

(defn get-url [short-url]
  (:normal_url (first (jdbc/query db-spec ["SELECT normal_url FROM urls WHERE short_url = ?" short-url]))))

(defn update-url [short-url normal-url]
  (jdbc/execute! db-spec ["UPDATE urls SET normal_url = ? WHERE short_url = ?" normal-url short-url]))

(defn delete-url [short-url]
  (jdbc/execute! db-spec ["DELETE FROM urls WHERE short_url = ?" short-url]))