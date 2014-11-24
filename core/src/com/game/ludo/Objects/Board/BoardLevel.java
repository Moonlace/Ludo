package com.game.ludo.Objects.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import com.game.ludo.Constants;
import com.game.ludo.Objects.Player.PlayerType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RicardoJorge on 22/11/14.
 */

/**
 *
 */

/**
 * Represents the Ludo game board.
 */
public class BoardLevel {
    // BoardField matrix that represents the 24 fields and home fields.
    public BoardField board[][];
    // Holds the location of the blue home fields.
    public List<Point> blueHomeFields;
    // Holds the location of the yellow home fields.
    public List<Point> yellowHomeFields;
    // Holds the location of the red home fields.
    public List<Point> redHomeFields;
    // Holds the location of the green home fields.
    public List<Point> greenHomeFields;
    // The size of each field.
    private float mGridSize;

    public BoardLevel(int rows, int columns, float gridSize, int homeFieldNum) {
        this.mGridSize = gridSize;
        this.blueHomeFields = new ArrayList<Point>(homeFieldNum);
        this.yellowHomeFields = new ArrayList<Point>(homeFieldNum);
        this.redHomeFields = new ArrayList<Point>(homeFieldNum);
        this.greenHomeFields = new ArrayList<Point>(homeFieldNum);
        this.setupBoard(rows, columns);
    }

    // Configures the the board based on a JSON file.
    private void setupBoard(int rows, int columns) {
        this.board = new BoardField[rows][columns];
        BoardField token;
        Point nextLocation = null;
        int[] board = this.loadJSONBoard();

        for (int c = columns - 1, i = 0; c >= 0; c--) {
            for (int r = 0; r < rows; r++, i++) {
                int type = board[i];
                switch (type) {
                    case Constants.LUDO_BOARD_NEXT_UP_TOKEN: {
                        nextLocation = this.getNextUpLocation(r, c);
                        break;
                    }
                    case Constants.LUDO_BOARD_NEXT_DOWN_TOKEN: {
                        nextLocation = this.getNextDownLocation(r, c);
                        break;
                    }
                    case Constants.LUDO_BOARD_NEXT_LEFT_TOKEN: {
                        nextLocation = this.getNextLeftLocation(r, c);
                        break;
                    }
                    case Constants.LUDO_BOARD_NEXT_RIGHT_TOKEN: {
                        nextLocation = this.getNextRightLocation(r, c);
                        break;
                    }
                    case Constants.LUDO_BOARD_NEXT_NULL_TOKEN: {
                        nextLocation = null;
                        break;
                    }
                    case Constants.LUDO_BOARD_BLUE_HOME: {
                        nextLocation = null;
                        this.blueHomeFields.add(new Point(r, c));
                        break;
                    }
                    case Constants.LUDO_BOARD_YELLOW_HOME: {
                        nextLocation = null;
                        this.yellowHomeFields.add(new Point(r, c));
                        break;
                    }
                    case Constants.LUDO_BOARD_RED_HOME: {
                        nextLocation = null;
                        this.redHomeFields.add(new Point(r, c));
                        break;
                    }
                    case Constants.LUDO_BOARD_GREEN_HOME: {
                        nextLocation = null;
                        this.greenHomeFields.add(new Point(r, c));
                        break;
                    }
                    default:
                        nextLocation = null;
                        break;
                }

                Point location = new Point(r, c);
                token = new BoardField(nextLocation, location);
                this.board[r][c] = token;
            }
        }
    }

    // Get a home field point
    public Point getHomePoint(PlayerType playerType, int numOfTokensOnHome){
        Point homePoint = null;
        if (playerType == PlayerType.BLUE) {
            homePoint = this.blueHomeFields.get(numOfTokensOnHome -1);
        }
        else if (playerType == PlayerType.RED) {
            homePoint = this.redHomeFields.get(numOfTokensOnHome -1);
        }
        else if (playerType == PlayerType.YELLOW) {
            homePoint = this.yellowHomeFields.get(numOfTokensOnHome -1);
        }
        else if (playerType == PlayerType.GREEN) {
            homePoint = this.greenHomeFields.get(numOfTokensOnHome -1);
        }
        return homePoint;
    }

    // Returns the 4 different starting fields
    public BoardField[] getStartingFields() {
        BoardField[] startingBases = {this.board[2][0], this.board[0][4], this.board[4][6], this.board[6][2]};
        return startingBases;
    }

    // Loads the configuration JSON file
    private int[] loadJSONBoard () {
        FileHandle file = Gdx.files.internal(Constants.LUDO_BOARD_JSON_LEVEL);
        JsonValue root = new JsonReader().parse(file);
        JsonValue boardElement = root.get(Constants.LUDO_BOARD_JSON_LEVEL_ARRAY_KEY);
        return boardElement.asIntArray();
    }

    private Point getNextUpLocation(int row, int column) {
        return new Point(row, column + 1);
    }

    private Point getNextDownLocation(int row, int column) {
        return new Point(row, column - 1);
    }

    private Point getNextLeftLocation(int row, int column) {
        return new Point(row - 1, column);
    }

    private Point getNextRightLocation(int row, int column) {
        return new Point(row + 1, column);
    }

    // returns a Vector2 with the coordinates used to display the Tokens on the screen
    public Vector2 getCenterOfLocation(int row, int column) {
        return new Vector2((row * this.mGridSize) + this.mGridSize/2,
                (column * this.mGridSize) + this.mGridSize/2);
    }
}
