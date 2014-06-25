package com.we.PixokalypsePrototypes.test;

public class TestActor {
	public String spriteName = "";
	public float velocityX = 0;
	public float velocityY = 0;
	int windowHeight;
	int windowWidth;
	public float x = 0;
	public float y = 0;

	public TestActor(int width, int height, String spritename) {
		this.spriteName = spritename;
		windowWidth = width;
		windowHeight = height;
		resertActor();
	}

	public boolean isOutOfWindow() {
		return (x + 20 > windowWidth || y + 20 > windowHeight);
	}

	public void resertActor() {
		if ((Math.random() * 2) > 1) {
			x = -20;
			y = (float) (windowWidth * Math.random());

			velocityX = (float) (80 * Math.random() + 20);
			velocityY = (float) (50 * Math.random() + 20);
		} else {
			x = (float) (windowHeight * Math.random());
			y = -20;

			velocityY = (float) (80 * Math.random() + 20);
			velocityX = (float) (50 * Math.random() + 20);
		}
	}

	public void updatePosition(float delta) {
		x += (velocityX * delta);
		y += (velocityY * delta);
	}
}