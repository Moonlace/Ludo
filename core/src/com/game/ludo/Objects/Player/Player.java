package com.game.ludo.Objects.Player;

import com.game.ludo.Objects.StartBase.StartBase;
import com.game.ludo.Objects.Token.Token;

/**
 * Created by RicardoJorge on 23/11/14.
 */

/**
 * Represents a player
 */
public class Player {
    // The type of the player
    public PlayerType playerType;
    // The start base of the player
    public StartBase startBase;
    // The tokens of the player
    public Token[] tokens;

    public Player(StartBase startBase, Token[] tokens, PlayerType playerType) {
        this.startBase = startBase;
        this.playerType = playerType;
        this.tokens = tokens;
    }

    // returns the amount of tokens that are on the home field
    public int numberOfTokensHome() {
        int tokensHome = 0;
        for (int i = 0; i < this.tokens.length; i++) {
            Token token = this.tokens[i];
            if (token.isHome) {
                tokensHome++;
            }
        }
        return tokensHome;
    }
}
