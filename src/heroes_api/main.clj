(ns heroes-api.main
  (:require [heroes-api.components.di :as components])
  (:gen-class))

(defn -main
  [& _]
  (components/start :dev)
  (println "Starting server"))


