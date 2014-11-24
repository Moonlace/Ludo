package com.game.ludo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.ludo.Objects.Player.PlayerType;

/**
 * Created by RicardoJorge on 22/11/14.
 */
public class Resources {
    public Image background = new Image(new Texture("gameBoard.png"));
    public Sprite tokenBlue = new Sprite(new Texture("tokenBlue.png"));
    public Sprite tokenGreen = new Sprite(new Texture("tokenGreen.png"));
    public Sprite tokenRed = new Sprite(new Texture("tokenRed.png"));
    public Sprite tokenYellow = new Sprite(new Texture("tokenYellow.png"));

    public Sprite getToken(PlayerType type) {
        switch (type) {
            case RED:
                return this.tokenRed;
            case GREEN:
                return this.tokenGreen;
            case YELLOW:
                return this.tokenYellow;
            case BLUE:
                return this.tokenBlue;
            default:
                break;
        }
        return null;
    }
}
