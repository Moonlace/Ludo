package com.game.ludo.Objects.Board;

import com.game.ludo.Objects.Token.Token;

/**
 * Created by RicardoJorge on 22/11/14.
 */

/**
 * Represents a field (or square) of the game board.
*/
public class BoardField {
    // the next location we can go to.
    private Point mNextLocation;
    // the current location.
    private Point mLocation;
    // every board field may have a token.
    public Token token;

    public BoardField(Point nextLocation, Point location) {
        this.mNextLocation = nextLocation;
        this.mLocation = location;
    }

    public Point getNextLocation() {
        return this.mNextLocation;
    }

    // The location where every token starts.
    public Point getLocation() {
        return this.mLocation;
    }
}
