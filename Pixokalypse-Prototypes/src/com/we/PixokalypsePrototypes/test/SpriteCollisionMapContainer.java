package com.we.PixokalypsePrototypes.test;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;


public class SpriteCollisionMapContainer {
		TextureAtlas spriteSheet;
		HashMap<String,Array<Integer>[][]> spriteCollisionmapHashmap;
	public SpriteCollisionMapContainer(){
		
		spriteSheet = new TextureAtlas(Gdx.files.internal("data/heightmap/collisionpack.atlas"));
		spriteCollisionmapHashmap = new HashMap<String, Array<Integer>[][]>();
				
		//Für alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:spriteSheet.getRegions()){
			String name = item.name;
			//spriteCollisionmapHashmap.put(name, spriteToCollisionmapArray(spriteSheet.createSprite(name)));
			//spriteHashmap.get(name).setSize(width, height);
		}
		System.out.println("anzahl Collisionmaps: " + spriteCollisionmapHashmap.size());
	}
	
	private Array<Integer>[][] spriteToCollisionmapArray(Sprite createSprite) {
		//magic
		return null;
	}

	public Array<Integer>[][] getSpriteCollisionmap(String spriteName){
		return spriteCollisionmapHashmap.get(spriteName);
	}
}
