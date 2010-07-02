(ns running.core
  (:use [clojure.contrib.server-socket])
  (:require [clojure.main :as main]
	    [running.utils :as utils]))

(comment
  (1) Bind a thread to a port with a listener
  (2) Create the closure
  (3) Pass it through the network to the port
  (4) The remote listener should execute the closure)




