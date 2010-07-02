(ns running.utils
  (:require [clojure.main :as main]))

(defstruct closure :closure)

(defn- eval-str [s]
  (main/main "-e" s))

(defn- get-eval [map]
  (eval (:closure map)))

(defn- extract
  ([str]
     (with-in-str str (read))))

(defn extract-eval
  "Returns the evaluation of the closure
  contained in the input string."
  [str] (get-eval (extract str)))

(defn store
  "Packages the closure such that it can
  be written to a socket, etc."
  [c] (struct closure c))

(defn store-all
  "Packages all the closures using store.
   Returns a list of the stored values
   suitable for writing and reading.
   The preferred usage."
  ([& cs]
     (lazy-seq (reduce conj '() (map store cs)))))

(defn extract-eval-all
  "The preferred usage. Parses the string
   into a collection of closures, and
   proceeds to evaluate all of them,
   returning a list of the results."
  ([str]
     (let [coll (extract str)]
       (lazy-seq (map #(get-eval %) coll)))))