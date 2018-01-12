package com.geek.rpg.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.geek.rpg.game.MainMenu.MainMenuScreen;
import com.geek.rpg.game.Prepare.PrepareScreen;
import com.geek.rpg.game.Skirmish.SkirmishScreen;
import com.geek.rpg.game.WorldMap.WorldMapScreen;

public class ScreenManager
{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public enum ScreenType
	{
		MAIN_MENU,
		WORLD_MAP,
		SKIRMISH,
		PREPARE
	}

	private static final ScreenManager instance = new ScreenManager();

	public static ScreenManager getInstance()
	{
		return instance;
	}

	private ScreenManager()
	{
	}

	private App app;
	private Viewport viewport;
	private MainMenuScreen mainMenuScreen;
	private SkirmishScreen skirmishScreen;
	private WorldMapScreen worldMapScreen;
	private PrepareScreen prepareScreen;

	public void init(App app, SpriteBatch batch)
	{
		this.app = app;
		this.mainMenuScreen = new MainMenuScreen(batch);
		this.skirmishScreen = new SkirmishScreen(batch);
		this.worldMapScreen = new WorldMapScreen(batch);
		this.prepareScreen = new PrepareScreen(batch);
		this.viewport = new FitViewport(WIDTH, HEIGHT);
		this.viewport.update(WIDTH, HEIGHT, true);
		this.viewport.apply();
	}

	public void onResize(int width, int height)
	{
		viewport.update(width, height, true);
		viewport.apply();
	}

	public void switchScreen(ScreenType type, Object... params)
	{
		Screen screen = app.getScreen();
		Assets.getInstance().clear();
		if (screen != null)
			screen.dispose();

		ScreenTemplate screenTemplate = null;
		switch (type)
		{
			case MAIN_MENU:
				screenTemplate = mainMenuScreen;
				break;
			case WORLD_MAP:
				screenTemplate = worldMapScreen;
				break;
			case PREPARE:
				screenTemplate = prepareScreen;
				break;
			case SKIRMISH:
				screenTemplate = skirmishScreen;
				break;
		}
		screenTemplate.show(params);
		app.setScreen(screenTemplate);
	}

	public void dispose()
	{
		Assets.getInstance().dispose();
		mainMenuScreen.dispose();
		worldMapScreen.dispose();
		skirmishScreen.dispose();
	}

	public Viewport getViewport()
	{
		return viewport;
	}
}
