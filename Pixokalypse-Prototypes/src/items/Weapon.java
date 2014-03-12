package items;

public abstract class Weapon extends Item{
	private int range;
	private int damage;
	private float fireCooldown;
	private float fireCounter = 0;  //wenn 0 oder kleinerkann geschossen werden;
	
	public Weapon(int range, int damage, float fireCooldown){
		this.range = range;
		this.damage = damage;
		this.fireCooldown = fireCooldown;
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

	public float getFirerate() {
		return fireCooldown;
	}

	public void setFireCooldown(int fireCooldown) {
		this.fireCooldown = fireCooldown;
	}
	
	public void tick(float delta){
		if(fireCounter > 0){
			fireCounter -= delta;
		}
	}
	
	public void shoot(){
		if(fireCounter <=0){
			//do damage to something
			fireCounter = fireCooldown;
		}
		
		
	}
	
	public boolean isReadyToShoot(){
		return (fireCounter <= 0);
	}
	
	
}
