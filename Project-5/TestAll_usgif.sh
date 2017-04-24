#!/bin/bash
echo Compiling...
javac -cp .:stdlib.jar:algs4.jar Huffman.java
javac -cp .:stdlib.jar:algs4.jar Quick3String.java
javac -cp .:stdlib.jar:algs4.jar BurrowsWheeler.java
javac -cp .:stdlib.jar:algs4.jar MoveToFront.java
echo
echo Testing Mirror Functionality with us.gif
echo -- Huffman --
java -cp .:stdlib.jar:algs4.jar Huffman - < us.gif | java -cp .:stdlib.jar:algs4.jar Huffman + > temp
diff us.gif temp
echo
echo -- BurrowsWheeler --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < us.gif | java -cp .:stdlib.jar:algs4.jar BurrowsWheeler + > temp
diff us.gif temp
echo
echo -- MoveToFront --
java -cp .:stdlib.jar:algs4.jar MoveToFront - < us.gif | java -cp .:stdlib.jar:algs4.jar MoveToFront + > temp
diff us.gif temp
echo
echo Testing Combined Functionality
echo
echo -- us.gif.bwt --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < us.gif > temp
diff us.gif.bwt temp
echo
echo -- us.gif.bwt.mtf.huf --
java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < us.gif | java -cp .:stdlib.jar:algs4.jar MoveToFront - | java -cp .:stdlib.jar:algs4.jar Huffman - > temp
diff us.gif.bwt.mtf.huf temp
echo
echo -- us.gif.huf --
java -cp .:stdlib.jar:algs4.jar Huffman - < us.gif > temp
diff us.gif.huf temp
echo
echo -- us.gif.mtf --
java -cp .:stdlib.jar:algs4.jar MoveToFront - < us.gif > temp
diff us.gif.mtf temp
rm temp *.class
echo

