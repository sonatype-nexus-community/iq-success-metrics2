#!/bin/bash

cd ..

mvn clean package spring-boot:repackage

cp -v target/smproto-0.2.jar quickstart/successmetrics2.jar



