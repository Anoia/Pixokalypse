package ui;

import java.util.ArrayList;

import screens.GameScreen;
import agents.PlayerCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
public class HUD {
	
	private Stage stage;
    private GameScreen game;
    private Skin skin;
    
    Table characterInfoPanel; 
    ArrayList<CharacterInfo> characterInfoList = new ArrayList<CharacterInfo>();
    
    public HUD(GameScreen game, Stage stage, Skin skin){
        this.game =  game;
        this.stage = stage;
        this.skin = skin;

        init();
    }

	private void init() {
		characterInfoPanel = new Table();
		for(PlayerCharacter pc: game.getPlayerCharacters()){
			CharacterInfo info = new CharacterInfo(pc);
			characterInfoList.add(info);
			characterInfoPanel.add(info).pad(5);
			characterInfoPanel.row();
		}
		characterInfoPanel.pack();
		System.out.println(characterInfoPanel.getHeight());
		characterInfoPanel.setPosition(10, Gdx.graphics.getHeight()-characterInfoPanel.getHeight()-10);
		
		
		stage.addActor(characterInfoPanel);
		
	}
    
	
	private class CharacterInfo extends HorizontalGroup{
		PlayerCharacter pc;
		ProgressBar healthbar;
		
		public CharacterInfo(PlayerCharacter pc){
			this.pc = pc;
			create();
		}
		private void create() {
			VerticalGroup group = new VerticalGroup();
			
			Label name = new Label(pc.getName(), skin);
			//name.setSize(100, 20);
			healthbar = new ProgressBar(0, pc.maxHealth, 1, false, skin, "healthbar");
			healthbar.setValue(pc.currentHealth);
			healthbar.setTouchable(Touchable.disabled);
			
			group.pad(2);
			group.addActor(name);
			group.pad(2);
			group.addActor(healthbar);
			group.pad(2);
			group.left();
			group.pack();
			
			Container<Image> c = new Container<Image>();
			Sprite sprite = new Sprite(new Texture("data/characters/portrait.png"));
			Image image = new Image(new TextureRegionDrawable(sprite));
			image.addListener(new ClickListener(){
				@Override
                public void clicked(InputEvent event, float x, float y) {
					game.setSelectedPlayerCharacter(pc);
				}
			});
			c.setActor(image);
			c.pack();
			System.out.println(c.getWidth());
			
			
			
			
			addActor(c);
			
			addActor(group);
			pack();
		}
		public void update(){
			healthbar.setValue(pc.currentHealth);
		}
		
	}
	
	public void update(){
		for(CharacterInfo info: characterInfoList){
			info.update();
		}
	}

}
