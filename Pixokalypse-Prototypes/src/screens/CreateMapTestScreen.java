package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.we.PixokalypsePrototypes.PixokalypsePrototypes;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class CreateMapTestScreen implements Screen{ //,InputProcessor {
	PixokalypsePrototypes game;
	
 	private OrthographicCamera camera;
	private Map mainMap;

	private SpriteContainer spriteContainer;
	
	public CreateMapTestScreen(PixokalypsePrototypes game) {
		this.game = game;
		mainMap = new Map();
		spriteContainer = new SpriteContainer("data/textures/pack.atlas");
		System.out.println("alles fertig");
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(true, width, height);
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
//		angeblich f�r transparenz notwendig
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		//Sprites Rendern anfang
		int tileSize = 40;
		Sprite sprite;
		for(int x = 0; x < mainMap.mapSize; x ++){
			for(int y = 0; y < mainMap.mapSize; y++){
				String spriteName = mainMap.data[x][y].spriteName;
				sprite = spriteContainer.getGroundSprite(spriteName);
				sprite.setPosition(x*tileSize,y*tileSize);
				sprite.draw(game.batch);	
			}
		}
			
		//Sprites Rendern ende
		game.batch.end();
		
		
		/*	
		game.shapeRenderer.setProjectionMatrix(camera.combined);
		game.shapeRenderer.begin(ShapeType.Filled);
			int tileSize = 40;
			for(int i = 0; i < mainMap.mapSize; i ++){
				for(int j = 0; j < mainMap.mapSize; j++){
					switch(mainMap.map[i][j].fieldCategory){
					case EMPTY:
						game.shapeRenderer.setColor(new Color(0, .5f, 0, 1));
						break;
					case BUILDING:
						float color = 1-((1.f/255.f)*((4.f*mainMap.map[i][j].blockID+1.f))%255);
						game.shapeRenderer.setColor(new Color(color, 0, 0, 1));
						break;
					case STREET:
						game.shapeRenderer.setColor(Color.DARK_GRAY);
						break;
					}
					game.shapeRenderer.rect(i*tileSize, j*tileSize, tileSize, tileSize);
				}
			}
			
		game.shapeRenderer.end();
		*/
//      angeblich f�r transparenz notwendig ende
		Gdx.gl.glDisable(GL10.GL_BLEND);
		

		//FPS unten links rendern
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.batch.begin();
		game.font12.draw(game.batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
		game.batch.end();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}