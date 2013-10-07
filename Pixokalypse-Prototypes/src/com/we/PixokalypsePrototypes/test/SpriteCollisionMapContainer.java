package com.we.PixokalypsePrototypes.test;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class SpriteCollisionMapContainer {
		TextureAtlas spriteSheet;
		HashMap<String,byte[][]> spriteCollisionmapHashmap;
	public SpriteCollisionMapContainer(){
		
		spriteSheet = new TextureAtlas(Gdx.files.internal("data/heightmap/collisionpack.atlas"));
		spriteCollisionmapHashmap = new HashMap<String, byte[][]>();
				
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
			
			byte[][] collisionMap = new byte[tr.getRegionWidth()][tr.getRegionHeight()];	
			for(int x = tr.getRegionX(); x < tr.getRegionWidth()+tr.getRegionX();x++){
				for(int y = tr.getRegionY(); y < tr.getRegionHeight()+tr.getRegionY();y++){
					color = new Color(pixMap.getPixel(x, y));
					String blub = color.toString();

					if(0 == blub.compareToIgnoreCase("ffffff00"))System.out.print("Darf nicht würd ich sagen");
					else if(0 == blub.compareToIgnoreCase("000000ff"))collisionMap[y-tr.getRegionY()][x-tr.getRegionX()] = 1;
					else if(0 == blub.compareToIgnoreCase("ffffffff"))collisionMap[y-tr.getRegionY()][x-tr.getRegionX()] = 0;
				}
			}
			spriteCollisionmapHashmap.put(name, collisionMap);
		}
		textureData.disposePixmap();//weiß nicht ob das muss :D
		fullexture.dispose();
		pixMap.dispose();
	}
	
	public byte[][] getSpriteCollisionmap(String spriteName){
		return spriteCollisionmapHashmap.get(spriteName);
	}
}
