javac ./ru/nsu/pozhidaev/Main.java
jar -cfe Main.jar ru.nsu.pozhidaev.Main ru/nsu/pozhidaev/Main.class
java -jar Main.jar
rm ru/nsu/pozhidaev/Main.class
rm Main.jar

