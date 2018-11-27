#!/bin/bash


if [ $3 -eq 3 ]
then
        echo "java dsa.streams.interfaces.StreamUtil" $1 $2 $3 $4
            time java dsa.streams.interfaces.StreamUtil $1 $2 $3 $4
        else
                echo "java dsa.streams.interfaces.StreamUtil" $1 $2 $3
                    time java dsa.streams.interfaces.StreamUtil $1 $2 $3
                fi
