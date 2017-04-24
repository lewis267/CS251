#!/bin/bash
echo Compiling...
javac -cp .:stdlib.jar:algs4.jar Huffman.java
javac -cp .:stdlib.jar:algs4.jar Quick3String.java
javac -cp .:stdlib.jar:algs4.jar BurrowsWheeler.java
javac -cp .:stdlib.jar:algs4.jar MoveToFront.java
echo
echo Testing Mirror Functionality with abra.txt
echo -- Huffman --
java -cp .:stdlib.jar:algs4.jar Huffman - < abra.txt | java -cp .:stdlib.jar:algs4.jar Huffman + > temp.txt
diff abra.txt temp.txt
echo
echo -- BurrowsWheeler --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < abra.txt | java -cp .:stdlib.jar:algs4.jar BurrowsWheeler + > temp.txt
diff abra.txt temp.txt
echo
echo -- MoveToFront --
java -cp .:stdlib.jar:algs4.jar MoveToFront - < abra.txt | java -cp .:stdlib.jar:algs4.jar MoveToFront + > temp.txt
diff abra.txt temp.txt
echo
echo Testing Combined Functionality
echo
echo -- abra.txt.bwt --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < abra.txt > temp.txt
diff abra.txt.bwt temp.txt
echo
echo -- abra.txt.bwt.mtf.huf --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < abra.txt | java -cp .:stdlib.jar:algs4.jar MoveToFront - | java -cp .:stdlib.jar:algs4.jar Huffman - > temp.txt
diff abra.txt.bwt.mtf.huf temp.txt
echo
echo -- abra.txt.huf --
java -cp .:stdlib.jar:algs4.jar Huffman - < abra.txt > temp.txt
diff abra.txt.huf temp.txt
echo
echo -- abra.txt.mtf --
java -cp .:stdlib.jar:algs4.jar MoveToFront - < abra.txt > temp.txt
diff abra.txt.mtf temp.txt
echo

