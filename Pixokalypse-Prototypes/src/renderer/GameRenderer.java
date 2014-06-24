package renderer;

import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import renderer.effects.Effect;
import screens.GameScreen;
import util.Constants;

import agents.Agent;
import agents.Enemy;
import agents.Follower;
import agents.Player;
import agents.Zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.we.PixokalypsePrototypes.test.FieldCategory;
import com.we.PixokalypsePrototypes.test.Map;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class GameRenderer {
	
	private GameScreen game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Map map;
	private SpriteContainer spriteContainer;
	
	private int tileSize = Constants.TILE_SIZE;
	
	public BitmapFont font12, font24;
	
	Comparator<ElementWithZIndex> comparator;
	
	
	// Shader stuff
	private FrameBuffer fbo;	
	
	private ShaderProgram defaultShader;
	private ShaderProgram finalShader;
	
	public static final float ambientIntensity = .7f;
	public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);
	
	final String vertexShader = Gdx.files.internal("data/shader/vertexShader.glsl").readString();
	final String defaultPixelShader = Gdx.files.internal("data/shader/defaultPixelShader.glsl").readString();
	final String finalPixelShader =  Gdx.files.internal("data/shader/pixelShader.glsl").readString();
	
	private Texture light;
	
	public GameRenderer(GameScreen game, SpriteBatch batch, OrthographicCamera camera, Map map){
		this.game = game;
		this.batch = batch;
		this.camera = camera;
		this.map = map;
		spriteContainer = new SpriteContainer();
		initializeShader();
		comparator = new Comparator<ElementWithZIndex>() {
			@Override
			public int compare(ElementWithZIndex o1, ElementWithZIndex o2) {
				return o1.getZIndex() - o2.getZIndex();
			}
		};
		
	}
	
	private void initializeShader(){
		
		ShaderProgram.pedantic = false;
		defaultShader = new ShaderProgram(vertexShader, defaultPixelShader);
		finalShader = new ShaderProgram(vertexShader, finalPixelShader);	
		
		finalShader.begin();
		finalShader.setUniformi("u_lightmap", 1);
		finalShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
				ambientColor.z, ambientIntensity);
		finalShader.end();
		
		light = new Texture("data/shader/light.png");
		
	}
	
	public void update(float delta){
		
		drawLightToFBO();
		
		camera.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.setShader(finalShader);
		batch.begin();
		fbo.getColorBufferTexture().bind(1);
		light.bind(0);
		
		// Sprites Rendern anfang
		renderGround();
		//renderWithZIndex(delta);
		//renderAgents();
		renderPlayer();
		renderFollowers();
		renderZombies();
		renderBuildings();
	
	//	renderWithZIndex();
		
		batch.end();
		//renderFPS();
	}
	
	private void drawLightToFBO() {
		fbo.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(defaultShader);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		float lightSize = 150.0f;
		Player p = game.getPlayer();
		batch.draw(light, p.x - lightSize*0.5f , p.y-4 - lightSize*0.5f, lightSize, lightSize);
		batch.end();
		fbo.end();
		
	}

	private void renderWithZIndex() {
		ArrayList<ElementWithZIndex> toRender = new ArrayList<ElementWithZIndex>();
		
		//Add Elements To List
		toRender.addAll(game.getPlayerCharacters());
		toRender.addAll(getZombiesToRender());
		toRender.addAll(getBuildingsToRender());
		Collections.sort(toRender, comparator);
		
		//Actual Rendering
		String lastSpriteName = "";
		String spriteName = "";
		Sprite sprite = null;
		
		for(ElementWithZIndex element: toRender){
			// TODO Replace with subtype polymorphism: every ElementWithZIndex should have required information to render!
			// 
			if(element instanceof Building){
				Building b = (Building) element;
				spriteName = b.getSpriteName();
				if(!spriteName.equals(lastSpriteName)){
					sprite = spriteContainer.getSprite("Block-"+spriteName);
					lastSpriteName = spriteName;
				}
				
				sprite.setBounds(b.getX(), b.getY(), b.getWidth(),b.getHeight());
				sprite.draw(batch);
				
			}else{
				//it's an agent
				spriteName = element.getSpriteName();
				if(!spriteName.equals(lastSpriteName)){
					sprite = spriteContainer.getSprite(spriteName);
					lastSpriteName = spriteName;
				}
				sprite.setPosition(element.getX()-sprite.getWidth() / 2, element.getY() - sprite.getHeight());
				sprite.draw(batch);	
			}
		}
		
	}

	private ArrayList<Enemy> getZombiesToRender() {
		ArrayList<Enemy> enemies = game.getEnemies();
		ArrayList<Enemy> toRender = new ArrayList<Enemy>();
		for(Enemy e: enemies){
			if (e.getX() > camera.position.x-Gdx.graphics.getWidth()/2
					&& e.getX() < camera.position.x+Gdx.graphics.getWidth()/2
					&& e.getY() > camera.position.y-Gdx.graphics.getHeight()/2
					&& e.getY() < camera.position.y+Gdx.graphics.getHeight()/2
					) {
				toRender.add(e);
			}
		}
		return toRender;
	}

	private void renderGround(){
		Sprite sprite;
		//Bodenebene Rendern
		for (int x = 0; x < map.mapSize; x++) {
			for (int y = 0; y < map.mapSize; y++) {
				// skip wenn nicht im Sichtbarem bereich
				if (camera.position.x > (x * tileSize - (Gdx.graphics
						.getWidth() * 0.6f))
						&& camera.position.x < (x * tileSize + (Gdx.graphics
								.getWidth() * 0.6f))
								&& camera.position.y > (y * tileSize - (Gdx.graphics
										.getHeight() * 0.6f))
										&& camera.position.y < (y * tileSize + (Gdx.graphics
												.getHeight() * 0.6f))) {
					String spriteName = map.data[x][y].spriteName;
					sprite = spriteContainer.getSprite("Ground-"+spriteName);
					sprite.setBounds(x * tileSize, y * tileSize, tileSize,
							tileSize);
					sprite.draw(batch);
				}

			}

		}
	}
	
	private void renderBuildings(){
		Sprite sprite;
		for (int x = 0; x < map.mapSize; x++) {
			for (int y = 0; y < map.mapSize; y++) {
				// skip wenn nicht im Sichtbarem bereich
				if (camera.position.x > (x * tileSize - (Gdx.graphics
						.getWidth() * 0.6f))
						&& camera.position.x < (x * tileSize + (Gdx.graphics
								.getWidth() * 0.6f))
								&& camera.position.y > (y * tileSize - (Gdx.graphics
										.getHeight() * 0.6f))
										&& camera.position.y < (y * tileSize + (Gdx.graphics
												.getHeight() * 0.6f))) {
					
					if(map.data[x][y].fieldCategory == FieldCategory.BUILDING){
						String spriteName = map.data[x][y].spriteName;		
						sprite = spriteContainer.getSprite("Block-"+spriteName);
						if(sprite != null){
							sprite.setBounds((x * tileSize), (y * tileSize)-tileSize*1.5f, tileSize,tileSize*2.5f);
							sprite.draw(batch);
							
						}else System.out.println("Fehler: "+spriteName+" gibt null");
						
					}
				}

			}

		}
	}
	
	private ArrayList<Building> getBuildingsToRender(){
		ArrayList<Building> buildings = new ArrayList<Building>();
		for (int x = 0; x < map.mapSize; x++) {
			for (int y = 0; y < map.mapSize; y++) {
				// skip wenn nicht im Sichtbarem bereich
				if (camera.position.x > (x * tileSize - (Gdx.graphics
						.getWidth() * 0.6f))
						&& camera.position.x < (x * tileSize + (Gdx.graphics
								.getWidth() * 0.6f))
								&& camera.position.y > (y * tileSize - (Gdx.graphics
										.getHeight() * 0.6f))
										&& camera.position.y < (y * tileSize + (Gdx.graphics
												.getHeight() * 0.6f))) {
					
					if(map.data[x][y].fieldCategory == FieldCategory.BUILDING){
						String spriteName = map.data[x][y].spriteName;	
						int xPos = x*tileSize;
						int yPos = (int)(y*tileSize - tileSize*1.5f);
						int width = tileSize;
						int height = (int) (tileSize * 2.5);
						buildings.add(new Building(xPos, yPos, width, height, spriteName));
					}
				}

			}

		}
		
		return buildings;
	}
	
	private void renderAgents(){
		ArrayList<Agent> agents = new ArrayList<Agent>();
		agents.addAll(game.getPlayerCharacters());
		agents.addAll(getZombiesToRender());
		String lastSpriteName = "";
		Sprite sprite = null;
		for(Agent a: agents){
			String spriteName = a.getSpriteName();
			if(!spriteName.equals(lastSpriteName)){
				sprite = spriteContainer.getSprite(spriteName);
				lastSpriteName = spriteName;
			}
			sprite.setPosition(a.getX()-sprite.getWidth() / 2, a.getY() - sprite.getHeight());
			sprite.draw(batch);
		}
	}
	
	private void renderPlayer(){
		Player player = game.getPlayer();
		Sprite sprite = spriteContainer.getSprite(player.getSpriteName());
		sprite.setPosition(player.x - sprite.getWidth() / 2, player.y - sprite.getHeight());
		sprite.draw(batch);
	}
	
	private void renderFollowers(){
		ArrayList<Follower> followers = game.getFollowers();
		Sprite sprite;
		if (!followers.isEmpty()) {
			for (Follower f : followers) {
				sprite = spriteContainer.getSprite(f.getSpriteName());
				sprite.setPosition(f.x - sprite.getWidth() / 2,
						f.y - sprite.getHeight());
				sprite.draw(batch);
			}
		}
	}
	
	private void renderZombies(){
		ArrayList<Enemy> enemies = getZombiesToRender();
		Sprite sprite;
		if (!enemies.isEmpty()) {
			for (Enemy e : enemies) {
				sprite = spriteContainer.getSprite(e.getSpriteName());				
				sprite.setPosition(e.x - sprite.getWidth() / 2,
						e.y - sprite.getHeight());
				sprite.draw(batch);
			}
		}
	}
	
	public void setFonts(BitmapFont font12, BitmapFont font24){
		this.font12 = font12;
		this.font24 = font24;
	}
	
	private void renderFPS(){
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.begin();
		font24.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 30, Gdx.graphics.getHeight() - 30);
		batch.end();
	}
	
	public void dispose(){

	}
	
	public void resize(int width, int height){
		fbo = new FrameBuffer(Format.RGBA8888, width, height, false);

		finalShader.begin();
		finalShader.setUniformf("resolution", width, height);
		finalShader.end();	
	}

}
