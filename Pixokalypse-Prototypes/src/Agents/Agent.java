package Agents;


public abstract class Agent {
	public float x;
	public float y;
	public float movementSpeed = 20;
	public int maxHealth = 10;
	public int currentHealth = maxHealth;
	
	
	
	public Agent(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void makeStep(float delta, int xDirection, int yDirection){
		x += delta*movementSpeed*xDirection;
		y += delta*movementSpeed*yDirection;
	}
	
}
