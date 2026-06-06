(ns url-shortener.server
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [url-shortener.db :as db]))

(defn generate-short []
  (let [chars "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"]
    (apply str (take 6 (repeatedly #(rand-nth chars))))))

(defroutes app-routes
  ;; Создать (сократить)
  (POST "/" [normal-url]
    (let [short-url (generate-short)]
      (db/insert-url short-url normal-url)
      {:status 200 :body short-url}))
  
  ;; Показать (получить нормальный)
  (GET "/:short-url" [short-url]
    (if-let [normal (db/get-url short-url)]
      {:status 200 :body normal}
      {:status 404 :body "Ошибка"}))
  
  ;; Изменить
  (PUT "/:short-url" [short-url normal-url]
    (if (db/get-url short-url)
      (do (db/update-url short-url normal-url)
          {:status 200 :body "OK"})
      {:status 404 :body "Ошибка"}))
  
  ;; Удалить
  (DELETE "/:short-url" [short-url]
    (if (db/get-url short-url)
      (do (db/delete-url short-url)
          {:status 200 :body "OK"})
      {:status 404 :body "Ошибка"}))
  
  (route/not-found "Not Found"))

(def app (wrap-params app-routes))

(defn start-server [port]
  (db/init-db)
  (println "Сервер запущен на порту" port "на хосте 0.0.0.0")
  ;; Добавляем параметр :host "0.0.0.0" в карту настроек
  (run-jetty app {:port port :host "0.0.0.0" :join? false}))
