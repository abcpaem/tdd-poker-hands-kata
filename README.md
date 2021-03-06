# TDD Poker Hands kata
Test Driven Development exercise for Poker Hands.

### What is a kata?

A Kata in martial arts means “a system of individual training exercises”. Just like in martial arts, coding also requires consistent practise to hone in the skill.

### What is the goal?

Implement the Poker Hands Kata using Test-Driven Development.

### Instructions

- Read the Poker Hands kata requirements at Coding Dojo: https://codingdojo.org/kata/PokerHands/
- Solve the Poker Hands kata in Java programming language.

## Solution

The main goal is to compare several pairs of poker hands and indicate which one has the highest rank or indicate if it is a tie. After some analysis of the poker rules, here is a list of assumptions and considerations:
- Cards will have the following values: 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king and ace, represented as: 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A.
- Each card has a suit which can be: club, diamond, heart, or spade (denoted as C, D, H, and S).
- There will be 9 ranks for a poker hand, ordered from lowest to highest: *High Card, Pair, Two Pairs, Three of a kind, Straight, Flush, Full house, Four of a kind* and *Straight flush*.
- There won't be any input validation, as per the rules above, the program will consider that a poker hand will contain valid cards from a poker deck, and that they will be provided in the following format: `2H 3D 5S 9C KD` 

### Test Driven Development process

Instead of going straight to compare two poker hands as the suggested test cases in the kata instructions, I decided to apply the KISS principle and start by checking all possible ranks for a poker hand, so this is the TDD process followed:

1) Add a test to check if a poker hand is ranked as *High Card*. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults01.html).
2) Add test for poker hand ranked as *Pair*. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults02.html).
3) Add test for *Two Pairs* rank. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults03.html).
4) Apply DRY principle and refactor all previous tests as parameterized tests. See all tests passing [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults04.html).
5) Add test for *Three of a kind* rank. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults05.html).
6) Add test for *Straight* rank. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults06.html).
7) Add test for *Flush* rank. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults07.html).
8) Add test for *Full house* rank. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults08.html).
9) Refactor code for Pair, Three of a Kind and Full House ranks. See all tests passing [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults09.html).
10) Add test for *Four of a kind* rank. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults10.html).
11) Add test for *Straight flush* rank. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults11.html).
12) Add test for *High Card* winner, between 2 poker hands. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults12.html).
13) Refactor code to create *Player* inner class. See all tests passing [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults13.html).
14) Add test for *Full house* winner. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults14.html).
15) Add test for *High Card* winner with second-highest card. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults15.html).
16) Add test for *High Card* tie. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults16.html).
17) Add test for *Pair* winner with the highest pair. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults17.html).
18) Add test for *Pair* winner with same pair and the highest card. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults18.html).
19) Add test for *Two Pairs* winner with the second-highest pair. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults19.html).
20) Add test for *Two Pairs* winner with same two pairs and the highest card. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults20.html).
21) Add test for *Three of a kind* winner with the highest value of the 3 cards. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults21.html).
22) Add test for *Straight* winner with the highest card. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults22.html).
23) Add test for *Flush* winner with the highest card. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults23.html).
24) Add test for *Full house* winner with the highest 3 cards. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults24.html).
25) Add test for *Four of a kind* winner with the highest 4 cards. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults25.html).
26) Add test for *Straight flush* winner with the highest card. See test [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResults26.html).

Finally, you can see all the tests passing [here](https://htmlview.glitch.me/?https://github.com/abcpaem/tdd-poker-hands-kata/blob/main/docs/TestResultsAll.html).

### What's next?:
- Add exception message when a card is invalid for any of following reasons:
  - It doesn't exist in a poker deck.
  - It is duplicated.
- Validate that a hand contains 5 cards.

---
### How to run the application
1) cd to the project root folder in the command line
2) `mvn compile`
3) `mvn exec:java -Dexec.mainClass=clan.techreturners.poker.Main`

To run tests use:
- ``mvn test``

## Technology
This project was built using:
- Java 17.0.2
- JUnit 5.8.2
- Maven 3.8.5
- IntelliJ 2021.3.2 (Community Edition).
