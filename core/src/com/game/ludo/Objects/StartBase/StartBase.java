package com.game.ludo.Objects.StartBase;

import com.badlogic.gdx.math.Vector2;
import com.game.ludo.Objects.Board.BoardField;
import com.game.ludo.Objects.Board.Point;
import com.game.ludo.Objects.Token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RicardoJorge on 23/11/14.
 */

/**
 * The Start Base class is responsible for saving the references of the Tokens that are not still on the run.
 */
public abstract class StartBase {
    // BoardField matrix.
    protected BoardField mStartBase[][];
    protected int nNumOfRows;
    protected int nNumOfColumns;
    // the number of Tokens that the Start Base starts with.
    public int mNumOfStartLocations;
    // the number of Tokens that are still on the Start Base.
    public int mNumOfFilledLocations;
    protected float mGridSize;
    protected float mGridOX;
    protected float mGirdOY;
    // the first field the Tokens will go to.
    public BoardField startField;

    public StartBase(int rows, int columns, float gridSize, float gridOX,
                     float gridOY, int numOfStartLocations, BoardField startField) {
        this.mStartBase = new BoardField[rows][columns];
        this.nNumOfRows = rows;
        this.nNumOfColumns = columns;
        this.mNumOfStartLocations = numOfStartLocations;
        this.mNumOfFilledLocations = numOfStartLocations;
        this.mGridSize = gridSize;
        this.mGridOX = gridOX;
        this.mGirdOY = gridOY;
        this.startField = startField;
        this.setupStartBase(startField);
    }

    // returns the fields that are currently being used.
    public List<BoardField> getUsedFields () {
        List<BoardField> userFields = new ArrayList<BoardField>();
        for (int r = 0; r < this.nNumOfRows; r++) {
            for (int c = 0; c < this.nNumOfColumns; c++) {
                BoardField boardField = this.mStartBase[r][c];
                if (boardField != null && boardField.token != null) {
                    userFields.add(boardField);
                }
            }
        }
        return userFields;
    }

    // removes a token from the start base based on the given point.
    public void removeToken(Point p) {
        this.mStartBase[p.getX()][p.getY()].token = null;
    }

    // Creates board fields for the start base, 9 for now even if we only use 2.
    private void setupStartBase(BoardField startField) {
        for (int r = 0; r < this.nNumOfRows; r++) {
            for (int c = 0; c < this.nNumOfColumns; c++) {
                Point location = new Point(r, c);
                BoardField boardField = new BoardField(startField.getLocation(), location);
                this.mStartBase[r][c] = boardField;
            }
        }
    }

    // Returns the Vector2 coordinates for the Token to be displayed
    public Vector2 getCenterOfLocation(int row, int column) {
        return new Vector2((row * this.mGridSize) + this.mGridOX + 3*this.mGridSize/4,
                (column * this.mGridSize) + this.mGirdOY + 3*this.mGridSize/4);
    }

    // Configures and sets the fields that will be used.
    public abstract void setUsableFields(Token[] tokens);

    // Returns an available BoardField
    public abstract BoardField getAvailableStartBoardField();
}
