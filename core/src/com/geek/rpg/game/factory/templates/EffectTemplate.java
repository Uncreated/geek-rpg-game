package com.geek.rpg.game.factory.templates;

import com.geek.rpg.game.factory.JsonLoader;
import com.geek.rpg.game.factory.JsonTemplate;

public class EffectTemplate extends JsonTemplate
{
	public static final JsonLoader<EffectTemplate> jsonLoader = new JsonLoader<EffectTemplate>("effects", EffectTemplate[].class);

	private String name;
	private boolean instant = true;
	private boolean damaging = true;
	private int rounds = 1;
	private Dependencies incDependencies;//Берутся у инициатора
	private Dependencies lowDependencies;//Берутся у цели
	private int animationId = 0;

	public String getName()
	{
		return name;
	}

	public boolean isInstant()
	{
		return instant;
	}

	public boolean isDamaging()
	{
		return damaging;
	}

	public int getRounds()
	{
		return rounds;
	}

	public Dependencies getIncDependencies()
	{
		return incDependencies;
	}

	public Dependencies getLowDependencies()
	{
		return lowDependencies;
	}

	public int getAnimationId()
	{
		return animationId;
	}

	public static EffectTemplate get(int id)
	{
		return EffectTemplate.jsonLoader.get(id);
	}

	public class Dependencies
	{
		private float stamina = 0.0f;
		private float strength = 0.0f;
		private float agility = 0.0f;
		private float intellect = 0.0f;
		private float armor = 0.0f;

		public float getStamina()
		{
			return stamina;
		}

		public float getStrength()
		{
			return strength;
		}

		public float getAgility()
		{
			return agility;
		}

		public float getIntellect()
		{
			return intellect;
		}

		public float getArmor()
		{
			return armor;
		}
	}
}
