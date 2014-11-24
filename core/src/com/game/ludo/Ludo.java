package com.game.ludo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.ludo.Objects.Board.BoardField;
import com.game.ludo.Objects.Board.BoardLevel;
import com.game.ludo.Objects.Board.Point;
import com.game.ludo.Objects.GameController.GameController;
import com.game.ludo.Objects.GameController.GameTest;
import com.game.ludo.Objects.Player.Player;
import com.game.ludo.Objects.Player.PlayerType;
import com.game.ludo.Objects.StartBase.StartBase;
import com.game.ludo.Objects.StartBase.StartBaseFactory;

import java.util.Iterator;
import java.util.List;

/**
 * Created by RicardoJorge on 22/11/14.
 */
public class Ludo extends ApplicationAdapter {
    private GameController mGameController;
    //private GameTest mGameTest;

    @Override
	public void create () {
        this.mGameController = new GameController();
        //this.mGameTest = new GameTest();
	}

	@Override
	public void render () {
        this.mGameController.gameLoop();
        //this.mGameTest.gameLoop();
	}
}
