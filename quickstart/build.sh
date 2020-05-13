#!/bin/bash

cd ..

mvn clean package spring-boot:repackage

cp -v ./target/smproto-0.1.jar ./runtime/successmetrics2.jar



