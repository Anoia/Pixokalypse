package agents;

import potentialField.Target;

public class Enemy extends Agent {

	public Target target;

	public Enemy(float x, float y, String name, String spriteName) {
		super(x, y, name, spriteName);
		movementSpeed = 10;
		// TODO Auto-generated constructor stub
	}

}
