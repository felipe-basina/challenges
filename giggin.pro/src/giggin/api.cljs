(ns giggin.api
  (:require [ajax.core :refer [GET]]
            [giggin.state :as state]))

(def URL "https://gist.githubusercontent.com/jacekschae/6f449ccd07d78740c6dcb54b07a3d5bc/raw/c94e3c21ea39248adb5c23636a0f5fe3d9a4f5f9/gigs.json")

(defn index-by
      [key collection]
      "Transform a coll to a map with a given key as a lookup value"
      (->> collection
           (map (juxt key identity))
           (into {})))

;; Using the walk/keywordize-keys
;; to transform the string id into a clojure keyword
;; Needs to import [clojure.walk :as walk]
(comment
  (defn index-by
      [key collection]
      "Transform a coll to a map with a given key as a lookup value"
      (->> collection
           (map (juxt key identity))
           (into {})
           (walk/keywordize-keys))))

;; With thread (last) macro
(defn handler [response]
      (reset! state/gigs (index-by :id response)))

;; Without thread macro
(comment
  (defn handler [response]
      (reset! state/gigs (walk/keywordize-keys (into {} (map (juxt :id identity) response))))))

(defn error-handler [{:keys [status status-text]}]
      (.log js/console (str "something bad happened: " status " " status-text)))

(defn fetch-gigs
      []
      (GET URL
           {:handler         handler
            :error-handler   error-handler
            :response-format :json
            :keywords?       true}))