package com.we.PixokalypsePrototypes.test;

import java.nio.ByteBuffer;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public class SpriteCollisionMapContainer {
		TextureAtlas spriteSheet;
		HashMap<String,byte[][]> spriteCollisionmapHashmap;
	public SpriteCollisionMapContainer(){
		
		spriteSheet = new TextureAtlas(Gdx.files.internal("data/heightmap/collisionpack.atlas"));
		spriteCollisionmapHashmap = new HashMap<String, byte[][]>();
				
		//Für alle definierten Spriteregions einen neuen Eintrag in der Hashmap anlegen
		for(AtlasRegion item:spriteSheet.getRegions()){
			String name = item.name;
			System.out.println(item.name);
			spriteCollisionmapHashmap.put(name, spriteToCollisionmapArray(spriteSheet.createSprite(name)));
		}
	}
	
	private byte[][] spriteToCollisionmapArray(Sprite createSprite) {
		byte[][] collisionMap = new byte[createSprite.getRegionWidth()][createSprite.getRegionHeight()];
		Pixmap pm = new Pixmap(createSprite.getRegionWidth(), createSprite.getRegionHeight(), createSprite.getTexture().getTextureData().getFormat());
		TextureData td = createSprite.getTexture().getTextureData();
		td.prepare();
		pm = td.consumePixmap();
		for(int x = 0; x < createSprite.getRegionWidth();x++){
			for(int y = 0; y < createSprite.getRegionHeight();y++){
				Color color = new Color(pm.getPixel(x, y));
				if(color.r == 1 && color.b==1 && color.g == 1)collisionMap[x][y] = 0;
				if(color.r == 0 && color.b==0 && color.g == 0)collisionMap[x][y] = 1;
			}		
		}
		return collisionMap;
	}

	public byte[][] getSpriteCollisionmap(String spriteName){
		return spriteCollisionmapHashmap.get(spriteName);
	}
}
