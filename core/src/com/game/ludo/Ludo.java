package com.game.ludo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ludo extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("gameBoard.png");
	}

	@Override
	public void render () {
        this.setScreen();
	}

    private void setScreen() {
        Gdx.gl.glClearColor(0.98039216f, 0.86666667f, 0.70588235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        int oXCenter = (Gdx.app.getGraphics().getWidth() - img.getWidth())/2;
        int oYCenter = (Gdx.app.getGraphics().getHeight() - img.getHeight())/2;

        batch.draw(img, oXCenter, oYCenter);
        batch.end();
    }
}
