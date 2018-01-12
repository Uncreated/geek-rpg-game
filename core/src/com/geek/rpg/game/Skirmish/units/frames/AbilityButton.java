package com.geek.rpg.game.Skirmish.units.frames;

import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.factory.systems.AbilitySystem;
import com.geek.rpg.game.factory.templates.Ability;
import com.geek.rpg.game.primitives.GameObject;
import com.geek.rpg.game.primitives.Painted;

public class AbilityButton extends GameObject
{
	private static final int WIDTH = 80;
	private static final int HEIGHT = 80;

	private Ability ability;

	private Unit initiator;
	private Unit target;


	public AbilityButton(Vector2 center, Ability ability, Unit initiator, Unit target)
	{
		super(center, WIDTH, HEIGHT, 9);

		this.ability = ability;
		this.initiator = initiator;
		this.target = target;

		setPainted(new Painted(ability.getAnimationId()));

		if (!ability.isAvailable(initiator))
			getPainted().getAnimation().setColor(0.25f, 0.25f, 0.25f, 1.0f);
	}

	@Override
	public boolean isClick()
	{
		if (super.isClick() && AbilitySystem.canCastSpell(ability, initiator))
		{
			Ability.getSystem().cast(ability, initiator, target);
			return true;
		}
		return false;
	}
}
