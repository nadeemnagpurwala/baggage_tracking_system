package com.tracking.app.util;
import java.util.Scanner;

public class InputUtil {
    public static String readOption(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
