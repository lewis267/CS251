#!/bin/bash
echo Compiling...
javac -classpath .:stdlib.jar Point.java
javac -classpath .:stdlib.jar Brute.java
javac -classpath .:stdlib.jar Fast.java
javac -classpath .:stdlib.jar PointPlotter.java
javac -classpath .:stdlib.jar Test.java
echo
java -classpath .:stdlib.jar Test 10 20 50
echo
java -classpath .:stdlib.jar Brute < input50.txt
#java -classpath .:stdlib.jar PointPlotter < input100.txt
echo
echo "#### Difference ####"
diff visualPoints.txt brute_output50.txt
echo "#### End Difference ####"
echo
