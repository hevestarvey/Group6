package com.rpg.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Vector2 position;
    private float width;
    private float height;
    private float speed;
    private Rectangle bounds; // Define the bounds rectangle
    private float[] starVertices;

    public Player(float x, float y, float width, float height) {
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.speed = 100;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= speed * deltaTime;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Inside Player.java

    public void move(Vector2 movement, Rectangle playArea) {
        Vector2 newPosition = position.cpy().add(movement);

        // Create a new Rectangle for the new position bounds
        Rectangle newBounds = new Rectangle(newPosition.x, newPosition.y, width, height);

        // Check if the new position is inside the play area
        if (playArea.overlaps(newBounds)) {
            // Update position if within bounds
            position = newPosition;
            bounds.setPosition(position.x, position.y);
        }
    }

    public void createStarModel() {
        // Define the vertices for a simple star shape
        starVertices = new float[] {
                0, 0, // Center point
                -10, -10,
                10, -10,
                // Repeat pattern for other points to form a star
                // ...
        };
    }

    public float[] getStarVertices() {
        return starVertices;
    }


    // Also, add a getter for the speed
    public float getSpeed() {
        return speed;
    }

}
