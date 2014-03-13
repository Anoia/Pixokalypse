package com.we.PixokalypsePrototypes;
import screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class PixokalypsePrototypes extends Game {
        
    	public SpriteBatch batch;
    	public BitmapFont font12, font24;
    	public ShapeRenderer shapeRenderer;
    	public Skin skin;

    	public PixokalypsePrototypes(){
    	}
    	
        public void create() {
        	//Font generieren
        	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Minecraftia.ttf"));
        	font12 = generator.generateFont(12); // font size 12 pixels
        	font24 = generator.generateFont(24); // font size 25 pixels
        	generator.dispose(); // don't forget to dispose to avoid memory leaks!
        	
    		font12.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    		font24.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    		
    		TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
            skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
            skin.add("font12", font12);
            skin.add("font24", font24);
    		

    		
    	//Renderer erzeugen
     		shapeRenderer = new ShapeRenderer();
    		batch = new SpriteBatch();
    		
    		
    		//Sprung zum Mainscreen und  Übergabe des gameobjekts um in jeder sceen die public variablen
    		//und somit die renderer, Schriftarten nutzen zu können.
            this.setScreen(new MainMenuScreen(this));
        }

        public void render() {
                super.render(); //important!
        }
        
        public void dispose() {
                batch.dispose();
                font12.dispose();
                shapeRenderer.dispose();
        		//skin.dispose();
        }
        
        public int getNextPowOf2(int value){
        	int result = 2;
        	while(value > result){
        		result *=2;
        	}
        	return result;
        }

}