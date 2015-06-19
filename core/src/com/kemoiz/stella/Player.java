package com.kemoiz.stella;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.stella.handlers.Ruler;
import com.kemoiz.stella.handlers.State;

public class Player {

	private TextureRegion[] anim = new TextureRegion[4];

	private Sprite player;

	public double maxVelocity = 8.0d;
	public float momentum = 0;
	public float speed = 0.8f;
	public float torque = speed - (speed * 0.1f);

	private Vector2 coords;

	public Player(Texture texture) {

		anim[0] = new TextureRegion(texture, 48, 128);
		player = new Sprite(anim[0]);
		coords = new Vector2(200, -200);
		player.setPosition(Ruler.width(200), Ruler.height(100));
		player.setSize(Ruler.width(48), Ruler.height(128));

	}

	public void renderAndUpdate(SpriteBatch batch, float worldSpeed, float f) {

		player.setY(f);

		maxVelocity = speed * 15f;
		this.speed = worldSpeed / 15f + 1.5f;
		momentum = momentum * 0.75f;

		if (MotherClass.state != State.DEATH && Gdx.input.isTouched()
				&& maxVelocity > Math.abs(momentum)) {

			if (Gdx.input.getX() > (Gdx.graphics.getWidth()) / 2)
				momentum += speed;
			else
				momentum -= speed;

		}

		coords.x = coords.x + momentum;

		if (coords.x < 0) {
			coords.x = 0;
			momentum = 0;
		}

		if (coords.x > 440) {
			coords.x = 440;
			momentum = 0;

		}

		player.setPosition(Ruler.width(coords.x), coords.y + f);

		player.draw(batch);
	}

	public Sprite getSprite() {
		return player;

	}

}
