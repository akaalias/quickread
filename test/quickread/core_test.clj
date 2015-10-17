(ns quickread.core-test
  (:require [clojure.test :refer :all]
            [diffbot.core :refer [article]]
            [quickread.core :refer :all]
            [quickread.data]))

(deftest test-summarize-blog-post 
  (with-redefs [article (fn [url & more] quickread.data/api-response)]
    (testing "It returns a summarized version of a blog post"
      (is (= quickread.data/extracts
             (summarize-blog-post "http://mock.com"))))))

(deftest test-summarize-paragraphs
  (testing "It returns the first paragraph, then only the first sentences of each following paragraph. Then the full last paragraph."
    (is (= '(
             {:summary "First. First." :remainder []} 
             {:summary "Second." :remainder ["Second."]} 
             {:summary "Third." :remainder ["Third."]} 
             {:summary "Last. Last." :remainder []})
           (summarize-paragraphs 
            '("First. First." "Second. Second." "Third. Third." "Last. Last."))))))

(deftest test-first-sentence-of-paragraph
  (testing "It returns the first sentence of a paragraph"
    (is (= "First sentence." 
           (first-sentence-of-paragraph "First sentence. Second sentence? Here is another one.")))))

(deftest test-rest-sentences-of-paragraph
  (testing "It returns the all but the first sentence of a paragraph"
    (is (= '("Second sentence?" "Here is another one.")
           (rest-sentences-of-paragraph "First sentence. Second sentence? Here is another one.")))))
