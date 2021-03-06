package ru.itskills.prs.ui;

import ru.itskills.prs.util.NumberUtil;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static ru.itskills.prs.util.NumberUtil.UNKNOWN;

/**
 * Encapsulates the logic of communication with user interface making it pluggable and testable
 */
public class UserInterface {

    private final Scanner in;
    private final PrintStream out;

    public UserInterface(InputStream is, PrintStream out) {
        this.in = new Scanner(is);
        this.out = out;
    }

    /**
     * Prints the message to the output stream
     *
     * @param text to be printed
     */
    public void sendMessage(String text) {
        out.println(text);
    }

    /**
     * Requests a user's input for a user name
     *
     * @return user name
     */
    public String getUserName() {
        return in.nextLine();
    }

    /**
     * Requests a user's input for a shape
     *
     * @return a string input representing the chosen shape
     */
    public String getUserChoice() {
        return in.nextLine().toUpperCase();
    }

    /**
     * Requests a user's input for a desirable number of rounds
     *
     * @return number of rounds or {@link NumberUtil#UNKNOWN} in case of an error
     */
    public int getNumberOfRounds() {
        try {
            return Integer.parseInt(in.nextLine());
        } catch (NoSuchElementException | NumberFormatException e) {
            return UNKNOWN;
        }
    }
}
