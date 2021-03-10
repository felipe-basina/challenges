(ns giggin.core
  (:require [reagent.core :as r]
            [giggin.components.footer :refer [footer]]
            [giggin.components.gigs :refer [gigs]]
            [giggin.components.header :refer [header]]
            [giggin.components.orders :refer [orders]]))

(defn app
  []
  [:div.container
   [header]
   [gigs]
   [orders]
   [footer]])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))

;; To run = npm run dev