#!/bin/bash

FileName=$1
M=$2

mkdir -p Test/MergeSort/$M
file="Test/MergeSort/${M}/${FileName}"

echo "" > $file

for d in 23
do

	echo $file

	echo "" >> $file
	echo "N = ${FileName} M=${M} d=${d}" >> $file
	echo "" >> $file

	echo java dsa.dwaymerge.external.ExternalDWayMergeSort ${FileName} ${M} ${d}
	gtime -f ' %e' -o $file -a java dsa.dwaymerge.external.ExternalDWayMergeSort "../Data/${FileName}" $M $d

done