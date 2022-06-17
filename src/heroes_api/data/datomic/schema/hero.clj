(ns heroes-api.data.datomic.schema.hero
  (:require [datomic.api :as d]))

(def ^:private hero-schema
  [{:db/ident       :hero/uuid
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "UUID of hero"}

   {:db/ident       :hero/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Name of hero"}

   {:db/ident       :hero/universe
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "In which universe is the hero inserted"}

   {:db/ident       :hero/biography
    :db/valueType   :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/isComponent true
    :db/doc         "Ref to biography"}

   ;biography
   {:db/ident       :biography/full-name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Full name of hero"}

   {:db/ident       :biography/alter-egos
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "alter egos of hero"}

   {:db/ident       :biography/aliases
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/many
    :db/doc         "Aliases of hero"}])

(defn create
  [conn]
  (d/transact conn hero-schema))

(defn biography->schema [{:keys [full-name alias alter-egos]}]
  {:biography/full-name full-name
   :biography/aliases alias
   :biography/alter-egos alter-egos})

(defn hero->schema [{:keys [uuid name universe biography]}]
  {:hero/uuid uuid
   :hero/name name
   :hero/universe universe
   :hero/biography (biography->schema biography)})

(defn biography->entity [schema]
  {:full-name (:biography/full-name schema)
   :alter-egos (:biography/alter-egos schema)
   :aliases (:biography/aliases schema)})

(defn to->entity [schema]
  (println schema)
  (if schema
    {:uuid      (str (:hero/uuid schema))
     :name      (:hero/name schema)
     :universe  (:hero/universe schema)
     :biography (biography->entity (:hero/biography schema))}
    schema))