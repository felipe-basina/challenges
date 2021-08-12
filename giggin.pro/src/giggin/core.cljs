(ns giggin.core
  (:require [reagent.core :as r]
            [giggin.components.header :refer [header]]
            [giggin.components.gigs :refer [gigs]]
            [giggin.components.orders :refer [orders]]
            [giggin.components.footer :refer [footer]]))

;; We can also define the div like this
;; [:div {:class "container"}]
;; [:div {:id "my-div"}]
(defn app
  []
  [:div.container
   [header]
   [gigs]
   [orders]
   [footer]])

;; The export macro allows the main function
;; to be available at the HTML file
(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
