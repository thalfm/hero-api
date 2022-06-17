(ns heroes-api.routes
  (:require [io.pedestal.http.route :as route]
            [heroes-api.service.heroes :as service.heroes]
            [io.pedestal.http.body-params :as body-params]))

(def endpoints
  (route/expand-routes
    #{["/heroes" :post [(body-params/body-params) service.heroes/create!] :route-name :create-heroes]
      ["/heroes/:id" :get service.heroes/show! :route-name :read-hero]
      ["/heroes/:id" :put [(body-params/body-params) service.heroes/update!] :route-name :update-heroes]
      ["/heroes/:id" :delete service.heroes/destroy! :route-name :delete-heroes]
      ["/heroes" :get service.heroes/index! :route-name :list-heroes]}))