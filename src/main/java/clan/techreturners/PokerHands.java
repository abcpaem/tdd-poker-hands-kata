package clan.techreturners;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PokerHands {
    public enum Rank {UNKNOWN, HIGH_CARD;}

    HashMap<Character, Integer> cardValue = new HashMap<>();

    public PokerHands() {
        for (int i = 2; i < 10; i++) cardValue.put((char) (i + '0'), i);
        cardValue.put('T', 10);
        cardValue.put('J', 11);
        cardValue.put('Q', 12);
        cardValue.put('K', 13);
        cardValue.put('A', 14);
    }

    public Rank getRank(String hand) {
        var cards = hand.split("\\s+");
        var playerHand = getPlayerHand(cards);
        var handValues = playerHand.keySet();
        var handSuits = playerHand.values();

        // Check if hand contains 5 cards with consecutive values
        var consecutiveValues = IntStream.iterate((Integer) handValues.toArray()[0], i -> i + 1)
                .limit(handValues.size()).boxed()
                .collect(Collectors.toList());
        boolean isStraight = new ArrayList<>(handValues).equals(consecutiveValues) && handValues.size() == 5;

        // Check if hand contains 5 cards of the same suit
        boolean isSameSuit = handSuits.stream().distinct().count() <= 1 && handValues.size() == 5;

        if (!isStraight && !isSameSuit)
            return Rank.HIGH_CARD;

        return Rank.UNKNOWN;
    }

    private TreeMap<Integer, String> getPlayerHand(String[] cards) {
        TreeMap<Integer, String> p = new TreeMap<>();

        for (String card : cards) {
            Integer value = cardValue.get(card.charAt(0));
            String suit = p.containsKey(value) ? p.get(value) + card.substring(1) : card.substring(1);
            p.put(value, suit);
        }
        return p;
    }
}