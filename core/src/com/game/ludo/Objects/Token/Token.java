package com.game.ludo.Objects.Token;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.ludo.Constants;
import com.game.ludo.Objects.Board.Point;
import com.game.ludo.Objects.Player.PlayerType;

/**
 * Created by RicardoJorge on 23/11/14.
 */

/**
 * Represents a pie that is used on the board
 */
public class Token extends Image{

    public PlayerType tokenType;
    public Sprite sprite;
    public int movedSpaces;
    public Point currLocation;
    public Point possibleFutureLocation;
    private boolean canMove;
    public boolean isBase;
    public boolean isHome;
    public boolean isField;

    public Token(PlayerType tokenType, Sprite sprite) {
        super(sprite);
        this.sprite = sprite;
        this.tokenType = tokenType;
        this.movedSpaces = 0;
        this.canMove = false;
        this.isBase = true;
        this.isHome = false;
        this.isField = false;
        this.currLocation = new Point(0,0);
        this.possibleFutureLocation = new Point(0,0);
    }

    public void setCanMove(boolean canMove, Point possibleFutureLocation) {
        this.possibleFutureLocation = (canMove) ? possibleFutureLocation : null;
        float alpha = (canMove) ? Constants.TOKEN_SELECTABLE_ALPHA : Constants.TOKEN_NOT_SELECTABLE_ALPHA;
        this.canMove = canMove;
        Color c = new Color(this.sprite.getColor());
        c.set(c.r, c.g, c.b, alpha);
        this.setColor(c);
    }

    public boolean getCanMove() {
        return this.canMove;
    }
}
