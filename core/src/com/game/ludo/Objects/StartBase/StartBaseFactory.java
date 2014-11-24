package com.game.ludo.Objects.StartBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.game.ludo.Objects.Board.BoardField;
import com.game.ludo.Objects.Player.PlayerType;
import com.game.ludo.Objects.Token.Token;

/**
 * Created by RicardoJorge on 23/11/14.
 */

/**
 * Start Base factory.
 */
public class StartBaseFactory {
    public static StartBase createStartBase(PlayerType type,
                                            int rows,
                                            int columns,
                                            float gridSize,
                                            int numOfStartLocations,
                                            BoardField startField,
                                            Token[] tokens) {

        float gridLeftMargin;
        float gridBottomMargin;
        StartBase startBase;

        // sets the corresponding margin positions in order to draw the Tokens in the right place
        switch (type) {
            case RED:
                gridLeftMargin = 0;
                gridBottomMargin = (7 * gridSize);
                startBase = new RedStartBase(rows,
                        columns, gridSize, gridLeftMargin,
                        gridBottomMargin, numOfStartLocations, startField, tokens);
                break;
            case GREEN:
                gridLeftMargin = (7 * gridSize);
                gridBottomMargin = 0;
                startBase = new GreenStartBase(rows,
                        columns, gridSize, gridLeftMargin,
                        gridBottomMargin, numOfStartLocations, startField, tokens);
                break;
            case YELLOW:
                gridLeftMargin = 0;
                gridBottomMargin = 0;
                startBase = new YellowStartBase(rows,
                        columns, gridSize, gridLeftMargin,
                        gridBottomMargin, numOfStartLocations, startField, tokens);
                break;
            case BLUE:
                gridLeftMargin = (7 * gridSize);
                gridBottomMargin = (7 * gridSize);
                startBase = new BlueStartBase(rows,
                        columns, gridSize, gridLeftMargin,
                        gridBottomMargin, numOfStartLocations, startField, tokens);
                break;
            default:
                startBase = null;
                break;
        }
        return startBase;
    }
}
