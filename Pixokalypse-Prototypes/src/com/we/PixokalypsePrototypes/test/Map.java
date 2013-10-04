package com.we.PixokalypsePrototypes.test;

import java.util.HashSet;

import com.sun.xml.internal.ws.message.EmptyMessageImpl;

enum Direction{
	UP, RIGHT, DOWN, LEFT
}

public class Map {
	
	private int mapSize; //anzahl der Felder 
	private int minFieldsPerBlock;
	private int randomFieldsPerBlock; //optionale Geb�ude
	private Field[][] map;
	private int mapBorder; //rand der map, wenn der erreicht ist wird nicht mehr generiert
	
	public Map(){
		this(40, 7, 3, 7);
	}
	
	public Map(int mapSize, int minFieldsPerBlock, int randomFieldsPerBlock, int mapBorder){
		this.mapSize = mapSize;
		this.minFieldsPerBlock = minFieldsPerBlock;
		this.randomFieldsPerBlock = randomFieldsPerBlock;
		this.mapBorder = mapBorder;
		map = new Field[mapSize][mapSize];
		this.initEmptyMap();
		this.generateMap();
		
		//Print Map
	}
	
	private void initEmptyMap() {
		for(int x = 0; x < mapSize; x++){
			for(int y = 0; y < mapSize; y++){
				map[x][y] = new Field(x, y);
			}
		}
	}

	private void generateMap(){
		int currentFieldX = (int) mapSize/2;
		int currentFieldY = currentFieldX;
		
		int turnTimes = 2; //anzahl der drehungen bis die strecke verl�ngert werden muss
		int distance = 1; //schritte zu gehen bis richtung gewechselt werden muss
		int distanceCounter = distance; //um schritte runter zu z�hlen
		Boolean keepGenerating = true;
		
		Direction direction = Direction.DOWN;
		//int i = direction.ordinal();
		//direction = Direction.values()[(i++)%Direction.values().length];
		
		int currentBlockId = 1;
		
		while(keepGenerating){
			
			if(distanceCounter == 0){ //wenn aktuelle strecke zuende gelaufen
				turnTimes--; //dehungen bis strecken�nderung verringern
				
				//richtungs�nderung
				int newDirection = (direction.ordinal() + 1) % Direction.values().length;
				direction = Direction.values()[newDirection];
				
				//distance erh�hen wenn turnTimes = 0;
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
			if(field.fieldType == FieldType.EMPTY){
				createBlock(field, currentBlockId);
				currentBlockId++;
			}
			
			keepGenerating = !(currentFieldX==mapBorder || currentFieldY == mapBorder);
			
		}	
		
	}

	private void createBlock(Field field, int currentBlockId) {
		field.blockID = currentBlockId;
		field.fieldType = FieldType.BUILDING;
		
		//emptyNeigbours enth�lt alle direkten Nachbarn zu field, auf denen Geb�ude platziert werden k�nnen
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
			fieldToAdd.fieldType = FieldType.BUILDING;
			fieldsAddedToBlock.add(fieldToAdd);
			possibleStartFields.add(fieldToAdd);
			amountOfBuildingsToPlace--;
			
		}
		
		//Stra�en um fieldsAddedtoBlock
		for(Field f: fieldsAddedToBlock){
			emptyNeighbours = getEmptyNeighbours(f);
			for(Field whenIGrowUpIWantToBeAStreet: emptyNeighbours){
				whenIGrowUpIWantToBeAStreet.blockID = 0;
				whenIGrowUpIWantToBeAStreet.fieldType = FieldType.STREET;
			}
		}
		
		
	}
	
	private HashSet<Field> getEmptyNeighbours(Field field){
		HashSet<Field> emptyNeighbours = new HashSet<Field>();
		for(int x = field.xAxis-1; x <= field.xAxis+1; x++){
			for(int y = field.yAxis-1; y <= field.yAxis+1; y++){
				if(!(x == field.xAxis && y == field.yAxis)){
					Field candidate = map[x][y];
					if(candidate.fieldType == FieldType.EMPTY){
						emptyNeighbours.add(candidate);
					}
				}
			}
		}
		
		return emptyNeighbours;
	}

	public void printASCII() {
		for(int i = 0; i < mapSize; i ++){
			System.out.print("\n");
			for(int j = 0; j < mapSize; j++){
				switch(map[i][j].fieldType){
				case EMPTY:
					System.out.print(" ");
					break;
				case BUILDING:
					System.out.print(" ");
					break;
				case STREET:
					System.out.print("+");
					break;
				}
			}
		}
		
	}

}
