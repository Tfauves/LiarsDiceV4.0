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
    public int checkBidQty;
    public int checkBidDiceFaceValue;
    public boolean isActiveGame = false;
    public boolean isValidBid = false;
    public boolean isStartingPlayer = true;
    public boolean isActiveRound = false;
    public boolean isLieCalled = false;

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
        //System.out.println(diceOnTable);
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
                    spaces();
                    System.out.println("Valid Bid1...");
                } else {
                    System.out.println("Invalid Bid1!!!");
                    turn();
                }
                isStartingPlayer = false;
                isValidBid = false;
            } else {
                bid(activePlayer);
                validateBid(checkBidQty, checkBidDiceFaceValue);
                if (isValidBid) {
                    currentBidQty = checkBidQty;
                    currentBidDiceFaceValue = checkBidDiceFaceValue;
                    spaces();
                    System.out.println("Valid Bid2...");
                } else {
                    System.out.println("Invalid Bid2!!!");
                    System.out.println(" The previous bid was " + previousBidDieQty + "x " + previousBidDieFaceValue + " try again.");
                   turn();
                }
            }
            System.out.println(activePlayer.playerName + "'s bid " + currentBidQty + "x " + currentBidDiceFaceValue + "'s");
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
        //System.out.println("Type (b) to bid of (l) to call liar.");
        //String bidOrCall = scanner.nextLine();
        System.out.println("Make your bid!");
        System.out.println("Enter a qty: ");
        checkBidQty = scanner.nextInt();
        System.out.println("Enter a die face value: ");
        checkBidDiceFaceValue = scanner.nextInt();
        scanner.nextLine();

    }

//    Bid must have a qty and a face value.
//    Bid can not equal the previous bid.
//    Qty of the bid can equal, be less than, or greater than previous as long as the faceValue is greater.
//    If the faceValue of the bid is equal to the faceValue of the previous bid. The qty must be greater.

    public void validateBid(int checkBidQty, int checkBidDiceFaceValue) {
        if (checkBidQty == 0 || checkBidDiceFaceValue == 0) {
            isValidBid = false;

        } else if (checkBidQty == previousBidDieQty && checkBidDiceFaceValue == previousBidDieFaceValue) {
            isValidBid = false;

        }
        if (checkBidDiceFaceValue > previousBidDieFaceValue) {
            isValidBid = true;

        } else if (checkBidDiceFaceValue == previousBidDieFaceValue && checkBidQty > previousBidDieQty) {
            isValidBid = true;
        } else {
            isValidBid = false;
        }
    }

    public void spaces() {
        int spaceCounter = 0;
        while (spaceCounter < 20) {

        System.out.println();
        spaceCounter++;
        }
    }



}
