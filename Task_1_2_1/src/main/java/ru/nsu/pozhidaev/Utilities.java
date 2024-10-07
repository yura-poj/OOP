package ru.nsu.pozhidaev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class for useful functions.
 */
public class Utilities {
    private Scanner scanner;

    /**
     * constructor.
     *
     * @param fileName string of path of file.
     */
    public Utilities(String fileName) {
        File file;

        file = new File(fileName);

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * return new line from file without gaps.
     *
     * @return string - new line.
     */
    public String newLine() {
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine().replace(" ", "");
    }
}
