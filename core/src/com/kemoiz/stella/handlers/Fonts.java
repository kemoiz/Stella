package com.kemoiz.stella.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Fonts {

	public BitmapFont white;
	private LabelStyle fontStyle;
	public Label distanceString, distanceMeter, velocityString, velocityMeter;

	public Fonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("font/FRADM.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		System.out.println(Gdx.graphics.getHeight());
		double f = (Gdx.graphics.getWidth() / 480.0f);
		System.out.println(f);
		parameter.size = (int) (32 * f);
		parameter.color = Color.WHITE;
		parameter.kerning = false;
		parameter.genMipMaps = false;

		white = generator.generateFont(parameter);
		generator.dispose();
		white.setScale(1);
		white.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		fontStyle = new LabelStyle(white, Color.WHITE);

		distanceString = new Label("distance", fontStyle);
		distanceString.setPosition(Ruler.width(20), Ruler.height(750));
		distanceMeter = new Label("n/a", fontStyle);
		distanceMeter.setPosition(Ruler.width(20), Ruler.height(715));
		distanceMeter.setFontScale(0.75f);
		velocityString = new Label("velocity", fontStyle);
		velocityString.setPosition(Ruler.width(360), Ruler.height(750));
		velocityMeter = new Label("n/a", fontStyle);
		velocityMeter.setPosition(Ruler.width(410), Ruler.height(715));
		velocityMeter.setFontScale(0.75f);
		velocityString.setAlignment(Align.topRight);
		velocityMeter.setAlignment(Align.right);
	}

	public void update(double distance, float velocity) {

		distanceMeter.setText(String.valueOf((int) distance) + " km");
		velocityMeter.setText(String.valueOf((int) velocity) + " km/s");
	}

}
