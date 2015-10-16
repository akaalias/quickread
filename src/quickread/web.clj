(ns quickread.web
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:use [hiccup.core]
        [hiccup.page]
        [quickread.core]))

(defn layout [content]
  (html5 {:lang "en"} 
         [:head (include-css "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css")
          (include-css "/style.css")
          ] 
         [:body 
          [:div {:class "container"} 
           [:div {:class "row"} 
            [:div {:class "col-md-6"} 
             content]]]]))

(defn get-summary []
  (for [x (summarize-blog-post "http://blog.ycombinator.com/yc-continuity-fund")]
    [:p x]))

(defroutes app-routes
  (GET "/" [] (html (layout (get-summary))))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
