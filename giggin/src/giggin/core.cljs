(ns giggin.core
  (:require [reagent.core :as r]
            [giggin.components.footer :as f]
            [giggin.components.gigs :as g]
            [giggin.components.header :as h]
            [giggin.components.orders :as o]))

(defn app
  []
  [:div.container
   [:header (h/header)]
   [:gigs (g/gigs)]
   [:orders (o/orders)]
   [:footer (f/footer)]])

(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))

;; To run = npm run dev