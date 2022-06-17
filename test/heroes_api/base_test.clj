(ns heroes-api.base-test
  (:require [clojure.test :refer :all]
            [io.pedestal.test :refer :all]
            [heroes-api.components.di :as components]
            [clojure.data.json :as json]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [heroes-api.routes :as r])
  (:use [clojure.pprint]))

(def system (components/start :test))

(defn reset-database []
  (reset! (:conn (:database system)) []))

(defn- service-fn [system]
  (get-in system [:pedestal :service ::http/service-fn]))

(def service (service-fn system))

(def url-for
  (route/url-for-routes r/endpoints))

(defn request
  [verb keyword-path & {:keys [body path-params]}]
  (response-for service
                verb
                (url-for
                  keyword-path
                  :path-params path-params)
                :headers {"Content-Type" "application/json"}
                :body body))

(defn create-default-hero []
  (let [{:keys [status body]} (request
                                :post
                                :create-heroes
                                :body "{\"name\":\"Batman\",\"universe\":\"DC\",\"biography\":{\"full-name\":\"Bruce Wayne\", \"alter-egos\":\"\", \"alias\": [\"The Dark Knight\",\"Batman Beyond\"]}}")
        hero (:hero (json/read-json body))]
    {:status status :hero hero}))