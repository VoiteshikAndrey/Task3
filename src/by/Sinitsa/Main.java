package by.Sinitsa;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String[] words = args;
        words = IsCorrect(words);
        int compChoice = compChoice(words);
        byte[] key = key();
        try {
            HMAC(key, words, compChoice);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        int choice = menu(words);

        if (choice == 0) {
            return;
        }
        System.out.println("Computer choice: " + words[compChoice - 1]);
        if (choice == compChoice) {
            System.out.println("Draw!");
        } else if ((choice - compChoice) <= (words.length - 1)) {
            if (choice > compChoice) {
                System.out.println("You win!");
            } else {
                System.out.println("You lose!");
            }
        } else {
            if (choice > compChoice) {
                System.out.println("You lose!");
            } else {
                System.out.println("You win!");
            }
        }
        System.out.println("HMAC key: " + DatatypeConverter.printHexBinary(key));
    }

    public static String[] IsCorrect(String[] words) {
        if (words.length % 2 != 0 && words.length > 1 && Unic(words)) {
            return words;

        } else {
            System.out.println("Input Error. Try again!");
            Scanner sc2 = new Scanner(System.in);
            String input = sc2.nextLine();
            words = input.split(" ");
            return IsCorrect(words);
        }

    }

    public static boolean Unic(String[] words) {
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].equals(words[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int compChoice(String[] words) {
        return (int) (1 + Math.random() * words.length);
    }

    public static void HMAC(byte[] key, String[] words, int compChoice) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        sha256_HMAC.init(new SecretKeySpec(key, "HmacSHA256"));
        byte[] result = sha256_HMAC.doFinal(words[compChoice - 1].getBytes());
        System.out.println("HMAC: " + DatatypeConverter.printHexBinary(result));
    }

    public static Integer menu(String[] words) {
        System.out.println("Choice your move:");
        for (int i = 0; i < words.length; i++) {
            System.out.println(i + 1 + " - " + words[i]);
        }
        System.out.println("0 - exit");
        Scanner sc1 = new Scanner(System.in);
        String choice = sc1.nextLine();
        int choiceInt = -1;
        try {
            Integer.parseInt(choice);
            choiceInt = Integer.parseInt(choice);
            if (choiceInt >= 0 && choiceInt <= words.length) {
                if (choiceInt != 0) {
                    System.out.println("\nYour choice: " + words[choiceInt - 1]);
                }
                return choiceInt;
            }
            System.out.println("\n" + "Invalid choice!" + "\n" + "Select again." + "\n");
            menu(words);
        } catch (NumberFormatException exception) {
            System.out.println("\n" + "Invalid choice!" + "\n" + "Select again." + "\n");
            menu(words);
        }

        return choiceInt;
    }

    public static byte[] key() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return bytes;

    }
}