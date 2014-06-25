package potentialField;

import agents.Agent;

public class CombinedFields extends PotentialField {
	
	public CombinedFields(StaticPotentialField environment){
		
		this.width = environment.width;
		this.height = environment.height;
		this.fieldArray = new int[width][height];
		
		for(int i = 0; i < width; i++){
			for(int j=0; j < height; j++){
				this.fieldArray[i][j] = environment.fieldArray[i][j];
				
			}
		}
	}
	
	public void add(Target target){
		add(target.target.fieldArray, target.x, target.y);
	}
	
	public void add(PotentialField field, Agent agent){
		
		int x = (int) (agent.x - (field.width/2));
		int y = (int) (agent.y - (field.height/2));
		add(field.fieldArray, x, y);
	}
	
	public void add(int[][] mapToAdd, int mapToAddXPos, int mapToAddYPos){
		int x = (mapToAddXPos < 0)? 0 : mapToAddXPos;
		int y = (mapToAddYPos < 0)? 0 : mapToAddYPos; 
		
		
		int maxI = (x + mapToAdd.length);
		maxI = (maxI > fieldArray.length)?fieldArray.length-1:maxI;
		maxI = (mapToAddXPos < 0)? maxI+mapToAddXPos : maxI;
		
		int maxJ = (y + mapToAdd[0].length);
		maxJ = (maxJ > fieldArray[0].length)?fieldArray[0].length-1:maxJ;
		maxJ = (mapToAddYPos < 0)? maxJ + mapToAddYPos : maxJ;


		for(int i = x; i < maxI; i++){
			for(int j = y; j < maxJ; j++){
				int add = mapToAdd[i-mapToAddXPos][j-mapToAddYPos];
				fieldArray[i][j] += add;
				
			}
		}
		
	}
	
	public void remove(Target target){
		remove(target.target.fieldArray, target.x, target.y);
	}
	
	public void remove(PotentialField field, Agent agent){
		
		int x = (int) (agent.x - (field.width/2));
		int y = (int) (agent.y - (field.height/2));
		remove(field.fieldArray, x, y);
	}
	
	public void remove(int[][] mapToRemove, int mapToRemoveXPos, int mapToRemoveYPos){
		int x = (mapToRemoveXPos < 0)? 0 : mapToRemoveXPos;
		int y = (mapToRemoveYPos < 0)? 0 : mapToRemoveYPos; 
		
		int maxI = (x + mapToRemove.length)%fieldArray.length;
		maxI = (mapToRemoveXPos < 0)? maxI+mapToRemoveXPos : maxI;
		int maxJ = (y + mapToRemove[0].length)%fieldArray[0].length;
		maxJ = (mapToRemoveYPos < 0)? maxJ + mapToRemoveYPos : maxJ;
		
		for(int i = x; i < maxI; i++){
			for(int j = y; j < maxJ; j++){
				int remove = mapToRemove[i-mapToRemoveXPos][j-mapToRemoveYPos];
				fieldArray[i][j] -= remove;
				
			}
		}
		
	}

}
