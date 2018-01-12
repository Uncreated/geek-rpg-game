package com.geek.rpg.game.factory.templates;

import com.geek.rpg.game.App;

public class Level
{
	private LevelTemplate template;

	public Level(int id)
	{
		template = LevelTemplate.jsonLoader.get(id);
	}

	public int getId()
	{
		return template.getId();
	}

	public String getName()
	{
		return template.getName();
	}

	public int getFriendlyCharactersCount()
	{
		return template.getFriendlyCharactersCount();
	}

	public LevelTemplate.Enemy[] getEnemies()
	{
		return template.getEnemies();
	}

	public void complete()
	{
		int maxLvl = App.Selections.getProgressCell().getLevels();
		if (getId() == maxLvl)
			App.Selections.getProgressCell().setLevels(maxLvl + 1);
	}
}
