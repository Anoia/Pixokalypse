package screens;

import potentialField.PotentialFieldManager;
import agents.PlayerCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.we.PixokalypsePrototypes.PixokalypsePrototypes;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteCollisionMapContainer;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class PotentialFieldMapTestScreen implements Screen { // ,InputProcessor
																private OrthographicCamera camera;
	private Sprite debugSprite;
	// DebugVariablen zum Zeichen der PotentialFelder
	private Texture debugTexture;
	// {
	final PixokalypsePrototypes game;
	private Map mainMap;
	private PotentialFieldManager manager;
	private PlayerCharacter player;
	private PlayerCharacter player2;

	private PlayerCharacter player3;
	private PlayerCharacter player4;
	private PlayerCharacter player5;

	private SpriteCollisionMapContainer spriteCollisionMapConainer;
	private SpriteContainer spriteContainer;

	public PotentialFieldMapTestScreen(final PixokalypsePrototypes game) {
		this.game = game;
		/*
		 * manager = new PotentialFieldManager(new
		 * StaticPotentialField(Gdx.graphics.getWidth(),
		 * Gdx.graphics.getHeight()));
		 * Gdx.input.setInputProcessor(new
		 * PotentialFieldInputProcessor(manager)); player = new
		 * PlayerCharacter(100, 100, "name", "player");
		 * manager.addPlayerCharacter(player); player2 = new
		 * PlayerCharacter(150, 100, "name", "player");
		 * manager.addPlayerCharacter(player2); player3 = new
		 * PlayerCharacter
		 * ((int)Gdx.graphics.getWidth()/2,(int)Gdx
		 * .graphics.getHeight()/2, "name", "player");
		 * manager.addPlayerCharacter(player3); player4 = new
		 * PlayerCharacter(150, 130, "name", "player");
		 * manager.addPlayerCharacter(player4); player5 = new
		 * PlayerCharacter(150, 80, "name", "player");
		 * manager.addPlayerCharacter(player5); spriteContainer
		 * = new SpriteContainer(); spriteCollisionMapConainer =
		 * new SpriteCollisionMapContainer("data/height.txt",
		 * "data/height.png"); mainMap = new Map();
		 * manager.addCollisionMapToEnvironment(mainMap,
		 * spriteCollisionMapConainer);
		 */
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
	}

	// Zeichnet die combinedMap des PotentialFieldManagers in einen Sprite
	public void reDrawDebugMap() {
		System.out.println("rebuild debugmap");
		if (manager.combinedMap != null) {
			Pixmap pm = new Pixmap(Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight(), Format.RGBA4444);
			// PixMap pixMap = new PixMap();
			float farbzahl = 1.f / 255;
			for (int x = 0; x < manager.combinedMap.width; x++) {
				for (int y = 0; y < manager.combinedMap.height; y++) {
					float zahl = farbzahl
							* manager.combinedMap.fieldArray[x][y];
					Color color = new Color(1 - zahl, 1, 1, 1);
					if (manager.combinedMap.fieldArray[x][y] >= 10000)
						color = Color.BLACK;
					if (manager.combinedMap.fieldArray[x][y] == 0)
						color = new Color(0, 0, 0, 0);
					// if(manager.combinedMap.potentialFieldMap[x][y] !=
					// 0)System.out.println(zahl);
					// System.out.println(Integer.toHexString(color.toIntBits()));
					pm.setColor(color);
					// pm.setColor(Color.BLACK);
					pm.fillRectangle(x, y, 1, 1);
				}
			}

			debugTexture = new Texture(game.getNextPowOf2(Gdx.graphics
					.getWidth()), game.getNextPowOf2(Gdx.graphics.getHeight()),
					Format.RGBA4444);
			debugTexture.draw(pm, 0, 0);
			pm.dispose();

			// texture = new Texture(Gdx.files.internal("data/libgdx.png"));
			debugTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion region = new TextureRegion(debugTexture, 0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			debugSprite = new Sprite(region);
			debugSprite.setPosition(0, 0);
			debugSprite.flip(false, true);
			manager.neuRendernA = false;
			manager.neuRendernB = false;
		}

	}

	@Override
	public void render(float delta) {
		manager.step(delta);
		if (manager.neuRendernA && manager.neuRendernB)
			reDrawDebugMap();
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// angeblich f�r transparenz notwendig
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		// Sprites Rendern anfang
		int tileSize = 40;
		Sprite sprite;
		for (int x = 0; x < mainMap.mapSize; x++) {
			for (int y = 0; y < mainMap.mapSize; y++) {
				String spriteName = mainMap.data[x][y].spriteName;
				sprite = spriteContainer.getSprite("Ground-" + spriteName);
				sprite.setPosition(x * tileSize, y * tileSize);
				sprite.draw(game.batch);
			}
		}

		// combinedMapField Rendern f�r Debugzwecke
		if (debugSprite != null) {
			debugSprite.draw(game.batch);
		}

		// Sprites Rendern ende
		game.batch.end();

		// Shape rendern
		game.shapeRenderer.setProjectionMatrix(camera.combined);
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(Color.RED);
		game.shapeRenderer.rect(player.x - 3, player.y - 3, 7, 7);
		game.shapeRenderer.setColor(Color.GREEN);
		game.shapeRenderer.rect(player2.x - 3, player2.y - 3, 7, 7);
		game.shapeRenderer.setColor(Color.BLUE);
		game.shapeRenderer.rect(player3.x - 3, player3.y - 3, 7, 7);
		game.shapeRenderer.setColor(Color.ORANGE);
		game.shapeRenderer.rect(player4.x - 3, player4.y - 3, 7, 7);
		game.shapeRenderer.setColor(Color.DARK_GRAY);
		game.shapeRenderer.rect(player5.x - 3, player5.y - 3, 7, 7);
		game.shapeRenderer.end();

		// angeblich f�r transparenz notwendig ende
		Gdx.gl.glDisable(GL20.GL_BLEND);

		// FPS unten links rendern
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.batch.begin();
		game.font12.draw(game.batch,
				"fps: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(true, width, height);
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}