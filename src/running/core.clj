(ns running.core
  (:use [clojure.contrib.server-socket])
  (:require [clojure.main :as main]
	    [running.utils :as utils]))

(comment
  (1) Bind a thread to a port with a listener
  (2) Wrap a closure in a map
  (3) Pass it through the network to the port
  (4) The remote listener should execute the closure)

(defstruct closure :closure)
(defn make-closure-listener []
  #())

(defn bind-listener-to-port [port]
  ())



(create-server )



