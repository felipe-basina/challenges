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

(defn ^:dev/after-load start
      []
      (r/render
        [app]
        (.getElementById js/document "app")))

;; The export macro allows the main function
;; to be available at the HTML file
(defn ^:export main
      []
      "Function which runs, only once, at the very beginning for initializing the application"
      ;; Fetching data from the API
      ;;(api/fetch-gigs)
      (start)
      (firebase-init)
      (db-subscribe ["gigs"]))
