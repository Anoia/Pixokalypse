package items;

public abstract class Weapon extends Item{
	private int range;
	private int damage;
	private int firerate;
	
	public Weapon(int range, int damage, int firerate){
		this.range = range;
		this.damage = damage;
		this.firerate = firerate;
	}
	
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getFirerate() {
		return firerate;
	}

	public void setFirerate(int firerate) {
		this.firerate = firerate;
	}
	
	
}
