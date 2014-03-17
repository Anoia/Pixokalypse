package agents;

public class Zombie extends Enemy {

	public Zombie(float x, float y) {
		super(x, y, "zombie");
	}
	
	public Zombie(float x, float y, String spriteName) {
		super(x, y, spriteName);
	}

}
