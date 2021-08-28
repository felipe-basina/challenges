(ns giggin.components.orders
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]
            [giggin.components.checkout-modal :refer [checkout-modal]]
            [giggin.components.admin-panel :refer [admin-panel]]))

(comment defn total []
         ;; Destructuring the id, qtd from each order
         (reduce + (map (fn [[id qtd]] (* qtd (get-in @state/gigs [id :price])))
                        @state/orders)))

;; Calculating the total using thread last
(defn total []
      (->> @state/orders
           (map (fn [[id qtd]]
                    (if (get-in @state/gigs [id :sold-out])
                      0
                      (* qtd (get-in @state/gigs [id :price])))))
           (reduce +)))

(defn orders
      []
      (let [remove-from-order #(swap! state/orders dissoc %)
            remove-all-orders #(reset! state/orders {})]
           [:aside
            ;; This button is necessary when needed to send to firebase
            ;; all the items returned by the API
            (when @state/user
                  [admin-panel])
            (if (empty? @state/orders)
              [:div.empty
               [:div.title "You don't have any orders"]
               [:div.subtitle "Click on a + to add an order"]]
              [:div.order
               [:div.body
                (doall
                  (for [[id quant] @state/orders]
                       (let [gig (id @state/gigs)]
                            [:div.item {:key id}
                             [:div.img
                              [:img {:src (:img gig)
                                     :alt (:title gig)}]]
                             [:div.content
                              (if (:sold-out gig)
                                [:p.sold-out "Sold out"]
                                [:p.title (:title gig) " \u00D7 " quant])] ;; It does not need to wrap in (str) function when dealing with strings
                             [:div.action
                              (if (:sold-out gig)
                                [:div.price (format-price 0)]
                                [:div.price (format-price (* (:price gig) quant))])
                              [:button.btn.btn--link.tooltip
                               {:data-tooltip "Remove"
                                :on-click     #(remove-from-order id)}
                               [:i.icon.icon--cross]]]])))]
               [:div.total
                [:hr]
                [:div.item
                 [:div.content "Total"]
                 [:div.action
                  [:div.price (format-price (total))]]
                 [:button.btn.btn--link.tooltip
                  {:data-tooltip "Remove all"
                   :on-click     #(remove-all-orders)}
                  [:i.icon.icon--delete]]]
                [checkout-modal]]])]))
