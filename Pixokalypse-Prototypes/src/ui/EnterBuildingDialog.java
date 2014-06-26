package ui;

import screens.GameScreen;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.we.PixokalypsePrototypes.test.Field;

public class EnterBuildingDialog extends Dialog{

	public EnterBuildingDialog(String title, Skin skin, Field building) {
		super(title, skin);
		this.text("Do you really want to enter this building?");
		this.button("yes", building);
		this.button("no");
		
	}
	
	@Override
	public void result(Object object){
		if(object!= null  && object instanceof Field){
			System.out.println("You entered the Building. Stuff happens.");
			Field building = (Field) object;
			building.entered = true;
		}
		GameScreen.paused = false;
	}
	
	
	

}
