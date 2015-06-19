package com.kemoiz.stella.handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetHandler extends AssetManager {

	public AssetHandler() {

		load("gfx/splash_low.png", Texture.class);
		load("gfx/player.png", Texture.class);
		load("gfx/galaxy_layer0.png", Texture.class);
		load("gfx/galaxy_layer1.png", Texture.class);
		load("gfx/wall_texture.png", Texture.class);

		// load("gfx/block_off.png", Texture.class);

	}

}
