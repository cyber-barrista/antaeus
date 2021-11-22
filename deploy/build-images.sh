#!/bin/bash

set -e

# TODO Refactor once proper CI is introduced
cd ..
./gradlew shadowJar
cd deploy

cp ../pleo-antaeus-app/build/libs/* ./antaeus

docker build ./antaeus --tag antaeus

docker build ./payment-manager --tag payment-manager
