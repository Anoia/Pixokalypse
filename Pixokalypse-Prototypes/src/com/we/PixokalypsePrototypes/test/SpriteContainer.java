package com.we.PixokalypsePrototypes.test;

import java.util.HashMap;

import util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public class SpriteContainer {
	//Sprite atlas
	TextureAtlas spriteSheet;
	HashMap<String,Sprite> spriteHashmap;
	
	//Funktion entfernen
	public SpriteContainer(String sourceAtlas){
		spriteSheet = new TextureAtlas(Gdx.files.internal(sourceAtlas));
		spriteHashmap = new HashMap<String,Sprite>();
		//F�r alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:spriteSheet.getRegions()){
			String name = item.name;
			Sprite sprite = spriteSheet.createSprite(name);
			sprite.setSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
			sprite.flip(false, true);
			spriteHashmap.put(name, sprite);
			//spriteHashmap.get(name).setSize(width, height);
		}
	}
	
	public SpriteContainer() {
		spriteSheet = new TextureAtlas(Gdx.files.internal("data/textures.txt"));
		spriteHashmap = new HashMap<String,Sprite>();
		//F�r alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:spriteSheet.getRegions()){
			String name = item.name;
			Sprite sprite = spriteSheet.createSprite(name);
			sprite.setSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
			sprite.flip(false, true);
			spriteHashmap.put(name, sprite);
			//spriteHashmap.get(name).setSize(width, height);
		}
	}

	public int getSpriteCount(){
		return this.spriteHashmap.size();
	}
	
	public Sprite getSprite(String spriteName){
		return spriteHashmap.get(spriteName);
	}
	
	public String getRandomSpriteName(){
		return (String) spriteHashmap.keySet().toArray()[(int) (Math.random()*spriteHashmap.size())];
	}
}
