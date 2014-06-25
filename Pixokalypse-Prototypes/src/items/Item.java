package items;

public abstract class Item {
	private String itemName;
	private String spriteName;

	public String getItemName() {
		return this.itemName;
	}

	public String getSpriteName() {
		return spriteName;
	}

	public void setItemName(String name) {
		this.itemName = name;
	}

	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
	}
}
