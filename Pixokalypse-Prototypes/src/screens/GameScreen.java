package screens;

import input.GameInputProcessor;
import items.Weapon;

import java.util.ArrayList;
import java.util.Random;

import potentialField.PotentialFieldManager;
import potentialField.StaticPotentialField;
import renderer.EffectsRenderer;
import renderer.GameRenderer;
import renderer.effects.Effect;
import renderer.effects.ShootingEffect;
import util.Constants;
import util.RayTracer;
import util.Utils;
import agents.Agent;
import agents.Character;
import agents.Enemy;
import agents.Follower;
import agents.Player;
import agents.Zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.we.PixokalypsePrototypes.PixokalypsePrototypes;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteCollisionMapContainer;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class GameScreen implements Screen {

	private PixokalypsePrototypes game;

	private OrthographicCamera camera;
	private Map map;
	private SpriteContainer spriteContainer;
	private SpriteCollisionMapContainer spriteCollisionMapContainer;
	private PotentialFieldManager manager;
	int tileSize = Constants.TILE_SIZE; // one tile = one field on map

	private Player player;
	private ArrayList<Follower> followers = new ArrayList<Follower>();
	private ArrayList<Character> playerCharacters = new ArrayList<Character>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private ArrayList<Effect> renderEffects = new ArrayList<Effect>();

	
	GameRenderer renderer;
	EffectsRenderer effectsRenderer;

	private RayTracer rayTracer;
	

	
	public GameScreen(PixokalypsePrototypes game) {
		this.game = game;
		map = new Map(40,8,4,10);
		manager = new PotentialFieldManager(new StaticPotentialField(
				map.mapSize * tileSize, map.mapSize * tileSize));

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.setToOrtho(true);
		camera.zoom = 0.2f;

		Gdx.input.setInputProcessor(new GameInputProcessor(manager, camera));
		
		
		//System.out.println("Spriteanz: " + spriteContainer.getSpriteCount());
		spriteCollisionMapContainer = new SpriteCollisionMapContainer(
				"data/height.txt", "data/height.png");
		//System.out.println("Collisionmapanz: " + spriteCollisionMapContainer.getCollisionmapCount());
		
		manager.addCollisionMapToEnvironment(map, spriteCollisionMapContainer);

		// The Player, only one!
		//PLAYERNAMEN SETZEN!!!!!!!!!!!!!
		Random rand = new Random();
		player = new Player(200, 200, "Char-"+(rand.nextInt(4)+1)+"-alive");
		manager.addPlayerCharacter(player);
		playerCharacters.add(player);

		// zufallsnamen!!
		for (int i = 0; i < 3; i++) {
			Follower f = new Follower(200 + 3 * i, 200 + 3 * i,"Char-"+(rand.nextInt(4)+1)+"-alive");
			followers.add(f);
			playerCharacters.add(f);
			manager.addPlayerCharacter(f);
		}
		
		renderer = new GameRenderer(this, game.batch, camera, map);
		renderer.setFonts(game.font12, game.font24);
		effectsRenderer = new EffectsRenderer(this, game.batch);
		effectsRenderer.setFonts(game.font12, game.font24);
		rayTracer = new RayTracer(manager.getEnvironmentMap());
		
		createZombies();
		

	}
	


	public void updateGame(float delta){
		//move all Actors
		manager.step(delta);
		coolDownWeapons(delta);
		tickRenderEffects(delta);
		action(delta);
		
		camera.position.set(player.x, player.y, 0);
	}
	
	private void tickRenderEffects(float delta) {
		ArrayList<Effect> dead = new ArrayList<Effect>();
		for(Effect e: renderEffects){
			e.tick(delta);
			if(e.isDead()) dead.add(e);
		}
		renderEffects.removeAll(dead);
		
	}



	private void coolDownWeapons(float delta) {
		for(Character c: playerCharacters){
			c.getEquipppedWeapon().tick(delta);
		}
		
	}



	private void action(float delta){
		ArrayList<Enemy> dead = new ArrayList<Enemy>();
		ArrayList<Enemy> enemiesCloseToPlayer = new ArrayList<Enemy>();
		
		for(Enemy e: enemies){
			if(Utils.getDistance(player, e) < 100){
				enemiesCloseToPlayer.add(e);
			}
		}
		
		
		//PlayerCharacters attack
		for(Character character: playerCharacters){
			Weapon weapon = character.getEquipppedWeapon();
			if(!weapon.isReadyToShoot()){
				//System.out.println("Weapon not ready");
				continue;
			}
			for(Enemy enemy: enemiesCloseToPlayer){
				if(rayTracer.castRay((int)character.x, (int)character.y, (int)enemy.x, (int)enemy.y, character.getEquipppedWeapon().getRange(), false)){
					weapon.shoot();
					enemy.currentHealth -= weapon.getDamage();
					System.out.print("PEW! ");
					//renderEffects.add(new TextEffect(enemy.x, enemy.y, ""+weapon.getDamage()));
					addShootingEffect(character, enemy);
					
					if(enemy.currentHealth <= 0){
						dead.add(enemy);
						System.out.println("KILL");
					}
					break;
				}
			}
			enemies.removeAll(dead);
			enemiesCloseToPlayer.removeAll(dead);
			dead.clear();
			if(!weapon.isReadyToShoot()){
				//only one char can shoot
				break;
			}
		}
		
		
		//zombies
		
		
	}
	
	private void addShootingEffect(Agent start, Agent goal){
		Vector3 startVector = new Vector3(start.x, start.y-4, 0);
		Vector3 goalVector = new Vector3(goal.x, goal.y-4, 0);
		camera.project(startVector);
		camera.project(goalVector);
		
		renderEffects.add(new ShootingEffect(startVector.x, startVector.y, goalVector.x, goalVector.y));
	}
	


	@Override
	public void render(float delta) {
		updateGame(delta);		
		renderer.update(delta);
		effectsRenderer.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// camera = new OrthographicCamera(width, height);
		// camera.setToOrtho(true);
		// camera.zoom = 0.5f;
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	
	public Player getPlayer(){
		return player;
	}
	
	public ArrayList<Follower> getFollowers(){
		return followers;
	}
	
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
	
	/* Brute Force Zombie Creation :D */
	private void createZombies() {
		String spriteNameArray[] = {"Zombie-slow-2", "Zombie-slow-3", "Zombie-slow-1", "Zombie-default-1", "Zombie-default-2", "Zombie-default-4", "Zombie-default-3", "Zombie-fat-1", "Zombie-fat-2", "Zombie-fast-1", "Zombie-fast-2"};
		Random rand = new Random();
		for(int i = 0; i < 400; i++){
			int x = Utils.random(100, map.mapSize * tileSize-100);
			int y = Utils.random(100, map.mapSize * tileSize-100);
			if(manager.getEnvironmentMap().fieldArray[x][y] == 0){
				//create Zombie
				enemies.add(new Zombie(x, y, spriteNameArray[(rand.nextInt(spriteNameArray.length))]));
			}else{
				i--;
			}
		}
		
	}



	public ArrayList<Effect> getRenderEffects() {
		return renderEffects;
	}



	public ArrayList<Character> getPlayerCharacters() {
		return playerCharacters;
	}
}
