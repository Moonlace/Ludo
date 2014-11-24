package com.game.ludo.Objects.Dice;

import java.util.Random;

/**
 * Created by RicardoJorge on 23/11/14.
 */

/**
 * Simple dice
 */
public class Dice {

    static Random diceRoller = new Random();

    static public int rollDice() {
        return diceRoller.nextInt(6) + 1;
    }
}
