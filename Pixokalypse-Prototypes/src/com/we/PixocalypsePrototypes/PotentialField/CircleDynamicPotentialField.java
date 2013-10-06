package com.we.PixocalypsePrototypes.PotentialField;

import java.util.HashSet;

public class CircleDynamicPotentialField extends DynamicPotentialField{
	
	public Boolean pushing;
	public int radius;
	
	public CircleDynamicPotentialField(int radius, Boolean pushing){
		super(radius*2+1, radius*2+1);
		this.pushing = pushing;
		this.radius = radius;
		createCircle(radius);
	}

	private void createCircle(int radius) {
		
		GridPoint2 center = new GridPoint2(radius, radius);
		
		HashSet<GridPoint2> done = new HashSet<GridPoint2>(); 
		HashSet<GridPoint2> todo;
		
		potentialFieldMap[center.x][center.y] = pushing? radius : 0;
		done.add(center);

		for(int i = 1; i <= radius; i++){
			todo = getEmptyNeighbours(done);
			setAllPoints(todo, i);
			done = todo;
		}
		
	}
	
	private void setAllPoints(HashSet<GridPoint2> points, int value){
		value = pushing ? radius-value : value;
		for(GridPoint2 point: points){
			potentialFieldMap[point.x][point.y] = value; 
		}
	}
	
	private HashSet<GridPoint2> getEmptyNeighbours(HashSet<GridPoint2> points){
		HashSet<GridPoint2> neighbours = new HashSet<GridPoint2>();
		for(GridPoint2 point: points){
			for(int i = point.x-1; i <= point.x+1; i++){
				for(int j = point.y-1; j <= point.y+1; j++){
					if(potentialFieldMap[i][j] == -1){
						neighbours.add(new GridPoint2(i, j));
					}
				}
			}
		}
		
		
		return neighbours;		
	}
	
	public void printASCII(){
		for(int i = 0; i < potentialFieldMap.length; i++){
			System.out.println("");
			for(int j = 0; j < potentialFieldMap[i].length; j++){
				System.out.print(potentialFieldMap[i][j]);
			}
		}
	}
	
	/*
	public static void main(String[] arg){
		CircleDynamicPotentialField c  = new CircleDynamicPotentialField(0, true);
		c.printASCII();
	}*/

}
