package ui;

import java.util.ArrayList;

import screens.GameScreen;
import agents.PlayerCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
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
		Slider healthbar;
		
		public CharacterInfo(PlayerCharacter pc){
			this.pc = pc;
			create();
		}
		private void create() {
			VerticalGroup group = new VerticalGroup();
			
			Label name = new Label(pc.getName(), skin);
			name.setSize(100, 20);
			healthbar = new Slider(0, pc.maxHealth, 1, false, skin);
			healthbar.setValue(pc.currentHealth);
			group.pad(5);
			group.addActor(name);
			group.pad(5);
			group.addActor(healthbar);
			group.pad(5);
			group.pack();
			addActor(group);
			System.out.println(group.getHeight());
			pack();
			System.out.println(getHeight()+" hr");
		}
		public void update(){
			if(pc.currentHealth<=0){
				characterInfoList.remove(this);
				characterInfoPanel.removeActor(this);
			}
			healthbar.setValue(pc.currentHealth);
		}
		
	}
	
	public void update(){
		for(CharacterInfo info: characterInfoList){
			info.update();
		}
	}

}
