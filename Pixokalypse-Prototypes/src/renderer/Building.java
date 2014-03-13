package renderer;

public class Building implements ElementWithZIndex{
	
	int x, y, width, height;
	String spriteName;
	
	public Building(int x, int y, int width, int height, String spriteName){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spriteName = spriteName;
	}

	@Override
	public int getZIndex() {
		return y;
	}

	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	@Override
	public String getSpriteName() {
		return spriteName;
	}
	
}
