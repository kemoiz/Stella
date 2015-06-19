package com.kemoiz.stella.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.kemoiz.stella.MotherClass;

public class Wall {

	private int holes, id;
	private double speed;
	private double originalY;

	private double screenY;

	private Texture texture;

	private boolean[] matrix = new boolean[16];
	public Rectangle[] rectangles = new Rectangle[16];
	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	public Wall(int holes, double d, int id, double originalY) {
		this.holes = holes;
		this.speed = d;
		this.id = id;
		this.originalY = originalY + 400;
		texture = MotherClass.assetHandler.get("gfx/wall_texture.png",
				Texture.class);

		Gdx.app.log("log", "spawning wall");

		int steps = 0; // temporary infinite loop workaround
		do {

			for (int i = 3; i < matrix.length - 2; i++) {
				if (Math.random() > 0.50f && holes >= 0 && !matrix[i]) {

					matrix[i] = false;
					matrix[i + 1] = false;
					matrix[i + 2] = false;
					holes -= 1;

					i += 2;

				} else {
					matrix[i] = true;

				}

			}

			steps++;
		} while (holes > 0 || steps > 999);

		for (int i = 0; i < 2; i++) {
			matrix[i] = true;
		}
		for (int i = 14; i < 16; i++) {
			matrix[i] = true;
		}

		for (int i = 0; i < 16; i++) {

			if (matrix[i]) {
				rectangles[i] = new Rectangle(i * 32, 0, 32, 32);
			}
		}
	}

	public void update(double actualY) {
		screenY = originalY;

	}

	public void draw(SpriteBatch batch) {
		// Gdx.app.log("screenY", String.valueOf(screenY));
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);

		for (int i = 0; i < 16; i++) {

			if (matrix[i]) {

				batch.draw(texture, i * 32, (float) originalY);

			}
		}

		for (int i = 0; i < 16; i++) {
			if (rectangles[i] != null)
				rectangles[i].y = (float) originalY;
			// if (matrix[i])
			// shapeRenderer.rect(rectangles[i].x, rectangles[i].y
			// + batch.getProjectionMatrix().getScaleY(),
			// rectangles[i].width, rectangles[i].height);
		}
		shapeRenderer.end();

	}

	public double getY() {
		// TODO Auto-generated method stub
		return originalY;
	}

}
