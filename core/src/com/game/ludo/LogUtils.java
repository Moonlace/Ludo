package com.game.ludo;

import com.badlogic.gdx.Gdx;

/**
 * Created by RicardoJorge on 23/11/14.
 */
public class LogUtils {

    public static void Log(String className, int value) {
        Gdx.app.log(className, Integer.toString(value));
    }
}
