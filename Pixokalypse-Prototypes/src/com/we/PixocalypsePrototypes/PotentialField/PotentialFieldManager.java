package com.we.PixocalypsePrototypes.PotentialField;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import Agents.Agent;
import Agents.PlayerCharacter;

public class PotentialFieldManager {
	
	private StaticPotentialField  environmentMap;
	public CombinedMap combinedMap;
	public boolean neuRendernA = false;
	public boolean neuRendernB = false;
	
	
	private ArrayList<PlayerCharacter> playerCharacters;
	private CircleDynamicPotentialField pushingPlayerCharacterPotentialFieldMap; //wird auf andere playerCharacters angewendet, damit sie nicht ineinander laufen
	private Target playerCharacterTarget;
	
	
	public PotentialFieldManager(StaticPotentialField environmentMap){
		
		this.environmentMap = environmentMap;
		this.playerCharacters = new ArrayList<PlayerCharacter>();
		pushingPlayerCharacterPotentialFieldMap = new CircleDynamicPotentialField(15, true);
		this.setPlayerCharacterTarget((int)Gdx.graphics.getWidth()/2, (int)Gdx.graphics.getHeight());	
	}
	
	public void step(float delta){
		stepPlayerCharacters(delta);
		if(neuRendernA)neuRendernB = true;
	}
	
	public void stepPlayerCharacters(float delta){
		
		combinedMap = new CombinedMap(environmentMap);
		combinedMap.add(playerCharacterTarget);		
		for(PlayerCharacter pc: playerCharacters){
			combinedMap.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}
		
		for(PlayerCharacter pc: playerCharacters){
			combinedMap.remove(pushingPlayerCharacterPotentialFieldMap, pc);
			GridPoint2 destination = getDestination(pc, combinedMap);
			int xDirection = destination.x - (int)pc.x;
			int yDirection = destination.y - (int)pc.y;
			pc.makeStep(delta, xDirection, yDirection);
			combinedMap.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}

	}
	
	private GridPoint2 getDestination(Agent pc, CombinedMap map) {
		int smallest = map.potentialFieldMap[(int)pc.x][(int)pc.y];
		GridPoint2 pos = new GridPoint2((int)pc.x, (int)pc.y);
		for(int i = (int)pc.x-1; i<=(int)pc.x+1; i++){
			for(int j = (int)pc.y-1; j<=(int)pc.y+1; j++){
				if(map.potentialFieldMap[i][j] < smallest){
					pos.set(i, j);
					smallest = map.potentialFieldMap[i][j];
				}
			}
		}
		
		
		
		return pos;
	}

	public void addPlayerCharacter(PlayerCharacter pc){
		playerCharacters.add(pc);
	}
	
	public void setPlayerCharacterTarget(int x, int y){
		System.out.println("new target");
		int radius = 200;
		//playerCharacterTarget = new Target(new CircleDynamicPotentialField(radius, false), x-radius, y-radius);
		playerCharacterTarget = new Target(new SuperIntelligentDynamicPotentialField(environmentMap, radius, false,x-radius, y-radius), x-radius, y-radius);
		
		neuRendernA = true;
	}

	//Hilfsfunktion zum Debugen
	//Setzt ein "Gebäude" in die environmentMap
	public void drawOnEnvironmentMap(int screenX, int screenY) {
		int diameter = 10;
		for(int x = screenX-diameter; x < screenX+diameter;x++){
			for(int y = screenY-diameter; y < screenY+diameter;y++){
				environmentMap.potentialFieldMap[x][y] = 10000;
			}	
		}
		neuRendernA = true;
	}

}
