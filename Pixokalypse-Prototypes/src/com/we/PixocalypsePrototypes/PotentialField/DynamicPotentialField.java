package com.we.PixocalypsePrototypes.PotentialField;

public abstract class DynamicPotentialField extends PotentialField {
	
	
	
	public DynamicPotentialField(int width, int heigth){
		this.width = width;
		this.height = heigth;
		potentialFieldMap = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				potentialFieldMap[i][j] = -1;
			}
		}
	}

}
