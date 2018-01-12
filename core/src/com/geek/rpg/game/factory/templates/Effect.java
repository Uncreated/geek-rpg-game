package com.geek.rpg.game.factory.templates;

import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.Skirmish.units.UnitStats;
import com.geek.rpg.game.primitives.GameObject;
import com.geek.rpg.game.primitives.Painted;

public class Effect
{
	private EffectTemplate template;

	private Unit initiator;
	private Unit target;

	private int rounds;

	public Effect(int id)
	{
		template = EffectTemplate.jsonLoader.get(id);
		rounds = template.getRounds();
	}

	public void setUnits(Unit initiator, Unit target)
	{
		this.initiator = initiator;
		this.target = target;
	}

	public boolean isInstant()
	{
		return template.isInstant();
	}

	public boolean isEnded()
	{
		return rounds == 0;
	}

	public void tick()
	{
		rounds--;
		tickEffect();
	}

	public void tickEffect()
	{
		int power = getPower(template, initiator, target);

		if (power < 1)
			power = 1;

		target.healthEffects(template, template.isDamaging() ? -power : power);
		target.addSubObject(new Animation(this));
	}

	public static int getPower(EffectTemplate effectTemplate, Unit initiator, Unit target)
	{
		float power = 0.0f;
		float resistance = 1.0f;

		UnitStats initiatorStats = initiator.getStats();
		EffectTemplate.Dependencies incDep = effectTemplate.getIncDependencies();
		if (incDep != null)
		{
			power += (float) initiatorStats.getStamina() * incDep.getStamina();
			power += (float) initiatorStats.getStrength() * incDep.getStrength();
			power += (float) initiatorStats.getAgility() * incDep.getAgility();
			power += (float) initiatorStats.getIntellect() * incDep.getIntellect();
			power += (float) initiatorStats.getArmor() * incDep.getArmor();
		}

		UnitStats targetStats = target.getStats();
		EffectTemplate.Dependencies lowDep = effectTemplate.getLowDependencies();
		if (lowDep != null)
		{
			resistance *= (1.0f - (float) targetStats.getStamina() * lowDep.getStamina());
			resistance *= (1.0f - (float) targetStats.getStrength() * lowDep.getStrength());
			resistance *= (1.0f - (float) targetStats.getAgility() * lowDep.getAgility());
			resistance *= (1.0f - (float) targetStats.getIntellect() * lowDep.getIntellect());
			resistance *= (1.0f - (float) targetStats.getArmor() * lowDep.getArmor());
		}

		return (int) (power * resistance);
	}

	private class Animation extends GameObject
	{
		private Animation(Effect effect)
		{
			super(effect.target.getCenter(), 0, 0, 4);

			setPainted(new Painted(effect.template.getAnimationId()));
			getPainted().setOnActionEndEvent(new Runnable()
			{
				@Override
				public void run()
				{
					getParent().removeSubObject(Animation.this);
				}
			});
		}
	}
}
