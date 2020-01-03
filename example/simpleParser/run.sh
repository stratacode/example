#!/bin/bash

SCC_CMD=`which scc`
if [ "$SCC_CMD" = "" ] ; then
  echo "scc command not found in path"
  exit 1
fi

SC_BIN_DIR=`dirname $SCC_CMD`
SC_JAR=${SC_BIN_DIR}/sc.jar

mkdir -p build
javac -d build src/sc/simpleParser/SimpleParser.java -classpath $SC_JAR 
java -classpath build:$SC_JAR sc.simpleParser.SimpleParser -srcPath testSrc "$@"
