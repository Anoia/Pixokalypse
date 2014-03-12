package agents;

import items.Pistol;
import items.Weapon;

public class Character extends Agent {
	
	private String CharacterName;
	
	
	public Character(float x, float y){
		super(x, y);
		setEquipppedWeapon(new Pistol());
	}


	public String getCharacterName() {
		return CharacterName;
	}


	public void setCharacterName(String characterName) {
		CharacterName = characterName;
	}

	
}
