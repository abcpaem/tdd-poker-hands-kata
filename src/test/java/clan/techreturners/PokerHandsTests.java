package clan.techreturners;

import clan.techreturners.PokerHands.Rank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokerHandsTests {
    @Test
    void checkHandForHighCard() {
        // Arrange
        String hand = "2H 5S 3D 9C KD";

        // Act
        Rank rank = new PokerHands().getRank(hand);

        // Assert
        assertEquals(Rank.HIGH_CARD, rank);
    }
}