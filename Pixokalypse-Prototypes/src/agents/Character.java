package agents;

import items.Pistol;
import items.Unarmed;
import items.Weapon;

public class Character extends Agent {
	
	public Character(float x, float y, String name, String spriteName){
		super(x, y, name, spriteName);
		setEquipppedWeapon(new Pistol());
	}

	@Override
	public int getZIndex() {
		return (int) y;
	}

}
