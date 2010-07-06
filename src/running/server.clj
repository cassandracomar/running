(ns running.server
  (:require [clojure.main :as main])
  (:use [clojure.java.io]
	[running.utils]))

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

(defn extract-eval-all
  "The preferred usage. Parses the string
   into a collection of closures, and
   proceeds to evaluate all of them,
   returning a list of the results."
  ([str]
     (let [coll (extract str)]
       (lazy-seq (map #(get-eval %) coll)))))

(defstruct server :ServerSocket :Socket :InputStream :OutputStream :Thread)

(def returnQueue '())

(defn createServer [port]
  (let [ss (java.net.ServerSocket. port)
	s (.accept ss)
	in (.getInputStream s)
	out (.getOutputStream s)
	t (future (serverCallback
		   (java.io.InputStreamReader. in)))]
    (struct server ss s in out t)))

(defn- readAllAvailable
  "Reads all available text in the provided
   Reader."
  [rdr]
  (loop [txt ""
	 ready (.ready rdr)]
    (if ready
      (recur (str txt (char (.read rdr))) (.ready rdr))
      txt)))

(defn- serverCallback [isr]
  (let [txt (readAllAvailable isr)]
    (when-not (= txt "")
      (let [val (extract-eval txt)]
	(concat (list val) returnQueue)))
    (java.lang.Thread/sleep 100)
    (serverCallback isr)))