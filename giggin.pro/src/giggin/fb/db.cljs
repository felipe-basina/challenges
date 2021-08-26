(ns giggin.fb.db
  (:require ["firebase/app" :refer [database]]
            [clojure.string :as str]
            [giggin.state :as state]))

(defn db-ref
      [path]
      (.ref (database) (str/join "/" path)))

(defn db-save!
      [path value]
      (.set (db-ref path) value))

(defn db-subscribe
      [path]
      (.on (db-ref path)
           "value"
           (fn [snapshot]
               (reset! state/gigs (js->clj (.val snapshot) :keywordize-keys true))))) ;; js->clj convert from js to cljs