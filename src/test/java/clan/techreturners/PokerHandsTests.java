package clan.techreturners;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokerHandsTests {
    @ParameterizedTest(name = "{index}) For hand \"{0}\", its rank is {1}")
    @CsvSource(delimiterString = "->", textBlock = """
            2H 5S 3D 9C KD -> HIGH_CARD
            2H 5S 3D 3C KD -> PAIR
            5H 5S 3D 3C KD -> TWO_PAIRS
            5H 3S 3D 3C KD -> THREE_OF_A_KIND
            TH JS QD KC AD -> STRAIGHT
            2H 5H 3H 9H KH -> FLUSH
            2H 2S 2D KC KD -> FULL_HOUSE
            2H 2S 2D 2C KD -> FOUR_OF_A_KIND
            8H 9H TH JH QH -> STRAIGHT_FLUSH
            """)
    void checkHandForRank(String hand, PokerHands.Rank expectedRank) {
        assertEquals(expectedRank, new PokerHands().getRank(hand));
    }

    @ParameterizedTest(name = "{index}) For 2 hands: \"{0}\" and \"{1}\", the outcome is: {2}")
    @CsvSource(textBlock = """
            2H 3D 5S 9C KD, 2C 3H 4S 8C AH, Player 2 wins. With High card: Ace
            2H 4S 4C 2D 4H, 2S 8S AS QS 3S, Player 1 wins. With Full house: 4 over 2
            2H 3D 5S 9C KD, 2C 3H 4S 8C KH, Player 1 wins. With High card: 9
            2H 3D 5S 9C KD, 2D 3H 5C 9S KH, Tie.
            2H 2D 5S 9C KD, 2D 3H 3C 9S KH, Player 2 wins. With Pair: 3
            """)
    void checkWinnerForTwoHands(String firsthand, String secondHand, String expectedResult) {
        assertEquals(expectedResult, new PokerHands().getWinner(firsthand, secondHand));
    }
}