(ns giggin.components.checkout-modal
  (:require [reagent.core :as r]))

(defn checkout-modal []
      (let [modal (r/atom false)
            toggle-modal #(reset! modal %)]
           ;; It is needed to put the body in an anonymous function
           ;; so it will be the one to render before the binding values "modal", "toggle-modal"
           (fn [] 
               [:div.checkout-modal
            [:button.btn.btn--secondary
             {:on-click #(toggle-modal true)}
             "Checkout"]
            [:div.modal (when @modal {:class "active"})     ;; When modal is true
             [:div.modal__overlay]
             [:div.modal__container
              [:div.modal__body
               [:div.payment
                [:img.payment-logo {:src "/img/paypal.svg"
                                    :alt "Paypal logo"}]
                [:img.payment-logo {:src "/img/stripe.svg"
                                    :alt "Stripe logo"}]]]
              [:div.modal__footer
               [:button.btn.btn--link.float--left
                {:on-click #(toggle-modal false)}
                "Cancel"]]]]])))