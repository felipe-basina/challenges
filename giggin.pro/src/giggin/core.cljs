(ns giggin.core
  (:require [reagent.core :as r]
            [giggin.components.header :refer [header]]
            [giggin.components.gigs :refer [gigs]]
            [giggin.components.orders :refer [orders]]
            [giggin.components.footer :refer [footer]]
            [giggin.api :as api]
            [giggin.fb.init :refer [firebase-init]]
            [giggin.fb.db :refer [db-subscribe]]))

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
      ;; Fetching data from the API
      ;;(api/fetch-gigs)
      (r/render
        [app]
        (.getElementById js/document "app"))
      (firebase-init)
      (db-subscribe ["gigs"]))
