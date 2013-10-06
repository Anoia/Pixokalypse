package com.we.PixocalypsePrototypes.PotentialField;

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
		int[][] mapToAdd = target.target.potentialFieldMap;
		int x = (target.x < 0)? 0 : target.x;
		int y = (target.y < 0)? 0 : target.y;
		System.out.println("TARGET X Y"+ target.x + ""+target.y);
		System.out.println("static: "+ potentialFieldMap.length + " "+potentialFieldMap[0].length);
		System.out.println("Map to Add size: "+mapToAdd.length + " "+mapToAdd[0].length);


		
		System.out.println("X: "+x+" Y: "+y);
		System.out.println("X_MAX: "+(x + mapToAdd.length)%potentialFieldMap.length+" Y_MAX: "+(y + mapToAdd[0].length)%potentialFieldMap[0].length);
		

		int maxI = (x + mapToAdd.length)%potentialFieldMap.length;
		maxI = (target.x < 0)? maxI+target.x : maxI;
		
		int maxJ = (y + mapToAdd[0].length)%potentialFieldMap[0].length;
		maxJ = (target.y < 0)? maxJ + target.y : maxJ;
		
		for(int i = x; i < maxI; i++){
			for(int j = y; j < maxJ; j++){
				
						//System.out.println("X_maptoadd: "+(i-target.x)+" Y_maptoadd: "+(j-target.y));
				
				int add = mapToAdd[i-target.x][j-target.y];
				potentialFieldMap[i][j] += add;
				
			}
		}
		
				
		
		
	}

}
