#!/bin/bash

version=$1

docker run --name successmetrics2 -v $HOME:/var/tmp -p 4040:4040 successmetrics2:${version}
