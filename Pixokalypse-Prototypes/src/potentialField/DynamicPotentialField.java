package potentialField;

public abstract class DynamicPotentialField extends PotentialField {
	
	
	
	public DynamicPotentialField(int width, int heigth){
		this.width = width;
		this.height = heigth;
		fieldArray = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				fieldArray[i][j] = -1;
			}
		}
	}

}
