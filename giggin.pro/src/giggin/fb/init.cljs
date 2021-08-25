(ns giggin.fb.init
  (:require ["firebase/app" :as firebase]
            ["firebase/database"]
            ["firebase/auth"]))

(defn firebase-init
      []
      "Controls the firebase initialization"
      ;; (cjs -> js or #js - to send a map as JS object
      (if (zero? (alength firebase/apps))
        (firebase/initializeApp #js {:apiKey      "AIzaSyCskb4dbV6zQTinojacpC2SUPRBDx2zZlM"
                                     :authDomain  "poc-login-1.firebaseapp.com"
                                     :databaseURL "https://poc-login-1.firebaseio.com"
                                     :projectId   "poc-login-1"})
        (firebase/app)))