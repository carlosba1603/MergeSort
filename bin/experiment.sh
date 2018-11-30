#!/bin/bash

for streamType in {1..4}
do
    
    echo "" > ${streamType}.out
    echo "Stream Type = ${streamType}" >> ${streamType}.out
    echo "" >> ${streamType}.out


    for k in {1..30}
    do
        
        echo "" >> ${streamType}.out 
        echo "k = ${k} N = $1 " >> ${streamType}.out 
        echo "" >> ${streamType}.out

        if [ $streamType -eq 3 ]
        then

            gtime -f ' %e' -o ${streamType}.out -a  echo java dsa.streams.interfaces.StreamUtil $k $1 $streamType $2
            #gtime -f ' %e' -o ${streamType}.out -a java dsa.streams.interfaces.StreamUtil $k $1 $streamType $2
        else
            gtime -f ' %e' -o ${streamType}.out -a  echo java dsa.streams.interfaces.StreamUtil $k $1 $streamType
            #gtime -f ' %e' -o ${streamType}.out -a java dsa.streams.interfaces.StreamUtil $k $1 $streamType
        fi
    done

    echo "" >> ${streamType}.out
done
