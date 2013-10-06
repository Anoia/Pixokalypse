package com.we.PixocalypsePrototypes.PotentialField;

import java.util.ArrayList;

import Agents.Agent;
import Agents.PlayerCharacter;

public class PotentialFieldManager {
	
	private StaticPotentialField  environmentMap;
	
	
	
	private ArrayList<PlayerCharacter> playerCharacters;
	private CircleDynamicPotentialField pushingPlayerCharacterPotentialFieldMap; //wird auf andere playerCharacters angewendet, damit sie nicht ineinander laufen
	private Target playerCharacterTarget;
	
	
	public PotentialFieldManager(StaticPotentialField environmentMap){
		
		this.environmentMap = environmentMap;
		this.playerCharacters = new ArrayList<PlayerCharacter>();
		pushingPlayerCharacterPotentialFieldMap = new CircleDynamicPotentialField(15, true);
		this.setPlayerCharacterTarget(200, 200);	
	}
	
	public void step(float delta){
		stepPlayerCharacters(delta);
	}
	
	public void stepPlayerCharacters(float delta){
		
		CombinedMap map = new CombinedMap(environmentMap);
		map.add(playerCharacterTarget);		
		for(PlayerCharacter pc: playerCharacters){
			map.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}
		
		//+ all players
		
		
		
		//schleife
		//--current player
		//movePlayer
		for(PlayerCharacter pc: playerCharacters){
			map.remove(pushingPlayerCharacterPotentialFieldMap, pc);
			GridPoint2 destination = getDestination(pc, map);
			System.out.println("dest: " + destination.x + " "+destination.y);
			int xDirection = destination.x - (int)pc.x;
			int yDirection = destination.y - (int)pc.y;
			pc.makeStep(delta, xDirection, yDirection);
			map.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}
		//get new Pos
		//++current player
		//repeat
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
		int radius = 300;
		playerCharacterTarget = new Target(new CircleDynamicPotentialField(radius, false), x-radius, y-radius);
	}

}
