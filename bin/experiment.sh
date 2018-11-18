#!/bin/bash

for i in `seq 1 $1`
do
    echo "java dsa.streams.interfaces.StreamUtil" $2 $3
	time java dsa.streams.interfaces.StreamUtil $2 $3
done
