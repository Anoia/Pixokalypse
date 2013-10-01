package com.we.PixokalypsePrototypes;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class PixokalypsePrototypes extends Game {
        
    	SpriteBatch batch;
    	BitmapFont font12, font24;
    	ShapeRenderer shapeRenderer;
    	Skin skin;

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

        //Ab hier wird ein standard Skin erzeugt für Buttons und Text.
    		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
    		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
    		skin = new Skin();
    		
    		// Generate a 1x1 white texture and store it in the skin named "white".
    		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
    		pixmap.setColor(Color.WHITE);
    		pixmap.fill();
    		skin.add("white", new Texture(pixmap));

    		// Store the default libgdx font under the name "default".
    		skin.add("default", font12);

    		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
    		TextButtonStyle textButtonStyle = new TextButtonStyle();
    		textButtonStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY);
    		textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);

    		textButtonStyle.font = skin.getFont("default");
    		textButtonStyle.fontColor = Color.BLACK;
    		textButtonStyle.disabled = skin.newDrawable("white", new Color(.9f,.9f,.9f,1.f));
    		textButtonStyle.disabledFontColor = Color.GRAY;
    		
    		LabelStyle labelStyle = new LabelStyle();
    		labelStyle.font = font12;
    		skin.add("defaultLabel", labelStyle);
    		skin.add("default", textButtonStyle);
    	//ende Skinerzeugung
    		
    	//Renderer erzeugen
     		shapeRenderer = new ShapeRenderer();
    		batch = new SpriteBatch();
    		
    		
    		//Sprung zum Mainscreen und übergabe des gameobjekts um in jeder sceen die public variablen
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
        		skin.dispose();
        }

}