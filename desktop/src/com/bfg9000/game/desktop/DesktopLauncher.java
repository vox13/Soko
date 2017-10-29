package com.bfg9000.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bfg9000.game.SokoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SOKOBAN";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new SokoGame(), config);
	}
}
