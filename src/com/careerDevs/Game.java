package com.careerDevs;

import java.util.*;

public class Game {
    public Scanner scanner = new Scanner(System.in);
    public List<Player> playerList = new ArrayList<>();
    public Map<Integer, Integer> diceOnTable = new HashMap<>();
    public final byte MAX_PLAYERS = 8;
    public final byte MIN_PLAYERS = 2;
    public int previousBidDieQty;
    public int getPreviousBidDieFaceValue;
    public int currentBidQty;
    public int getCurrentBidDiceFaceValue;

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
        for (Player activePlayer : playerList) {
            System.out.println(activePlayer.playerName + "'s turn");
            System.out.println("Your hand is " + activePlayer.cup.displayHand());
        }
    }


}
