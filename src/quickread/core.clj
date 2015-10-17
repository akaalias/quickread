(ns quickread.core
  (:use [opennlp.nlp])
  (:require [diffbot.core :refer [article]]
            [clojure.string :as str]))

(def token "")

(def get-sentences (make-sentence-detector "resources/en-sent.bin"))

(defn first-sentence-of-paragraph [p]
  (first (get-sentences p)))

(defn rest-sentences-of-paragraph [p]
  (rest (get-sentences p)))

(defn summarize-paragraphs [ps]
  (let [first-p (first ps)
        last-p (last ps)
        middle (subvec (vec ps) 1 (- (count ps) 1))]
    (flatten (conj '() 
                   {:summary last-p :remainder []} 
                   (map (fn [x] {:summary (first-sentence-of-paragraph x)
                                 :remainder (vec (rest-sentences-of-paragraph x))}) middle)
                   {:summary first-p :remainder []}))))

(defn summarize-blog-post [url]
  (let [the-response (article token url :fields ["title" "text"])]
    {:title (:title the-response)
     :paragraphs
     (vec
      (summarize-paragraphs (str/split
                             (:text
                              the-response) #"\n")))}))
