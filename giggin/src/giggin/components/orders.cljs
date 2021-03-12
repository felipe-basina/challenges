(ns giggin.components.orders
  (:require [giggin.helpers :as helper]
            [giggin.state :as state]))

(defn total []
      (->> @state/orders
           (map (fn [[id quant]] (* (get-in @state/gigs [id :price]) quant)))
           (reduce +)))

(defn orders []
      [:aside
       (if (empty? @state/orders)
         [:div.empty
          [:div.title "You don't have any orders"]
          [:div.subtitle "Click on a + to add an order"]])
         [:div.order
          [:div.body
           (for [[id quant] @state/orders]
                (if (> quant 0)
                  [:div.item {:key      id
                              :on-click (fn [] (swap! state/orders update id dec))}
                   [:div.img
                    [:img {:src (get-in @state/gigs [id :img])
                           :alt (get-in @state/gigs [id :title])}]]
                   [:div.content
                    [:p.title (str (get-in @state/gigs [id :title]) " \u00D7 " quant)]]
                   [:div.action
                    [:div.price (helper/format-price (* (get-in @state/gigs [id :price]) quant))]
                    [:button.btn.btn--link.tooltip
                     {:data-tooltip "Remove"
                      :on-click     (fn [] (swap! state/orders dissoc id))}
                     [:i.icon.icon--cross]]]]
                  (swap! state/orders dissoc id)))]
          [:div.total
           [:hr]
           [:div.item
            [:div.content "Total"]
            [:div.action
             [:div.price (helper/format-price (total))]]
            [:button.btn.btn--link.tooltip
             {:data-tooltip "Remove all"
              :on-click     (fn [] (reset! state/orders {}))}
             [:i.icon.icon--delete]]]]]])