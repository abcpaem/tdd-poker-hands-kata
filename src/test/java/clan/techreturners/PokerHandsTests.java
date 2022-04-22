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
            """)
    void checkHandForRank(String hand, PokerHands.Rank expectedRank) {
        assertEquals(expectedRank, new PokerHands().getRank(hand));
    }
}