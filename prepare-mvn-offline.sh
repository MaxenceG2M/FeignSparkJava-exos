#!/usr/bin/env bash

rm -rf ./m2
mvn -Dmaven.repo.local=./m2/repository clean install -DskipTests
