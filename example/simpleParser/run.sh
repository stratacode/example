#!/bin/sh

SCJAR=/jjv/vbuild/bin/sc.jar 
mkdir -p build
javac -d build src/sc/simpleParser/SimpleParser.java -classpath $SCJAR 
java -classpath build:$SCJAR sc.simpleParser.SimpleParser -srcPath testSrc "$@"
