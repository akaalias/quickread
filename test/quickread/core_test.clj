(ns quickread.core-test
  (:require [clojure.test :refer :all]
            [diffbot.core :refer [article]]
            [quickread.core :refer :all]))

(deftest test-summarize-blog-post 
  (with-redefs [article (fn [url & more] {:text "Hello.\nWorld."})]
    (testing "It returns a summarized version of a blog post"
      (is (= '("Hello." "World.")
             (summarize-blog-post "http://none.com"))))))

(deftest test-summarize-paragraphs
  (testing "It returns the first paragraph, then only the first sentences of each following paragraph. Then the full last paragraph."
    (is (= '("First. First." "Second." "Third." "Last. Last.")
           (summarize-paragraphs '("First. First." "Second. Second." "Third. Third." "Last. Last."))))))

(deftest test-first-sentence-of-paragraph
  (testing "It returns the first sentence of a paragraph"
    (is (= "First sentence." (first-sentence-of-paragraph "First sentence. Second sentence? Here is another one.")))))
