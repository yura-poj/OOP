: << 'COMMENT'
This script compile Main.java into the Main.class
after that we combine it into jar file Main.jar and run it
to avoid extra files we delete them
COMMENT
javac ./ru/nsu/pozhidaev/Main.java #compile Main.java into Main.class
jar -cfe Main.jar ru.nsu.pozhidaev.Main ru/nsu/pozhidaev/Main.class # combine Main.class into Main.jar
java -jar Main.jar # run Main.jur file
rm ru/nsu/pozhidaev/Main.class # delete extra file Main.class after running the program
rm Main.jar # delete extra file Main.jar after running the program