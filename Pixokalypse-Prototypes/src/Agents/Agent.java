package agents;

import potentialField.Target;

public abstract class Agent {
	public float x;
	public float y;
	public float movementSpeed = 25;
	
	//für zombies
	//public Target target;
	
	public Agent(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void makeStep(float delta, int xDirection, int yDirection){
		x += delta*movementSpeed*xDirection;
		y += delta*movementSpeed*yDirection;
	}
	
}
