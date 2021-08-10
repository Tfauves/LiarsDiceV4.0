package com.careerDevs;

import java.util.*;

public class Game {
    public Scanner scanner = new Scanner(System.in);
    public List<Player> playerList = new ArrayList<>();
    public Map<Integer, Integer> diceOnTable = new HashMap<>();
    public final byte MAX_PLAYERS = 8;
    public final byte MIN_PLAYERS = 2;
    public int previousBidDieQty;
    public int previousBidDieFaceValue;
    public int currentBidQty;
    public int currentBidDiceFaceValue;
    public boolean isActiveGame = false;
    public boolean isValidBid = false;
    public boolean isStartingPlayer = true;
    public boolean isActiveRound = false;

    public Game() {
        System.out.println("Enter number of players: ");
        int numberOfPlayers;
        do {
            numberOfPlayers = scanner.nextByte();
            scanner.nextLine();
        } while (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS);

        while (playerList.size() < numberOfPlayers) {
            System.out.println("Enter player name: ");
            playerList.add(new Player((scanner.nextLine()).trim()));
        }
    }

    public void rollAll() {
        isActiveRound = true;
        for (Player activePlayer : playerList) {
            activePlayer.cup.roll();
            setDiceOneTable(activePlayer.cup.dice);

        }
        System.out.println(diceOnTable);
    }

    public void setDiceOneTable(List<Die> dice) {
        for (Die die : dice) {
            if (diceOnTable.containsKey(die.faceValue)) {
                diceOnTable.put(die.faceValue, diceOnTable.get(die.faceValue) + 1);
            } else {
                diceOnTable.put(die.faceValue, 1);
            }
        }
    }

    public void turn() {
        while (isActiveRound)
        for (Player activePlayer : playerList) {
            System.out.println(activePlayer.playerName + "'s turn");
            System.out.println("Your hand is " + activePlayer.cup.displayHand());
            if (isStartingPlayer) {
                roundOpenBid();
                if (isValidBid) {
                    System.out.println("Valid Bid...");
                } else {
                    System.out.println("Invalid Bid!!!");
                    turn();
                }
                isStartingPlayer = false;
                isValidBid = false;
            spaces();
            } else {
                bid(activePlayer);
                validateBid(currentBidQty, currentBidDiceFaceValue);
                if (isValidBid) {
                    System.out.println("Valid Bid...");
                } else {
                    System.out.println("Invalid Bid!!!");
                    System.out.println(" The previous bid was " + previousBidDieQty + "x " + previousBidDieFaceValue + " try again.");
                    bid(activePlayer);
                }
               // validateBid(currentBidQty, currentBidDiceFaceValue);
            }
            System.out.println(activePlayer.playerName + "'s bid " + currentBidQty + "x " + currentBidDiceFaceValue);
            System.out.println();
        }
    }

    public void roundOpenBid() {
        System.out.println("Make your bid.");
        System.out.println("Enter die qty: ");
        currentBidQty = scanner.nextInt();
        System.out.println("Enter die face value: ");
        currentBidDiceFaceValue = scanner.nextInt();
        scanner.nextLine();
        isValidBid = currentBidQty != 0 && currentBidDiceFaceValue != 0;
    }

    public void bid(Player activePlayer) {
        previousBidDieQty = currentBidQty;
        previousBidDieFaceValue = currentBidDiceFaceValue;
        System.out.println("Make your bid!");
        System.out.println("Enter a qty: ");
        currentBidQty = scanner.nextInt();
        System.out.println("Enter a die face value: ");
        currentBidDiceFaceValue = scanner.nextInt();
        scanner.nextLine();
    }

//    Bid must have a qty and a face value.
//    Bid can not equal the previous bid.
//    Qty of the bid can equal, be less than, or greater than previous as long as the faceValue is greater.
//    If the faceValue of the bid is equal to the faceValue of the previous bid. The qty must be greater.

    public void validateBid(int currentBidQty, int currentBidDiceFaceValue) {

        if (currentBidQty == 0 || currentBidDiceFaceValue == 0) {
            isValidBid = false;

        } else if (currentBidQty == previousBidDieQty && currentBidDiceFaceValue == previousBidDieFaceValue) {
            isValidBid = false;

        }
        if (currentBidDiceFaceValue > previousBidDieFaceValue) {
            isValidBid = true;
        } else isValidBid = currentBidDiceFaceValue == previousBidDieFaceValue && currentBidQty > previousBidDieQty;
    }

    public void spaces() {
        int spaceCounter = 0;
        while (spaceCounter < 20) {

        System.out.println();
        spaceCounter++;
        }
    }



}
