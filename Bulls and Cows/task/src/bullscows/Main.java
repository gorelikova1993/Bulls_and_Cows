package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final String dictionary = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static void main(String[] args) {
        int turn = 0;
        int secretLength = 0;


        System.out.println("Input the length of the secret code:");

        try {
            secretLength =  Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error: \"abc 0 -7\" isn't a valid number.");
            System.exit(0);
        }

        if (secretLength == 0) {
            System.out.println("Error: 0 isn't a valid number.");
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");

        int possibleSymbols = Integer.parseInt(scanner.nextLine());

        if (possibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }

        if (secretLength > possibleSymbols) {
            System.out.println("Error: it's not possible to generate a code with a length of " + secretLength + "" +
                    " with " + possibleSymbols + " unique symbols.");
            System.exit(0);
        }

        StringBuilder asterisk = new StringBuilder();
        int i = 0;
        while (i < secretLength) {
            asterisk.append("*");
            i++;
        }
        String range = "(0";
        if (possibleSymbols > 10 ) {
            range = range + "-9, a-" + dictionary.charAt(possibleSymbols - 1) + ")";
        } else {
            range = range + "-" + dictionary.charAt(possibleSymbols-1);
        }
        System.out.println("The secret is prepared: " + asterisk + " " + range);
                //**** (0-9, a-f).");
        System.out.println("Okay, let's start a game!");
        String randomSecret = getRandomSecret(secretLength, possibleSymbols);
        String guess = "";
        while (!guess.equals(randomSecret)) {
            turn++;
            System.out.println("Turn " + turn + ":");
            guess = scanner.nextLine();
            calculateScore(randomSecret,guess);
        }
        scanner.close();
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public static void calculateScore(String randNum, String guess) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < guess.length(); i++) {
            int index = randNum.indexOf(guess.charAt(i));
            //System.out.println(guess.charAt(i) + " " + index);
            if (index == i) {
                bulls++;
            } else if (index >= 0) {
                cows++;
            }
        }

        String bull = bulls == 1 ? "bull" : "bulls";
        String cow = cows == 1 ? "cow" : "cows";

        if (bulls != 0 && cows != 0) {
            System.out.println("Grade: " + bulls + " " + bull + " and " + cows + " " + cow);
        } else if (bulls != 0) {
            System.out.println("Grade: " + bulls + " " + bull);
        } else if (cows != 0) {
            System.out.println("Grade: " + cows + " " + cow);
        } else {
            System.out.println("Grade: None");
        }
    }

    static String getRandomSecret(int length, int possibleSymbols){
        StringBuilder result = new StringBuilder();
        char random;
        int index;

        while(true){
            index  = ThreadLocalRandom.current().nextInt(0, possibleSymbols);
            random = dictionary.charAt(index);
            if (!result.toString().contains(String.valueOf(random))){
                result.append(random);
            }
            if(result.length() == length){
                break;
            }
        }

        return result.toString();
    }
}
