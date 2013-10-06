package com.we.PixocalypsePrototypes.PotentialField;

public class StaticPotentialField extends PotentialField{
	
	
	public int x;
	public int y;

	public StaticPotentialField(int width, int height){
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		potentialFieldMap = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				potentialFieldMap[i][j] = 0;
			}
		}
	}
	
	
	
	
	


}
