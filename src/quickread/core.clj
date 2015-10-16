(ns quickread.core
  (:use [opennlp.nlp])
  (:require [diffbot.core :refer [article]]
            [clojure.string :as str]))

(def token "")

(def get-sentences (make-sentence-detector "resources/en-sent.bin"))

(defn first-sentence-of-paragraph [s]
  (first (get-sentences s)))

(defn summarize-paragraphs [ps]
  (let [first-p (first ps)
        last-p (last ps)
        middle (subvec (vec ps) 1 (- (count ps) 1))]
    (flatten (conj '() last-p (map first-sentence-of-paragraph middle) first-p))))

(defn summarize-blog-post [url]
  (summarize-paragraphs (str/split
                         (:text
                          (article token url :fields ["text"])) #"\n")))




