(ns giggin.fb.init
  (:require ["firebase/app" :as firebase]
            ["firebase/database"]
            ["firebase/auth"]
            [giggin.fb.auth :refer [on-auth-state-changed]]))

(defn firebase-init
      []
      "Controls the firebase initialization"
      ;; (cjs -> js or #js - to send a map as JS object
      (comment
        ;; As the creation of function (after-load start)
        ;; the application will load/create the firebase connection just once
        if (zero? (alength firebase/apps))
        (firebase/app))
      (firebase/initializeApp #js {:apiKey      "AIzaSyCskb4dbV6zQTinojacpC2SUPRBDx2zZlM"
                                   :authDomain  "poc-login-1.firebaseapp.com"
                                   :databaseURL "https://poc-login-1.firebaseio.com"
                                   :projectId   "poc-login-1"})
      (on-auth-state-changed))