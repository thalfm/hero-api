(ns heroes-api.components.di
  (:require [com.stuartsierra.component :as component]
            [heroes-api.components.database :as database]
            [heroes-api.components.service-map :as service-map]
            [heroes-api.components.context-deps :as context-deps]
            [heroes-api.components.pedestal :as server]))

(defn component-system [environment]
  (component/system-map
    :database (database/new-database environment)
    :service-map (service-map/new-service-map environment)
    :context-deps (component/using (context-deps/new-context-deps) [:database])
    :pedestal (component/using (server/new-pedestal) [:service-map :context-deps])))

(defn start [environment]
  (component/start (component-system environment)))


