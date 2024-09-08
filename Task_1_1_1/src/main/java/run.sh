: << 'COMMENT'
This script compile Heapsort.java into the Heapsort.class
after that we combine it into jar file Heapsort.jar and run it
COMMENT
javadoc -d docs ./ru/nsu/pozhidaev/Heapsort.java
javac ./ru/nsu/pozhidaev/Heapsort.java #compile Heapsort.java into Heapsort.class
jar -cfe Heapsort.jar ru.nsu.pozhidaev.Heapsort ru/nsu/pozhidaev/Heapsort.class # combine Heapsort.class into Heapsort.jar
java -jar Heapsort.jar # run Heapsort.jur file