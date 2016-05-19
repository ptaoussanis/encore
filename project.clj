(defproject com.taoensso/encore "2.53.0"
  :author "Peter Taoussanis <https://www.taoensso.com>"
  :description "Core utils library for Clojure/Script"
  :url "https://github.com/ptaoussanis/encore"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}
  :min-lein-version "2.3.3"
  :global-vars {*warn-on-reflection* true
                *assert*             true
                ;; *unchecked-math*  :warn-on-boxed
                }

  :dependencies
  [[org.clojure/clojure      "1.5.1"]
   [org.clojure/clojurescript "1.7.145" :scope "provided"]
   [org.clojure/tools.reader "0.10.0"]
   [com.taoensso/truss       "1.2.0"]]

  :plugins
  [[lein-pprint  "1.1.2"]
   [lein-ancient "0.6.10"]
   [lein-codox   "0.9.5"]]

  :profiles
  {;; :default [:base :system :user :provided :dev]
   :server-jvm {:jvm-opts ^:replace ["-server"]}
   :1.5  {:dependencies [[org.clojure/clojure "1.5.1"]]}
   :1.6  {:dependencies [[org.clojure/clojure "1.6.0"]]}
   :1.7  {:dependencies [[org.clojure/clojure "1.7.0"]]}
   :1.8  {:dependencies [[org.clojure/clojure "1.8.0"]]}
   :test {:dependencies [[org.clojure/test.check "0.9.0"]]}
   :dev
   [:1.8 :test
    {:dependencies
     [[org.clojure/clojurescript "1.7.145"]
      [org.clojure/core.async    "0.2.374"]]

     :plugins
     [;; These must be in :dev, Ref. https://github.com/lynaghk/cljx/issues/47:
      [lein-cljsbuild      "1.1.3"]]}]}

  ;; :jar-exclusions [#"\.cljx|\.DS_Store"]

  :source-paths ["src"]
  :test-paths   ["src" "test"]

  :cljsbuild
  {:test-commands {}
   :builds
   [{:id "main"
     :source-paths   ["src"]
     ;; :notify-command ["terminal-notifier" "-title" "cljsbuild" "-message"]
     :compiler       {:output-to "target/main.js"
                      :optimizations :advanced
                      :pretty-print false}}
    {:id "tests"
     :source-paths   ["src" "test"]
     ;; :notify-command []
     :compiler       {:output-to "target/tests.js"
                      :optimizations :whitespace
                      :pretty-print true
                      :main "taoensso.encore.tests"}}]}

  :auto-clean false

  :codox
  {:language :clojure ; [:clojure :clojurescript] ; No support?
   :source-paths ["target/classes"]
   :source-uri
   {#"target/classes" "https://github.com/ptaoussanis/encore/blob/master/src/{classpath}x#L{line}"
    #".*"             "https://github.com/ptaoussanis/encore/blob/master/{filepath}#L{line}"}}

  :aliases
  {"test-all"   ["do" "clean,"
                 "with-profile" "+1.7:+1.8" "test,"
                 ;; "with-profile" "+test" "cljsbuild" "test"
                 ]
   "build-once" ["do" "clean," "cljsbuild" "once" "main"]
   "deploy-lib" ["do" "build-once," "deploy" "clojars," "install"]
   "start-dev"  ["with-profile" "+server-jvm" "repl" ":headless"]}

  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"})
