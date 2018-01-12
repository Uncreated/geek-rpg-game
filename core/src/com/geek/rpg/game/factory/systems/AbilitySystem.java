package com.geek.rpg.game.factory.systems;

import com.badlogic.gdx.math.MathUtils;
import com.geek.rpg.game.Skirmish.SkirmishLogic;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.factory.templates.Ability;

import java.util.ArrayList;

public class AbilitySystem
{
	private static boolean canApplySpell(Ability ability, Unit initiator, Unit target)
	{
		if (ability.getMinLevel() > initiator.getLevel())
			return false;

		if (initiator.isEnemy(target))
			return ability.isForEnemy();
		else if (initiator == target)
			return ability.isForSelf();
		else
			return ability.isForFriend();
	}

	public static boolean canCastSpell(Ability ability, Unit initiator)
	{
		return initiator.getStats().getCurMana() >= ability.getCost() &&
				ability.getCurCooldown() < 1;
	}

	public static void cast(Ability ability, Unit initiator, Unit target)
	{
		initiator.addEffect(initiator, initiator, ability.getInitiatorEffectId());
		target.addEffect(initiator, target, ability.getTargetEffectId());

		if (ability.getCount() > 1)
		{
			ArrayList<Unit> units = new ArrayList<Unit>(target.getFriendlyTeam().getUnits());
			units.remove(target);
			for (int i = ability.getCount() - 1; i > 0 && units.size() > 0; i--)
			{
				Unit unit = units.get(MathUtils.random(units.size() - 1));
				if (unit.isAlive())
					unit.addEffect(initiator, unit, ability.getTargetEffectId());
				else
					i++;
				units.remove(unit);
			}
		}

		initiator.getPainted().setAction(ability.getType());

		initiator.getStats().setCurMana(initiator.getStats().getCurMana() - ability.getCost());
		ability.setCurCooldown(ability.getCooldown());

		SkirmishLogic.get().endTurn();
	}

	public static ArrayList<Ability> getAvailableAbilities(Unit unit, Unit target)
	{
		ArrayList<Ability> abilities = new ArrayList<Ability>();

		if (target.isAlive())
			for (Ability ability : unit.getAbilities())
				if (canApplySpell(ability, unit, target))
					abilities.add(ability);

		return abilities;
	}
}
