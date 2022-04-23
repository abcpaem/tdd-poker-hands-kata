package clan.techreturners;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.*;

public class PokerHands {
    public enum Rank {
        UNKNOWN, HIGH_CARD, PAIR, TWO_PAIRS, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase().replaceAll("_", " ");
        }
    }

    public String getWinner(String firsthand, String secondHand) {
        Player winner = getWinner(new Player(firsthand, "Player 1"), new Player(secondHand, "Player 2"));

        return winner == null ? "Tie." : format("%s wins. With %s", winner.name, winner.getWinningCards());
    }

    public Rank getRank(String hand) {
        return new Player(hand, "").rank;
    }

    private Player getWinner(Player p1, Player p2) {
        Player winner = p1.rank.ordinal() > p2.rank.ordinal() ? p1 : p1.rank == p2.rank ? null : p2;

        if (winner == null) {
            if (p1.rank == Rank.PAIR) {
                winner = p1.getPair() > p2.getPair() ? p1 : p1.getPair() < p2.getPair() ? p2 : null;
            }

            // Highest card will be the winner
            if (winner == null) {
                int[] p1Values = p1.getValues();
                int[] p2Values = p2.getValues();
                for (int i = p1Values.length - 1; i >= 0; i--) {
                    if (p1Values[i] > p2Values[i]) {
                        p1.setHighestCard(p1Values[i]);
                        winner = p1;
                        break;
                    } else if (p1Values[i] < p2Values[i]) {
                        p2.setHighestCard(p2Values[i]);
                        winner = p2;
                        break;
                    }
                }
            }
        }
        return winner;
    }

    private class Player {
        private final Rank rank;
        private final String name;
        private final TreeMap<Integer, String> hand;
        private final static HashMap<Character, Integer> cardValue = new HashMap<>();
        private String highestCard = "";
        private String winningCards = "";

        private Player(String hand, String name) {
            for (int i = 2; i < 10; i++) cardValue.put((char) (i + '0'), i);
            cardValue.put('T', 10);
            cardValue.put('J', 11);
            cardValue.put('Q', 12);
            cardValue.put('K', 13);
            cardValue.put('A', 14);
            this.hand = getPlayerHand(hand.split("\\s+"));
            this.rank = getRank(this.hand);
            this.name = name;
            setWinningCards();
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

        private Rank getRank(TreeMap<Integer, String> hand) {
            var handValues = hand.keySet();
            var handSuits = hand.values();
            var countValues = handValues.size();

            // Check if hand contains 5 cards with consecutive values
            var consecutiveValues = IntStream.iterate((Integer) handValues.toArray()[0], i -> i + 1)
                    .limit(countValues).boxed()
                    .collect(Collectors.toList());
            boolean isStraight = new ArrayList<>(handValues).equals(consecutiveValues) && countValues == 5;

            // Check if hand contains 5 cards of the same suit
            boolean isSameSuit = handSuits.stream().distinct().count() <= 1 && countValues == 5;

            // Check if hand contains only one pair
            var containsOnePair = handSuits.stream().filter(s -> s.length() == 2).count() == 1;
            boolean isOnePair = containsOnePair && countValues == 4;

            // Check if hand contains two pairs
            boolean isTwoPairs = handSuits.stream().filter(s -> s.length() == 2).count() == 2 && countValues == 3;

            // Check if hand contains 3 cards with same value
            var containsThreeOfaKind = handSuits.stream().filter(s -> s.length() == 3).count() == 1;
            boolean isThreeOfaKind = containsThreeOfaKind && countValues == 3;

            // Check if hand contains 5 cards of the same suit
            boolean isFlush = handSuits.stream().allMatch(hand.firstEntry().getValue()::equals) && countValues == 5;

            // Check if hand contains 3 cards of the same value, with the remaining 2 cards forming a pair
            boolean isFullHouse = containsThreeOfaKind && containsOnePair && countValues == 2;

            // Check if hand contains 4 cards with the same value
            boolean isFourOfaKind = handSuits.stream().filter(s -> s.length() == 4).count() == 1 && countValues == 2;

            // Check if hand contains 5 cards of the same suit with consecutive values
            boolean isStraightFlush = isStraight && isSameSuit;

            if (isStraightFlush) return Rank.STRAIGHT_FLUSH;
            if (isFourOfaKind) return Rank.FOUR_OF_A_KIND;
            if (isFullHouse) return Rank.FULL_HOUSE;
            if (isFlush) return Rank.FLUSH;
            if (isStraight && !isSameSuit) return Rank.STRAIGHT;
            if (isThreeOfaKind) return Rank.THREE_OF_A_KIND;
            if (isTwoPairs) return Rank.TWO_PAIRS;
            if (isOnePair) return Rank.PAIR;
            if (!isStraight && !isSameSuit) return Rank.HIGH_CARD;

            return Rank.UNKNOWN;
        }

        private int[] getValues() {
            return hand.keySet().stream().mapToInt(Integer::valueOf).toArray();
        }

        private void setWinningCards() {
            if (rank == Rank.FULL_HOUSE) {
                this.winningCards = String.format("%s over %s",
                        hand.entrySet().stream().filter(s -> s.getValue().length() == 3).findFirst().get().getKey().toString(),
                        hand.entrySet().stream().filter(s -> s.getValue().length() == 2).findFirst().get().getKey().toString());
            } else if (rank == Rank.PAIR) {
                this.winningCards = "" + getPair();
            }
        }

        private int getPair() {
            return hand.entrySet().stream().filter(s -> s.getValue().length() == 2).findFirst().get().getKey();
        }

        private void setHighestCard(int value) {
            this.highestCard = switch (value) {
                case 11 -> "Jack";
                case 12 -> "Queen";
                case 13 -> "King";
                case 14 -> "Ace";
                default -> "" + value;
            };
        }

        public String getWinningCards() {
            String winCards = this.rank + ": ";

            if (rank == Rank.HIGH_CARD) winCards += highestCard;
            else if (rank == Rank.PAIR)
                winCards += getPair() + (!highestCard.isEmpty() ? " and High card: " + highestCard : "");
            else winCards += this.winningCards;

            return winCards;
        }
    }
}