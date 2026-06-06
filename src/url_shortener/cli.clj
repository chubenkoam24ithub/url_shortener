(ns url-shortener.cli
  (:require [url-shortener.client :as client]
            [clojure.string :as str]))

(defn print-menu []
  (println "\n=== URL Shortener ===")
  (println "1. Создать")
  (println "2. Показать")
  (println "3. Изменить")
  (println "4. Удалить")
  (println "5. Выйти"))

(defn start-cli []
  (println "Клиент URL Shortener")
  (loop []
    (print-menu)
    (print "Выберите действие: ")
    (flush)
    (let [choice (read-line)]
      (case choice
        "1" (do (println "Введите обычный URL для сокращения:")
                (print "> ") (flush)
                (let [url (read-line)]
                  (println "Отправка запроса...")
                  (println (client/create-url url)))
                (recur))
        "2" (do (println "Введите короткий URL:")
                (print "> ") (flush)
                (let [short-url (read-line)]
                  (println "Отправка запроса...")
                  (println (client/get-url short-url)))
                (recur))
        "3" (do (println "Введите через пробел короткий URL и обычный URL:")
                (print "> ") (flush)
                (let [input (read-line)
                      parts (str/split input #"\s+")]
                  (if (= 2 (count parts))
                    (do (println "Отправка запроса...")
                        (println (client/update-url (first parts) (second parts))))
                    (println "Ответ: Ошибка (неверный формат ввода)"))
                  (recur)))
        "4" (do (println "Введите короткий URL:")
                (print "> ") (flush)
                (let [short-url (read-line)]
                  (println "Отправка запроса...")
                  (println (client/delete-url short-url)))
                (recur))
        "5" (println "Программа завершена.")
        (do (println "Неверный ввод. Попробуйте еще раз.")
            (recur))))))