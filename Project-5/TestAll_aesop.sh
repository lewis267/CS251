#!/bin/bash
echo Compiling...
javac -cp .:stdlib.jar:algs4.jar Huffman.java
javac -cp .:stdlib.jar:algs4.jar Quick3String.java
javac -cp .:stdlib.jar:algs4.jar BurrowsWheeler.java
javac -cp .:stdlib.jar:algs4.jar MoveToFront.java
echo
echo Testing Mirror Functionality with aesop.txt
echo -- Huffman --
java -cp .:stdlib.jar:algs4.jar Huffman - < aesop.txt | java -cp .:stdlib.jar:algs4.jar Huffman + > temp.txt
diff aesop.txt temp.txt
echo
echo -- BurrowsWheeler --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < aesop.txt | java -cp .:stdlib.jar:algs4.jar BurrowsWheeler + > temp.txt
diff aesop.txt temp.txt
echo
echo -- MoveToFront --
java -cp .:stdlib.jar:algs4.jar MoveToFront - < aesop.txt | java -cp .:stdlib.jar:algs4.jar MoveToFront + > temp.txt
diff aesop.txt temp.txt
echo
echo Testing Combined Functionality
echo
echo -- aesop.txt.bwt --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < aesop.txt > temp.txt
diff aesop.txt.bwt temp.txt
echo
echo -- aesop.txt.bwt.mtf.huf --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < aesop.txt | java -cp .:stdlib.jar:algs4.jar MoveToFront - | java -cp .:stdlib.jar:algs4.jar Huffman - > temp.txt
diff aesop.txt.bwt.mtf.huf temp.txt
echo
echo -- aesop.txt.huf --
java -cp .:stdlib.jar:algs4.jar Huffman - < aesop.txt > temp.txt
diff aesop.txt.huf temp.txt
echo
echo -- aesop.txt.mtf --
java -cp .:stdlib.jar:algs4.jar MoveToFront - < aesop.txt > temp.txt
diff aesop.txt.mtf temp.txt
echo

