#!/bin/sh

cd bundles/example/example/simpleParser

./run.sh -typeName sc.test.Foo -from Foo -to Bar
./run.sh -typeName sc.test.Foo -from methodOne -to methodONE 
./run.sh -typeName sc.test.Foo -from fieldOne -to fieldONE 
