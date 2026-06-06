(ns url-shortener.client
  (:require [clj-http.client :as client]))

(def base-url "http://localhost:8080/")

(defn create-url [normal-url]
  (try
    (let [res (client/post base-url {:form-params {:normal-url normal-url}})]
      (str "Ответ: " (:body res)))
    (catch Exception _ "Ответ: Ошибка")))

(defn get-url [short-url]
  (try
    (let [res (client/get (str base-url short-url))]
      (str "Ответ: " (:body res)))
    (catch Exception _ "Ответ: Ошибка")))

(defn update-url [short-url normal-url]
  (try
    (let [res (client/put (str base-url short-url) {:form-params {:normal-url normal-url}})]
      (str "Ответ: " (:body res)))
    (catch Exception _ "Ответ: Ошибка")))

(defn delete-url [short-url]
  (try
    (let [res (client/delete (str base-url short-url))]
      (str "Ответ: " (:body res)))
    (catch Exception _ "Ответ: Ошибка")))