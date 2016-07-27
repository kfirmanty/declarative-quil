(ns declarative-quil.core
  (:require [quil.core :as q]))

(def type-fn
  {:ellipse q/ellipse
   :rect q/rect})

(defn child-size [parent child]
  (if (not-empty (:size child))
    (:size child)
    (:size parent)))

(defn min-distance [x-s y-s]
  (+ (/ x-s 2) (/ y-s 2)))

(defn child-pos [parent rel [ch-w ch-h]]
  (let [[p-x p-y] (:pos parent)
        [p-w p-h] (:size parent)]
    (case rel
      :above [p-x (- p-y (min-distance p-h ch-h))]
      :below [p-x (+ p-y (min-distance p-h ch-h))]
      :left [(- p-x (min-distance p-w ch-w)) p-y]
      :right [(+ p-x (min-distance p-w ch-w)) p-y])))

(defn draw-child [parent rel child]
    (let [[ch-w ch-h] (child-size parent child)
          [ch-x ch-y] (child-pos parent rel [ch-w ch-h])
          ch-fn ((:type child) type-fn)]
    (ch-fn ch-x ch-y ch-w ch-h)))

(defn please [parent & rels]
  (let [[p-x p-y] (:pos parent)
        [p-w p-h] (:size parent)
        p-fn ((:type parent) type-fn)]
    (p-fn p-x p-y p-w p-h)
    (doseq [[rel child] (partition 2 rels)] (draw-child parent rel child))))

(defn init []
  (q/ellipse-mode :center)
  (q/rect-mode :center))
