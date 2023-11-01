(ns heroes-api.components.database
  (:require [com.stuartsierra.component :as component]
            [datomic.api :as d]
            [config.core :refer [env]]
            [heroes-api.data.datomic.schema.hero :as schema-t]))

(defrecord DatabaseInMemory []
  component/Lifecycle

  (start [this]
    (assoc this :conn (atom [])))

  (stop [this]
    (assoc this :conn nil)))

(defrecord DatabaseDatomic
  [db-uri]
  component/Lifecycle

  (start [this]
    (d/create-database db-uri)
    (let [conn (d/connect db-uri)]
      (schema-t/create conn)
      (merge this {:conn conn
                   :uri  db-uri})))
  (stop [this]
    (if (:conn this) (d/release (:conn this)))
    (dissoc this :uri :conn)))

(defn new-database-datomic
  []
  (println "Start datomic connection")
  (->> (:datomic-secret-password env)
       (str (:db-uri env))
       ->DatabaseDatomic))

(defn new-database-in-memory []
  (println "Start In Memory connection")
  (->DatabaseInMemory))

(defn new-database [environment]
  (if (contains? #{:local :test} environment) (new-database-in-memory) (new-database-datomic)))