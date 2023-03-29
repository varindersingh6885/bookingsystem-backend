#!/bin/bash

mvn clean install -f ./apigateway
docker build -t varindersingh6885/nagpbookingsystem_api-gatway ./apigateway
docker push varindersingh6885/nagpbookingsystem_api-gatway

mvn clean install -f ./booking-saga-orchestrator
docker build -t varindersingh6885/nagpbookingsystem_booking-saga-orchestrator ./booking-saga-orchestrator
docker push varindersingh6885/nagpbookingsystem_booking-saga-orchestrator

mvn clean install -f ./booking-service
docker build -t varindersingh6885/nagpbookingsystem_booking-service ./booking-service
docker push varindersingh6885/nagpbookingsystem_booking-service

mvn clean install -f ./db-flights-service
docker build -t varindersingh6885/nagpbookingsystem_db-flights-service ./db-flights-service
docker push varindersingh6885/nagpbookingsystem_db-flights-service

mvn clean install -f ./discovery-server
docker build -t varindersingh6885/nagpbookingsystem_discovery-server ./discovery-server
docker push varindersingh6885/nagpbookingsystem_discovery-server

mvn clean install -f ./flights-service
docker build -t varindersingh6885/nagpbookingsystem_flights-service ./flights-service
docker push varindersingh6885/nagpbookingsystem_flights-service

mvn clean install -f ./identity-service
docker build -t varindersingh6885/nagpbookingsystem_identity-service ./identity-service
docker push varindersingh6885/nagpbookingsystem_identity-service

mvn clean install -f ./payment-service
docker build -t varindersingh6885/nagpbookingsystem_payments-service ./payment-service
docker push varindersingh6885/nagpbookingsystem_payments-service