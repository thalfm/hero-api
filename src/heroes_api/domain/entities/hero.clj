(ns heroes-api.domain.entities.hero)

(defn new-hero
  ([name biography group-affiliation relatives]
   {:uuid (random-uuid)
    :name name
    :biography biography
    :group-affiliation group-affiliation
    :relatives relatives})

  ([uuid name biography group-affiliation relatives]
   {:uuid uuid
    :name name
    :biography biography
    :group-affiliation group-affiliation
    :relatives relatives}))