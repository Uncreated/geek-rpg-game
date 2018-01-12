package com.geek.rpg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geek.rpg.game.Progress.ProgressCell;
import com.geek.rpg.game.factory.templates.Level;
import com.geek.rpg.game.factory.templates.ProgressTemplate;

import java.util.ArrayList;

public class App extends Game
{
	private SpriteBatch batch;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		ScreenManager.getInstance().init(this, batch);
		ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MAIN_MENU);
		//setScreen(skirmishScreen);
	}

	@Override
	public void render()
	{
		float dt = Gdx.graphics.getDeltaTime();
		this.getScreen().render(dt);
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		ScreenManager.getInstance().dispose();
	}

	public static class Settings
	{
		public Settings()
		{
			//load
		}
	}

	public static class Selections
	{
		//From MainMenu
		private static ProgressCell progressCell;

		//From WorldMap
		private static Level level;

		//From Prepare
		private static ArrayList<ProgressTemplate.ProgressHero> heroes;


		public static ProgressCell getProgressCell()
		{
			return progressCell;
		}

		public static void setProgressCell(ProgressCell progressCell)
		{
			Selections.progressCell = progressCell;
		}

		public static Level getLevel()
		{
			return level;
		}

		public static void setLevel(Level level)
		{
			Selections.level = level;
		}

		public static ArrayList<ProgressTemplate.ProgressHero> getHeroes()
		{
			return heroes;
		}

		public static void setHeroes(ArrayList<ProgressTemplate.ProgressHero> heroes)
		{
			Selections.heroes = heroes;
		}
	}
}