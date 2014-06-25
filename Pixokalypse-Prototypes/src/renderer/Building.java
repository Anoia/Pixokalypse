package renderer;

public class Building implements ElementWithZIndex {

	String spriteName;
	int x, y, width, height;

	public Building(int x, int y, int width, int height, String spriteName) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spriteName = spriteName;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String getSpriteName() {
		return spriteName;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZIndex() {
		return y;
	}

}
