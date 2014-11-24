package com.game.ludo.Objects.StartBase;

import com.game.ludo.Objects.Board.BoardField;
import com.game.ludo.Objects.Board.Point;
import com.game.ludo.Objects.Player.PlayerType;
import com.game.ludo.Objects.Token.Token;

/**
 * Created by RicardoJorge on 23/11/14.
 */
public class RedStartBase extends StartBase {

    public RedStartBase(int rows, int columns, float gridSize, float gridOX,
                        float gridOY, int numOfStartLocations, BoardField startField, Token[] tokens) {
        super(rows, columns, gridSize, gridOX, gridOY, numOfStartLocations, startField);
        this.setUsableFields(tokens);
    }

    @Override
    public void setUsableFields(Token[] tokens) {
        /**
         *  This is the our start field setup:
         *  O X X
         *  X O X
         *  X X X
         */
        tokens[0].currLocation = new Point(0,2);
        this.mStartBase[0][2].token = tokens[0];
        tokens[1].currLocation = new Point(1,1);
        this.mStartBase[1][1].token = tokens[1];
    }

    @Override
    public BoardField getAvailableStartBoardField() {
        BoardField startBase = null;
        if (this.mStartBase[0][2].token == null) {
            startBase = this.mStartBase[0][2];
        }
        else if (this.mStartBase[1][1].token == null){
            startBase = this.mStartBase[1][1];
        }
        return startBase;
    }
}
