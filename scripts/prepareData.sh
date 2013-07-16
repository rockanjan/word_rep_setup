#!/bin/bash
if [ $# -ne 1 ]
then
    echo "Usage: `basename $0` {rep_file}"
    exit -1
fi

train_length=219554
testa_length=55044
testb_length=50350
muc7_length=61282

testa_start=$(($train_length+1))
testa_end=$(($testa_start+$testa_length-1))

testb_start=$(($testa_end+1))
testb_end=$(($testb_start+$testb_length-1))

muc7_start=$(($testb_end+1))
muc7_end=$(($muc7_start+$muc7_length-1))

sed_train_param="1,$train_length"
sed_testa_param="$testa_start,$testa_end"
sed_testb_param="$testb_start,$testb_end"
sed_muc7_param="$muc7_start,$muc7_end"

#create temp represenatation file separated by spaces instead of |
tempfile="/tmp/combined.rep"
awk -F" " '{print $2}' $1 | tr '|' ' ' > $tempfile
#separate
sed -n "`echo $sed_train_param`p" $tempfile > /tmp/train
sed -n "`echo $sed_testa_param`p" $tempfile > /tmp/testa
sed -n "`echo $sed_testb_param`p" $tempfile > /tmp/testb
sed -n "`echo $sed_muc7_param`p" $tempfile > /tmp/muc7

#combine
paste -d " " train_iob2.txt /tmp/train > train_rep.txt
paste -d " " testa_iob2.txt /tmp/testa > testa_rep.txt
paste -d " " testb_iob2.txt /tmp/testb > testb_rep.txt
paste -d " " muc7_iob2.txt /tmp/muc7 > muc7_rep.txt




