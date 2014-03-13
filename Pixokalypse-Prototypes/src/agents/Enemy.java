package agents;

import potentialField.Target;

public class Enemy extends Agent {
	
	public Target target;

	public Enemy(float x, float y, String spriteName) {
		super(x, y, spriteName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getZIndex() {
		return (int) y;
	}

}
