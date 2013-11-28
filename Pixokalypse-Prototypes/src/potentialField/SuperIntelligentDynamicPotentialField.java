package potentialField;

import java.util.HashSet;

import util.GridPoint2;

public class SuperIntelligentDynamicPotentialField extends DynamicPotentialField{
	
	public Boolean pushing;
	public int distance;
	public int x;
	public int y;
	
	private StaticPotentialField environmentMap;
	
	public SuperIntelligentDynamicPotentialField(StaticPotentialField environmentMap, int distance, Boolean pushing,int x, int y){
		super(distance*2+1, distance*2+1);
		this.pushing = pushing;
		this.distance = distance;
		this.environmentMap = environmentMap;
		this.x = x;
		this.y = y;
		createPotentialField();
	}

	private void createPotentialField() {
		
		GridPoint2 center = new GridPoint2(distance, distance);
		
		HashSet<GridPoint2> done = new HashSet<GridPoint2>(); 
		HashSet<GridPoint2> todo;
		
		fieldArray[center.x][center.y] = pushing? distance : 0;
		done.add(center);
		int laufVar = 1;
		while(!done.isEmpty()){
			todo = getEmptyNeighbours(done);
			setAllPoints(todo, laufVar);
			laufVar+=1;
			done = todo;
		}
		System.out.println("zonenen von 0 bis "+laufVar);
		for(int i = 0; i < fieldArray.length; i++){
			for(int j = 0; j < fieldArray[0].length; j++){
				if(fieldArray[i][j] == -1){
					fieldArray[i][j] = 0;
				}
			}
		}
	}
	
	private void setAllPoints(HashSet<GridPoint2> points, int value){
		value = pushing ? distance-value : value;
		for(GridPoint2 point: points){
			fieldArray[point.x][point.y] = value; 
		}
	}
	
//	private HashSet<GridPoint2> getEmptyNeighbours(HashSet<GridPoint2> points){
//		HashSet<GridPoint2> neighbours = new HashSet<GridPoint2>();
//		for(GridPoint2 point: points){
//			for(int i = point.x-1; i <= point.x+1; i++){
//				for(int j = point.y-1; j <= point.y+1; j++){
//					if(potentialFieldMap[i][j] == -1){
//						neighbours.add(new GridPoint2(i, j));
//					}
//				}
//			}
//		}
//		
//		
//		return neighbours;		
//	}
	
	
	private HashSet<GridPoint2> getEmptyNeighbours(HashSet<GridPoint2> points){
		HashSet<GridPoint2> neighbours = new HashSet<GridPoint2>();
		
		//in relation zur staticMap
		for(GridPoint2 point: points){
			if(point.x+this.x >0 && 
					point.x+this.x <environmentMap.width-1 && 
					point.y+this.y > 0 && 
					point.y+this.y < environmentMap.height-1 &&
					
					point.x >0 && 
					point.x <this.width-1 && 
					point.y > 0 && 
					point.y < this.height-1
					){
				//oben
				if(fieldArray[point.x][point.y-1] == -1 && environmentMap.fieldArray[point.x+this.x][point.y-1+this.y] == 0){
					neighbours.add(new GridPoint2(point.x, point.y-1));	
				}
				//oben-Rechts
				//if(potentialFieldMap[point.x+1][point.y-1] == -1 && environmentMap.potentialFieldMap[point.x+1+this.x][point.y-1+this.y] == 0){
				//	neighbours.add(new GridPoint2(point.x+1, point.y-1));	
				//}
				//rechts
				if(fieldArray[point.x+1][point.y] == -1 && environmentMap.fieldArray[point.x+1+this.x][point.y+this.y] == 0){
					neighbours.add(new GridPoint2(point.x+1, point.y));	
				}
				//untenrechts
				//if(potentialFieldMap[point.x+1][point.y+1] == -1 && environmentMap.potentialFieldMap[point.x+1+this.x][point.y+1+this.y] == 0){
				//	neighbours.add(new GridPoint2(point.x+1, point.y+1));	
				//}
				//unten
				if(fieldArray[point.x][point.y+1] == -1 && environmentMap.fieldArray[point.x+this.x][point.y+1+this.y] == 0){
					neighbours.add(new GridPoint2(point.x, point.y+1));	
				}
				//untenlinks
				//if(potentialFieldMap[point.x-1][point.y+1] == -1 && environmentMap.potentialFieldMap[point.x-1+this.x][point.y+1+this.y] == 0){
				//	neighbours.add(new GridPoint2(point.x-1, point.y+1));	
				//}
				//links
				if(fieldArray[point.x-1][point.y] == -1 && environmentMap.fieldArray[point.x-1+this.x][point.y+this.y] == 0){
					neighbours.add(new GridPoint2(point.x-1, point.y));	
				}
				//obenlinks
				//if(potentialFieldMap[point.x-1][point.y-1] == -1 && environmentMap.potentialFieldMap[point.x-1+this.x][point.y-1+this.y] == 0){
				//	neighbours.add(new GridPoint2(point.x-1, point.y-1));	
				//}	
			}
			
		}
		
		
		return neighbours;		
	}
	
	public void printASCII(){
		for(int i = 0; i < fieldArray.length; i++){
			System.out.println("");
			for(int j = 0; j < fieldArray[i].length; j++){
				System.out.print(fieldArray[i][j]);
			}
		}
	}
	
	/*
	public static void main(String[] arg){
		CircleDynamicPotentialField c  = new CircleDynamicPotentialField(9, false);
		c.printASCII();
	}*/

}
