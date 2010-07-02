(ns running.utils
  (:require [clojure.main :as main]))

(defstruct closure :closure)

(defn eval-str [s]
  (main/main "-e" s))