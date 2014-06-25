package ui;

import screens.GameScreen;
import agents.PlayerCharacter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
public class HUD {
	
	private Stage stage;
    private GameScreen game;
    private Skin skin;
    
    public HUD(GameScreen game, Stage stage, Skin skin){
        this.game =  game;
        this.stage = stage;
        this.skin = skin;

        init();
    }

	private void init() {
		Table characterInfoPanel = new Table();
		for(PlayerCharacter pc: game.getPlayerCharacters()){
			characterInfoPanel.addActor(new CharacterInfo(pc));
		}
		
		characterInfoPanel.setPosition(10, 10);
		
		
		stage.addActor(characterInfoPanel);
		
	}
    
	
	private class CharacterInfo extends WidgetGroup{
		PlayerCharacter pc;
		
		public CharacterInfo(PlayerCharacter pc){
			this.pc = pc;
			create();
		}
		private void create() {
			
			Label name = new Label(pc.getName(), skin);
			Slider healthbar = new Slider(0, pc.maxHealth, 1, false, skin);
			addActor(name);
			addActor(healthbar);
			
		}
		
	}

}
