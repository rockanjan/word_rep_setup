#!/bin/bash
#converts the muc7 tags in CONLL standard
if [ $# -ne 2 ]
then
    echo "Usage: `basename $0` input_file output_file"
    exit -1
fi
cp $1 $2;
sed -i 's/B-PERSON/B-PER/g' $2;
sed -i 's/I-PERSON/I-PER/g' $2;
sed -i 's/B-LOCATION/B-LOC/g' $2;
sed -i 's/I-LOCATION/I-LOC/g' $2;
sed -i 's/B-ORGANIZATION/B-ORG/g' $2;
sed -i 's/I-ORGANIZATION/I-ORG/g' $2;
sed -i 's/.\{1\}-NUMBER/O/g' $2;
sed -i 's/.\{1\}-MONEY/O/g' $2;
sed -i 's/.\{1\}-DATE/O/g' $2;
sed -i 's/.\{1\}-PERCENT/O/g' $2;
sed -i 's/.\{1\}-TIME/O/g' $2;

