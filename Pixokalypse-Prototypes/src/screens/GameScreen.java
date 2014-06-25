package screens;

import input.CustomInputDetector;
import input.CustomInputHandler;
import items.Weapon;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;

import javax.json.Json;
import javax.json.stream.JsonParser;

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
import agents.Enemy;
import agents.PlayerCharacter;
import agents.Zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.we.PixokalypsePrototypes.PixokalypsePrototypes;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteCollisionMapContainer;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class GameScreen implements Screen {

	private OrthographicCamera camera;

	EffectsRenderer effectsRenderer;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private PixokalypsePrototypes game;
	private PotentialFieldManager manager;
	private Map map;
	private ArrayList<PlayerCharacter> playerCharacters = new ArrayList<PlayerCharacter>();

	private RayTracer rayTracer;
	private ArrayList<Effect> renderEffects = new ArrayList<Effect>();
	GameRenderer renderer;

	private PlayerCharacter selectedPlayerCharacter;

	private SpriteCollisionMapContainer spriteCollisionMapContainer;
	private SpriteContainer spriteContainer;

	int tileSize = Constants.TILE_SIZE; // one tile = one field on map

	public GameScreen(PixokalypsePrototypes game) {
		this.game = game;
		map = new Map(40, 8, 4, 10);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		getCamera().setToOrtho(true);
		getCamera().zoom = 0.2f;

		spriteCollisionMapContainer = new SpriteCollisionMapContainer(
				"data/height.txt", "data/height.png");

		createPlayerCharacters();

		manager = new PotentialFieldManager(this, new StaticPotentialField(
				map.mapSize * tileSize, map.mapSize * tileSize));
		getPotentialFieldManager().addCollisionMapToEnvironment(map, spriteCollisionMapContainer);

		//Gdx.input.setInputProcessor(new GameInputProcessor(getPotentialFieldManager(), getCamera()));
		Gdx.input.setInputProcessor(new CustomInputDetector(new CustomInputHandler(this)));

		renderer = new GameRenderer(this, game.batch, getCamera(), map);
		renderer.setFonts(game.font12, game.font24);
		effectsRenderer = new EffectsRenderer(this, game.batch, getCamera());
		effectsRenderer.setFonts(game.font12, game.font24);
		rayTracer = new RayTracer(getPotentialFieldManager().getEnvironmentMap());

		createZombies();
		
		getCamera().position.set(selectedPlayerCharacter.x,
				selectedPlayerCharacter.y, 0);

	}

	private void action(float delta) {
		ArrayList<Enemy> deadEnemies = new ArrayList<Enemy>();
		ArrayList<Enemy> enemiesCloseToPlayer = new ArrayList<Enemy>();

		for (Enemy e : enemies) {
			if (Utils.getDistance(selectedPlayerCharacter, e) < 100) {
				enemiesCloseToPlayer.add(e);
			}
		}

		// PlayerCharacters attack
		for (PlayerCharacter character : playerCharacters) {
			Weapon weapon = character.getEquipppedWeapon();
			if (!weapon.isReadyToShoot()) {
				continue;
			}
			for (Enemy enemy : enemiesCloseToPlayer) {
				if (rayTracer.castRay((int) character.x, (int) character.y,
						(int) enemy.x, (int) enemy.y, character
								.getEquipppedWeapon().getRange(), false)) {
					weapon.shoot();
					enemy.currentHealth -= weapon.getDamage();
					addShootingEffect(character, enemy);

					if (enemy.currentHealth <= 0) {
						deadEnemies.add(enemy);
					}
					break;
				}
			}
			enemies.removeAll(deadEnemies);
			enemiesCloseToPlayer.removeAll(deadEnemies);
			deadEnemies.clear();
			if (!weapon.isReadyToShoot()) {
				// only one char can shoot at a time
				break;
			}
		}

		// zombies
	}

	private void addShootingEffect(Agent start, Agent end) {
		renderEffects.add(new ShootingEffect(start, end));
	}

	private void coolDownWeapons(float delta) {
		for (PlayerCharacter c : playerCharacters) {
			c.getEquipppedWeapon().tick(delta);
		}
	}

	private void createPlayerCharacters() {

		String data = Gdx.files.internal("data/names.json").readString();
		JsonParser parser = Json.createParser(new StringReader(data));
		ArrayList<String> names = new ArrayList<String>();
		while (parser.hasNext()) {
			JsonParser.Event event = parser.next();
			switch (event) {
			case END_ARRAY:
				break;
			case VALUE_STRING:
				String name = parser.getString();
				names.add(name);
			}
		}

		Random rand = new Random();

		for (int i = 0; i < 4; i++) {
			PlayerCharacter pc = new PlayerCharacter(200 + 3 * i, 200 + 3 * i,
					names.get(rand.nextInt(names.size())), "Char-"
							+ (rand.nextInt(4) + 1) + "-alive");
			playerCharacters.add(pc);
		}

		selectedPlayerCharacter = playerCharacters.get(0);

	}

	/* Brute Force Zombie Creation :D */
	private void createZombies() {
		String spriteNameArray[] = { "Zombie-slow-2", "Zombie-slow-3",
				"Zombie-slow-1", "Zombie-default-1", "Zombie-default-2",
				"Zombie-default-4", "Zombie-default-3", "Zombie-fat-1",
				"Zombie-fat-2", "Zombie-fast-1", "Zombie-fast-2" };
		Random rand = new Random();
		for (int i = 0; i < 400; i++) {
			int x = Utils.random(100, map.mapSize * tileSize - 100);
			int y = Utils.random(100, map.mapSize * tileSize - 100);
			if (getPotentialFieldManager().getEnvironmentMap().fieldArray[x][y] == 0) {
				// create Zombie
				enemies.add(new Zombie(x, y, spriteNameArray[(rand
						.nextInt(spriteNameArray.length))]));
			} else {
				i--;
			}
		}

	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public ArrayList<PlayerCharacter> getPlayerCharacters() {
		return playerCharacters;
	}

	public ArrayList<Effect> getRenderEffects() {
		return renderEffects;
	}

	public PlayerCharacter getSelectedPlayerCharacter() {
		return selectedPlayerCharacter;
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float delta) {
		updateGame(delta);
		renderer.update(delta);
		effectsRenderer.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {

	}

	private void tickRenderEffects(float delta) {
		ArrayList<Effect> dead = new ArrayList<Effect>();
		for (Effect e : renderEffects) {
			e.tick(delta);
			if (e.isDead())
				dead.add(e);
		}
		renderEffects.removeAll(dead);

	}

	public void updateGame(float delta) {
		getPotentialFieldManager().step(delta);
		coolDownWeapons(delta);
		tickRenderEffects(delta);
		action(delta);

		getCamera().position.set(selectedPlayerCharacter.x,
				selectedPlayerCharacter.y, 0);
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public PotentialFieldManager getPotentialFieldManager() {
		return manager;
	}


}
