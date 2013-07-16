#!/bin/bash
if [ $# -ne 1 ] 
then
	echo "Usage: `basename $0` {modelfile}"
	exit -1
fi

echo "__________TRAIN_________"
crf_test -m $1 train_rep.txt.features | awk -F " " '{print $NF}' > /tmp/train.pred
paste -d " " ../train_iob2.txt /tmp/train.pred > /tmp/train.gold.pred
./conlleval < /tmp/train.gold.pred
echo "________________________"

echo "__________TESTA_________"
crf_test -m $1 testa_rep.txt.features | awk -F " " '{print $NF}' > /tmp/testa.pred
paste -d " " ../testa_iob2.txt /tmp/testa.pred > /tmp/testa.gold.pred
./conlleval < /tmp/testa.gold.pred
echo "________________________"

echo "__________TESTB_________"
crf_test -m $1 testb_rep.txt.features | awk -F " " '{print $NF}' > /tmp/testb.pred
paste -d " " ../testb_iob2.txt /tmp/testb.pred > /tmp/testb.gold.pred
./conlleval < /tmp/testb.gold.pred
echo "________________________"

echo "__________MUC7_________"
crf_test -m $1 muc7_rep.txt.features | awk -F " " '{print $NF}' > /tmp/muc7.pred
#remove MISC from the predictions
sed -i 's/.\{1\}-MISC/O/g' /tmp/muc7.pred
paste -d " " ../muc7_iob2.txt /tmp/muc7.pred > /tmp/muc7.gold.pred
./conlleval < /tmp/muc7.gold.pred
echo "________________________"




