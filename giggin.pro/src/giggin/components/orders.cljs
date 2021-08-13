(ns giggin.components.orders
  (:require [giggin.state :as state]))

(comment defn total []
         ;; Destructuring the id, qtd from each order
         (reduce + (map (fn [[id qtd]] (* qtd (get-in @state/gigs [id :price])))
                        @state/orders)))

;; Calculating the total using thread last
(defn total []
      (->> @state/orders
           (map (fn [[id qtd]] (* qtd (get-in @state/gigs [id :price]))))
           (reduce +)))

(defn orders
      []
      [:aside
       [:div.order
        [:div.body
         (for [[id quant] @state/orders]
              [:div.item {:key id}
               [:div.img
                [:img {:src (get-in @state/gigs [id :img])
                       :alt (get-in @state/gigs [id :title])}]]
               [:div.content
                [:p.title (str (get-in @state/gigs [id :title]) " \u00D7 " quant)]]
               [:div.action
                [:div.price (* (get-in @state/gigs [id :price]) quant)]
                [:button.btn.btn--link.tooltip
                 {:data-tooltip "Remove"
                  :on-click     #(swap! state/orders dissoc id)}
                 [:i.icon.icon--cross]]]])]
        [:div.total
         [:hr]
         [:div.item
          [:div.content "Total"]
          [:div.action
           [:div.price (total)]]
          [:button.btn.btn--link.tooltip
           {:data-tooltip "Remove all"
            :on-click     #(reset! state/orders {})}
           [:i.icon.icon--delete]]]]]])
