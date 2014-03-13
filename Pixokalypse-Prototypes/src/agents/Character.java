package agents;

import items.Pistol;
import items.Unarmed;
import items.Weapon;

public class Character extends Agent {
	
	private String CharacterName;
	
	
	public Character(float x, float y, String spriteName){
		super(x, y, spriteName);
		setEquipppedWeapon(new Pistol());
	}


	public String getCharacterName() {
		return CharacterName;
	}


	public void setCharacterName(String characterName) {
		CharacterName = characterName;
	}


	@Override
	public int getZIndex() {
		return (int) y;
	}

	
}
