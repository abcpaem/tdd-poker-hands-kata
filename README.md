# TDD Poker Hands kata
Test Driven Development exercise for Poker Hands.

### What is a kata?

A Kata in martial arts means “a system of individual training exercises”. Just like in martial arts, coding also requires consistent practise to hone in the skill.

### What is the goal?

Plan out and have a go at the Poker Hands Kata using Test-Driven Development. It is not expected to have a finished solution but have had a good go at using Test-Driven Development.

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


---
## How to run the tests
- cd to the project root folder in the command line
- ``mvn test``

## Technology
This project was built using:
- Java version 17.0.2
- JUnit 5.8.2 for unit testing
- Apache Maven 3.8.5 as project manager
- Community Edition for the IntelliJ 2021.3.2 development environment.
