(ns heroes-api.domain.entities.biography)

(defn new-biography
  ([full-name alias]
   {:full-name full-name :alias alias}))