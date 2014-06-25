package potentialField;

import java.util.ArrayList;

import screens.GameScreen;
import util.Constants;
import util.GridPoint2;
import util.Utils;
import agents.Agent;
import agents.Enemy;
import agents.PlayerCharacter;

import com.badlogic.gdx.Gdx;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteCollisionMapContainer;

public class PotentialFieldManager {

	public CombinedFields combinedMap;

	private StaticPotentialField environmentMap;
	private GameScreen game;
	public boolean neuRendernA = false;
	public boolean neuRendernB = false;

	private Target playerCharacterTarget;
	private CircleDynamicPotentialField pushingPlayerCharacterPotentialFieldMap;	// wird auf andere playerChars angewendet,
																		 		 	// damit sie nicht ineinander laufen
	
	private CircleDynamicPotentialField pullingZombiePotentialFieldMap;

	public PotentialFieldManager(GameScreen game,
			StaticPotentialField environmentMap) {
		this.game = game;
		this.environmentMap = environmentMap;
		pushingPlayerCharacterPotentialFieldMap = new CircleDynamicPotentialField(
				7, true);
		pullingZombiePotentialFieldMap = new CircleDynamicPotentialField(30, false);
		this.setPlayerCharacterTarget((int) Gdx.graphics.getWidth() / 2,
				(int) Gdx.graphics.getHeight());
	}

	public void addCollisionMapToEnvironment(Map map,
			SpriteCollisionMapContainer spriteCollisionMapContainer) {
		// Für jeden MapTile
		int tileSize = Constants.TILE_SIZE;// müssen wir in der Map Speichern :D
		for (int i = 0; i < map.mapSize; i++) {
			for (int j = 0; j < map.mapSize; j++) {
				// Collisionmap in Environmentmap kopieren
				// System.out.println("Spritename: "+map.data[j][i].spriteName);
				int[][] collisionmap = spriteCollisionMapContainer
						.getSpriteCollisionmap("Heightmap-"
								+ map.data[j][i].spriteName);
				for (int row = 0; row < tileSize; row++) {
					if (collisionmap == null)
						System.out.println("collisionMap is NULL für sprite: "
								+ map.data[j][i].spriteName);
					if (environmentMap == null)
						System.out.println("enviromentnMap is NULL");
					System.arraycopy(collisionmap[tileSize - 1 - row], 0,
							environmentMap.fieldArray[row + j * tileSize], i
									* tileSize, tileSize);
				}
			}
		}
	}

	// Hilfsfunktion zum Debugen
	// Setzt ein "Gebäude" in die environmentMap
	public void drawOnEnvironmentMap(int screenX, int screenY) {
		int radius = 10;

		CircleDynamicPotentialField cDPF = new CircleDynamicPotentialField(
				radius * 2, true);
		System.out.println(cDPF.fieldArray.length);
		System.out.println((screenX - radius - 10) + ","
				+ (screenX + radius + 9));

		for (int x = screenX - radius - 10; x < screenX + radius + 9; x++) {
			for (int y = screenY - radius - 10; y < screenY + radius + 9; y++) {
				System.out.println((x - (screenX - radius - 10)) + ","
						+ (y - (screenY - radius - 10)));
				System.out.println();
				int bla = cDPF.fieldArray[x - (screenX - radius - 10)][y
						- (screenY - radius - 10)];
				environmentMap.fieldArray[x][y] = bla;
			}
		}

		for (int x = screenX - radius; x < screenX + radius; x++) {
			for (int y = screenY - radius; y < screenY + radius; y++) {
				environmentMap.fieldArray[x][y] = 10000;
			}
		}
		neuRendernA = true;
	}

	/**
	 * Checks the Fields around the Agent and chooses the one with the lowest
	 * value as the destination
	 * 
	 * @param agent The Agent whose destination should be determined
	 * @param map 	The PotentialFieldMap for that Agent
	 * @return 		the Position the Agent should choose
	 */
	private GridPoint2 getDestination(Agent agent, CombinedFields map) {
		int smallest = map.fieldArray[(int) agent.x][(int) agent.y];
		GridPoint2 pos = new GridPoint2((int) agent.x, (int) agent.y);
		for (int i = (int) agent.x - 1; i <= (int) agent.x + 1; i++) {
			for (int j = (int) agent.y - 1; j <= (int) agent.y + 1; j++) {
				if (map.fieldArray[i][j] < smallest) {
					pos.set(i, j);
					smallest = map.fieldArray[i][j];
				}
			}
		}

		return pos;
	}

	public StaticPotentialField getEnvironmentMap() {
		return environmentMap;
	}

	public void printZoneACII(int screenX, int screenY) {
		ArrayList<PlayerCharacter> playerCharacters = game
				.getPlayerCharacters();
		int radius = 20;
		System.out.println("\n\n");
		for (PlayerCharacter player : playerCharacters) {
			System.out.println(player.x + "," + player.y);
		}
		for (int y = screenY - radius; y < screenY + radius; y++) {
			System.out.println();
			for (int x = screenX - radius; x < screenX + radius; x++) {
				int value = combinedMap.fieldArray[x][y];
				String stringValue = "" + value;
				while (stringValue.length() < 5)
					stringValue = " " + stringValue;
				boolean playerposition = false;
				for (PlayerCharacter player : playerCharacters) {
					if ((int) player.x == x && (int) player.y == y)
						playerposition = true;
				}
				if (playerposition) {
					stringValue = stringValue.substring(1, 5);
					stringValue = "X" + stringValue;
				}
				System.out.print("[" + stringValue + "]");
			}
		}
	}

	public void setPlayerCharacterTarget(int x, int y) {
		int radius = 200;
		playerCharacterTarget = new Target(
				new SuperIntelligentDynamicPotentialField(environmentMap,
						radius, false, x - radius, y - radius), x - radius, y
						- radius);
		neuRendernA = true;
	}

	public void step(float delta) {
		stepPlayerCharacters(delta);
		stepEnemies(delta);
		if (neuRendernA)
			neuRendernB = true;
	}

	private void stepEnemies(float delta) {
		
		ArrayList<Enemy> enemies = game.getEnemiesOnScreen();
		combinedMap = new CombinedFields(environmentMap);
		for(Enemy e: enemies){
			PlayerCharacter closestPlayerCharacter = game.getClosestPlayerCharacterForEnemy(e);
			combinedMap.add(pullingZombiePotentialFieldMap, closestPlayerCharacter);
			GridPoint2 destination = getDestination(e, combinedMap);
			int xDirection = destination.x - (int) e.x;
			int yDirection = destination.y - (int) e.y;		
			e.makeStep(delta, xDirection, yDirection);
			combinedMap.remove(pullingZombiePotentialFieldMap, closestPlayerCharacter);
			
		}
				
	}

	public void stepPlayerCharacters(float delta) {
		ArrayList<PlayerCharacter> playerCharacters = game.getPlayerCharacters();
		combinedMap = new CombinedFields(environmentMap);
		combinedMap.add(playerCharacterTarget);
		for (PlayerCharacter pc : playerCharacters) {
			combinedMap.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}

		for (PlayerCharacter pc : playerCharacters) {
			combinedMap.remove(pushingPlayerCharacterPotentialFieldMap, pc);
			GridPoint2 destination = getDestination(pc, combinedMap);
			int xDirection = destination.x - (int) pc.x;
			int yDirection = destination.y - (int) pc.y;
			pc.makeStep(delta, xDirection, yDirection);
			combinedMap.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}

	}

}
