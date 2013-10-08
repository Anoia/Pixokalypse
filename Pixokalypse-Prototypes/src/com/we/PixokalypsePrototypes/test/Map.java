package com.we.PixokalypsePrototypes.test;

import java.util.HashSet;

enum Direction{
	UP, RIGHT, DOWN, LEFT
}

public class Map {
	
	public int mapSize; //anzahl der Felder
	private int minFieldsPerBlock;
	private int randomFieldsPerBlock; //optionale Gebäude
	public Field[][] map;
	private int mapBorder; //rand der map, wenn der erreicht ist wird nicht mehr generiert
	
	public Map(){
		this(15, 2, 2, 3);
	}
	
	public Map(int mapSize, int minFieldsPerBlock, int randomFieldsPerBlock, int mapBorder){
		this.mapSize = mapSize;
		this.minFieldsPerBlock = minFieldsPerBlock;
		this.randomFieldsPerBlock = randomFieldsPerBlock;
		this.mapBorder = mapBorder;
		map = new Field[mapSize][mapSize];
		this.initEmptyMap();
		this.generateMap();
		this.fixStreets();
		this.setTileSprites();
		printASCII();
		//Print Map
	}
	

	private void setTileSprites() {
		for(int x = 1; x < mapSize-1; x++){
			for(int y = 1; y < mapSize-1; y++){
				Field field = map[x][y];
				if(field.fieldCategory == FieldCategory.STREET)setStreetTile(x, y);
			}
		}
		
		for(int x = 0; x < mapSize; x++){
			for(int y = 0; y < mapSize; y++){
				Field field = map[x][y];
				if(field.fieldCategory == FieldCategory.EMPTY)setRandomeEmptyField(x, y);
				else if(field.fieldCategory == FieldCategory.BUILDING)setRandomNonStreetTile(x, y);
			}
		}
	}

	private void initEmptyMap() {
		for(int x = 0; x < mapSize; x++){
			for(int y = 0; y < mapSize; y++){
				map[x][y] = new Field(x, y);
			}
		}
	}

	/**
	 * Generates the map by moving in a spiral path from the middle to the outside 
	 * and generating blocks of buildings with streets around them on the way. 
	 */
	private void generateMap(){
		int currentFieldX = (int) mapSize/2;
		int currentFieldY = currentFieldX;
		
		int turnTimes = 2; //anzahl der drehungen bis die strecke verlängert werden muss
		int distance = 1; //schritte zu gehen bis richtung gewechselt werden muss
		int distanceCounter = distance; //um schritte runter zu zählen
		Boolean keepGenerating = true;
		
		Direction direction = Direction.DOWN;
		//int i = direction.ordinal();
		//direction = Direction.values()[(i++)%Direction.values().length];
		
		int currentBlockId = 1;
		
		while(keepGenerating){
			
			if(distanceCounter == 0){ //wenn aktuelle strecke zuende gelaufen
				turnTimes--; //dehungen bis streckenänderung verringern
				
				//richtungsänderung
				int newDirection = (direction.ordinal() + 1) % Direction.values().length;
				direction = Direction.values()[newDirection];
				
				//distance erhöhen wenn turnTimes = 0;
				if(turnTimes == 0){
					turnTimes = 2;
					distance++;
				}
				//starte counter mit neuer distance von vorne
				distanceCounter = distance;				
			}
			
			distanceCounter--;
			switch(direction){
				case UP: 
					currentFieldY--;
					break;
				case RIGHT: 
					currentFieldX++;
					break;
				case DOWN: 
					currentFieldY++;
					break;
				case LEFT: 
					currentFieldX--;
					break;
			}
			
			Field field = map[currentFieldX][currentFieldY];			
			if(field.fieldCategory == FieldCategory.EMPTY){
				createBlock(field, currentBlockId);
				currentBlockId++;
			}
			
			keepGenerating = !(currentFieldX==mapBorder || currentFieldY == mapBorder);
			
		}	
	}

	/**
	 * generates a block of buildings around a field (including that field)
	 * @param field
	 * @param currentBlockId
	 */
	private void createBlock(Field field, int currentBlockId) {
		field.blockID = currentBlockId;
		field.fieldCategory = FieldCategory.BUILDING;
		
		//emptyNeigbours enthält alle direkten Nachbarn zu field, auf denen Gebäude platziert werden können
		HashSet<Field> emptyNeighbours = new HashSet<Field>();
		HashSet<Field> fieldsAddedToBlock  = new HashSet<Field>();
		HashSet<Field> possibleStartFields  = new HashSet<Field>();
		
		possibleStartFields.add(field);
		fieldsAddedToBlock.add(field);
		
		int amountOfBuildingsToPlace = minFieldsPerBlock + (int) (Math.random()*randomFieldsPerBlock +1 ) - 1;
		
		while(amountOfBuildingsToPlace > 0 ){
			
			while(emptyNeighbours.isEmpty()){
				if(possibleStartFields.isEmpty()){
					break; //hier geht gar nichts mehr! ENDE
				}else{
					field = possibleStartFields.iterator().next();
					possibleStartFields.remove(field);
					emptyNeighbours = getEmptyNeighbours(field);
				}
			}
			
			if(emptyNeighbours.isEmpty()){
				//break in der oberen while wurde getriggert
				break;
			}			
			
			//wir haben auf jeden fall emptyNachbarn
			
			Field fieldToAdd = emptyNeighbours.iterator().next();
			emptyNeighbours.remove(fieldToAdd);
			fieldToAdd.blockID = currentBlockId;
			fieldToAdd.fieldCategory = FieldCategory.BUILDING;
			fieldsAddedToBlock.add(fieldToAdd);
			possibleStartFields.add(fieldToAdd);
			amountOfBuildingsToPlace--;
			
		}
		
		createStreetsAroundBlock(fieldsAddedToBlock);
		
		
	}
	
	/**
	 * surrounds a block of buildings with streets
	 * @param block a HashSet<Field> containing all the Fields that belong to the Block
	 */
	private void createStreetsAroundBlock(HashSet<Field> block){
		for(Field f: block){
			HashSet<Field> emptyNeighbours = getEmptyNeighbours(f);
			for(Field whenIGrowUpIWantToBeAStreet: emptyNeighbours){
				whenIGrowUpIWantToBeAStreet.blockID = 0;
				whenIGrowUpIWantToBeAStreet.fieldCategory = FieldCategory.STREET;
			}
		}
	}
	
	/**
	 * finds all the neighbours of a field whose fieldCategory is EMPTY
	 * @param field the field whose neighbours should be found
	 * @return HashSet with all empty fields
	 */
	private HashSet<Field> getEmptyNeighbours(Field field){
		HashSet<Field> emptyNeighbours = new HashSet<Field>();
		for(int x = field.xAxis-1; x <= field.xAxis+1; x++){
			for(int y = field.yAxis-1; y <= field.yAxis+1; y++){
				if(!(x == field.xAxis && y == field.yAxis)){
					Field candidate = map[x][y];
					if(candidate.fieldCategory == FieldCategory.EMPTY){
						emptyNeighbours.add(candidate);
					}
				}
			}
		}
		
		return emptyNeighbours;
	}

	
	/**
	 * prints the map to the console
	 */
	public void printASCII() {
		for(int i = 0; i < mapSize; i ++){
			System.out.print("\n");
			for(int j = 0; j < mapSize; j++){
				switch(map[j][i].fieldCategory){
				case EMPTY:
					System.out.print("   ");
					break;
				case BUILDING:
					System.out.print(" B ");
					break;
				case STREET:
					System.out.print(" + ");
					break;
				}
			}
		}
		
	}
	
	/**
	 * removes Blocks of 4 or 6 streets by replacing one of the Streets with a Building
	 */
	private void fixStreets() {
		
		for(int y = 1; y < mapSize-1; y++){
			for(int x = 1; x < mapSize-1; x++){
				if(map[x][y].fieldCategory == FieldCategory.STREET &&  //1
						map[x+1][y].fieldCategory == FieldCategory.STREET && //2
 						map[x+1][y+1].fieldCategory == FieldCategory.STREET && //3
						map[x][y+1].fieldCategory == FieldCategory.STREET){   //4
					
					//4er gefunden
					if(!(map[x-1][y].fieldCategory == FieldCategory.STREET) && 
							!(map[x][y-1].fieldCategory == FieldCategory.STREET)){
						//links oben kann weg
						makeBuilding(x, y, map[x-1][y].blockID);
						
					}else if(!(map[x+1][y-1].fieldCategory == FieldCategory.STREET) && 
							!(map[x+2][y].fieldCategory == FieldCategory.STREET)){
						//rechts oben kann weg
						makeBuilding(x+1, y, map[x+1][y-1].blockID);
						
					}else if(!(map[x-1][y+1].fieldCategory == FieldCategory.STREET) && 
							!(map[x][y+2].fieldCategory == FieldCategory.STREET)){
						//links unten kann weg
						makeBuilding(x, y+1, map[x-1][y+1].blockID);
						
					}else if(!(map[x+2][y+1].fieldCategory == FieldCategory.STREET) && 
							!(map[x+1][y+2].fieldCategory == FieldCategory.STREET)){
						//rechts unten kann weg
						makeBuilding(x+1, y+1, map[x+2][y+1].blockID);
						
					}else{
						//6er!
						
						if(map[x][y+2].fieldCategory == FieldCategory.STREET && map[x+1][y+2].fieldCategory == FieldCategory.STREET){
							//vertikaler 6er
							if(map[x-1][y+1].fieldCategory != FieldCategory.STREET){
								//12 ist keine Straße, 4 kann weg
								makeBuilding(x, y+1, map[x-1][y+1].blockID);
								
							}else if(map[x+2][y+1].fieldCategory != FieldCategory.STREET){
								//9 ist keine Straße, 3 kann weg
								makeBuilding(x+1, y+1, map[x+2][y+1].blockID);
								
							}
							
						}else if(map[x+2][y].fieldCategory == FieldCategory.STREET && map[x+2][y+1].fieldCategory == FieldCategory.STREET){
							//horizontaler 6er
							if(map[x+1][y-1].fieldCategory != FieldCategory.STREET){
								//7 ist keine straße, 2 kann weg
								makeBuilding(x+1, y, map[x+1][y-1].blockID);
								
							}else if(map[x+1][y+2].fieldCategory != FieldCategory.STREET){
								//10 ist keine Straße, 3 kann weg
								makeBuilding(x+1, y+1, map[x+1][y+2].blockID);
							}
							
						}
						
						
					}
					
				}
				
			}
		}
		
	}
	
	private void setRandomeEmptyField(int x,int y){
		String[] emptyFieldSpriteNames ={"empty1", "empty2", "empty3"};
		Field field = map[x][y];
		field.spriteName = emptyFieldSpriteNames[(int)(Math.random() * emptyFieldSpriteNames.length)];
	}
	private void setRandomNonStreetTile(int x, int y){
		String[] fieldSpriteNames ={"gA1", "gB1", "gC1", "hA1", "hA2", "hB1", "hC1", "hC2", "hC3", "pA1", "pA2", "pA3", "pA4", "sA1", "userA1", "userB1", 
				"userB2", "userB3"};
		Field field = map[x][y];
		field.spriteName = fieldSpriteNames[(int)(Math.random() * fieldSpriteNames.length)];
		
	}
	private void setStreetTile(int x, int y){
		//Strassentexturen setzen
		boolean strO = true;
		boolean strR = true;
		boolean strU = true;
		boolean strL = true;
		int neigbouhrstreetsCount = 0;
		Field field;
		field = map[x][y];

			//vier felder anschauen
			//oben

			if(map[x][y-1].fieldCategory == FieldCategory.STREET){
				strO = true;
				neigbouhrstreetsCount++;
			}else{
				strO = false;
			}

			//unten
			if(map[x][y+1].fieldCategory == FieldCategory.STREET){
				strU = true;
				neigbouhrstreetsCount++;
			}else{
				strU = false;
			}

			//rechts
			if(map[x+1][y].fieldCategory == FieldCategory.STREET){
				strR = true;
				neigbouhrstreetsCount++;
			}else{
				strR = false;
			}

			//links

			if(map[x-1][y].fieldCategory == FieldCategory.STREET){
				strL = true;
				neigbouhrstreetsCount++;
			}else{
				strL = false;
			}


			//Anliegende Strassen erkannt! nun Schritt gehen
			//println strCount
			switch (neigbouhrstreetsCount){
			case 1: field.spriteName = "strORUL1";
				break;
			case 2:
					if(strO && strL) field.spriteName = "strOL1";
					else if(strO && strR) field.spriteName = "strOR1";
					else if(strO && strU) field.spriteName = "strOU1";
					else if(strR && strL) field.spriteName = "strRL1";
					else if(strR && strU) field.spriteName = "strRU1";
					else if(strU && strL) field.spriteName = "strUL1";
					else System.out.println("street 2neighbours fail");
					break;
				case 3:
					if(strO && strR && strL) field.spriteName = "strORL1";
					else if(strO && strR && strU) field.spriteName = "strORU1";
					else if(strO && strU && strL) field.spriteName = "strOUL1";
					else if(strR && strU && strL) field.spriteName = "strRUL1";
					else System.out.println("street 3neighbours fail");
					break;
				case 4:
					 field.spriteName = "strORUL1";
					break;
			}
		}
	/**
	 * changes the Type of a Field to BUILDING, assignes blockID
	 * @param x x-position of the field on the map
	 * @param y y-position of the field on the map
	 * @param blockID new Block id of the field
	 */
	private void makeBuilding(int x, int y, int blockID){
		Field f = map[x][y];
		f.fieldCategory = FieldCategory.BUILDING;
		f.blockID = blockID;
	}

}
