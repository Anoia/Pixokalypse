package com.we.PixokalypsePrototypes;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Pixokalypse-Prototypes";
		cfg.useGL20 = true;
		cfg.width = 960;
		cfg.height = 640;
		//test zwei :D
		//cfg.foregroundFPS = 60;
		new LwjglApplication(new PixokalypsePrototypes(), cfg);
	}
}
