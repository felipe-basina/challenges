(ns giggin.core
  (:require [reagent.core :as r]))

;; We can also define the div like this
;; [:div {:class "container"}]
;; [:div {:id "my-div"}]
(defn app
  []
  [:div.container
   [:p {:style {:color "red"}} "Hello from ClojureJS"]])

;; The export macro allows the main function
;; to be available at the HTML file
(defn ^:export main
  []
  (r/render
    [app]
    (.getElementById js/document "app")))
