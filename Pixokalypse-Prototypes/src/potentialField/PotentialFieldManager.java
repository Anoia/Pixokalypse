package potentialField;

import java.util.ArrayList;

import util.GridPoint2;
import Agents.Agent;
import Agents.Character;

import com.badlogic.gdx.Gdx;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteCollisionMapContainer;

public class PotentialFieldManager {
	
	private StaticPotentialField  environmentMap;
	public CombinedFields combinedMap;
	public boolean neuRendernA = false;
	public boolean neuRendernB = false;
	
	
	private ArrayList<Character> playerCharacters;
	private CircleDynamicPotentialField pushingPlayerCharacterPotentialFieldMap; //wird auf andere playerCharacters angewendet, damit sie nicht ineinander laufen
	private Target playerCharacterTarget;
	
	
	public PotentialFieldManager(StaticPotentialField environmentMap){
		
		this.environmentMap = environmentMap;
		this.playerCharacters = new ArrayList<Character>();
		pushingPlayerCharacterPotentialFieldMap = new CircleDynamicPotentialField(7, true);
		this.setPlayerCharacterTarget((int)Gdx.graphics.getWidth()/2, (int)Gdx.graphics.getHeight());	
	}
	
	public void step(float delta){
		stepPlayerCharacters(delta);
		if(neuRendernA)neuRendernB = true;
	}
	
	public void stepPlayerCharacters(float delta){
		combinedMap = new CombinedFields(environmentMap);
		combinedMap.add(playerCharacterTarget);		
		for(Character pc: playerCharacters){
			combinedMap.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}
		
		for(Character pc: playerCharacters){
			combinedMap.remove(pushingPlayerCharacterPotentialFieldMap, pc);
			GridPoint2 destination = getDestination(pc, combinedMap);
			int xDirection = destination.x - (int)pc.x;
			int yDirection = destination.y - (int)pc.y;
			pc.makeStep(delta, xDirection, yDirection);
			combinedMap.add(pushingPlayerCharacterPotentialFieldMap, pc);
		}

	}
	
	private GridPoint2 getDestination(Agent pc, CombinedFields map) {
		int smallest = map.fieldArray[(int)pc.x][(int)pc.y];
		GridPoint2 pos = new GridPoint2((int)pc.x, (int)pc.y);
		for(int i = (int)pc.x-1; i<=(int)pc.x+1; i++){
			for(int j = (int)pc.y-1; j<=(int)pc.y+1; j++){
				if(map.fieldArray[i][j] < smallest){
					pos.set(i, j);
					smallest = map.fieldArray[i][j];
				}
			}
		}
		
		
		
		return pos;
	}

	public void addCollisionMapToEnvironment(Map map, SpriteCollisionMapContainer spriteCollisionMapContainer){
		//F�r jeden MapTile
		int tileSize = 40;// m�ssen wir in der Map Speichern :D
		for(int i = 0; i < map.mapSize;i++){
			for(int j = 0; j < map.mapSize;j++){
				//Collisionmap in Environmentmap kopieren
				System.out.println("Spritename: "+map.data[j][i].spriteName);
				int[][] collisionmap = spriteCollisionMapContainer.getSpriteCollisionmap(map.data[j][i].spriteName);
				for(int row = 0; row < tileSize;row++){
					if(collisionmap == null)System.out.println("collisionMap is NULL");
					if(environmentMap == null)System.out.println("enviromentnMap is NULL");
					System.arraycopy( collisionmap[39-row], 0, environmentMap.fieldArray[row+j*tileSize], i*tileSize, tileSize);
				}
			}	
		}
	}
		
	public void addPlayerCharacter(Character pc){
		playerCharacters.add(pc);
	}
	
	public void setPlayerCharacterTarget(int x, int y){
		System.out.println("new target");
		int radius = 200;
		//playerCharacterTarget = new Target(new CircleDynamicPotentialField(radius, false), x-radius, y-radius);
		playerCharacterTarget = new Target(new SuperIntelligentDynamicPotentialField(environmentMap, radius, false,x-radius, y-radius), x-radius, y-radius);
		
		neuRendernA = true;
	}

	//Hilfsfunktion zum Debugen
	//Setzt ein "Geb�ude" in die environmentMap
	public void drawOnEnvironmentMap(int screenX, int screenY) {
		int radius = 10;
		
		CircleDynamicPotentialField cDPF = new CircleDynamicPotentialField(radius*2, true);
		System.out.println(cDPF.fieldArray.length);
		System.out.println((screenX-radius-10)+","+(screenX+radius+9));
		
		for(int x = screenX-radius-10; x < screenX+radius+9;x++){
			for(int y = screenY-radius-10; y < screenY+radius+9;y++){
				System.out.println((x-(screenX-radius-10))+","+(y-(screenY-radius-10)));
				System.out.println();
				int bla = cDPF.fieldArray[x-(screenX-radius-10)][y-(screenY-radius-10)];
				environmentMap.fieldArray[x][y] = bla;
			}	
		}
		
		for(int x = screenX-radius; x < screenX+radius;x++){
			for(int y = screenY-radius; y < screenY+radius;y++){
				environmentMap.fieldArray[x][y] = 10000;
			}	
		}
		neuRendernA = true;
	}

	public void printZoneACII(int screenX, int screenY) {
		int radius = 20;
		System.out.println("\n\n");
			for(Character player: playerCharacters){
					System.out.println(player.x+","+player.y);
				}
		for(int y = screenY-radius; y < screenY+radius;y++){
			System.out.println();			
			for(int x = screenX-radius; x < screenX+radius;x++){
				int value = combinedMap.fieldArray[x][y];
				String stringValue = ""+value;
				while (stringValue.length()<5)stringValue = " "+stringValue;
				boolean playerposition = false;
				for(Character player: playerCharacters){
					if((int)player.x == x && (int)player.y == y)playerposition = true;
				}
				if(playerposition){
					stringValue = stringValue.substring(1, 5);
					stringValue = "X"+stringValue;
				}
				System.out.print("["+stringValue+"]");
			}	
		}
	}

}