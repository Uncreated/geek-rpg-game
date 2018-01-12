package com.geek.rpg.game.Progress;

import com.geek.rpg.game.factory.templates.ProgressTemplate;

public class ProgressCell
{
	private ProgressTemplate template;

	public ProgressCell(int id)
	{
		template = ProgressTemplate.jsonLoader.get(id);
	}

	public ProgressTemplate.ProgressHero[] getHeroes()
	{
		return template.getHeroes();
	}

	public int getLevels()
	{
		return template.getLevels();
	}

	public void setLevels(int levels)
	{
		template.setLevels(levels);
	}

	public void save()
	{
		ProgressTemplate.jsonLoader.save();
	}

	public static ProgressCell[] createAll()
	{
		ProgressTemplate[] templates = ProgressTemplate.getAll();
		ProgressCell[] cells = new ProgressCell[templates.length];
		for (int i = 0; i < templates.length; i++)
			cells[i] = new ProgressCell(templates[i].getId());

		return cells;
	}
}
