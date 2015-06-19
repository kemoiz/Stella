package com.kemoiz.stella;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.kemoiz.stella.handlers.AssetHandler;
import com.kemoiz.stella.handlers.Fonts;
import com.kemoiz.stella.handlers.State;
import com.kemoiz.stella.objects.Wall;

public class GameScreen implements Screen {

	private MotherClass motherClass;
	private AssetHandler assetHandler;

	private GameWorld gameWorld;
	private SpriteBatch gameBatch, guiBatch;

	private OrthographicCamera gameCamera, guiCamera;

	private ShaderProgram shader;
	private GameBackground gameBackground;
	private Fonts fonts;
	private float neonShadingSin = MathUtils.random(100f);
	public boolean loaded = false;

	public GameScreen(MotherClass motherClass, AssetHandler assetHandler) {
		this.motherClass = motherClass;
		this.assetHandler = assetHandler;
		gameBatch = new SpriteBatch();
		gameWorld = new GameWorld();
		guiBatch = new SpriteBatch();
		ShaderProgram.pedantic = false;

		gameCamera = new OrthographicCamera(480, 800);
		gameCamera.setToOrtho(false);
		gameCamera.update();
		gameBatch.setProjectionMatrix(gameCamera.combined);

		guiCamera = new OrthographicCamera(480, 800);
		guiCamera.setToOrtho(false);
		guiCamera.update();
		guiBatch.setProjectionMatrix(guiCamera.combined);
		fonts = new Fonts();
		gameBackground = new GameBackground(
				(Texture) assetHandler.get("gfx/galaxy_layer0.png"),
				(Texture) assetHandler.get("gfx/galaxy_layer1.png"));
		loaded = true;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gameCamera.zoom = 1f;
		gameCamera.position.y = gameWorld.getScreenY();
		gameCamera.update();
		gameBatch.setProjectionMatrix(gameCamera.combined);

		gameBackground.render(gameWorld.distance);
		gameBatch.begin();
		gameBatch.setColor(1, 1, 1, 1f);

		if (gameWorld.getTime() < 150) {
			gameBatch.setColor(1, 1, 1, (150 - gameWorld.getTime()) / 150f);

		} else {
			gameBatch.setColor(1, 1, 1, 1);
		}

		stateDependantRender(delta);
		for (Wall wall : gameWorld.walls) {
			wall.draw(gameBatch);
		}
		gameBatch.end();
		guiBatch.begin();
		renderLabels();
		guiBatch.end();
	}

	private void stateDependantRender(float delta) {
		switch (MotherClass.state) {
		case State.IDLE:

			break;
		case State.PLAY:

			gameWorld.tick(delta);
			gameWorld.gameOverCounter = 30;
			gameWorld.getPlayer().renderAndUpdate(gameBatch, gameWorld.speed,
					gameWorld.getScreenY());

			break;

		case State.DEATH:

			if (gameWorld.gameOverCounter > 0) {

				gameWorld.gameOverCounter--;
				// too lazy to make a proper death update routine, not so lazy
				// to write an unnecessary comment
				gameWorld.getPlayer().renderAndUpdate(gameBatch, 0,
						gameWorld.getScreenY());
				break;

			}

			if (gameWorld.distance >= 0) {
				gameWorld.gameOverTemp1 = gameWorld.gameOverTemp1
						+ gameWorld.gameOverTemp1;
				gameWorld.distance -= gameWorld.gameOverTemp1;
				gameWorld.speed -= gameWorld.gameOverTemp1;
			} else {
				gameWorld.distance = 0;
				gameWorld.speed = 10.5f;
				MotherClass.state = State.PLAY;
				gameWorld.setScreenY(420);
			}

			break;

		}

	}

	private void renderLabels() {

		fonts.update(gameWorld.distance, gameWorld.speed * 60);
		fonts.distanceMeter.draw(guiBatch, 1.0f);
		fonts.distanceString.draw(guiBatch, 1.0f);
		fonts.velocityMeter.draw(guiBatch, 1.0f);
		fonts.velocityString.draw(guiBatch, 1.0f);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
	}

	public GameWorld getWorld() {
		return gameWorld;
	}

}
