
for N in 1 4000000 31250000 62500000 125000000 250000000 500000000 1000000000 2000000000 4000000000
do

    for B in 2000 4000000 31250000 62500000 125000000 250000000 500000000 1000000000 2000000000
    do

        for streamType in {3..4}
        do

            mkdir -p Test/$N/$B
            file="Test/${N}/${B}/${streamType}.out"

            echo $file



            echo "" > $file
            echo "Stream Type = ${streamType}" >> $file
            echo "" >> $file


            for k in {1..30}
            do
                
                echo "" >> $file 
                echo "k = ${k} N = $1 " >> $file 
                echo "" >> $file

               
                echo java dsa.streams.interfaces.StreamUtil $k $N $streamType w $B
                echo "write" >> $file
                gtime -f ' %e' -o $file -a java dsa.streams.interfaces.StreamUtil $k $N $streamType w $B
                
                echo java dsa.streams.interfaces.StreamUtil $k $N $streamType r $B
                echo "read" >> $file
                gtime -f ' %e' -o $file -a java dsa.streams.interfaces.StreamUtil $k $N $streamType r $B

               
            done

            echo "" >> $file
        done
    done
done
