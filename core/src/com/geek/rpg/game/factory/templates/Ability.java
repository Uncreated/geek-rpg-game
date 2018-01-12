package com.geek.rpg.game.factory.templates;

import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.factory.systems.AbilitySystem;

public class Ability
{
	private static AbilitySystem system = new AbilitySystem();

	public static AbilitySystem getSystem()
	{
		return system;
	}

	private AbilityTemplate template;

	private int curCooldown = 0;

	public Ability(int id)
	{
		template = AbilityTemplate.jsonLoader.get(id);
	}

	public int getAnimationId()
	{
		return template.getAnimationId();
	}

	public boolean isForEnemy()
	{
		return template.isEnemy();
	}

	public boolean isForFriend()
	{
		return template.isFriend();
	}

	public boolean isForSelf()
	{
		return template.isSelf();
	}

	public int getInitiatorEffectId()
	{
		return template.getInitiatorEffectId();
	}

	public int getTargetEffectId()
	{
		return template.getTargetEffectId();
	}

	public int getCurCooldown()
	{
		return curCooldown;
	}

	public void setCurCooldown(int curCooldown)
	{
		this.curCooldown = curCooldown;
	}

	public int getCooldown()
	{
		return template.getCooldown();
	}

	public int getCost()
	{
		return template.getCost();
	}

	public boolean isAvailable(Unit unit)
	{
		return unit.getStats().getCurMana() >= template.getCost() && curCooldown == 0;
	}

	public int getMinLevel()
	{
		return template.getMinLevel();
	}

	public int getType()
	{
		return template.getType();
	}

	public int getCount()
	{
		return template.getCount();
	}
}
