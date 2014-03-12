package renderer;

import java.awt.Font;
import java.util.ArrayList;

import screens.GameScreen;
import util.Constants;

import agents.Enemy;
import agents.Follower;
import agents.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.we.PixokalypsePrototypes.test.FieldCategory;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class GameRenderer {
	
	private GameScreen game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Map map;
	private SpriteContainer spriteContainer;
	
	private int tileSize = Constants.TILE_SIZE;
	
	public BitmapFont font12, font24;
	
	//sprites

	Sprite playersprite;
	Texture playerTexture;
	Texture followerTexture;
	Sprite followerSprite;
	Sprite zombieSprite;
	Texture zombieTexture;
	
	public GameRenderer(GameScreen game, SpriteBatch batch, OrthographicCamera camera, Map map){
		this.game = game;
		this.batch = batch;
		this.camera = camera;
		this.map = map;
		spriteContainer = new SpriteContainer();
		initialize();
	}
	
	private void initialize(){
		//charaktersprites
		playerTexture = new Texture(Gdx.files.internal("data/characters/char_1.png"));
		playersprite = new Sprite(playerTexture, 0, 0, 6, 8);
		playersprite.flip(false, true);

		followerTexture = new Texture(Gdx.files.internal("data/characters/char_2.png"));
		followerSprite = new Sprite(followerTexture, 0, 0, 6, 8);
		followerSprite.flip(false, true);
		
		zombieTexture = new Texture(Gdx.files.internal("data/characters/zombie.png"));
		zombieSprite = new Sprite(zombieTexture, 0, 0, 6, 8);
		zombieSprite.flip(false, true);
	}
	
	public void update(float delta){
		
		camera.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Sprites Rendern anfang
		renderGround(delta);
		renderPlayer(delta);
		renderFollowers(delta);
		
		renderBuildings(delta);
		renderZombies(delta);
		
		batch.end();
		renderFPS();
	}
	
	private void renderGround(float delta){
		Sprite sprite;
		//Bodenebene Rendern
		for (int x = 0; x < map.mapSize; x++) {
			for (int y = 0; y < map.mapSize; y++) {
				// skip wenn nicht im Sichtbarem bereich
				if (camera.position.x > (x * tileSize - (Gdx.graphics
						.getWidth() * 0.6f))
						&& camera.position.x < (x * tileSize + (Gdx.graphics
								.getWidth() * 0.6f))
								&& camera.position.y > (y * tileSize - (Gdx.graphics
										.getHeight() * 0.6f))
										&& camera.position.y < (y * tileSize + (Gdx.graphics
												.getHeight() * 0.6f))) {
					String spriteName = map.data[x][y].spriteName;
					sprite = spriteContainer.getGroundSprite(spriteName);
					sprite.setBounds(x * tileSize, y * tileSize, tileSize,
							tileSize);
					sprite.draw(batch);
				}

			}

		}
	}
	
	private void renderBuildings(float delta){
		Sprite sprite;
		for (int x = 0; x < map.mapSize; x++) {
			for (int y = 0; y < map.mapSize; y++) {
				// skip wenn nicht im Sichtbarem bereich
				if (camera.position.x > (x * tileSize - (Gdx.graphics
						.getWidth() * 0.6f))
						&& camera.position.x < (x * tileSize + (Gdx.graphics
								.getWidth() * 0.6f))
								&& camera.position.y > (y * tileSize - (Gdx.graphics
										.getHeight() * 0.6f))
										&& camera.position.y < (y * tileSize + (Gdx.graphics
												.getHeight() * 0.6f))) {
					
					if(map.data[x][y].fieldCategory == FieldCategory.BUILDING){
						String spriteName = map.data[x][y].spriteName;		
						sprite = spriteContainer.getBlockSprite(spriteName);
						if(sprite != null){
							sprite.setBounds((x * tileSize), (y * tileSize)-tileSize*1.5f, tileSize,tileSize*2.5f);
							sprite.draw(batch);
						}else System.out.println("Fehler: "+spriteName+" gibt null");
						
					}
				}

			}

		}
	}
	
	private void renderPlayer(float delta){
		Player player = game.getPlayer();
		Sprite sprite = playersprite;
		sprite.setPosition(player.x - sprite.getWidth() / 2, player.y - sprite.getHeight());
		sprite.draw(batch);
	}
	
	private void renderFollowers(float delta){
		ArrayList<Follower> followers = game.getFollowers();
		Sprite sprite = followerSprite;
		if (!followers.isEmpty()) {
			for (Follower f : followers) {
				sprite.setPosition(f.x - sprite.getWidth() / 2,
						f.y - sprite.getHeight());
				sprite.draw(batch);
			}
		}
	}
	
	private void renderZombies(float delta){
		ArrayList<Enemy> enemies = game.getEnemies();
		Sprite sprite = zombieSprite;
		if (!enemies.isEmpty()) {
			for (Enemy e : enemies) {
				sprite.setPosition(e.x - sprite.getWidth() / 2,
						e.y - sprite.getHeight());
				sprite.draw(batch);
			}
		}
	}
	
	public void setFonts(BitmapFont font12, BitmapFont font24){
		this.font12 = font12;
		this.font24 = font24;
	}
	
	private void renderFPS(){
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.begin();
		font24.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 30, Gdx.graphics.getHeight() - 30);
		batch.end();
	}
	
	public void dispose(){
		playerTexture.dispose();
		followerTexture.dispose();
	}

}
