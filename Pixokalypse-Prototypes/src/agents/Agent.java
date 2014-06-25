package agents;

import renderer.ElementWithZIndex;
import items.Unarmed;
import items.Weapon;


public abstract class Agent implements ElementWithZIndex{
	public float x;
	public float y;
	public float movementSpeed = 20;
	public int maxHealth = 20;
	public int currentHealth = maxHealth;
	private int visualRange = 100;
	private Weapon equipppedWeapon;
	
	private String spriteName;
	
	private String name;
	
	
	public Agent(float x, float y, String name, String spriteName){
		this.x = x;
		this.y = y;
		this.name = name;
		this.spriteName = spriteName;
		setEquipppedWeapon(new Unarmed());
	}
	
	public void makeStep(float delta, int xDirection, int yDirection){
		x += delta*movementSpeed*xDirection;
		y += delta*movementSpeed*yDirection;
	}

	public int getVisualRange() {
		return visualRange;
	}

	public void setVisualRange(int visualRange) {
		this.visualRange = visualRange;
	}
	
	public Weapon getEquipppedWeapon() {
		return equipppedWeapon;
	}

	public void setEquipppedWeapon(Weapon equipppedWeapon) {
		this.equipppedWeapon = equipppedWeapon;
	}

	public String getSpriteName() {
		return spriteName;
	}

	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public int getX() {
		return (int) x;
	}
	
	@Override
	public int getY() {
		return (int) y;
	}
	
}
