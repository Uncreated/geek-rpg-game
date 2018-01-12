package com.geek.rpg.game.factory.templates;

import com.geek.rpg.game.factory.JsonLoader;
import com.geek.rpg.game.factory.JsonTemplate;

public class AbilityTemplate extends JsonTemplate
{
	public static final JsonLoader<AbilityTemplate> jsonLoader = new JsonLoader<AbilityTemplate>("abilities", AbilityTemplate[].class);

	private String name;
	private int minLevel = 1;
	private int cooldown = 1;
	private int cost = 0;
	private boolean enemy = false;
	private boolean friend = false;
	private boolean self = false;
	private int initiatorEffectId = 0;
	private int targetEffectId = 0;
	private int type = 1;
	private int count = 1;
	private int animationId = 0;

	public String getName()
	{
		return name;
	}

	public int getMinLevel()
	{
		return minLevel;
	}

	public int getCooldown()
	{
		return cooldown;
	}

	public int getCost()
	{
		return cost;
	}

	public boolean isEnemy()
	{
		return enemy;
	}

	public boolean isFriend()
	{
		return friend;
	}

	public boolean isSelf()
	{
		return self;
	}

	public int getInitiatorEffectId()
	{
		return initiatorEffectId;
	}

	public int getTargetEffectId()
	{
		return targetEffectId;
	}

	public int getType()
	{
		return type;
	}

	public int getCount()
	{
		return count;
	}

	public int getAnimationId()
	{
		return animationId;
	}
}
