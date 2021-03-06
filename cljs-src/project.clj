(defproject gooreplacer "2.0.0"
  :description "Modify, block URLs & Headers"
  :url "https://github.com/jiacai2050/gooreplacer4chrome"
  :license {:name "MIT"
            :url "http://liujiacai.net/license/MIT.html?year=2015"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.8.0-alpha2"]
                 [cljsjs/react-bootstrap "0.31.0-0"]
                 [antizer "0.2.2"]
                 [com.cemerick/piggieback "0.2.1"]
                 [figwheel-sidecar "0.5.14"]
                 [alandipert/storage-atom "2.0.1"]
                 [org.clojure/core.async "0.3.443"]
                 [org.clojure/core.match "0.3.0-alpha5"]
                 [cljs-http "0.1.43"]]
  :plugins [[lein-figwheel "0.5.14"]
            [lein-cljsbuild "1.1.7"]
            [lein-doo "0.1.8"]]
  :profiles {:dev {:repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             :dev-option {:source-paths ["src/option"]
                          :clean-targets ^{:protect false} [:target-path "resources/dev/option/js"] 
                          :cljsbuild {:builds [{:id "dev"
                                                :figwheel true
                                                :source-paths ["src/option" "src/common"]
                                                :compiler {:output-to "resources/dev/option/js/main.js"
                                                           :source-map true
                                                           :preloads [gooreplacer.preloads]
                                                           :asset-path "js"
                                                           :output-dir "resources/dev/option/js"
                                                           :optimizations :none
                                                           :main gooreplacer.core
                                                           :verbose true}}]}
                          :figwheel {:server-port 8080
                                     :http-server-root "dev/option"
                                     :css-dirs ["resources/dev/option/css"]
                                     :server-logfile ".figwheel_option.log"
                                     :repl true}}
             :dev-bg {:source-paths ["src/background" "src/common"] 
                      :clean-targets ^{:protect false} [:target-path "resources/dev/background/js"] 
                      :figwheel {:server-port 8081
                                 :http-server-root "dev/background"
                                 :server-logfile ".figwheel_bg.log"
                                 :repl true}
                      :cljsbuild {:builds [{:id "dev"
                                            :figwheel true
                                            :source-paths ["src/background"]
                                            :compiler {:output-to "resources/dev/background/js/main.js"
                                                       :source-map true
                                                       :output-dir "resources/dev/background/js"
                                                       :preloads [gooreplacer.preloads]
                                                       :asset-path "js"
                                                       :main gooreplacer.core
                                                       :optimizations :none
                                                       :verbose true}}]}}
             :release-option {:source-paths ["src/option" "src/common"] 
                              :clean-targets ^{:protect false} [:target-path "resources/release/option/js"] 
                              :cljsbuild {:builds [{:source-paths ["src/option" "src/common"]
                                                    :compiler {:output-to "resources/release/option/main.js"
                                                               :output-dir "resources/release/option/js"
                                                               :externs ["externs/chrome_extensions.js" "externs/chrome.js"]
                                                               :optimizations :advanced
                                                               :main gooreplacer.core}}]}}
             :release-bg {:source-paths ["src/background" "src/common"] 
                          :clean-targets ^{:protect false} [:target-path "resources/release/background/js"] 
                          :cljsbuild {:builds [{:source-paths ["src/background" "src/common"]
                                                :compiler {:output-to "resources/release/background/main.js"
                                                           :output-dir "resources/release/background/js"
                                                           :externs ["externs/chrome_extensions.js" "externs/chrome.js"]
                                                           :optimizations :advanced
                                                           :main gooreplacer.core}}]}}
             :test { :cljsbuild {:builds [{:id "test"
                                           :source-paths ["test" "src/common"]
                                           :compiler {:output-to "out/main.js"
                                                      :main gooreplacer.runner
                                                      :optimizations :none}}]}}}
  
  :aliases {"option"   ["with-profile" "dev-option" "do"
                        ["clean"]
                        ["figwheel" "dev"]]
            "bg"       ["with-profile" "dev-bg" "do"
                        ["clean"]
                        ["figwheel" "dev"]]
            "test-all" ["with-profile" "test" "do"
                        ["clean"]
                        ;; First install phantom
                        ["doo" "phantom" "test"]]})
