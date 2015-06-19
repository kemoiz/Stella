package com.kemoiz.stella;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.kemoiz.stella.handlers.Ruler;
import com.kemoiz.stella.handlers.State;

public class GameBackground {
	private ShaderProgram shaderProgram;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public static float time = 0;

	private Pixmap stars;
	private Texture layer0, layer1;
	private boolean forward = true;
	private double timer;
	private double y;

	public GameBackground(Texture l0, Texture l1) {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(240, 400);
		camera.setToOrtho(true);

		camera.position.set(240, 400, 0);
		camera.zoom = 0.5f;
		camera.update();
		shaderProgram = new ShaderProgram(
				Gdx.files.internal("shaders/background.vsh"),
				Gdx.files.internal("shaders/background.fsh"));
		System.out.println(shaderProgram.isCompiled() ? "shader compiled"
				: shaderProgram.getLog());
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(shaderProgram);
		// stars = new Pixmap(480, 800, Format.RGBA4444);
		// /stars.setColor(Color.RED);
		// stars.fill();
		this.layer0 = l0;
		this.layer1 = l1;
		layer0.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		layer1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

	}

	public void render(double distance) {

		distance = distance / 10.0d;

		camera.position.set(Ruler.width(240), Ruler.height(400), 0);
		camera.zoom = 0.9f;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		if (time == 0 && !forward)
			forward = true;

		// timer = (Math.sin(time / 500) + 1) * 1000;

		shaderProgram.begin();
		shaderProgram.setUniformf("time", Gdx.graphics.getFrameId() / 0.10f);
		shaderProgram.setUniformf("mouse", 300, 300);

		shaderProgram.end();
		if (Gdx.input.isKeyPressed(Keys.R)) {
			shaderProgram = new ShaderProgram(
					Gdx.files.internal("shaders/background.vsh"),
					Gdx.files.internal("shaders/background.fsh"));
			System.out.println(shaderProgram.isCompiled() ? "shader compiled"
					: shaderProgram.getLog());
			batch.setShader(shaderProgram);
		}

		if (MotherClass.state == State.PLAY) {
			time += 1;
		}
		if (MotherClass.state == State.DEATH) {
			time += 1;
		}

		if (time == 1)
			refresh();
		else {
			camera.zoom = 1f;
			camera.update();
			batch.begin();
			// batch.disableBlending();
			if (MotherClass.state == State.PLAY) {
				y = time * (distance);
				batch.setColor(1, 1, 1, 1);

			}
			if (MotherClass.state == State.DEATH) {
				// y -= (speed);
				y = time * (distance);

				batch.setColor(Color.RED);
			}

			batch.draw(layer1, 0, 0, 0, (int) (distance * 0.5f),
					(int) Ruler.width(480), (int) Ruler.height(800));
			batch.draw(layer0, 0, 0, 0, (int) distance, (int) Ruler.width(480),
					(int) Ruler.height(800));

			batch.end();

		}

	}

	private void refresh() {

		// stars_tex.draw(stars, 0, 0);
		batch.begin();

		batch.draw(layer0, 0, 0, 480, 800);

		batch.end();

	}

	public void dispose() {
		stars.dispose();
		layer0.dispose();

	}
}
