(ns heroes-api.domain.entities.biography)

(defn new-biography
  ([full-name alter-egos alias]
   {:full-name full-name :alter-egos alter-egos :alias alias}))