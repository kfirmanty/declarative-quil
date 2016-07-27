(ns declarative-quil.playground
    (:require [quil.core :as q]
              [quil.middleware :as m]
              [declarative-quil.core :as d]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (d/init)
  {:color 0
   :angle 0})

(defn update-state [state]
  {:color (mod (+ (:color state) 0.7) 255)
   :angle (+ (:angle state) 0.1)})

(defn draw-state [state]
  (q/background 240)
  (q/fill (:color state) 255 255)
  (d/please {:type :rect :pos [(q/mouse-x) 100] :size [50 10]} :above {:type :rect} :below {:type :ellipse}))

(q/defsketch declarative-quil
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
