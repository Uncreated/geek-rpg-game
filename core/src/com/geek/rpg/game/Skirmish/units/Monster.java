package com.geek.rpg.game.Skirmish.units;

import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.factory.systems.AbilitySystem;
import com.geek.rpg.game.factory.templates.Ability;
import com.geek.rpg.game.factory.templates.Effect;
import com.geek.rpg.game.factory.templates.EffectTemplate;

import java.util.ArrayList;
import java.util.Collections;

public class Monster extends Unit
{
	public Monster(Vector2 center, int id, int level)
	{
		super(center, id, level);
	}

	@Override
	public boolean yourMove()
	{
		//По очереди выбрать каждую цель и получить для неё возможные абилки
		//Каждый вариант проверить на предмет логичности (хилить фул хп - бред) и исключить ненужные
		//Выбрать наиболее привлекательную из оставшихся (по минимальным хп целей) и применить

		generateMove();

		return true;
	}

	public void generateMove()
	{
		ArrayList<Move> moves = new ArrayList<Move>();

		generateMoves(moves, this.getFriendlyTeam());
		generateMoves(moves, this.getEnemyTeam());

		Collections.sort(moves);

		Ability.getSystem().cast(moves.get(0).ability, this, moves.get(0).target);
	}

	private void generateMoves(ArrayList<Move> moves, Team team)
	{
		for (Unit unit : team.getUnits())
			for (Ability ability : Ability.getSystem().getAvailableAbilities(this, unit))
				if (AbilitySystem.canCastSpell(ability, this))
					moves.add(new Move(unit, ability));
	}

	private class Move implements Comparable<Move>
	{
		private Unit target;
		Ability ability;

		private Move(Unit target, Ability ability)
		{
			this.target = target;
			this.ability = ability;
		}

		private int getPriority()
		{
			return target.getStats().getCurHP();
		}

		@Override
		public int compareTo(Move o)
		{
			return getPriority() - o.getPriority();
		}
	}
}
