package com.we.PixocalypsePrototypes.PotentialField;

import Agents.Agent;

public class CombinedMap extends PotentialField {
	
	public CombinedMap(StaticPotentialField environment){
		
		this.width = environment.width;
		this.height = environment.height;
		this.potentialFieldMap = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j=0; j < height; j++){
				this.potentialFieldMap[i][j] = environment.potentialFieldMap[i][j];
				
			}
		}				
	}
	
	public void add(Target target){
		add(target.target.potentialFieldMap, target.x, target.y);
	}
	
	public void add(PotentialField field, Agent agent){
		
		int x = (int) (agent.x - (field.width/2));
		int y = (int) (agent.y - (field.height/2));
		add(field.potentialFieldMap, x, y);
	}
	
	public void add(int[][] mapToAdd, int mapToAddXPos, int mapToAddYPos){
		int x = (mapToAddXPos < 0)? 0 : mapToAddXPos;
		int y = (mapToAddYPos < 0)? 0 : mapToAddYPos; 
		
		
		int maxI = (x + mapToAdd.length)%potentialFieldMap.length;
		maxI = (mapToAddXPos < 0)? maxI+mapToAddXPos : maxI;
		int maxJ = (y + mapToAdd[0].length)%potentialFieldMap[0].length;
		maxJ = (mapToAddYPos < 0)? maxJ + mapToAddYPos : maxJ;
		
		for(int i = x; i < maxI; i++){
			for(int j = y; j < maxJ; j++){
				int add = mapToAdd[i-mapToAddXPos][j-mapToAddYPos];
				potentialFieldMap[i][j] += add;
				
			}
		}
		
	}
	
	public void remove(Target target){
		remove(target.target.potentialFieldMap, target.x, target.y);
	}
	
	public void remove(PotentialField field, Agent agent){
		
		int x = (int) (agent.x - (field.width/2));
		int y = (int) (agent.y - (field.height/2));
		remove(field.potentialFieldMap, x, y);
	}
	
	public void remove(int[][] mapToRemove, int mapToRemoveXPos, int mapToRemoveYPos){
		int x = (mapToRemoveXPos < 0)? 0 : mapToRemoveXPos;
		int y = (mapToRemoveYPos < 0)? 0 : mapToRemoveYPos; 
		
		int maxI = (x + mapToRemove.length)%potentialFieldMap.length;
		maxI = (mapToRemoveXPos < 0)? maxI+mapToRemoveXPos : maxI;
		int maxJ = (y + mapToRemove[0].length)%potentialFieldMap[0].length;
		maxJ = (mapToRemoveYPos < 0)? maxJ + mapToRemoveYPos : maxJ;
		
		for(int i = x; i < maxI; i++){
			for(int j = y; j < maxJ; j++){
				int remove = mapToRemove[i-mapToRemoveXPos][j-mapToRemoveYPos];
				potentialFieldMap[i][j] -= remove;
				
			}
		}
		
	}

}
