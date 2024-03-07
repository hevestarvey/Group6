package com.rpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class MyGame extends ApplicationAdapter {
	private Texture backgroundImage;
	ShapeRenderer shapeRenderer;
	Player player;
	Rectangle playArea; // Declare the playArea inside the class

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		player = new Player(50, 50, 16, 16);

		playArea = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		player.createStarModel();
	}

	@Override
	public void render() {
		handleInput(); // Method to handle player input
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw the background image
		batch.begin();
		batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

		// Update and draw the player
		player.update(Gdx.graphics.getDeltaTime()); // Update player logic
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());
		shapeRenderer.end();

		// Render the play area
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(playArea.x, playArea.y, playArea.width, playArea.height);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.YELLOW);

		// Get the star's vertices and draw it using the shapeRenderer
		float[] vertices = player.getStarVertices();
		shapeRenderer.polygon(vertices);

		shapeRenderer.end();
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose(); // Dispose of the ShapeRenderer when done
		backgroundImage.dispose();
	}

	private void handleInput() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		// Movement vector to keep track of the change in position
		Vector2 movement = new Vector2();

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			movement.x -= player.getSpeed() * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			movement.x += player.getSpeed() * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			movement.y += player.getSpeed() * deltaTime;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			movement.y -= player.getSpeed() * deltaTime;
		}

		player.move(movement, playArea);

	}
}
