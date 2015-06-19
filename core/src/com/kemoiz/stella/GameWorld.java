package com.kemoiz.stella;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.kemoiz.stella.handlers.RandomGenerator;
import com.kemoiz.stella.handlers.State;
import com.kemoiz.stella.objects.Wall;

public class GameWorld {

	public double distance = 0;
	public float speed = 10.5f;
	private float time = 0;
	private float acceleration = 0.025f;

	private Player player;
	public Array<Wall> walls = new Array<Wall>();

	public float gameOverTemp1 = 0, gameOverCounter = 30;
	private int currentWallId = 0;

	private double shuffleCounter;

	private float screenY = 420; // initial value, save somewhere
	private int minimumWallSpace = 160;

	public GameWorld() {

		player = new Player(MotherClass.assetHandler.get("gfx/player.png",
				Texture.class));

	}

	public void tick(float delta) {

		if (Gdx.input.isKeyPressed(Keys.D)) {
			// death state testing

			death();

		}

		collisionCheck();

		screenY += (speed) / 10.0;

		for (Wall wall : walls) {
			wall.update(getScreenY());
		}

		shuffleCounter += (speed / 10.0) - (speed * 0.05);

		if (shuffleCounter > minimumWallSpace) {
			shuffleCounter = 0;
			shufflePlayfield();
		}

		clearPlayfield();

		distance += speed * (delta * 60);

		time++;

		speed += acceleration;

	}

	private void collisionCheck() {
		Rectangle playerRect = new Rectangle(player.getSprite().getX(), player
				.getSprite().getY(), player.getSprite().getWidth(), player
				.getSprite().getHeight());
		for (Wall wall : walls) {

			for (Rectangle rect : wall.rectangles) {

				if (rect != null && Intersector.overlaps(playerRect, rect)) {

					death();

				}

			}

		}

	}

	private void clearPlayfield() {
		for (Wall wall : walls) {

			if (wall.getY() < screenY - 600) {
				Gdx.app.log("xx", "deleting wall");

				walls.removeValue(wall, false);
			}
		}

	}

	private void shufflePlayfield() {

		if (RandomGenerator.getFloat() > 0.0) {

			walls.add(new Wall(1, RandomGenerator.getFloat(), currentWallId,
					getScreenY()));
			currentWallId++;

		}

	}

	public float getTime() {

		return time;
	}

	public Player getPlayer() {
		return player;
	}

	public void death() {
		MotherClass.state = State.DEATH;

		gameOverTemp1 = 1.001f;
		gameOverCounter = 30;
		walls.clear();

	}

	public float getScreenY() {
		return screenY;
	}

	public void setScreenY(float screenY) {
		this.screenY = screenY;
	}

}
