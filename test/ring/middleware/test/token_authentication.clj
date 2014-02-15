(ns ring.middleware.test.token-authentication
  (:use clojure.test
        ring.middleware.token-authentication))

(def tokenized-request {:headers {"authorization" "Token token=letmein"}})
(def bad-tokenized-request {:headers {"authorization" "Token token=dontletmein"}})
(def malformed-tokenized-request {:headers {"authorization" "Token=dontletmein"}})

(defn authentication [t]
  (= t "letmein"))

(deftest test-authentication-success
  (let [handler  (wrap-token-authentication identity authentication)
        response (handler tokenized-request)]
    (is (= true (:token-authentication response)))))

(deftest test-authentication-failure-without-token
  (let [handler  (wrap-token-authentication identity authentication)
        response (handler {:headers {}})]
    (is (= "unauthorized" (:body response)))))

(deftest test-authentication-failure-with-bad-token
    (let [handler  (wrap-token-authentication identity authentication)
          response (handler bad-tokenized-request)]
      (is (= "unauthorized" (:body response)))))

(deftest test-authentication-failure-with-malformed-token-header
  (let [handler  (wrap-token-authentication identity authentication)
        response (handler malformed-tokenized-request)]
    (is (= "unauthorized" (:body response)))))

(deftest test-authentication-failure-with-custom-response
  (let [handler  (wrap-token-authentication
                  identity authentication {:body "naughty, naughty!"})
        response (handler bad-tokenized-request)]
    (is (= "naughty, naughty!" (:body response)))))
