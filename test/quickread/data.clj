(ns quickread.data)

(def api-response {:title "Hello" :text "First paragraph. Two sentences.\nSecond paragraph. Two sentences.\nThird paragraph. Two sentences.\nLast paragraph. Two sentences."})

(def extracts 
  {:title "Hello"
   :paragraphs [
                {:summary "First paragraph. Two sentences." :remainder []}
                {:summary "Second paragraph." :remainder ["Two sentences."]}
                {:summary "Third paragraph." :remainder ["Two sentences."]}
                {:summary "Last paragraph. Two sentences." :remainder []}]
   })
