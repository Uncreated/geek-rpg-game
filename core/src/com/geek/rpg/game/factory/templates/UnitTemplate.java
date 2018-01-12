package com.geek.rpg.game.factory.templates;

import com.geek.rpg.game.Skirmish.units.UnitStats;
import com.geek.rpg.game.factory.JsonLoader;
import com.geek.rpg.game.factory.JsonTemplate;

public class UnitTemplate extends JsonTemplate
{
	public static final JsonLoader<UnitTemplate> jsonLoader = new JsonLoader<UnitTemplate>("units", UnitTemplate[].class);

	private String name;
	private UnitStats baseStats;
	private Integer animationId;
	private Integer[] abilities;
	private int unitClass = 0;

	public String getName()
	{
		return name;
	}

	public UnitStats getBaseStats()
	{
		return baseStats;
	}

	public Integer getAnimationId()
	{
		return animationId;
	}

	public Integer[] getAbilities()
	{
		return abilities;
	}

	public int getUnitClass()
	{
		return unitClass;
	}
}
