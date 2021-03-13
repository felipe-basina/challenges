(ns inventory.inventory)

(def books [{:title "2001" :author "Clarke" :copies 2}
                {:title "Emma" :author "Austen" :copies 10}
                {:title "Misery" :author "King" :copies 101}])

(defn find-by-title
  "Search for a book by title,
  where title is a string and books is a collection
  of book maps, each of which must have a :title entry"
  [title books]
  (some #(when (= (:title %) title) %) books))

(defn number-of-copies-of
  "Return the number of copies in inventory of the
  given title, where title is a string and books is a collection
  of book maps each of which must have a :title entry"
  [title books]
  (:copies (find-by-title title books)))