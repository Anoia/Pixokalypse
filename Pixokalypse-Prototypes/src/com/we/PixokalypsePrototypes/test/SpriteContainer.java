package com.we.PixokalypsePrototypes.test;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public class SpriteContainer {
	//Sprite atlas
	TextureAtlas groundSpriteSheet;
	TextureAtlas blockSpriteSheet;
	//HashMap die auf Sprites des Atlassssses zeigt
	HashMap<String,Sprite> groundSpriteHashmap;
	HashMap<String,Sprite> blockSpriteHashmap;
	
	//Funktion entfernen
	public SpriteContainer(String sourceAtlas){
		
		groundSpriteSheet = new TextureAtlas(Gdx.files.internal(sourceAtlas));
		groundSpriteHashmap = new HashMap<String,Sprite>();
		
		//F�r alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:groundSpriteSheet.getRegions()){
			String name = item.name;
			Sprite sprite = groundSpriteSheet.createSprite(name);
			sprite.flip(false, true);
			groundSpriteHashmap.put(name, sprite);
			//spriteHashmap.get(name).setSize(width, height);
		}
	}
	
	public SpriteContainer() {
		groundSpriteSheet = new TextureAtlas(Gdx.files.internal("data/ground.txt"));
		groundSpriteHashmap = new HashMap<String,Sprite>();
		
		//F�r alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:groundSpriteSheet.getRegions()){
			String name = item.name;
			Sprite sprite = groundSpriteSheet.createSprite(name);
			sprite.flip(false, true);
			groundSpriteHashmap.put(name, sprite);
			//spriteHashmap.get(name).setSize(width, height);
		}
		
		blockSpriteSheet = new TextureAtlas(Gdx.files.internal("data/block.txt"));
		blockSpriteHashmap = new HashMap<String,Sprite>();
		
		//F�r alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:blockSpriteSheet.getRegions()){
			String name = item.name;
			Sprite sprite = blockSpriteSheet.createSprite(name);
			sprite.flip(false, true);
			blockSpriteHashmap.put(name, sprite);
			//spriteHashmap.get(name).setSize(width, height);
		}
	}

	public int getSpriteCount(){
		return this.groundSpriteHashmap.size() + this.blockSpriteHashmap.size();
	}
	
	public Sprite getGroundSprite(String spriteName){
		return groundSpriteHashmap.get(spriteName);
	}
	
	public Sprite getBlockSprite(String spriteName){
		return blockSpriteHashmap.get(spriteName);
	}
	
	public String getRandomSpriteName(){
		return (String) groundSpriteHashmap.keySet().toArray()[(int) (Math.random()*groundSpriteHashmap.size())];
	}
}
