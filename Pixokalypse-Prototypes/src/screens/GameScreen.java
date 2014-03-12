package screens;

import input.GameInputProcessor;

import java.util.ArrayList;

import potentialField.PotentialFieldManager;
import potentialField.StaticPotentialField;
import agents.Follower;
import agents.Player;

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
	int tileSize = 40; // one tile = one field on map

	private Player player;
	private ArrayList<Follower> followers = new ArrayList<Follower>();

	//sprites

	Sprite playersprite;
	Texture playerTexture;
	Texture followerTexture;
	Sprite followerSprite;
	
	public GameScreen(PixokalypsePrototypes game) {
		this.game = game;
		map = new Map(40,8,4,10);
		manager = new PotentialFieldManager(new StaticPotentialField(
				map.mapSize * tileSize, map.mapSize * tileSize));

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.setToOrtho(true);
		camera.zoom = 0.5f;

		Gdx.input.setInputProcessor(new GameInputProcessor(manager, camera));
		spriteContainer = new SpriteContainer();
		System.out.println("Spriteanz: " + spriteContainer.getSpriteCount());
		spriteCollisionMapContainer = new SpriteCollisionMapContainer(
				"data/height.txt", "data/height.png");
		System.out.println("Collisionmapanz: "
				+ spriteCollisionMapContainer.getCollisionmapCount());
		manager.addCollisionMapToEnvironment(map, spriteCollisionMapContainer);
		System.out.println("GAME SCREEN");

		// The Player, only one!
		player = new Player(200, 200);
		manager.addPlayerCharacter(player);

		for (int i = 0; i < 3; i++) {
			Follower f = new Follower(200 + 3 * i, 200 + 3 * i);
			followers.add(f);
			manager.addPlayerCharacter(f);
		}
		//charaktersprites
		playerTexture = new Texture(Gdx.files.internal("data/characters/char_1.png"));
		playersprite = new Sprite(playerTexture, 0, 0, 6, 8);
		playersprite.flip(false, true);

		followerTexture = new Texture(Gdx.files.internal("data/characters/char_2.png"));
		followerSprite = new Sprite(followerTexture, 0, 0, 6, 8);
		followerSprite.flip(false, true);
	}

	@Override
	public void render(float delta) {

		// NEED UPDATE METHOD!
		manager.step(delta);

		camera.position.set(player.x, player.y, 0);
		camera.update();

		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		// Sprites Rendern anfang
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
					sprite.draw(game.batch);
				}

			}

		}

		// Render Player
		sprite = playersprite;
		sprite.setPosition(player.x - sprite.getWidth() / 2,
				player.y - sprite.getHeight());
		sprite.draw(game.batch);

		// render followers
		sprite = followerSprite;
		if (!followers.isEmpty()) {
			for (Follower f : followers) {
				sprite.setPosition(f.x - sprite.getWidth() / 2,
						f.y - sprite.getHeight());
				sprite.draw(game.batch);
			}
		}

		
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
						System.out.println();		
						sprite = spriteContainer.getBlockSprite(spriteName);
						if(sprite != null){
							sprite.setBounds((x * tileSize), (y * tileSize)-tileSize*1.5f, tileSize,tileSize*2.5f);
							sprite.draw(game.batch);
						}else System.out.println("Fehler: "+spriteName+" gibt null");
						
					}
				}

			}

		}
		// Sprites Rendern ende
		//FPS
		
		game.batch.end();
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.batch.begin();
		game.font24.draw(game.batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 30, Gdx.graphics.getHeight() - 30);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// camera = new OrthographicCamera(width, height);
		// camera.setToOrtho(true);
		// camera.zoom = 0.5f;
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
		playerTexture.dispose();
		followerTexture.dispose();

	}

}
