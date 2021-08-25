(ns giggin.fb.init
  (:require ["firebase/app" :as firebase]
            ["firebase/database"]
            ["firebase/auth"]))

(defn firebase-init []
      (firebase/initializeApp {:apiKey            "AIzaSyDkeXUELMS7LAL7neoowkb6ZpPG5M1Fv2I"
                               :authDomain        "giggin-5507e.firebaseapp.com"
                               :projectId         "giggin-5507e"
                               :storageBucket     "giggin-5507e.appspot.com"
                               :messagingSenderId "282567272849"
                               :appId             "1:282567272849:web:a4fe8f33e5db85244e1230"
                               :measurementId     "G-F0N7CDFN91"}))