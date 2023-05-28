#!/bin/bash

docker build -t cv/pizza-dashboard ./pizza-dashboard

cd ./pizza-backend
./gradlew clean build
docker build -t cv/pizza-backend ./
