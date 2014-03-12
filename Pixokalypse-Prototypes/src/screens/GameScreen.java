package screens;

import input.GameInputProcessor;

import java.util.ArrayList;

import potentialField.PotentialFieldManager;
import potentialField.StaticPotentialField;
import renderer.GameRenderer;
import util.Constants;
import util.RayTracer;
import util.Utils;
import agents.Enemy;
import agents.Follower;
import agents.Player;
import agents.Zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.we.PixokalypsePrototypes.PixokalypsePrototypes;
import com.we.PixokalypsePrototypes.test.FieldCategory;
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
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	
	GameRenderer renderer;

	private RayTracer rayTracer;
	

	
	public GameScreen(PixokalypsePrototypes game) {
		this.game = game;
		map = new Map(40,8,4,10);
		manager = new PotentialFieldManager(new StaticPotentialField(
				map.mapSize * tileSize, map.mapSize * tileSize));

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.setToOrtho(true);
		camera.zoom = 0.3f;

		Gdx.input.setInputProcessor(new GameInputProcessor(manager, camera));
		
		
		//System.out.println("Spriteanz: " + spriteContainer.getSpriteCount());
		spriteCollisionMapContainer = new SpriteCollisionMapContainer(
				"data/height.txt", "data/height.png");
		//System.out.println("Collisionmapanz: " + spriteCollisionMapContainer.getCollisionmapCount());
		
		manager.addCollisionMapToEnvironment(map, spriteCollisionMapContainer);

		// The Player, only one!
		player = new Player(200, 200);
		manager.addPlayerCharacter(player);

		for (int i = 0; i < 3; i++) {
			Follower f = new Follower(200 + 3 * i, 200 + 3 * i);
			followers.add(f);
			manager.addPlayerCharacter(f);
		}
		
		renderer = new GameRenderer(this, game.batch, camera, map);
		renderer.setFonts(game.font12, game.font24);
		rayTracer = new RayTracer(manager.getEnvironmentMap());
		
		createZombies();
		

	}
	


	public void updateGame(float delta){
		manager.step(delta);
		camera.position.set(player.x, player.y, 0);
	}

	@Override
	public void render(float delta) {
		updateGame(delta);		
		renderer.update(delta);
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
		for(int i = 0; i < 400; i++){
			int x = Utils.random(100, map.mapSize * tileSize-100);
			int y = Utils.random(100, map.mapSize * tileSize-100);
			if(manager.getEnvironmentMap().fieldArray[x][y] == 0){
				//create Zombie
				enemies.add(new Zombie(x, y));
			}else{
				i--;
			}
		}
		
	}
}
