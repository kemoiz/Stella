package com.kemoiz.stella.handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Ruler {

	public static float width(double m) {
		return (float) (m * (Gdx.graphics.getWidth() / 480f));
	}

	public static float height(double m) {
		return (float) (m * (Gdx.graphics.getHeight() / 800f));
	}

	public static Vector2 vec2(Vector2 v) {

		return new Vector2(v.x * (Gdx.graphics.getWidth() / 480f), v.y
				* (Gdx.graphics.getHeight() / 800f));
	}
}
