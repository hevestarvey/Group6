package com.rpg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {
    private Vector2 position;
    private float speed;
    private Rectangle bounds;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime; // Keeps track of animation state time
    private TextureRegion staticSprite;

    // Constructor with Animation parameter for walk cycle
    public Player(float x, float y, float width, float height, Animation<TextureRegion> walkAnimation) {
        this.position = new Vector2(0, 0);
        this.speed = 100; // This can be changed to your preferred player speed
        this.bounds = new Rectangle(64, 64, width, height);
        this.stateTime = 0f;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime; // Accumulate elapsed animation time

        // Player movement input
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

        // Update the bounds to the new position
        bounds.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch batch) {
        // Get the current frame of the walk animation
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y, bounds.getWidth(), bounds.getHeight());

        float drawWidth = getWidth() * 2; // Scale up the width
        float drawHeight = getHeight() * 2; // Scale up the height
        batch.draw(currentFrame, position.x, position.y, drawWidth, drawHeight);
    }

    // Additional methods if necessary...

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public float getWidth() {
        return bounds.getWidth();
    }

    public float getHeight() {
        return bounds.getHeight();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
