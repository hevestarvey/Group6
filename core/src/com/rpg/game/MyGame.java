package com.rpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;



public class MyGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture backgroundImage;
	private Texture fightBackground;
	private Texture playerSpriteSheet;
	private Animation<TextureRegion> walkAnimation;
	private float stateTime;
	private Player player;
	private Rectangle playArea;
	private GameState gameState;

	private enum GameState {
		EXPLORING,
		FIGHTING
	}

	// MyGame class fields
	private Texture enemy1Texture;
	private Texture enemy2Texture;
	private Texture enemy3Texture;
	private Texture twinbladesTexture;
	// Positions for enemies and weapon
	private Vector2 enemy1Position;
	private Vector2 enemy2Position;
	private Vector2 enemy3Position;
	private Vector2 weaponPosition;
	private Viewport viewport;
	private Camera camera;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(1920, 1080, camera);

		batch = new SpriteBatch();
		backgroundImage = new Texture(Gdx.files.internal("background.png"));
		fightBackground = new Texture(Gdx.files.internal("battle_back5.png"));
		playerSpriteSheet = new Texture(Gdx.files.internal("idle.png")); // Ensure this is in your assets folder

		int frameWidth = playerSpriteSheet.getWidth() / 8;
		int frameHeight = playerSpriteSheet.getHeight() / 2;
		TextureRegion[][] tempFrames = TextureRegion.split(playerSpriteSheet, frameWidth, frameHeight);
		Animation<TextureRegion> idleAnimation = new Animation<>(0.2f, tempFrames[0]); // First row for idle
		Animation<TextureRegion> walkAnimation = new Animation<>(0.2f, tempFrames[1]); // Second row for walking
		stateTime = 0f;

		playArea = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameState = GameState.EXPLORING;

		// Create player with the first frame of walk animation

		player = new Player(50, 50, frameWidth, frameHeight, walkAnimation);

		int frameCols = 5; //

		enemy1Texture = new Texture(Gdx.files.internal("enemy1.png"));
		enemy2Texture = new Texture(Gdx.files.internal("enemy2.png"));
		enemy3Texture = new Texture(Gdx.files.internal("enemy3.png"));
		twinbladesTexture = new Texture(Gdx.files.internal("twin_blades.png"));

		// Initialize positions for enemies and weapon, adjust as needed
		enemy1Position = new Vector2(100, 200);
		enemy2Position = new Vector2(200, 200);
		enemy3Position = new Vector2(300, 200);
		weaponPosition = new Vector2(400, 100);

		// Example coordinates, adjust as needed based on the viewport and sprites' size
		enemy1Position = new Vector2(viewport.getWorldWidth() * 0.25f - enemy1Texture.getWidth() / 2, viewport.getWorldHeight() / 2);
		enemy2Position = new Vector2(viewport.getWorldWidth() * 0.5f - enemy2Texture.getWidth() / 2, viewport.getWorldHeight() / 2);
		enemy3Position = new Vector2(viewport.getWorldWidth() * 0.75f - enemy3Texture.getWidth() / 2, viewport.getWorldHeight() / 2);
		weaponPosition = new Vector2(viewport.getWorldWidth() / 2 - twinbladesTexture.getWidth() / 2, 0);



	}

	@Override
	public void render() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		handleInput();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		viewport.apply();
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		if (gameState == GameState.EXPLORING) {
			batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			player.draw(batch); // Draw player with current animation frame
		} else if (gameState == GameState.FIGHTING) {
			batch.draw(fightBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(enemy1Texture, enemy1Position.x, enemy1Position.y);
			batch.draw(enemy2Texture, enemy2Position.x, enemy2Position.y);
			batch.draw(enemy3Texture, enemy3Position.x, enemy3Position.y);
			batch.draw(twinbladesTexture, weaponPosition.x, weaponPosition.y);
		}
		batch.end();

		if (gameState == GameState.EXPLORING) {
			player.update(Gdx.graphics.getDeltaTime());
		}
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			gameState = gameState == GameState.EXPLORING ? GameState.FIGHTING : GameState.EXPLORING;
		}
		// Additional input handling for player movement
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
	}

	@Override
	public void dispose() {
		batch.dispose();
		backgroundImage.dispose();
		fightBackground.dispose();
		playerSpriteSheet.dispose();
		// Dispose other assets like player textures if necessary
	}
}
