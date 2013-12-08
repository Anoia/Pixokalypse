package com.we.PixokalypsePrototypes.test;

import java.util.HashMap;
import java.util.HashSet;

import util.GridPoint2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class SpriteCollisionMapContainer {
	public static final int TILE_SIZE = 40;
		TextureAtlas spriteSheet;
		HashMap<String,int[][]> spriteCollisionmapHashmap;
	public SpriteCollisionMapContainer(){
		
		spriteSheet = new TextureAtlas(Gdx.files.internal("data/heightmap/collisionpack.atlas"));
		spriteCollisionmapHashmap = new HashMap<String, int[][]>();
				
		//Für alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		Texture fullexture = new Texture(Gdx.files.internal("data/heightmap/collisionpack.png"));
		fullexture.dispose();
		TextureData textureData = fullexture.getTextureData();
		textureData.prepare();
		Pixmap pixMap = new Pixmap(fullexture.getWidth(),fullexture.getHeight(),textureData.getFormat());
		pixMap = textureData.consumePixmap();
		
		
		for(AtlasRegion item:spriteSheet.getRegions()){
			String name = item.name;
			TextureRegion tr = spriteSheet.findRegion(name);
			Color color;
			
			int[][] collisionMap = new int[tr.getRegionWidth()][tr.getRegionHeight()];	
			for(int x = tr.getRegionX(); x < tr.getRegionWidth()+tr.getRegionX();x++){
				for(int y = tr.getRegionY(); y < tr.getRegionHeight()+tr.getRegionY();y++){
					color = new Color(pixMap.getPixel(x, y));
					String blub = color.toString();

					if(0 == blub.compareToIgnoreCase("ffffff00"))System.out.print("Darf nicht würd ich sagen");
					else if(0 == blub.compareToIgnoreCase("000000ff"))collisionMap[x-tr.getRegionX()][y-tr.getRegionY()] = -1;
					else if(0 == blub.compareToIgnoreCase("ffffffff"))collisionMap[x-tr.getRegionX()][y-tr.getRegionY()] = 0;
				}
			}
			//Vertikal Spiegeln vorher!
			int[][] collisionMapGespiegelt = new int[tr.getRegionWidth()][tr.getRegionHeight()];
			for(int x = 0; x<collisionMap.length;x++){
				for(int y = 0; y<collisionMap.length;y++){
					collisionMapGespiegelt[collisionMap.length-1-x][y] = collisionMap[x][y];
				}
			}
			
			makeBuildingsPushFromCenter(collisionMapGespiegelt);
			//printASCII(collisionMapGespiegelt, name);
			spriteCollisionmapHashmap.put(name,  collisionMapGespiegelt);
		}
		textureData.disposePixmap();//weiß nicht ob das muss :D
		fullexture.dispose();
		pixMap.dispose();
	}
	
	private void makeBuildingsPushFromCenter(int[][] collisionMapGespiegelt) {
		//erstelle Startset mit Randcollisionspixeln
		int potentialFieldValue = 10001;
		HashSet<GridPoint2> points = new HashSet<GridPoint2>();
		for(int x = 0; x<collisionMapGespiegelt.length;x++){
			for(int y = 0; y<collisionMapGespiegelt.length;y++){
				GridPoint2 currentPoint = new GridPoint2(x,y);
				if(collisionMapGespiegelt[x][y]==-1 && hasNeighbourWithValue(currentPoint, 0, collisionMapGespiegelt)) points.add(currentPoint);
			}
		}
		
		
		while(!points.isEmpty()){
						
			setAllElementsToValue(points, potentialFieldValue, collisionMapGespiegelt);
			potentialFieldValue++;
			
			HashSet<GridPoint2> neighbours = new HashSet<GridPoint2>();
			
			for(GridPoint2 point:points){
				neighbours.addAll(getNeighbourswithValue(point, -1, collisionMapGespiegelt));
			}			
			points = neighbours;

		}
		
	}
	
	public void printASCII(int[][] collisionMap, String name){
		System.out.println("\n Name: "+name);
		for(int i = 0; i < collisionMap.length;i++){
			System.out.println();
			for(int j = 0; j < collisionMap.length;j++){
				if(collisionMap[i][j] == 0)System.out.print("[0]");
				else System.out.print("["+(collisionMap[i][j]-10000)+"]");
			}	
		}
	}
	
	private HashSet<GridPoint2> getNeighbourswithValue(GridPoint2 point, int value, int[][] collisionMapGespiegelt) {
		HashSet<GridPoint2> neighbours = new HashSet<GridPoint2>();
		for(int x = point.x-1; x<=point.x+1;x++){
			for(int y = point.y-1; y <=point.y+1;y++){
				if(isOutOfBound(x, y, this.TILE_SIZE)) continue;
				if(collisionMapGespiegelt[x][y]==value) neighbours.add(new GridPoint2(x,y));
			}
		}
		return neighbours;
	}

	private void setAllElementsToValue(HashSet<GridPoint2> set, int value, int[][] collisionMapGespiegelt){
		for(GridPoint2 point: set){
			collisionMapGespiegelt[point.x][point.y] = value;
		}
	}
	
	private boolean hasNeighbourWithValue(GridPoint2 currentPoint, int value, int[][] collisionMapGespiegelt) {
		for(int x = currentPoint.x-1; x<=currentPoint.x+1;x++){
			for(int y = currentPoint.y-1; y <=currentPoint.y+1;y++){
				if(isOutOfBound(x, y, this.TILE_SIZE)) continue;
				if(collisionMapGespiegelt[x][y]==value) return true;
			}
		}
		return false;
	}

	public boolean isOutOfBound(int x, int y, int arraySize){
		return (x <0 || x >= arraySize || y < 0 || y >= arraySize);
	}
	
	public int[][] getSpriteCollisionmap(String spriteName){
		return spriteCollisionmapHashmap.get(spriteName);
	}
}
