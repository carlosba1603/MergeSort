#!/bin/bash

for streamType in {1..2}
do

    mkdir -p Test/$N
    file="Test/${N}/${streamType}.out"

    echo "" > $file
    echo "Stream Type = ${streamType}" >> $file
    echo "" >> $file


    for k in {1..30}
    do
        
        echo "" >> $file 
        echo "k = ${k} N = $1 " >> $file 
        echo "" >> $file

        
        echo java dsa.streams.interfaces.StreamUtil $k $N $streamType w
        echo "write" >> $file
        gtime -f ' %e' -o $file -a java dsa.streams.interfaces.StreamUtil $k $N $streamType w 
        echo java dsa.streams.interfaces.StreamUtil $k $1 $streamType r           
        echo "read" >> $file
        gtime -f ' %e' -o $file -a java dsa.streams.interfaces.StreamUtil $k $N $streamType r
    
        
    done
done

