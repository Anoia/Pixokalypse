package agents;

import items.Weapon;

public class Character extends Agent {
	
	private Weapon equipppedWeapon;
	
	public Character(float x, float y){
		super(x, y);
		
	}

	public Weapon getEquipppedWeapon() {
		return equipppedWeapon;
	}

	public void setEquipppedWeapon(Weapon equipppedWeapon) {
		this.equipppedWeapon = equipppedWeapon;
	}
	
}
