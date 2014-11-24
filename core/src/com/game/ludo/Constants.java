package com.game.ludo;

/**
 * Created by RicardoJorge on 22/11/14.
 */
public class Constants {

    public static final String VICTORY_MESSAGE = "VICTORY";

    public static final int SPACES_NEEDED_TO_GO_HOME = 24;
    public static final int HOME_TOTAL_FIELDS = 2;

    public static final int ROLLED_VALUE_TO_SPAWN_TOKEN = 6;
    public static final int MAX_NUM_OF_ROLLED_DICES = 3;
    public static final float SECONDS_FOR_NEXT_ROLL = 2.0f;

    public static final int NUM_OF_PLAYERS = 4;

    public static final float BACKGROUND_COLOR_R = 0.98039216f;
    public static final float BACKGROUND_COLOR_G = 0.86666667f;
    public static final float BACKGROUND_COLOR_B = 0.70588235f;

    public static final int RED_PLAYER_INDEX = 1;
    public static final float BACKGROUND_RED_PLAYER_COLOR_R = 1.0f;
    public static final float BACKGROUND_RED_PLAYER_COLOR_G = 0.57647059f;
    public static final float BACKGROUND_RED_PLAYER_COLOR_B = 0.50980392f;

    public static final int BLUE_PLAYER_INDEX = 2;
    public static final float BACKGROUND_BLUE_PLAYER_COLOR_R = 0.50196078f;
    public static final float BACKGROUND_BLUE_PLAYER_COLOR_G = 0.6f;
    public static final float BACKGROUND_BLUE_PLAYER_COLOR_B = 1.0f;

    public static final int GREEN_PLAYER_INDEX = 3;
    public static final float BACKGROUND_GREEN_PLAYER_COLOR_R = 0.43921569f;
    public static final float BACKGROUND_GREEN_PLAYER_COLOR_G = 0.85882353f;
    public static final float BACKGROUND_GREEN_PLAYER_COLOR_B = 0.43921569f;

    public static final int YELLOW_PLAYER_INDEX = 0;
    public static final float BACKGROUND_YELLOW_PLAYER_COLOR_R = 1.0f;
    public static final float BACKGROUND_YELLOW_PLAYER_COLOR_G = 0.99215686f;
    public static final float BACKGROUND_YELLOW_PLAYER_COLOR_B = 0.50980392f;

    public static final int BACKGROUND_IMAGE_WIDTH = 768;
    public static final int BACKGROUND_IMAGE_HEIGHT = 768;

    public static final int BOARD_GRID_ROWS = 7;
    public static final int BOARD_GRID_COLUMNS = 7;
    public static final float BOARD_GRID_SIZE = 96;

    public static final String LUDO_BOARD_JSON_LEVEL= "Ludo_Board.json";
    public static final String LUDO_BOARD_JSON_LEVEL_ARRAY_KEY= "board";
    public static final int LUDO_BOARD_NEXT_UP_TOKEN = 1;
    public static final int LUDO_BOARD_NEXT_DOWN_TOKEN = 2;
    public static final int LUDO_BOARD_NEXT_LEFT_TOKEN = 3;
    public static final int LUDO_BOARD_NEXT_RIGHT_TOKEN = 4;
    public static final int LUDO_BOARD_NEXT_NULL_TOKEN = 0;
    public static final int LUDO_BOARD_BLUE_HOME = 5;
    public static final int LUDO_BOARD_YELLOW_HOME = 6;
    public static final int LUDO_BOARD_RED_HOME = 7;
    public static final int LUDO_BOARD_GREEN_HOME = 8;

    public static final int START_FIELD_GRID_ROWS = 3;
    public static final int START_FIELD_GRID_COLUMNS = 3;
    public static final int START_FIELD_LOCATION_NUMBER = 2;
    public static final float START_FIELD_GRID_SIZE = 64;

    public static final float TOKEN_SELECTABLE_ALPHA = 0.6f;
    public static final float TOKEN_NOT_SELECTABLE_ALPHA = 1.0f;

    public static final int DICE_TEXT_SCALE = 5;
}
