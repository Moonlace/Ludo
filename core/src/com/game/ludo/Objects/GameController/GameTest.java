package com.game.ludo.Objects.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.ludo.Constants;
import com.game.ludo.Objects.Board.BoardField;
import com.game.ludo.Objects.Board.BoardLevel;
import com.game.ludo.Objects.Board.Point;
import com.game.ludo.Objects.Player.Player;
import com.game.ludo.Objects.Player.PlayerType;
import com.game.ludo.Objects.StartBase.StartBase;
import com.game.ludo.Objects.StartBase.StartBaseFactory;
import com.game.ludo.Objects.Token.Token;
import com.game.ludo.Resources;

/**
 * Created by RicardoJorge on 23/11/14.
 */

/**
 * The GameTest is used to make some tests to the game
 */
public class GameTest {
    private Stage mStage;
    private Resources mResources;
    private BoardLevel mBoardLevel;
    private Player[] mPlayers;
    private Token mRedToken;

    public GameTest() {
        this.setup();
    }

    // setup and configuration
    private void setup () {
        int oXCenter = (Constants.BACKGROUND_IMAGE_WIDTH - Constants.BACKGROUND_IMAGE_WIDTH)/2;
        int oYCenter = (Constants.BACKGROUND_IMAGE_HEIGHT - Constants.BACKGROUND_IMAGE_HEIGHT)/2;
        this.mResources = new Resources();
        // setup the stage
        this.mStage = new Stage(new FitViewport(Constants.BACKGROUND_IMAGE_WIDTH, Constants.BACKGROUND_IMAGE_HEIGHT));
        Gdx.input.setInputProcessor(this.mStage);
        this.mResources.background.setPosition(oXCenter, oYCenter);
        this.mStage.addActor(this.mResources.background);
        this.mBoardLevel = new BoardLevel(Constants.BOARD_GRID_ROWS, Constants.BOARD_GRID_COLUMNS,
                Constants.BOARD_GRID_SIZE, Constants.HOME_TOTAL_FIELDS);
        this.tests();
    }

    public void gameLoop() {
        // set the background color
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR_R,
                Constants.BACKGROUND_COLOR_G,
                Constants.BACKGROUND_COLOR_B,
                1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.moveToken();

        this.mStage.draw();
    }

    public void tests() {
        //this.testBoardCreation();

        // this two tests should be run together with moveToken.
        this.testStartBaseLocationsDraw();
    }

    // makes a red token take a spin around the board, useful to know if there is any error in the circuit
    private void moveToken() {
        Player player = this.mPlayers[1];
        Token[] tokens = player.tokens;
        BoardField startBases = this.mBoardLevel.getStartingFields()[1];

        if (mRedToken == null) {
            // add token to the start position

            Point startLocation = startBases.getLocation();
            Vector2 startTokenLocation = this.mBoardLevel.getCenterOfLocation(startLocation.getX(),
                    startLocation.getY());
            this.mRedToken = new Token(PlayerType.RED, this.mResources.getToken(PlayerType.RED));
            this.mRedToken.currLocation = startLocation;
            this.mRedToken.setPosition(startTokenLocation.x, startTokenLocation.y);
            this.mStage.addActor(this.mRedToken);
        }
        else {
            Point currLocation = mRedToken.currLocation;
            BoardField boardField = this.mBoardLevel.board[currLocation.getX()][currLocation.getY()];
            Point nextLocation = boardField.getNextLocation();
            Vector2 updatedTokenLocation = this.mBoardLevel.getCenterOfLocation(nextLocation.getX(),
                    nextLocation.getY());
            this.mRedToken.currLocation = nextLocation;
            this.mRedToken.setPosition(updatedTokenLocation.x, updatedTokenLocation.y);
        }
    }

    /** Creates the 4 players needed for the game with corresponding tokens and starting base. */
    private void testStartBaseLocationsDraw() {
        this.mPlayers = new Player[Constants.NUM_OF_PLAYERS];
        BoardField[] startBases = this.mBoardLevel.getStartingFields();
        PlayerType[] types = {PlayerType.YELLOW, PlayerType.RED, PlayerType.BLUE, PlayerType.GREEN};
        Token[] tokens = new Token[Constants.START_FIELD_LOCATION_NUMBER];
        for(int i = 0; i < this.mPlayers.length; i++) {
            // create player tokens.
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

            // add token to the start position
            Point startLocation = startBases[i].getLocation();
            Image tokenImage = new Image(this.mResources.getToken(types[i]));
            Vector2 startTokenLocation =this.mBoardLevel.getCenterOfLocation(startLocation.getX(),
                    startLocation.getY());
            tokenImage.setPosition(startTokenLocation.x, startTokenLocation.y);
            this.mStage.addActor(tokenImage);

            // add tokens to the start base
            for (int r = 0; r < Constants.START_FIELD_GRID_ROWS; r ++) {
                for (int c = 0; c < Constants.START_FIELD_GRID_COLUMNS; c++) {
                    tokenImage = new Image(this.mResources.getToken(types[i]));
                    Vector2 tokenLocation = sb.getCenterOfLocation(r, c);
                    tokenImage.setPosition(tokenLocation.x, tokenLocation.y);
                    this.mStage.addActor(tokenImage);
                }
            }
        }
    }

    // creates a Token in every square of board
    private void testBoardCreation() {
        for (int r = 0; r < Constants.BOARD_GRID_ROWS; r++) {
            for (int c = 0; c < Constants.BOARD_GRID_COLUMNS; c++) {
                Vector2 tokenLocation = this.mBoardLevel.getCenterOfLocation(r, c);
                Image tokenImage = new Image(this.mResources.getToken(PlayerType.RED));
                tokenImage.setPosition(tokenLocation.x, tokenLocation.y);
                this.mStage.addActor(tokenImage);
            }
        }
    }
}
