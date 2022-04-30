package clan.techreturners.poker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String firstHand = "Let's play poker!", secondHand = "oh yes!";

        System.out.println(firstHand);

        while (true) {
            System.out.println("Please enter the 1st player hand:");
            firstHand = reader.nextLine().trim().toUpperCase();
            if (firstHand.isBlank()) break;

            System.out.println("Please enter the 2nd player hand:");
            secondHand = reader.nextLine().trim().toUpperCase();
            if (secondHand.isBlank()) break;

            try {
                System.out.println(new PokerHands().getWinner(firstHand, secondHand) + "\n");
            } catch (Exception e) {
                System.err.println("There was an error calculating the winner.\nPlease make sure you enter a hand in the right format, e.g. 2H 3D TS 9C KD\n");
            }
        }
    }
}
