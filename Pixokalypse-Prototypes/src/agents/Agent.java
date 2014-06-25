package agents;

import items.Unarmed;
import items.Weapon;

public abstract class Agent {
	public int currentHealth;
	private Weapon equipppedWeapon;
	public int maxHealth = 20;
	public float movementSpeed = 20;
	private String name;
	private String spriteName;
	private int visualRange = 100;

	public float x;

	public float y;

	public Agent(float x, float y, String name, String spriteName) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.spriteName = spriteName;
		currentHealth = maxHealth;
		setEquipppedWeapon(new Unarmed());
	}

	public Weapon getEquipppedWeapon() {
		return equipppedWeapon;
	}

	public String getName() {
		return name;
	}

	public String getSpriteName() {
		return spriteName;
	}

	public int getVisualRange() {
		return visualRange;
	}

	public void makeStep(float delta, int xDirection, int yDirection) {
		x += delta * movementSpeed * xDirection;
		y += delta * movementSpeed * yDirection;
	}

	public void setEquipppedWeapon(Weapon equipppedWeapon) {
		this.equipppedWeapon = equipppedWeapon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
	}

	public void setVisualRange(int visualRange) {
		this.visualRange = visualRange;
	}

}
