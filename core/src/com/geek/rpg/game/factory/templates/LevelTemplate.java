package com.geek.rpg.game.factory.templates;


import com.geek.rpg.game.factory.JsonLoader;
import com.geek.rpg.game.factory.JsonTemplate;

public class LevelTemplate extends JsonTemplate
{
	public static final JsonLoader<LevelTemplate> jsonLoader = new JsonLoader<LevelTemplate>("levels", LevelTemplate[].class);
	
	private String name = "NO_NAME";
	private int friendlyCharactersCount = 1;
	private Enemy[] enemies;

	public static JsonLoader<LevelTemplate> getJsonLoader()
	{
		return jsonLoader;
	}

	public String getName()
	{
		return name;
	}

	public int getFriendlyCharactersCount()
	{
		return friendlyCharactersCount;
	}

	public Enemy[] getEnemies()
	{
		return enemies;
	}

	public class Enemy
	{
		private int id = 1;
		private int level = 1;

		public int getId()
		{
			return id;
		}

		public int getLevel()
		{
			return level;
		}
	}
}
