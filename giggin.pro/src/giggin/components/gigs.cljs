(ns giggin.components.gigs
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]
            [giggin.components.gig-editor :refer [gig-editor]]
            [reagent.core :as r]
            [clojure.string :as str]))

;; This approach relies on list comprehension
(defn gigs
      []
      (let [modal (r/atom false)
            values (r/atom {:id       (str "gig-" (random-uuid))
                            :title    ""
                            :desc     ""
                            :price    0
                            :img      ""
                            :sold-out false})
            add-to-order #(swap! state/orders update % inc)
            insert-gig (fn [{:keys [id title desc price img sold-out]}]
                           (swap! state/gigs assoc id {:id       id
                                                       :title    (str/trim title)
                                                       :desc     (str/trim desc)
                                                       :price    (js/parseInt price)
                                                       :img      (str/trim img)
                                                       :sold-out sold-out})
                           (reset! modal false))]
           [:main
            [:div.gigs
             [:button.add-gig
              {:on-click #(reset! modal true)}
              [:div.add__title
               [:i.icon.icon--plus]
               [:p "Add gig"]]]
             [gig-editor modal values insert-gig]
             ;; Using destructuring to get the items from the map
             ;; So it is not needed to get values from map as (:keyword my-map)
             (for [{:keys [id img title price desc]} (vals @state/gigs)]
                            [:div.gig {:key id}
                             [:img.gig__artwork {:src img :alt title}]
                             [:div.gig__body
                              [:div.gig__title
                               [:div.btn.btn--primary.float--right.tooltip
                                {:data-tooltip "Add to order"
                                 :on-click     #(add-to-order id)}
                                [:i.icon.icon--plus]] title]
                              [:p.gig__price (format-price price)]
                              [:p.gig__desc desc]]])]]))

;; This approach relies on map function
(comment
  (defn gigs
        []
        [:main
         [:div.gigs (map (fn [gig]
                             [:div.gig {:key (:id gig)}
                              [:img.gig__artwork {:src (:img gig) :alt (:title gig)}]
                              [:div.gig__body
                               [:div.gig__title
                                [:div.btn.btn--primary.float--right.tooltip {:data-tooltip "Add to order"}
                                 [:i.icon.icon--plus]] (:title gig)]
                               [:p.gig__price (:price gig)]
                               [:p.gig__desc (:desc gig)]]])
                         (vals @state/gigs))]]))


;; Different ways to associate a symbol to a value
(defn greet-1 [name] (str "Hello " name))
(def greet-2 (fn [name] (str "Hello " name)))
(def greet-3 #(str "Hello " %))