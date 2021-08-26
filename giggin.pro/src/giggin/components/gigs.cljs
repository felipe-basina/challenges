(ns giggin.components.gigs
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]
            [giggin.components.gig-editor :refer [gig-editor]]
            [reagent.core :as r]
            [clojure.string :as str]
            [giggin.fb.db :refer [db-save!]]))

;; Get default images: https://source.unsplash.com/random/400x400

;; Save into Firebase
(defn gigs
      []
      (let [modal (r/atom {:active false})
            initial-values {:id       nil
                            :title    ""
                            :desc     ""
                            :price    0
                            :img      ""
                            :sold-out false}
            values (r/atom initial-values)
            add-to-order #(swap! state/orders update % inc)
            toggle-modal (fn [{:keys [active gig]}]
                             (swap! modal assoc :active active)
                             (reset! values gig))
            upsert-gig (fn [{:keys [id title desc price img sold-out] :as ori-map}]
                           ; Use 'id' if it exists, otherwise generates a new id
                           (let [gig-id (or id (str "gig-" (random-uuid)))]
                                (db-save! ["gigs" gig-id] #js {:id       gig-id
                                                               :title    (str/trim title)
                                                               :desc     (str/trim desc)
                                                               :price    (js/parseInt price)
                                                               :img      (str/trim img)
                                                               :sold-out sold-out}))
                           (toggle-modal {:active false :gig initial-values}))]
           [:main
            [:div.gigs
             [:button.add-gig
              {:on-click #(toggle-modal {:active true :gig initial-values})}
              [:div.add__title
               [:i.icon.icon--plus]
               [:p "Add gig"]]]
             [gig-editor {:modal          modal
                          :values         values
                          :upsert-gig     upsert-gig
                          :toggle-modal   toggle-modal
                          :initial-values initial-values}]
             ;; Using destructuring to get the items from the map
             ;; So it is not needed to get values from map as (:keyword my-map)
             ;; and using :as we can get the full map as well
             (for [{:keys [id img title price desc] :as gig} (vals @state/gigs)]
                  [:div.gig {:key id}
                   [:img.gig__artwork.gig__edit {:src      img
                                                 :alt      title
                                                 :on-click #(toggle-modal {:active true
                                                                           :gig    gig})}]
                   [:div.gig__body
                    [:div.gig__title
                     [:div.btn.btn--primary.float--right.tooltip
                      {:data-tooltip "Add to order"
                       :on-click     #(add-to-order id)}
                      [:i.icon.icon--plus]] title]
                    [:p.gig__price (format-price price)]
                    [:p.gig__desc desc]]])]]))

;; This approach relies on list comprehension
;; Save into memory using ATOM
(comment defn gigs
         []
         (let [modal (r/atom {:active false})
               initial-values {:id       nil
                               :title    ""
                               :desc     ""
                               :price    0
                               :img      ""
                               :sold-out false}
               values (r/atom initial-values)
               add-to-order #(swap! state/orders update % inc)
               toggle-modal (fn [{:keys [active gig]}]
                                (swap! modal assoc :active active)
                                (reset! values gig))
               upsert-gig (fn [{:keys [id title desc price img sold-out] :as ori-map}]
                              ; Use 'id' if it exists, otherwise generates a new id
                              (let [gig-id (or id (str "gig-" (random-uuid)))]
                                   (swap! state/gigs assoc gig-id {:id       gig-id
                                                                   :title    (str/trim title)
                                                                   :desc     (str/trim desc)
                                                                   :price    (js/parseInt price)
                                                                   :img      (str/trim img)
                                                                   :sold-out sold-out}))
                              (toggle-modal {:active false :gig initial-values}))]
              [:main
               [:div.gigs
                [:button.add-gig
                 {:on-click #(toggle-modal {:active true :gig initial-values})}
                 [:div.add__title
                  [:i.icon.icon--plus]
                  [:p "Add gig"]]]
                [gig-editor {:modal          modal
                             :values         values
                             :upsert-gig     upsert-gig
                             :toggle-modal   toggle-modal
                             :initial-values initial-values}]
                ;; Using destructuring to get the items from the map
                ;; So it is not needed to get values from map as (:keyword my-map)
                ;; and using :as we can get the full map as well
                (for [{:keys [id img title price desc] :as gig} (vals @state/gigs)]
                     [:div.gig {:key id}
                      [:img.gig__artwork.gig__edit {:src      img
                                                    :alt      title
                                                    :on-click #(toggle-modal {:active true
                                                                              :gig    gig})}]
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