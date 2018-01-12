package com.geek.rpg.game.Skirmish.units;

import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Skirmish.SkirmishLogic;
import com.geek.rpg.game.factory.templates.ProgressTemplate;

public class Hero extends Unit
{
	private ProgressTemplate.ProgressHero progressHero;

	public Hero(Vector2 center, ProgressTemplate.ProgressHero progressHero)
	{
		super(center, progressHero.getId(), progressHero.getLevel());

		this.progressHero = progressHero;
	}

	@Override
	public boolean yourMove()
	{
		if (SkirmishLogic.get().isAbilityBarClick())
			return true;

		if (isClickTeam(getFriendlyTeam()))
			return true;

		if (isClickTeam(getEnemyTeam()))
			return true;

		return false;
	}

	private boolean isClickTeam(Team team)
	{
		for (Unit unit : team.getUnits())
			if (unit.isClick())
			{
				SkirmishLogic.get().showAbilityBar(this, unit);
				return true;
			}

		return false;
	}

	@Override
	public String getInfoText()
	{
		return (progressHero.isMax() ? ("Max Level") : (
				"XP:" + progressHero.getCurExp() + "/" + progressHero.getMaxExp()
						/*+ " (" + progressHero.getExpPercent() + "%)"*/));
	}
}