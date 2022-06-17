(ns heroes-api.domain.entities.hero)

(defn new-hero
  ([name universe biography]
   {:uuid (random-uuid) :name name :universe universe :biography biography})

  ([uuid name universe biography]
   {:uuid uuid :name name :universe universe :biography biography}))