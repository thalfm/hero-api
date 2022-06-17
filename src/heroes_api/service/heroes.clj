(ns heroes-api.service.heroes
  (:require [heroes-api.controllers.heroes-handler :as controllers.heroes-handler]))

(defn create!
  [{:keys [json-params] {{store :conn} :database} :context-deps}]
  (let [{:keys                                [name universe]
         {:keys [full-name alter-egos alias]} :biography} json-params]
    (controllers.heroes-handler/create! store {:name name
                                               :universe universe
                                               :full-name full-name
                                               :alter-egos alter-egos
                                               :alias alias})))

(defn show!
  [{:keys [path-params] {{store :conn} :database} :context-deps}]
  (let [uuid (:id path-params)]
    (controllers.heroes-handler/show! store uuid)))

(defn update!
  [{:keys [path-params json-params] {{store :conn} :database} :context-deps}]
  (let [uuid-hero (:id path-params)
        {:keys                                               [name universe]
         {:keys [full-name alter-egos alias]} :biography} json-params]
    (controllers.heroes-handler/update! store {:uuid-hero uuid-hero
                                               :name name
                                               :universe universe
                                               :full-name full-name
                                               :alter-egos alter-egos
                                               :alias alias})))

(defn destroy!
  [{:keys [path-params] {{store :conn} :database} :context-deps}]
  (let [uuid (:id path-params)]
    (controllers.heroes-handler/destroy! store uuid)))

(defn index!
  [{{{store :conn} :database} :context-deps}]
  (controllers.heroes-handler/index! store))
