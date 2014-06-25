package renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import screens.GameScreen;
import util.Constants;
import agents.Agent;
import agents.Enemy;
import agents.PlayerCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.we.PixokalypsePrototypes.test.FieldCategory;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class GameRenderer {

	public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);
	public static final float ambientIntensity = .7f;
	private SpriteBatch batch;

	private OrthographicCamera camera;
	Comparator<Agent> comparator;

	final String defaultPixelShader = Gdx.files.internal(
			"data/shader/defaultPixelShader.glsl").readString();

	private ShaderProgram defaultShader;

	// Shader stuff
	private FrameBuffer fbo;

	final String finalPixelShader = Gdx.files.internal(
			"data/shader/pixelShader.glsl").readString();

	private ShaderProgram finalShader;
	public BitmapFont font12, font24;

	private GameScreen game;
	private Texture light;

	private Map map;
	private SpriteContainer spriteContainer;
	private int tileSize = Constants.TILE_SIZE;

	final String vertexShader = Gdx.files.internal(
			"data/shader/vertexShader.glsl").readString();

	public GameRenderer(GameScreen game, SpriteBatch batch,
			OrthographicCamera camera, Map map) {
		this.game = game;
		this.batch = batch;
		this.camera = camera;
		this.map = map;
		spriteContainer = new SpriteContainer();
		initializeShader();
		comparator = new Comparator<Agent>() {
			@Override
			public int compare(Agent o1, Agent o2) {
				return (int) (o1.y - o2.y);
			}
		};

	}

	public void dispose() {

	}

	private void drawLightToFBO() {
		fbo.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(defaultShader);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		float lightSize = 150.0f;
		PlayerCharacter p = game.getSelectedPlayerCharacter();
		batch.draw(light, p.x - lightSize * 0.5f, p.y - 4 - lightSize * 0.5f,
				lightSize, lightSize);
		batch.end();
		fbo.end();

	}

	private ArrayList<Building> getBuildingsToRender() {
		ArrayList<Building> buildings = new ArrayList<Building>();
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

					if (map.data[x][y].fieldCategory == FieldCategory.BUILDING) {
						String spriteName = map.data[x][y].spriteName;
						int xPos = x * tileSize;
						int yPos = (int) (y * tileSize - tileSize * 1.5f);
						int width = tileSize;
						int height = (int) (tileSize * 2.5);
						buildings.add(new Building(xPos, yPos, width, height,
								spriteName));
					}
				}

			}

		}

		return buildings;
	}

	private ArrayList<Enemy> getZombiesToRender() {
		ArrayList<Enemy> enemies = game.getEnemies();
		ArrayList<Enemy> toRender = new ArrayList<Enemy>();
		for (Enemy e : enemies) {
			if (e.x > camera.position.x - Gdx.graphics.getWidth() / 2
					&& e.x < camera.position.x + Gdx.graphics.getWidth() / 2
					&& e.y > camera.position.y - Gdx.graphics.getHeight() / 2
					&& e.y < camera.position.y + Gdx.graphics.getHeight() / 2) {
				toRender.add(e);
			}
		}
		return toRender;
	}

	private void initializeShader() {

		ShaderProgram.pedantic = false;
		defaultShader = new ShaderProgram(vertexShader, defaultPixelShader);
		finalShader = new ShaderProgram(vertexShader, finalPixelShader);

		finalShader.begin();
		finalShader.setUniformi("u_lightmap", 1);
		finalShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
				ambientColor.z, ambientIntensity);
		finalShader.end();

		light = new Texture("data/shader/light.png");

	}

	private void renderAgents() {
		ArrayList<Agent> agents = new ArrayList<Agent>();
		agents.addAll(game.getPlayerCharacters());
		agents.addAll(getZombiesToRender());
		Collections.sort(agents, comparator);
		Sprite sprite;
		for (Agent a : agents) {
			sprite = spriteContainer.getSprite(a.getSpriteName());
			sprite.setPosition(a.x - sprite.getWidth() / 2,
					a.y - sprite.getHeight());
			sprite.draw(batch);
		}
	}

	private void renderBuildings() {
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

					if (map.data[x][y].fieldCategory == FieldCategory.BUILDING) {
						String spriteName = map.data[x][y].spriteName;
						sprite = spriteContainer.getSprite("Block-"
								+ spriteName);
						if (sprite != null) {
							sprite.setBounds((x * tileSize), (y * tileSize)
									- tileSize * 1.5f, tileSize,
									tileSize * 2.5f);
							sprite.draw(batch);

						} else
							System.out.println("Fehler: " + spriteName
									+ " gibt null");

					}
				}

			}

		}
	}

	private void renderGround() {
		Sprite sprite;
		// Bodenebene Rendern
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
					sprite = spriteContainer.getSprite("Ground-" + spriteName);
					sprite.setBounds(x * tileSize, y * tileSize, tileSize,
							tileSize);
					sprite.draw(batch);
				}

			}

		}
	}

	public void resize(int width, int height) {
		fbo = new FrameBuffer(Format.RGBA8888, width, height, false);

		finalShader.begin();
		finalShader.setUniformf("resolution", width, height);
		finalShader.end();
	}

	public void setFonts(BitmapFont font12, BitmapFont font24) {
		this.font12 = font12;
		this.font24 = font24;
	}

	public void update(float delta) {

		drawLightToFBO();

		camera.update();

		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.setShader(finalShader);
		batch.begin();
		fbo.getColorBufferTexture().bind(1);
		light.bind(0);

		// Sprites Rendern anfang
		renderGround();
		// renderWithZIndex(delta);
		renderAgents();
		renderBuildings();

		batch.end();
	}

}
