package com.we.PixokalypsePrototypes.test;

import java.util.HashSet;

enum Direction {
	UP, RIGHT, DOWN, LEFT
}

public class Map {

	public int mapSize; // Anzahl der Felder(sprite tiles) auf der Karte
	private int minFieldsPerBlock; // min Fields per H�userblock
	private int randomFieldsPerBlock; // optionale Fields per H�userblock
	public Field[][] data; // die Karte
	private int mapBorder; // rand der map, wenn der erreicht ist wird nicht
							// mehr generiert

	public Map() {
		this(15, 2, 2, 3);
	}

	public Map(int mapSize, int minFieldsPerBlock, int randomFieldsPerBlock,
			int mapBorder) {
		this.mapSize = mapSize;
		this.minFieldsPerBlock = minFieldsPerBlock;
		this.randomFieldsPerBlock = randomFieldsPerBlock;
		this.mapBorder = mapBorder;
		data = new Field[mapSize][mapSize];
		this.initEmptyMap();
		this.generateMap();
		this.fixStreets();
		this.addInnerAndOuter();
		this.setTileSprites();
		// printASCII();
		// Print Map
	}

	private void addInnerAndOuter() {
		boolean addedOuter = true;

		while (addedOuter) {
			addedOuter = false;
			for (int x = 0; x < mapSize; x++) {
				for (int y = 0; y < mapSize; y++) {
					Field field = data[x][y];
					if (field.fieldCategory == FieldCategory.BUILDING) {
						// Outer: Setzen wenn BEIDE horizontalen und/oder BEIDE
						// vertikalen nachbarn straßen sind
						// Ground_Grass.png
						// else
						// Inner:Setzen wenn KEIN nachbar eine Straße ist
						// Ground_Parking.png
						// SOOOOOOOOOOOOORRRRRRRRRYYYYYYYYYY WERDE AUFRAUMEN!
						// DACHTE DIE FUNKTION WÄRE NÜTZLICHER UND EINFACHER ZU
						// GEBRAUCHEN :D
						if (((this.testNeighboursForFieldType(field, true,
								FieldCategory.STREET, true, false, false,
								false, false, false, false, false) || (this
								.testNeighboursForFieldType(field, true,
										FieldCategory.OUTER, true, false,
										false, false, false, false, false,
										false))) && (this
								.testNeighboursForFieldType(field, true,
										FieldCategory.STREET, false, false,
										false, false, true, false, false, false) || (this
								.testNeighboursForFieldType(field, true,
										FieldCategory.OUTER, false, false,
										false, false, true, false, false, false))))
								|| ((this.testNeighboursForFieldType(field,
										true, FieldCategory.STREET, false,
										false, true, false, false, false,
										false, false) || (this
										.testNeighboursForFieldType(field,
												true, FieldCategory.OUTER,
												false, false, true, false,
												false, false, false, false))) && (this
										.testNeighboursForFieldType(field,
												true, FieldCategory.STREET,
												false, false, false, false,
												false, false, true, false) || (this
										.testNeighboursForFieldType(field,
												true, FieldCategory.OUTER,
												false, false, false, false,
												false, false, true, false))))) {
							field.fieldCategory = FieldCategory.OUTER;
							addedOuter = true;
						} else if (this.testNeighboursForFieldType(field,
								false, FieldCategory.STREET, true, true, true,
								true, true, true, true, true)) {
							field.fieldCategory = FieldCategory.INNER;
						}
					}
				}
			}
		}

	}

	private void setTileSprites() {
		for (int x = 1; x < mapSize - 1; x++) {
			for (int y = 1; y < mapSize - 1; y++) {
				Field field = data[x][y];
				if (field.fieldCategory == FieldCategory.STREET)
					setStreetTile(x, y);
			}
		}

		for (int x = 0; x < mapSize; x++) {
			for (int y = 0; y < mapSize; y++) {
				Field field = data[x][y];
				if (field.fieldCategory == FieldCategory.EMPTY)
					field.spriteName = "Grass";
				else if (field.fieldCategory == FieldCategory.BUILDING)
					setBuildingTexture(field);
				else if (field.fieldCategory == FieldCategory.INNER)
					field.spriteName = "Parking";
				else if (field.fieldCategory == FieldCategory.OUTER)
					field.spriteName = "Grass";
			}
		}
	}
/**
 * 
 * @param field
 */
	private void setBuildingTexture(Field field) {
		String gebtyp = "A";
		// Prüfen der verschiedenen fälle
		boolean done = false;

		
		//NEUE LÖSUNG mit hässlichen Kommentaren aber übersichtlicher

//		Geb.kreuzung also Nord Ost Süd West Geb.
		
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				false,	//Nord-Ost 
				true, 	//Ost
				true, 	//Süd-Ost
				true,	//Süd
				false, 	//Süd-West
				true, 	//West
				true)	//Nord-West
				&&
				this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						false, 	//Nord
						true,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						true, 	//Süd-West
						false, 	//West
						false)	//Nord-West
				){
			field.spriteName = "A-X1";
			done = true;			
		}
				
				
				
				
				
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				true,	//Nord-Ost 
				true, 	//Ost
				false, 	//Süd-Ost
				true,	//Süd
				true, 	//Süd-West
				true, 	//West
				false)	//Nord-West
				&&
				this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						false, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						true, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						true)	//Nord-West)
						){
			field.spriteName = "A-X0";
			done = true;
		}
	
		
		//N, O, S und W müssen auf (STR oder OUTER) Testen an der stelle (NICHT GEB)
//		N
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				false, 	//Nord
				false,	//Nord-Ost 
				true, 	//Ost
				false, 	//Süd-Ost
				false,	//Süd
				false, 	//Süd-West
				true, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						true, 	//Nord
						true,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						false)	//Nord-West
				||
				this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						true, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						true)	//Nord-West	
						)){
			if(this.testNeighboursForFieldType(field, 
					false,//auf true prüfen? true
					FieldCategory.INNER, 
					true, 	//Nord
					false,	//Nord-Ost 
					false, 	//Ost
					false, 	//Süd-Ost
					false,	//Süd
					false, 	//Süd-West
					false, 	//West
					false)	//Nord-West	
					){
				field.spriteName = gebtyp+"-N";
				done = true;	
			}
		}
//		S
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				false, 	//Nord
				false,	//Nord-Ost 
				true, 	//Ost
				false, 	//Süd-Ost
				false,	//Süd
				false, 	//Süd-West
				true, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						false, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						true,	//Süd
						true, 	//Süd-West
						false, 	//West
						false)	//Nord-West
						||
						this.testNeighboursForFieldType(field, 
								false,//auf true prüfen? true
								FieldCategory.BUILDING, 
								false, 	//Nord
								false,	//Nord-Ost 
								false, 	//Ost
								true, 	//Süd-Ost
								true,	//Süd
								false, 	//Süd-West
								false, 	//West
								false)	//Nord-West
				)){
			if(this.testNeighboursForFieldType(field, 
					false,//auf true prüfen? true
					FieldCategory.INNER, 
					false, 	//Nord
					false,	//Nord-Ost 
					false, 	//Ost
					false, 	//Süd-Ost
					true,	//Süd
					false, 	//Süd-West
					false, 	//West
					false)	//Nord-West	
					){
			field.spriteName = gebtyp+"-S";
			done = true;
			}
		}		
//		W
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				false,	//Nord-Ost 
				false, 	//Ost
				false, 	//Süd-Ost
				true,	//Süd
				false, 	//Süd-West
				false, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						false, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						true, 	//West
						true)	//Nord-West
						||
						this.testNeighboursForFieldType(field, 
								false,//auf true prüfen? true
								FieldCategory.BUILDING, 
								false, 	//Nord
								false,	//Nord-Ost 
								false, 	//Ost
								false, 	//Süd-Ost
								false,	//Süd
								true, 	//Süd-West
								true, 	//West
								false)	//Nord-West
				)){
			if(this.testNeighboursForFieldType(field, 
					false,//auf true prüfen? true
					FieldCategory.INNER, 
					false, 	//Nord
					false,	//Nord-Ost 
					false, 	//Ost
					false, 	//Süd-Ost
					false,	//Süd
					false, 	//Süd-West
					true, 	//West
					false)	//Nord-West	
					){
			field.spriteName = gebtyp+"-W";
			done = true;
			}
		}
//		O
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				false,	//Nord-Ost 
				false, 	//Ost
				false, 	//Süd-Ost
				true,	//Süd
				false, 	//Süd-West
				false, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						false, 	//Nord
						false,	//Nord-Ost 
						true, 	//Ost
						true, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						false)	//Nord-West
						||
						this.testNeighboursForFieldType(field, 
								false,//auf true prüfen? true
								FieldCategory.BUILDING, 
								false, 	//Nord
								true,	//Nord-Ost 
								true, 	//Ost
								false, 	//Süd-Ost
								false,	//Süd
								false, 	//Süd-West
								false, 	//West
								false)	//Nord-West
				)){
			if(this.testNeighboursForFieldType(field, 
					false,//auf true prüfen? true
					FieldCategory.INNER, 
					false, 	//Nord
					false,	//Nord-Ost 
					true, 	//Ost
					false, 	//Süd-Ost
					false,	//Süd
					false, 	//Süd-West
					false, 	//West
					false)	//Nord-West	
					){
			field.spriteName = gebtyp+"-O";
			done = true;
			}
		}
//		SWi
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				false, 	//Nord
				false,	//Nord-Ost 
				false, 	//Ost
				false, 	//Süd-Ost
				true,	//Süd
				false, 	//Süd-West
				true, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						true,//auf true prüfen? true
						FieldCategory.STREET, 
						false, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						true, 	//Süd-West
						false, 	//West
						false)	//Nord-West
						||
						this.testNeighboursForFieldType(field, 
								true,//auf true prüfen? true
								FieldCategory.OUTER, 
								false, 	//Nord
								false,	//Nord-Ost 
								false, 	//Ost
								false, 	//Süd-Ost
								false,	//Süd
								true, 	//Süd-West
								false, 	//West
								false)	//Nord-West
				)){
			field.spriteName = gebtyp+"-SWi";
			done = true;
		}		
//		NWi
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				false,	//Nord-Ost 
				false, 	//Ost
				false, 	//Süd-Ost
				false,	//Süd
				false, 	//Süd-West
				true, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						true,//auf true prüfen? true
						FieldCategory.STREET, 
						false, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						true)	//Nord-West
						||
						this.testNeighboursForFieldType(field, 
								true,//auf true prüfen? true
								FieldCategory.OUTER, 
								false, 	//Nord
								false,	//Nord-Ost 
								false, 	//Ost
								false, 	//Süd-Ost
								false,	//Süd
								false, 	//Süd-West
								false, 	//West
								true)	//Nord-West
				)){
			field.spriteName = gebtyp+"-NWi";
			done = true;
		}
//		NOi
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				false,	//Nord-Ost 
				true, 	//Ost
				false, 	//Süd-Ost
				false,	//Süd
				false, 	//Süd-West
				false, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						true,//auf true prüfen? true
						FieldCategory.STREET, 
						false, 	//Nord
						true,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						false)	//Nord-West
						||
						this.testNeighboursForFieldType(field, 
								true,//auf true prüfen? true
								FieldCategory.OUTER, 
								false, 	//Nord
								true,	//Nord-Ost 
								false, 	//Ost
								false, 	//Süd-Ost
								false,	//Süd
								false, 	//Süd-West
								false, 	//West
								false)	//Nord-West
				)){
			field.spriteName = gebtyp+"-NOi";
			done = true;
		}
//		SOi
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				false, 	//Nord
				false,	//Nord-Ost 
				true, 	//Ost
				false, 	//Süd-Ost
				true,	//Süd
				false, 	//Süd-West
				false, 	//West
				false)	//Nord-West
				&&
				(this.testNeighboursForFieldType(field, 
						true,//auf true prüfen? true
						FieldCategory.STREET, 
						false, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						true, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						false)	//Nord-West
						||
						this.testNeighboursForFieldType(field, 
								true,//auf true prüfen? true
								FieldCategory.OUTER, 
								false, 	//Nord
								false,	//Nord-Ost 
								false, 	//Ost
								true, 	//Süd-Ost
								false,	//Süd
								false, 	//Süd-West
								false, 	//West
								false)	//Nord-West
				)){
			field.spriteName = gebtyp+"-SOi";
			done = true;
		}
//		SW
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				false,	//Nord-Ost 
				true, 	//Ost
				false, 	//Süd-Ost
				false,	//Süd
				false, 	//Süd-West
				false, 	//West
				false)	//Nord-West
				&&
				this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						false, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						true,	//Süd
						true, 	//Süd-West
						true, 	//West
						false)	//Nord-West
						){
			field.spriteName = gebtyp+"-SW";
			done = true;
		}
//		NO
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				false, 	//Nord
				false,	//Nord-Ost 
				false, 	//Ost
				false, 	//Süd-Ost
				true,	//Süd
				false, 	//Süd-West
				true, 	//West
				false)	//Nord-West
				&&
				this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						true, 	//Nord
						true,	//Nord-Ost 
						true, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						false, 	//West
						false)	//Nord-West
						){
			field.spriteName = gebtyp+"-NO";
			done = true;
		}
//		SO
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				true, 	//Nord
				false,	//Nord-Ost 
				false, 	//Ost
				false, 	//Süd-Ost
				false,	//Süd
				false, 	//Süd-West
				true, 	//West
				false)	//Nord-West
				&&
				this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						false, 	//Nord
						false,	//Nord-Ost 
						true, 	//Ost
						true, 	//Süd-Ost
						true,	//Süd
						false, 	//Süd-West
						false, 	//West
						false)	//Nord-West
						){
			field.spriteName = gebtyp+"-SO";
			done = true;
		}
//		NW
		if(!done)if(this.testNeighboursForFieldType(field, 
				true,//auf true prüfen? true
				FieldCategory.BUILDING, 
				false, 	//Nord
				false,	//Nord-Ost 
				true, 	//Ost
				false, 	//Süd-Ost
				true,	//Süd
				false, 	//Süd-West
				false, 	//West
				false)	//Nord-West
				&&
				this.testNeighboursForFieldType(field, 
						false,//auf true prüfen? true
						FieldCategory.BUILDING, 
						true, 	//Nord
						false,	//Nord-Ost 
						false, 	//Ost
						false, 	//Süd-Ost
						false,	//Süd
						false, 	//Süd-West
						true, 	//West
						true)	//Nord-West
						){
			field.spriteName = gebtyp+"-NW";
			done = true;
		}
	}

	private void initEmptyMap() {
		for (int x = 0; x < mapSize; x++) {
			for (int y = 0; y < mapSize; y++) {
				data[x][y] = new Field(x, y);
			}
		}
	}

	/**
	 * Generates the map by moving in a spiral path from the middle to the
	 * outside and generating blocks of buildings with streets around them on
	 * the way.
	 */
	private void generateMap() {
		int currentFieldX = (int) mapSize / 2;
		int currentFieldY = currentFieldX;

		int turnTimes = 2; // anzahl der drehungen bis die strecke verl�ngert
							// werden muss
		int distance = 1; // schritte zu gehen bis richtung gewechselt werden
							// muss
		int distanceCounter = distance; // um schritte runter zu z�hlen
		Boolean keepGenerating = true;

		Direction direction = Direction.DOWN;
		// int i = direction.ordinal();
		// direction = Direction.values()[(i++)%Direction.values().length];

		int currentBlockId = 1;

		while (keepGenerating) {

			if (distanceCounter == 0) { // wenn aktuelle strecke zuende gelaufen
				turnTimes--; // dehungen bis strecken�nderung verringern

				// richtungs�nderung
				int newDirection = (direction.ordinal() + 1)
						% Direction.values().length;
				direction = Direction.values()[newDirection];

				// distance erh�hen wenn turnTimes = 0;
				if (turnTimes == 0) {
					turnTimes = 2;
					distance++;
				}
				// starte counter mit neuer distance von vorne
				distanceCounter = distance;
			}

			distanceCounter--;
			switch (direction) {
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

			Field field = data[currentFieldX][currentFieldY];
			if (field.fieldCategory == FieldCategory.EMPTY) {
				createBlock(field, currentBlockId);
				currentBlockId++;
			}

			keepGenerating = !(currentFieldX == mapBorder || currentFieldY == mapBorder);

		}
	}

	/**
	 * generates a block of buildings around a field (including that field)
	 * 
	 * @param field
	 * @param currentBlockId
	 */
	private void createBlock(Field field, int currentBlockId) {
		field.blockID = currentBlockId;
		field.fieldCategory = FieldCategory.BUILDING;

		// emptyNeigbours enth�lt alle direkten Nachbarn zu field, auf denen
		// Geb�ude platziert werden k�nnen
		HashSet<Field> emptyNeighbours = new HashSet<Field>();
		HashSet<Field> fieldsAddedToBlock = new HashSet<Field>();
		HashSet<Field> possibleStartFields = new HashSet<Field>();

		possibleStartFields.add(field);
		fieldsAddedToBlock.add(field);

		int amountOfBuildingsToPlace = minFieldsPerBlock
				+ (int) (Math.random() * randomFieldsPerBlock + 1) - 1;

		while (amountOfBuildingsToPlace > 0) {

			while (emptyNeighbours.isEmpty()) {
				if (possibleStartFields.isEmpty()) {
					break; // hier geht gar nichts mehr! ENDE
				} else {
					field = possibleStartFields.iterator().next();
					possibleStartFields.remove(field);
					emptyNeighbours = getEmptyNeighbours(field);
				}
			}

			if (emptyNeighbours.isEmpty()) {
				// break in der oberen while wurde getriggert
				break;
			}

			// wir haben auf jeden fall emptyNachbarn

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
	 * 
	 * @param block
	 *            a HashSet<Field> containing all the Fields that belong to the
	 *            Block
	 */
	private void createStreetsAroundBlock(HashSet<Field> block) {
		for (Field f : block) {
			HashSet<Field> emptyNeighbours = getEmptyNeighbours(f);
			for (Field whenIGrowUpIWantToBeAStreet : emptyNeighbours) {
				whenIGrowUpIWantToBeAStreet.blockID = 0;
				whenIGrowUpIWantToBeAStreet.fieldCategory = FieldCategory.STREET;
			}
		}
	}

	private boolean testNeighboursForFieldType(Field field, boolean equal,
			FieldCategory fieldCategory, boolean north, boolean northEast,
			boolean east, boolean eastSouth, boolean south, boolean southWest,
			boolean west, boolean westNorth) {
		boolean returnValue = true;
		int x = field.xAxis, y = field.yAxis;

		// North test
		if (returnValue && north == true) {
			returnValue = !((data[x][y - 1].fieldCategory == fieldCategory) ^ equal);
		}
		// NorthEast test
		if (returnValue && northEast == true) {
			returnValue = !((data[x + 1][y - 1].fieldCategory == fieldCategory) ^ equal);
		}
		// East test
		if (returnValue && east == true) {
			returnValue = !((data[x + 1][y].fieldCategory == fieldCategory) ^ equal);
		}
		// EastSouth test
		if (returnValue && eastSouth == true) {
			returnValue = !((data[x + 1][y + 1].fieldCategory == fieldCategory) ^ equal);
		}
		// South test
		if (returnValue && south == true) {
			returnValue = !((data[x][y + 1].fieldCategory == fieldCategory) ^ equal);
		}
		// SouthWest test
		if (returnValue && southWest == true) {
			returnValue = !((data[x - 1][y + 1].fieldCategory == fieldCategory) ^ equal);
		}
		// West test
		if (returnValue && west == true) {
			returnValue = !((data[x - 1][y].fieldCategory == fieldCategory) ^ equal);
		}
		// WestNorth test
		if (returnValue && westNorth == true) {
			returnValue = !((data[x - 1][y - 1].fieldCategory == fieldCategory) ^ equal);
		}
		return returnValue;
	}

	/**
	 * finds all the neighbours of a field whose fieldCategory is EMPTY
	 * 
	 * @param field
	 *            the field whose neighbours should be found
	 * @return HashSet with all empty fields
	 */
	private HashSet<Field> getEmptyNeighbours(Field field) {
		HashSet<Field> emptyNeighbours = new HashSet<Field>();
		for (int x = field.xAxis - 1; x <= field.xAxis + 1; x++) {
			for (int y = field.yAxis - 1; y <= field.yAxis + 1; y++) {
				if (!(x == field.xAxis && y == field.yAxis)) {
					Field candidate = data[x][y];
					if (candidate.fieldCategory == FieldCategory.EMPTY) {
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
		for (int i = 0; i < mapSize; i++) {
			System.out.print("\n");
			for (int j = 0; j < mapSize; j++) {
				switch (data[j][i].fieldCategory) {
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
	 * removes Blocks of 4 or 6 streets by replacing one of the Streets with a
	 * Building
	 */
	private void fixStreets() {

		for (int y = 1; y < mapSize - 1; y++) {
			for (int x = 1; x < mapSize - 1; x++) {
				if (data[x][y].fieldCategory == FieldCategory.STREET
						&& // 1
						data[x + 1][y].fieldCategory == FieldCategory.STREET
						&& // 2
						data[x + 1][y + 1].fieldCategory == FieldCategory.STREET
						&& // 3
						data[x][y + 1].fieldCategory == FieldCategory.STREET) { // 4

					// 4er gefunden
					if (!(data[x - 1][y].fieldCategory == FieldCategory.STREET)
							&& !(data[x][y - 1].fieldCategory == FieldCategory.STREET)) {
						// links oben kann weg
						makeBuilding(x, y, data[x - 1][y].blockID);

					} else if (!(data[x + 1][y - 1].fieldCategory == FieldCategory.STREET)
							&& !(data[x + 2][y].fieldCategory == FieldCategory.STREET)) {
						// rechts oben kann weg
						makeBuilding(x + 1, y, data[x + 1][y - 1].blockID);

					} else if (!(data[x - 1][y + 1].fieldCategory == FieldCategory.STREET)
							&& !(data[x][y + 2].fieldCategory == FieldCategory.STREET)) {
						// links unten kann weg
						makeBuilding(x, y + 1, data[x - 1][y + 1].blockID);

					} else if (!(data[x + 2][y + 1].fieldCategory == FieldCategory.STREET)
							&& !(data[x + 1][y + 2].fieldCategory == FieldCategory.STREET)) {
						// rechts unten kann weg
						makeBuilding(x + 1, y + 1, data[x + 2][y + 1].blockID);

					} else {
						// 6er!

						if (data[x][y + 2].fieldCategory == FieldCategory.STREET
								&& data[x + 1][y + 2].fieldCategory == FieldCategory.STREET) {
							// vertikaler 6er
							if (data[x - 1][y + 1].fieldCategory != FieldCategory.STREET) {
								// 12 ist keine Stra�e, 4 kann weg
								makeBuilding(x, y + 1,
										data[x - 1][y + 1].blockID);

							} else if (data[x + 2][y + 1].fieldCategory != FieldCategory.STREET) {
								// 9 ist keine Stra�e, 3 kann weg
								makeBuilding(x + 1, y + 1,
										data[x + 2][y + 1].blockID);

							}

						} else if (data[x + 2][y].fieldCategory == FieldCategory.STREET
								&& data[x + 2][y + 1].fieldCategory == FieldCategory.STREET) {
							// horizontaler 6er
							if (data[x + 1][y - 1].fieldCategory != FieldCategory.STREET) {
								// 7 ist keine stra�e, 2 kann weg
								makeBuilding(x + 1, y,
										data[x + 1][y - 1].blockID);

							} else if (data[x + 1][y + 2].fieldCategory != FieldCategory.STREET) {
								// 10 ist keine Stra�e, 3 kann weg
								makeBuilding(x + 1, y + 1,
										data[x + 1][y + 2].blockID);
							}
						}
					}
				}
			}
		}
	}

	private void setStreetTile(int x, int y) {
		// Strassentexturen setzen
		boolean strO = true;
		boolean strR = true;
		boolean strU = true;
		boolean strL = true;
		int neigbouhrstreetsCount = 0;
		Field field;
		field = data[x][y];

		// vier felder anschauen
		// oben

		if (data[x][y - 1].fieldCategory == FieldCategory.STREET) {
			strO = true;
			neigbouhrstreetsCount++;
		} else {
			strO = false;
		}

		// unten
		if (data[x][y + 1].fieldCategory == FieldCategory.STREET) {
			strU = true;
			neigbouhrstreetsCount++;
		} else {
			strU = false;
		}

		// rechts
		if (data[x + 1][y].fieldCategory == FieldCategory.STREET) {
			strR = true;
			neigbouhrstreetsCount++;
		} else {
			strR = false;
		}

		// links

		if (data[x - 1][y].fieldCategory == FieldCategory.STREET) {
			strL = true;
			neigbouhrstreetsCount++;
		} else {
			strL = false;
		}

		// Anliegende Strassen erkannt! nun Schritt gehen
		// println strCount
		switch (neigbouhrstreetsCount) {
		case 1:
			field.spriteName = "strORUL-1";
			break;
		case 2:
			if (strO && strL)
				field.spriteName = "strOL-1";
			else if (strO && strR)
				field.spriteName = "strOR-1";
			else if (strO && strU)
				field.spriteName = "strOU-1";
			else if (strR && strL)
				field.spriteName = "strRL-1";
			else if (strR && strU)
				field.spriteName = "strRU-1";
			else if (strU && strL)
				field.spriteName = "strUL-1";
			else
				System.out.println("street 2neighbours fail");
			break;
		case 3:
			if (strO && strR && strL)
				field.spriteName = "strORL-1";
			else if (strO && strR && strU)
				field.spriteName = "strORU-1";
			else if (strO && strU && strL)
				field.spriteName = "strOUL-1";
			else if (strR && strU && strL)
				field.spriteName = "strRUL-1";
			else
				System.out.println("street 3neighbours fail");
			break;
		case 4:
			field.spriteName = "strORUL-1";
			break;
		}
	}

	/**
	 * changes the Type of a Field to BUILDING, assignes blockID
	 * 
	 * @param x
	 *            x-position of the field on the map
	 * @param y
	 *            y-position of the field on the map
	 * @param blockID
	 *            new Block id of the field
	 */
	private void makeBuilding(int x, int y, int blockID) {
		Field f = data[x][y];
		f.fieldCategory = FieldCategory.BUILDING;
		f.blockID = blockID;
	}

}
