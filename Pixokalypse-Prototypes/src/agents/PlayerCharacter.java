package agents;

import items.Pistol;
import items.Unarmed;
import items.Weapon;

public class PlayerCharacter extends Agent {
	
	public PlayerCharacter(float x, float y, String name, String spriteName){
		super(x, y, name, spriteName);
		setEquipppedWeapon(new Pistol());
	}

}
