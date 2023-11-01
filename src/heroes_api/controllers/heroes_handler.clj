(ns heroes-api.controllers.heroes-handler
  (:require [heroes-api.domain.repositories.hero-repository :as repo]
            [heroes-api.domain.entities.hero :as entity.hero]
            [heroes-api.domain.entities.biography :as entity.biography]))

(defn create!
  [store {:keys [name full-name alias group-affiliation relatives]}]
  (let [biography (entity.biography/new-biography full-name alias)
        hero (entity.hero/new-hero name biography group-affiliation relatives)]
    (repo/create! store hero)
    {:status 201 :body {:message "Success created" :hero hero}}))

(defn show!
  [store uuid]
  {:status 200 :body (repo/find-by-id! store uuid)})

(defn update!
  [store {:keys [uuid-hero name full-name alias group-affiliation relatives]}]
  (let [biography (entity.biography/new-biography full-name  alias)
        hero (entity.hero/new-hero uuid-hero name biography group-affiliation relatives)]
    (repo/update! store uuid-hero hero)
    {:status 200 :body {:message "Success updated" :hero hero}}))

(defn destroy!
  [store uuid]
  (repo/delete! store uuid)
  {:status 200 :body {:message "Success delete"}})

(defn index!
  [store]
  {:status 200 :body (repo/all! store {})})