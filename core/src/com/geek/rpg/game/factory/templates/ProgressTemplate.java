package com.geek.rpg.game.factory.templates;


import com.geek.rpg.game.factory.JsonLoader;
import com.geek.rpg.game.factory.JsonTemplate;

public class ProgressTemplate extends JsonTemplate
{
	private static final int[] expToLevel = {0, 100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600, -1};

	public static final JsonLoader<ProgressTemplate> jsonLoader = new JsonLoader<ProgressTemplate>("progress", ProgressTemplate[].class);

	private ProgressHero[] heroes;
	private int levels = 1;

	public ProgressHero[] getHeroes()
	{
		return heroes;
	}

	public int getLevels()
	{
		return levels;
	}

	public void setLevels(int levels)
	{
		this.levels = levels;
	}


	public static ProgressTemplate[] getAll()
	{
		return jsonLoader.getGeneratedArray();
	}

	public class ProgressHero
	{
		private int id;
		private int level;
		private int exp;

		public int getId()
		{
			return id;
		}

		public int getLevel()
		{
			return level;
		}

		public boolean isMax()
		{
			return level == expToLevel.length - 1;
		}

		public void setLevel(int level)
		{
			this.level = level;
		}

		public int getExp()
		{
			return exp;
		}

		public void setExp(int exp)
		{
			this.exp = exp;
		}

		public int getCurExp()
		{
			if (expToLevel[level] > 0)
				return exp - expToLevel[level - 1];
			else
				return getMaxExp();
		}

		public int getExpPercent()
		{
			return (getCurExp() * 100) / getMaxExp();
		}

		public int getMaxExp()
		{
			if (expToLevel[level] > 0)
				return expToLevel[level] - expToLevel[level - 1];
			else
				return expToLevel[level - 1] - expToLevel[level - 2];
		}

		public void addExp(int addExp)
		{
			exp += addExp;
			while (expToLevel[level] >= 0 && exp >= expToLevel[level])
				level++;
		}
	}
}
