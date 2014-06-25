package potentialField;

public class StaticPotentialField extends PotentialField {

	public int x;
	public int y;

	public StaticPotentialField(int width, int height) {
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		fieldArray = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				fieldArray[i][j] = 0;
			}
		}
	}

}
