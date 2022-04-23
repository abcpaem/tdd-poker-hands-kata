package clan.techreturners;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.*;

public class PokerHands {
    private static final String PLAYER1 = "Player 1";
    private static final String PLAYER2 = "Player 2";
    private static final String TIE_MSG = "Tie.";
    private static final String WINNING_MSG = "%s wins. With %s";

    public enum Rank {
        UNKNOWN, HIGH_CARD, PAIR, TWO_PAIRS, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase().replaceAll("_", " ");
        }
    }

    public String getWinner(String firsthand, String secondHand) {
        Player winner = getWinner(new Player(firsthand, PLAYER1), new Player(secondHand, PLAYER2));
        return winner == null ? TIE_MSG : format(WINNING_MSG, winner.name, winner.getWinningCards());
    }

    public Rank getRank(String hand) {
        return new Player(hand).rank;
    }

    private Player getWinner(Player p1, Player p2) {
        Player winner = p1.rank.ordinal() > p2.rank.ordinal() ? p1 : p1.rank == p2.rank ? null : p2;

        if (winner == null) {
            if (p1.rank == Rank.PAIR) {
                winner = p1.getPair() > p2.getPair() ? p1 : p1.getPair() < p2.getPair() ? p2 : null;
            } else if (p1.rank == Rank.TWO_PAIRS) {
                winner = getWinnerWithHighestCards(p1.getPairs(), p2.getPairs(), p1, p2);
            } else if (p1.rank == Rank.FULL_HOUSE) {
                winner = p1.getThreeOfaKind() > p2.getThreeOfaKind() ? p1 : p1.getThreeOfaKind() < p2.getThreeOfaKind() ? p2 : null;
            } else if (p1.rank == Rank.FOUR_OF_A_KIND) {
                winner = p1.getFourOfaKind() > p2.getFourOfaKind() ? p1 : p1.getFourOfaKind() < p2.getFourOfaKind() ? p2 : null;
            }

            // Highest card will be the winner
            if (winner == null) {
                winner = getWinnerWithHighestCards(p1.getValues(), p2.getValues(), p1, p2);
            }
        }
        return winner;
    }

    private Player getWinnerWithHighestCards(int[] p1Values, int[] p2Values, Player p1, Player p2) {
        Player winner = null;
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
        return winner;
    }

    private class Player {
        private static final String FULL_HOUSE_MSG = "%s over %s";
        private static final String TWO_CARDS_MSG = "%s and %s";
        private static final String HIGH_CARD_MSG = " and High card: ";
        private static final String PAIR_HIGH_CARD_MSG = " and High pair/card: ";
        private final Rank rank;
        private final String name;
        private final TreeMap<Integer, String> hand;
        private final static HashMap<Character, Integer> cardValue = new HashMap<>();
        private String highestCard = "";
        private String winningCards = "";

        private Player(String hand) {
            this(hand, "");
        }

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
                this.winningCards = String.format(FULL_HOUSE_MSG, getCardName(getThreeOfaKind()), getCardName(getPair()));
            } else if (rank == Rank.FOUR_OF_A_KIND) {
                this.winningCards = "" + getCardName(getFourOfaKind());
            } else if (rank == Rank.THREE_OF_A_KIND) {
                this.winningCards = "" + getCardName(getThreeOfaKind());
            } else if (rank == Rank.TWO_PAIRS) {
                int[] pairs = getPairs();
                this.winningCards = String.format(TWO_CARDS_MSG, getCardName(pairs[0]), getCardName(pairs[1]));
            } else if (rank == Rank.PAIR) {
                this.winningCards = "" + getCardName(getPair());
            }
        }

        private int getPair() {
            return hand.entrySet().stream().filter(s -> s.getValue().length() == 2).findFirst().get().getKey();
        }

        private int[] getPairs() {
            return hand.entrySet().stream().filter(s -> s.getValue().length() == 2).map(Map.Entry::getKey).mapToInt(Integer::valueOf).toArray();
        }

        private int getThreeOfaKind() {
            return hand.entrySet().stream().filter(s -> s.getValue().length() == 3).findFirst().get().getKey();
        }

        private int getFourOfaKind() {
            return hand.entrySet().stream().filter(s -> s.getValue().length() == 4).findFirst().get().getKey();
        }

        private void setHighestCard(int value) {
            this.highestCard = getCardName(value);
        }

        private String getCardName(int value) {
            return switch (value) {
                case 11 -> "Jack";
                case 12 -> "Queen";
                case 13 -> "King";
                case 14 -> "Ace";
                default -> "" + value;
            };
        }

        private String getWinningCards() {
            String winCards = this.rank + ": ";
            String highCard = !highestCard.isEmpty() ? HIGH_CARD_MSG + highestCard : "";

            if (rank == Rank.HIGH_CARD) {
                winCards += highestCard;
            } else if (rank == Rank.PAIR) {
                winCards += getPair() + highCard;
            } else if (rank == Rank.TWO_PAIRS) {
                int[] pairs = getPairs();
                winCards += String.format(TWO_CARDS_MSG, getCardName(pairs[0]), getCardName(pairs[1])) + (!highestCard.isEmpty() ? PAIR_HIGH_CARD_MSG + highestCard : "");
            } else if (rank == Rank.STRAIGHT || rank == Rank.FLUSH || rank == Rank.STRAIGHT_FLUSH) {
                winCards = this.rank + highCard;
            } else winCards += this.winningCards;

            return winCards;
        }
    }
}