package com.kemoiz.stella;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.kemoiz.stella.handlers.AssetHandler;
import com.kemoiz.stella.handlers.Ruler;
import com.kemoiz.stella.handlers.State;

public class MotherClass extends Game {

	public GameScreen gameScreen;
	private boolean loading;
	public static AssetHandler assetHandler;
	public static int state = State.PLAY;
	private Texture splash;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {

		assetHandler = new AssetHandler();
		loading = true;
		shapeRenderer = new ShapeRenderer();

		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		super.render();

		handleLoading();

	}

	private void handleLoading() {

		if (assetHandler.update() && loading) {
			loading = false;
			gameScreen = new GameScreen(this, assetHandler);
			setScreen(gameScreen);

		}
		batch.begin();
		if (gameScreen != null && gameScreen.getWorld().getTime() < 150) {
			batch.setColor(1, 1, 1,
					(150 - gameScreen.getWorld().getTime()) / 150f);
			drawSplash();
		}

		if (loading && assetHandler.isLoaded("gfx/splash_low.png")) {
			drawSplash();
		}

		batch.end();

		if (!assetHandler.update()) {

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.rect(Ruler.width(120), Ruler.height(120),
					Ruler.width(250), Ruler.height(10));
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.rect(Ruler.width(120), Ruler.height(120),
					assetHandler.getProgress() * (Ruler.width(250)),
					Ruler.height(10));
			shapeRenderer.end();
			System.out
					.println("progress loading:" + assetHandler.getProgress());
		}

	}

	public void drawSplash() {
		batch.draw((Texture) assetHandler.get("gfx/splash_low.png"), 0, 0,
				Ruler.width(480), Ruler.height(800));
	}

	@Override
	public void dispose() {

		assetHandler.dispose();
		super.dispose();
	}
}
