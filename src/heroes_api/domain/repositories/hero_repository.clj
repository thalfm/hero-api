(ns heroes-api.domain.repositories.hero-repository
  (:require [heroes-api.domain.repositories.hero-in-memory :as in-memory]
            [heroes-api.domain.repositories.hero-datomic :as datomic])
  (:import (clojure.lang Atom)))

(defn- select-db
  [db & _]
  (if-not (= (class db) Atom) :datomic :in-memory))

(defmulti all! select-db)
(defmulti find-by-id! select-db)
(defmulti create! select-db)
(defmulti update! select-db)
(defmulti delete! select-db)

;; Datomic
(defmethod all! :datomic
  [store map]
  (datomic/all! store map))

(defmethod find-by-id! :datomic
  [store id]
  (datomic/find-by-id! store id))

(defmethod create! :datomic
  [store hero]
  (datomic/create! store hero))

(defmethod update! :datomic
  [store id hero]
  (datomic/update! store id hero))

(defmethod delete! :datomic
  [store id]
  (datomic/delete! store id))

;;In Memory
(defmethod all! :in-memory
  [store map]
  (in-memory/all! store map))

(defmethod find-by-id! :in-memory
  [store id]
  (in-memory/find-by-id! store id))

(defmethod create! :in-memory
  [store hero]
  (in-memory/create! store hero))

(defmethod update! :in-memory
  [store id hero]
  (in-memory/update! store id hero))

(defmethod delete! :in-memory
  [store id]
  (in-memory/delete! store id))
