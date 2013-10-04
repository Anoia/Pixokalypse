package com.we.PixokalypsePrototypes.test;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public class SpriteContainer {
	//Sprite atlas
		TextureAtlas spriteSheet;
	//HashMap die auf Sprites des Atlassssses zeigt
		HashMap<String,Sprite> spriteHashmap;
		
	public SpriteContainer(){
		
		spriteSheet = new TextureAtlas(Gdx.files.internal("data/textures/pack.atlas"));
		spriteHashmap = new HashMap<String,Sprite>();
		
		//Für alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:spriteSheet.getRegions()){
			String name = item.name;
			spriteHashmap.put(name, spriteSheet.createSprite(name));
			//spriteHashmap.get(name).setSize(width, height);
		}
		System.out.println("anzahl Sprites: " + spriteHashmap.size());
	}
	
	public Sprite getSprite(String spriteName){
		return spriteHashmap.get(spriteName);
	}
}
