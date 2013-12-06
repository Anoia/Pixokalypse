package screens;

import java.util.ArrayList;

import input.GameInputProcessor;
import potentialField.PotentialFieldManager;
import potentialField.StaticPotentialField;

import agents.Follower;
import agents.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	int tileSize = 40; //one tile = one field on map
	
	private Player player;
	private ArrayList<Follower> follower;
	
	
	public GameScreen(PixokalypsePrototypes game){
		this.game = game;
		map = new Map(60, 2, 2, 6);
		manager = new PotentialFieldManager(new StaticPotentialField(map.mapSize*tileSize, map.mapSize*tileSize));
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true);
		camera.zoom = 0.1f;
		
		Gdx.input.setInputProcessor(new GameInputProcessor(manager, camera));
		spriteContainer = new SpriteContainer();
		spriteCollisionMapContainer = new SpriteCollisionMapContainer();
		manager.addCollisionMapToEnvironment(map, spriteCollisionMapContainer);
		System.out.println("GAME SCREEN");
		
		//The Player, only one!
		player = new Player(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		manager.addPlayerCharacter(player);
		
		
	}

	@Override
	public void render(float delta) {
		
		//NEED UPDATE METHOD!
		manager.step(delta);
		
		camera.position.set(player.x, player.y, 0);
		camera.update();
		
		
		
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		

		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		//Sprites Rendern anfang
		Sprite sprite;
		for(int x = 0; x < map.mapSize; x ++){
			for(int y = 0; y < map.mapSize; y++){
				String spriteName = map.data[x][y].spriteName;
				sprite = spriteContainer.getSprite(spriteName);
				//sprite.setPosition(x*tileSize,y*tileSize);
				sprite.setBounds(x*tileSize,y*tileSize, tileSize, tileSize);
				sprite.draw(game.batch);	
			}
			
		}
		
		
		
		
		//Sprites Rendern ende
		game.batch.end();
		
		//Render Player + Followers
		game.shapeRenderer.setProjectionMatrix(camera.combined);
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(Color.RED);
		game.shapeRenderer.rect(player.x, player.y, 1, 1);
		game.shapeRenderer.end();
		
		

		
	}

	@Override
	public void resize(int width, int height) {
		//camera = new OrthographicCamera(width, height);
		//camera.setToOrtho(true);
		//camera.zoom = 0.5f;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
