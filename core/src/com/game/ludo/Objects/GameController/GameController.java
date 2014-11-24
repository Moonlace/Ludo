package com.game.ludo.Objects.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.ludo.Constants;
import com.game.ludo.Objects.Board.BoardField;
import com.game.ludo.Objects.Board.BoardLevel;
import com.game.ludo.Objects.Board.Point;
import com.game.ludo.Objects.Dice.Dice;
import com.game.ludo.Objects.Player.Player;
import com.game.ludo.Objects.Player.PlayerType;
import com.game.ludo.Objects.StartBase.StartBase;
import com.game.ludo.Objects.StartBase.StartBaseFactory;
import com.game.ludo.Objects.Token.Token;
import com.game.ludo.Resources;

import java.util.Iterator;
import java.util.List;

/**
 * Created by RicardoJorge on 23/11/14.
 */

/**
 * The GameController contains all the logic of the game.
 */
public class GameController {
    private Stage mStage;
    // Contains all the Sprites and Image needed for the game.
    private Resources mResources;
    // BoardLevel instance.
    private BoardLevel mBoardLevel;
    // The players.
    private Player[] mPlayers;
    // A dice TextButton used to determine the next roll.
    private TextButton mDice;
    // The value of the last rolled dice.
    private int mRolledValue;
    // The number o used dices before passing the turn to another player.
    private int mDiceRoles;
    // Used with the mPlayers Array we get a reference to the current player.
    private int mCurrentPlayerIndex;
    // Elapsed time used for the Dice roll.
    private float mElapsedTime;
    // Blocks or unlocks the startPhase.
    private boolean mStartPhase;

    public GameController() {
        this.setup();
    }

    private void setup() {
        // setup and configuration
        this.mResources = new Resources();
        this.mStage = new Stage(new FitViewport(Constants.BACKGROUND_IMAGE_WIDTH, Constants.BACKGROUND_IMAGE_HEIGHT));
        Gdx.input.setInputProcessor(this.mStage);
        this.mStage.addActor(this.mResources.background);
        BitmapFont font = new BitmapFont();
        TextButton.TextButtonStyle mDiceStyle = new TextButton.TextButtonStyle();
        mDiceStyle.font = font;
        mDiceStyle.font.setScale(Constants.DICE_TEXT_SCALE);
        this.mDice = new TextButton("", mDiceStyle);
        Table menuTable = new Table();
        menuTable.add(this.mDice);
        menuTable.setFillParent(true);
        this.mStage.addActor(menuTable);
        this.mBoardLevel = new BoardLevel(Constants.BOARD_GRID_ROWS, Constants.BOARD_GRID_COLUMNS,
                Constants.BOARD_GRID_SIZE, Constants.HOME_TOTAL_FIELDS);
        this.createPlayers();
        this.setupStartBases();
        this.nextPlayersTurn();
        this.mStartPhase = true;
    }

    /**
     *
     * @param r amount of red from 0.0f to 1.0f
     * @param g amount of green from 0.0f to 1.0f
     * @param b amount of blue from 0.0f to 1.0f
     */
    public void setBackgroundColor(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * The game loop is mainly composed of the startPhase, selectionPhase and evaluationPhase.
     * */
    public void gameLoop() {
        this.startPhase();
        this.mStage.draw();
    }

    /**
     * The first part of the game loop.
     */
    private void startPhase() {

        // get the number of tokens that are already home.
        Player player = this.getCurrentPlayer();
        int tokensHome = player.numberOfTokensHome();
        if (tokensHome == Constants.START_FIELD_LOCATION_NUMBER) {
            //Victory
            this.mDice.setText(Constants.VICTORY_MESSAGE);
            return;
        }

        // Slow down how fast the dice is rolled.
        if (!this.mStartPhase || this.mElapsedTime < Constants.SECONDS_FOR_NEXT_ROLL) {
            this.mElapsedTime += Gdx.graphics.getDeltaTime();
            return;
        }

        // Roll the dice.
        this.rollDice();

        /**
         * If there is attlist one token on the field or the rolled value is 6
         * go to the 2 part of the game loop.
         */
        boolean allTokensInStartBase = allTokensInStartBase();
        boolean playerTokensInStartBase = playerTokensInStartBase();
        if (!playerTokensInStartBase || this.mRolledValue == Constants.ROLLED_VALUE_TO_SPAWN_TOKEN) {
            this.selectionPhase();
        }
        // until there is one token on the field every player can throw 3 dices
        else if (allTokensInStartBase && this.mDiceRoles < Constants.MAX_NUM_OF_ROLLED_DICES) {
            this.startPhase();
        }else {
            this.nextPlayersTurn();
        }
    }

    /**
     * The second part of the game loop.
     */
    private void selectionPhase() {
        // disable the startPhase method.
        this.mStartPhase = false;
        // get the current player.
        Player player = this.getCurrentPlayer();
        // the the current player tokens.
        Token[] tokens = player.tokens;

        // loop through all the current player tokens.
        for (int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            // is the token is in is home let him be
            if (token.isHome) {
                continue;
            }
            // get the current player start location.
            Point startLocation = player.startBase.startField.getLocation();
            // get the possible future location of the token.
            Point futureLocation = this.getFutureLocation(token, this.mRolledValue);
            // get the board field of the future location.
            BoardField boardField = (futureLocation != null) ? this.mBoardLevel.board[futureLocation.getX()][futureLocation.getY()] : null;

            // if the board is not null and has a token with tokenType.
            if (boardField != null && boardField.token != null && boardField.token.tokenType != null) {
                // enable token for throwing opponent token.
                if (boardField.token.tokenType != token.tokenType) {
                    token.setCanMove(true, boardField.getLocation());
                }
                // own token at target position.
                else if (boardField.token.tokenType == token.tokenType) {
                    token.setCanMove(false, null);
                }
            }
            else if (boardField != null) {
                token.setCanMove(true, boardField.getLocation());
            }
            // enable token at start field
            else if (startLocation.equals(token.currLocation)) {
                token.setCanMove(true, futureLocation);
            }
            // dice score 6 and min 1 token in start base
            else if (this.mRolledValue == Constants.ROLLED_VALUE_TO_SPAWN_TOKEN
                    && player.startBase.mNumOfFilledLocations > 0) {
                token.setCanMove(true, startLocation);
                token.isBase = false;
                token.isField = true;
                player.startBase.mNumOfFilledLocations--;
                Point pointToRemove = new Point(token.currLocation.getX(), token.currLocation.getY());
                player.startBase.removeToken(pointToRemove);
                break;
            } else {
                token.setCanMove(false, null);
            }
        }

        // simulate a decision from the user.
        this.fakeGestureDidTouch();
    }

    // Should be used to process the touch on a token
    private void fakeGestureDidTouch() {
        // since touches are not working, just select any token as long as it able too move
        Player player = this.getCurrentPlayer();
        Token[] tokens = player.tokens;

        for (int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            if (token.isField && token.getCanMove()) {
                this.evaluationPhase(token);
                return;
            }
        }
        // there is now possible play to make
        this.mStartPhase = true;
        this.startPhase();
    }

    /**
     * The third part of the game loop.
     */
    private void evaluationPhase(Token token) {
        // get the location the token might have in the future.
        Point tokenPosition = new Point(token.possibleFutureLocation.getX(), token.possibleFutureLocation.getY());
        // get the board field corresponding to the given location.
        BoardField boardField = (token.isField) ? this.mBoardLevel.board[tokenPosition.getX()][tokenPosition.getY()] : null;
        // get the current player.
        Player player = this.getCurrentPlayer();
        // increment the number of moves the token gave.
        token.movedSpaces+= this.mRolledValue;

        // check if we can go move to the home base.
        if (token.movedSpaces > Constants.SPACES_NEEDED_TO_GO_HOME) {
            token.isHome = true;
            token.isField = false;
            tokenPosition = this.mBoardLevel.getHomePoint(player.playerType, player.numberOfTokensHome());
        }
        // if there is an opponent in the location send it back to is base.
        else if (boardField.token != null) {
            this.sendTokenToStartBase(boardField);
        }

        // move token token to its new place.
        this.mBoardLevel.board[tokenPosition.getX()][tokenPosition.getY()].token = token;
        Vector2 tokenLocation = this.mBoardLevel.getCenterOfLocation(tokenPosition.getX(), tokenPosition.getY());
        token.setPosition(tokenLocation.x, tokenLocation.y);
        token.currLocation = tokenPosition;
        token.setCanMove(false, null);

        // if the dice roll is not 6 then its time to end the turn.
        if (this.mRolledValue != Constants.ROLLED_VALUE_TO_SPAWN_TOKEN) {
            this.nextPlayersTurn();
        }

        // unlock the startPhase method and call it.
        this.mStartPhase = true;
        this.startPhase();
    }

    /**
     *
     * @param token a token
     * @param rollValue the value of the rolled dice
     * @return the location where the token will be if selected
     */
    private Point getFutureLocation(Token token, int rollValue) {
        if (!token.isField) {
            return  null;
        }
        Point nextLocation = new Point(token.currLocation.getX(), token.currLocation.getY());
        BoardField boardField = null;
        for (int i = 0; i < rollValue; i++) {
            boardField = this.mBoardLevel.board[nextLocation.getX()][nextLocation.getY()];
            nextLocation = new Point(boardField.getNextLocation().getX(), boardField.getNextLocation().getY());
        }
        return nextLocation;
    }

    /**
     * Rolls a dice updates the mDice TextButton, mRolledValue, mDiceRoles and mElapsedTime
     */
    private void rollDice() {
        this.mElapsedTime = 0;
        this.mDiceRoles++;
        this.mRolledValue = Dice.rollDice();
        this.mDice.setText(Integer.toString(this.mRolledValue));
    }

    // returns yes if all the tokens are still in the start base
    private boolean allTokensInStartBase() {
        for(int i = 0; i < this.mPlayers.length; i++) {
            Player player = this.mPlayers[i];
            if (player.startBase.mNumOfFilledLocations != player.startBase.mNumOfStartLocations) {
                return false;
            }
        }
        return true;
    }

    // checks if the current player has all the tokens on base
    private boolean playerTokensInStartBase() {
        Player player = this.getCurrentPlayer();
        return (player.startBase.mNumOfFilledLocations == player.startBase.mNumOfStartLocations);
    }

    // returns a Token to the starting base
    private void sendTokenToStartBase (BoardField boardField) {
        Player player = this.getPlayerWithType(boardField.token.tokenType);
        BoardField baseBoardField = player.startBase.getAvailableStartBoardField();
        if (baseBoardField != null) {
            boardField.token.isBase = true;
            baseBoardField.token = boardField.token;
            boardField.token = null;
            Point location = boardField.getLocation();
            Vector2 tokenLocation = this.mBoardLevel.getCenterOfLocation(location.getX(), location.getY());
            baseBoardField.token.setPosition(tokenLocation.x, tokenLocation.y);
        }
    }

    // Draws the tokens on there starting position
    private void setupStartBases() {
        for(int i = 0; i < this.mPlayers.length; i++) {
            Player player = this.mPlayers[i];
            List<BoardField> usedFields = player.startBase.getUsedFields();
            for(Iterator<BoardField> b = usedFields.iterator(); b.hasNext(); ) {
                BoardField boardField = b.next();
                Token token = boardField.token;
                Point location = boardField.getLocation();
                Vector2 tokenLocation = player.startBase.getCenterOfLocation(location.getX(), location.getY());
                token.setPosition(tokenLocation.x, tokenLocation.y);
                this.mStage.addActor(token);
            }
        }
    }

    /** Creates the 4 players needed for the game with corresponding tokens and starting base. */
    private void createPlayers() {
        this.mPlayers = new Player[Constants.NUM_OF_PLAYERS];
        this.mCurrentPlayerIndex = 0;
        BoardField[] startBases = this.mBoardLevel.getStartingFields();
        PlayerType[] types = {PlayerType.YELLOW, PlayerType.RED, PlayerType.BLUE, PlayerType.GREEN};
        Token[] tokens;
        for(int i = 0; i < this.mPlayers.length; i++) {
            // create player tokens.
            tokens = new Token[Constants.START_FIELD_LOCATION_NUMBER];
            for (int j = 0; j < Constants.START_FIELD_LOCATION_NUMBER; j++) {
                tokens[j] = new Token(types[i], this.mResources.getToken(types[i]));
            }
            // create player start base.
            StartBase sb = StartBaseFactory.createStartBase(types[i],
                    Constants.START_FIELD_GRID_ROWS,
                    Constants.START_FIELD_GRID_COLUMNS,
                    Constants.START_FIELD_GRID_SIZE,
                    Constants.START_FIELD_LOCATION_NUMBER,
                    startBases[i],
                    tokens);
            // create player.
            this.mPlayers[i] = new Player(sb, tokens, types[i]);
        }
    }

    // starts the next player turn
    private void nextPlayersTurn() {
        // reset the number of dice rolls, rolled value and elapsedTime
        this.mDiceRoles = 0;
        this.mRolledValue = 0;
        this.mElapsedTime = 0;
        //increment the mCurrentPlayerIndex when it reaches NUM_OF_PLAYERS go back to the start
        if (this.mCurrentPlayerIndex == Constants.NUM_OF_PLAYERS - 1) {
            this.mCurrentPlayerIndex = 0;
        }
        else {
            this.mCurrentPlayerIndex++;
        }

        // change the background color to the corresponding player color
        switch (this.mCurrentPlayerIndex) {
            case Constants.RED_PLAYER_INDEX:
                this.setBackgroundColor(Constants.BACKGROUND_RED_PLAYER_COLOR_R,
                        Constants.BACKGROUND_RED_PLAYER_COLOR_G,
                        Constants.BACKGROUND_RED_PLAYER_COLOR_B);
                break;
            case Constants.BLUE_PLAYER_INDEX:
                this.setBackgroundColor(Constants.BACKGROUND_BLUE_PLAYER_COLOR_R,
                        Constants.BACKGROUND_BLUE_PLAYER_COLOR_G,
                        Constants.BACKGROUND_BLUE_PLAYER_COLOR_B);
                break;
            case Constants.GREEN_PLAYER_INDEX:
                this.setBackgroundColor(Constants.BACKGROUND_GREEN_PLAYER_COLOR_R,
                        Constants.BACKGROUND_GREEN_PLAYER_COLOR_G,
                        Constants.BACKGROUND_GREEN_PLAYER_COLOR_B);
                break;
            case Constants.YELLOW_PLAYER_INDEX:
                this.setBackgroundColor(Constants.BACKGROUND_YELLOW_PLAYER_COLOR_R,
                        Constants.BACKGROUND_YELLOW_PLAYER_COLOR_G,
                        Constants.BACKGROUND_YELLOW_PLAYER_COLOR_B);
                break;
            default:
        }
    }

    // returns the current player
    private Player getCurrentPlayer() {
        Player player = this.mPlayers[this.mCurrentPlayerIndex];
        return player;
    }

    // returns the player type from the given player
    private Player getPlayerWithType(PlayerType type) {
        for (int i = 0; i < this.mPlayers.length; i++) {
            Player player = this.mPlayers[i];
            if (player.playerType == type) {
                return player;
            }
        }
        return null;
    }
}
