package ru.nsu.pozhidaev;

import java.util.Scanner;

/**
 * The main class.
 */
public class Equations {

    /**
     * The main function.
     */
    public static void equations() {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        String input;
        Expression e;
        while (true) {
            System.out.println("Print 'exit' to leave or your math equation: ");
            input = scanner.nextLine();

            if (input.equals("exit")) {

                break;
            }
            e = parser.parse(input);
            System.out.println("Print your variables like 'x=10;y=2': ");
            input = scanner.nextLine();
            System.out.println(e.evaluate(input));
        }

    }
}