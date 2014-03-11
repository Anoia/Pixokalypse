package screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.we.PixokalypsePrototypes.PixokalypsePrototypes;
import com.we.PixokalypsePrototypes.test.SpriteCollisionMapContainer;
import com.we.PixokalypsePrototypes.test.SpriteContainer;
import com.we.PixokalypsePrototypes.test.TestActor;

public class ParseTextureAtlasTestScreen implements Screen{ //,InputProcessor {
	final PixokalypsePrototypes game;
	
 	private OrthographicCamera camera;
 	private SpriteContainer spriteContainer;
 	private SpriteCollisionMapContainer spriteCollisionmapContainer;
	private ArrayList<TestActor> actorList;
	private int width;
	private int height;
	private boolean addNewActors = true;

	private int countdownTillStop = 120;
	public ParseTextureAtlasTestScreen(final PixokalypsePrototypes gam) {
		this.game = gam;
		spriteContainer = new SpriteContainer("data/ground.txt");
		spriteCollisionmapContainer = new SpriteCollisionMapContainer("data/height.txt", "data/height.png");
		actorList = new ArrayList<TestActor>();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false, width, height);
	}

	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void render(float delta) {
		
		if(addNewActors){
			//Generierte Actors pro Frame
			for (int i = 0; i < 10;i++){
				actorList.add(new TestActor(width, height, spriteContainer.getRandomSpriteName()));
			}
			if(Gdx.graphics.getFramesPerSecond() < 45 && Gdx.graphics.getFramesPerSecond() > 10) countdownTillStop --;
			if(countdownTillStop < 1)addNewActors = false;
		}
		
		Iterator itr = actorList.iterator();
	      while(itr.hasNext()) {
	    	 TestActor testActor = (TestActor) itr.next();
	         if(testActor.isOutOfWindow()) testActor.resertActor();
	         else testActor.updatePosition(delta);
	      }
	      
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				
//		angeblich f�r transparenz notwendig
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		//Sprites Rendern anfang
		
		Sprite sprite;
		TestActor testActor;
        
		itr = actorList.iterator();
	    while(itr.hasNext()) {
	    	testActor = (TestActor) itr.next();
	    	sprite = spriteContainer.getGroundSprite(testActor.spriteName);
			sprite.setPosition(testActor.x,testActor.y);
			sprite.draw(game.batch);
		}
		
	    //Sprites Rendern ende
		game.batch.end();

		
//      angeblich f�r transparenz notwendig ende
		Gdx.gl.glDisable(GL10.GL_BLEND);
		

		//FPS unten links rendern
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.batch.begin();
		if(!addNewActors)game.font24.draw(game.batch, "Anz gerenderte Tiles pro Frame: " + actorList.size(), 20, Gdx.graphics.getHeight()/2);
		game.font24.draw(game.batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 30, 30);
		game.font12.draw(game.batch, "("+countdownTillStop+")Anz gerenderte Tiles pro Frame: " + actorList.size(), Gdx.graphics.getWidth()/2, 13);
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