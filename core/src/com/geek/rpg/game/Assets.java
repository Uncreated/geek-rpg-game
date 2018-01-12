package com.geek.rpg.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.freetype


public class Assets
{
	private static final Assets instance = new Assets();

	public static Assets getInstance()
	{
		return instance;
	}

	private AssetManager assetManager = new AssetManager();

	public void load(ScreenManager.ScreenType type)
	{
		assetManager.load("noTexture.png", Texture.class);
		assetManager.load("font.fnt", BitmapFont.class);
		switch (type)
		{
			case SKIRMISH:

				loadTextures("background.png",
						"selector.png",
						"healthBar.png",
						"manaBar.png");
				break;
			case WORLD_MAP:
				loadTextures("backgroundSky.png",
						"flyingIsland.png");
				break;
		}
		assetManager.finishLoading();
	}

	public void loadTextures(String... textures)
	{
		for (String texture : textures)
			assetManager.load(texture, Texture.class);
	}

	public void clear()
	{
		assetManager.clear();
	}

	public void dispose()
	{
		assetManager.clear();
		assetManager.dispose();
	}

	private <T> T getAsset(String name, Class<T> tClass)
	{
		if (!assetManager.isLoaded(name))
		{
			assetManager.load(name, tClass);
			assetManager.finishLoadingAsset(name);
			//return null;
		}
		return assetManager.get(name, tClass);
	}

	public Texture getTexture(String name)
	{
		return getAsset(name, Texture.class);
	}

	public BitmapFont getFont(String name)
	{
		return getAsset(name, BitmapFont.class);
	}

	public BitmapFont getFont(String name, int size)
	{
		return getAsset(name, BitmapFont.class);
		//FreeTypeGenerator generator;
		//return null;
	}
}
