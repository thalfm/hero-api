(ns heroes-api.domain.repositories.hero-in-memory)

(defn all! [database _]
  @database)

(defn find-by-id!
  [database id]
  (first (filter #(= (str (:uuid %)) id) @database)))

(defn create!
  [database hero]
  (swap! database conj hero))

(defn update!
  [database id hero]
  (let [database-filtered (filter #(not= (str (:uuid %)) id) @database)]
    (reset! database (conj database-filtered hero))))

(defn delete!
  [database id]
  (let [database-filtered (filter #(not= (str (:uuid %)) id) @database)]
    (reset! database database-filtered)))