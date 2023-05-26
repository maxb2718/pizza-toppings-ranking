#!/bin/bash

./gradlew clean build

docker build -t cv/pizza-backend ./