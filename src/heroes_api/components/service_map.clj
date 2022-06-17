(ns heroes-api.components.service-map
  (:require [heroes-api.routes :as routes]
            [io.pedestal.http :as http]
            [io.pedestal.http :as server]))

(defn new-service-map
  [environment]
  (-> {:env                 environment
       ::http/routes        routes/endpoints
       ::http/type          :jetty
       ::http/port          8890
       ::http/resource-path "/public"
       ::http/join?         false
       ::http/host          "0.0.0.0"}
      server/default-interceptors
      server/dev-interceptors))
