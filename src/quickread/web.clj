(ns quickread.web
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:use [hiccup.core]
        [hiccup.page]
        [hiccup.form]
        [hiccup.element]
        [quickread.core]))

(defn with-html [content]
  (html5 {:lang "en"} 
         [:head 
          (include-css "/bootstrap.css")
          (include-css "/style.css")
          (include-js "//code.jquery.com/jquery-2.1.4.min.js")
          (include-js "/app.js")
          ] 
         [:body 
          [:div {:class "container"} 
           content]]))

(defn home-layout [content]
  (with-html
    content))

(defn page-layout [content]
  (with-html
    [:div {:style "margin-top: 80px;"}
     [:nav {:class "navbar navbar-default navbar-fixed-top"}
      (link-to {:class "navbar-brand"} "/" "Home")]
     [:div {:class "row"} 
      [:div {:class "col-md-3"}]
      [:div {:class "col-md-6"} 
       content]]]))

(defn get-summary [url]
  (let [extract (summarize-blog-post url)]
    [:article 
     [:h1 (:title extract)]
     [:hr]
     (for [x (:paragraphs extract) ]
       [:section
        [:p {:class "lead text-primary"} (:summary x)
         "&nbsp;"
         (if (> (count (:remainder x)) 0)

           (link-to {:onclick (str "expandSecondary(" (Math/abs (hash (:remainder x))) ")")} "#secondary" "+"))]

        (if (first (:remainder x))
          [:p {:class (str  (Math/abs (hash (:remainder x))) " text-secondary bg-info")} 
           (first (:remainder x))
           (map (fn [sentence] [:p {:class (str (Math/abs (hash (:remainder x))) " text-ternary bg-warning")} 
                                sentence]) (rest (:remainder x)))])

        [:br]
        ])]))

(defn home-action []
  (html (home-layout   
         [:div
          [:h1 {:class "text-center"} "Distill blog posts, essays and articles into a quick read."]
          [:hr]
          [:row
           [:div {:class "col-md-4"}]
           [:div {:class "col-md-5"}
            (form-to {:class "form-inline"} [:get "/read"]
                     [:div {:class "form-group"}
                      (text-field {:class "form-control input-lg"} "url")]
                     (submit-button {:class "btn btn-lg btn-primary"} "Read quick"))]]])))

(defn read-action [req]
  (let [url (get (:params req) :url)]
    (html (page-layout (get-summary url)))))

(defroutes app-routes
  (GET "/" [] (home-action))
  (GET "/read" req (read-action req))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
