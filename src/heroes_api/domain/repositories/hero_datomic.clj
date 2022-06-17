/(ns heroes-api.domain.repositories.hero-datomic
  (:require [datomic.api :as d]
            [heroes-api.data.datomic.schema.hero :as schema]))

(defn all! [database, {:keys [id]}]
  (let [query {:query '{:find  [[(pull ?hero [*]) ...]]
                        :in    [$]
                        :where [[?hero :hero/uuid _]]}
               :args  [(d/db database)]}
        condition (cond-> query

                       id (->
                            (update-in [:query :in] conj '?id)
                            (update-in [:query :where] conj '[?hero :hero/uuid ?id])
                            (update-in [:args] conj id)))]
    (mapv schema/to->entity (d/query condition))))

(defn find-by-id!
  [database id]
  (let [heroes (all! database {:id (parse-uuid id)})]
    (if (not-empty heroes)
      (first heroes)
      {})))

(defn create!
  [database hero]
  (d/transact database [(schema/hero->schema hero)]))

(defn update!
  [database id hero]
  (create! database (assoc hero :uuid (parse-uuid id))))

(defn delete!
  [database id]
  (d/transact database [[:db/retractEntity [:hero/uuid (parse-uuid id)]]]))