package com.game.ludo.Objects.StartBase;

import com.game.ludo.Objects.Board.BoardField;
import com.game.ludo.Objects.Board.Point;
import com.game.ludo.Objects.Player.PlayerType;
import com.game.ludo.Objects.Token.Token;

/**
 * Created by RicardoJorge on 23/11/14.
 */
public class GreenStartBase extends StartBase {

    public GreenStartBase(int rows, int columns, float gridSize, float gridOX,
                          float gridOY, int numOfStartLocations, BoardField startField, Token[] tokens) {
        super(rows, columns, gridSize, gridOX, gridOY, numOfStartLocations, startField);
        this.setUsableFields(tokens);
    }

    @Override
    public void setUsableFields(Token[] tokens) {
        /**
         *  This is the our start field setup:
         *  X X X
         *  X O X
         *  X X O
         */
        tokens[0].currLocation = new Point(2,0);
        this.mStartBase[2][0].token = tokens[0];
        tokens[1].currLocation = new Point(1,1);
        this.mStartBase[1][1].token = tokens[1];
    }

    @Override
    public BoardField getAvailableStartBoardField() {
        BoardField startBase = null;
        if (this.mStartBase[2][0].token == null) {
            startBase = this.mStartBase[2][0];
        }
        else if (this.mStartBase[1][1].token == null) {
            startBase = this.mStartBase[1][1];
        }
        return startBase;
    }
}
