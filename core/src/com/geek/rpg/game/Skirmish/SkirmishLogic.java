package com.geek.rpg.game.Skirmish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.App;
import com.geek.rpg.game.ScreenManager;
import com.geek.rpg.game.Skirmish.units.Hero;
import com.geek.rpg.game.Skirmish.units.Monster;
import com.geek.rpg.game.Skirmish.units.Team;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.Skirmish.units.frames.AbilityBar;
import com.geek.rpg.game.factory.templates.LevelTemplate;
import com.geek.rpg.game.factory.templates.ProgressTemplate;

import java.util.ArrayList;

public class SkirmishLogic
{
	private static SkirmishLogic skirmishLogic = null;

	public static SkirmishLogic get()
	{
		return skirmishLogic;
	}

	private SequenceNode sequenceNode;
	private Team leftTeam;
	private Team rightTeam;
	private Unit[] units;

	private boolean isNextTurn = false;
	private boolean isEnded = false;

	private AbilityBar abilityBar;

	private int totalExp = 0;

	private float lockTimer = 0;

	SkirmishLogic()
	{
		skirmishLogic = this;

		Vector2 center = new Vector2(ScreenManager.WIDTH / 2, ScreenManager.HEIGHT * 0.4f);

		Vector2 tmp = new Vector2(0, 0);

		leftTeam = new Team(center, true);
		for (ProgressTemplate.ProgressHero hero : App.Selections.getHeroes())
			leftTeam.addUnit(new Hero(tmp, hero));

		rightTeam = new Team(center, false);
		for (LevelTemplate.Enemy enemy : App.Selections.getLevel().getEnemies())
			rightTeam.addUnit(new Monster(tmp, enemy.getId(), enemy.getLevel()));

		for (Unit unit : leftTeam.getUnits())
			unit.setTeams(leftTeam, rightTeam);

		for (Unit unit : rightTeam.getUnits())
			unit.setTeams(rightTeam, leftTeam);

		sequenceNode = new SequenceNode(leftTeam, rightTeam);

		units = new Unit[leftTeam.size() + rightTeam.size()];
		int i = 0;
		for (Unit unit : leftTeam.getUnits())
			units[i++] = unit;

		for (Unit unit : rightTeam.getUnits())
			units[i++] = unit;

		newTurn();
	}

	public void endTurn()
	{
		lockTimer = 1.0f;
		sequenceNode.cur.setMoving(false);
		hideAbilityBar();
		isNextTurn = true;
	}

	private void nextTurn()
	{
		if (isNextTurn && lockTimer <= 0)
		{
			if (!isTeamAlive(leftTeam) || !isTeamAlive(rightTeam))
			{
				endSkirmish();
				return;
			}

			sequenceNode = sequenceNode.getNextAvailable();
			newTurn();
		}
	}

	private static boolean isTeamAlive(Team team)
	{
		for (Unit unit : team.getUnits())
			if (unit.isAlive())
				return true;

		return false;
	}

	private void endSkirmish()
	{
		isNextTurn = false;
		isEnded = true;

		int exp = 0;
		for (Unit unit : rightTeam.getUnits())
			if (!unit.isAlive())
				exp += unit.getLevel() * 25;

		if (isLeftWinner())
			App.Selections.getLevel().complete();
		else
			exp /= 5;

		totalExp = exp;

		exp /= leftTeam.size();
		ArrayList<ProgressTemplate.ProgressHero> progressHeroes = App.Selections.getHeroes();
		for (int i = 0; i < leftTeam.size(); i++)
			progressHeroes.get(i).addExp(exp);

		App.Selections.getProgressCell().save();
	}

	private void newTurn()
	{
		sequenceNode.cur.newTurn();
		sequenceNode.cur.setMoving(true);
		isNextTurn = false;
		if (sequenceNode.cur instanceof Monster)
			lockTimer = 1.0f;

		if (!sequenceNode.cur.isAlive())
			endTurn();
	}

	public void update(float dt)
	{
		//dt *= 0.2f;
		for (Unit unit : units)
			unit.update(dt);

		if (abilityBar != null)
			abilityBar.update(dt);

		lockTimer -= dt;

		if (lockTimer < 0.0f)
		{
			if (isNextTurn)
				nextTurn();
			else
			{
				boolean move = sequenceNode.cur.yourMove();

				if (sequenceNode.cur instanceof Hero && !move && Gdx.input.justTouched())
					hideAbilityBar();//Значит клик в случайное место
			}
		}
	}

	public void showAbilityBar(Unit initiator, Unit target)
	{
		hideAbilityBar();
		abilityBar = new AbilityBar(initiator, target);
		target.setSelected(true);
	}

	private void hideAbilityBar()
	{
		if (abilityBar != null)
		{
			abilityBar.dispose();
			abilityBar = null;
		}
	}

	public boolean isAbilityBarClick()
	{
		return abilityBar == null ? false : abilityBar.isClick();
	}

	AbilityBar getAbilityBar()
	{
		return abilityBar;
	}

	public Unit[] getUnits()
	{
		return units;
	}

	boolean isEnded()
	{
		return isEnded;
	}

	boolean isLeftWinner()
	{
		return isTeamAlive(leftTeam);
	}

	int getTotalExp()
	{
		return totalExp;
	}

	private class SequenceNode
	{
		Unit cur;
		SequenceNode next;

		SequenceNode(Team left, Team right)
		{
			this.cur = left.getUnit(0);
			SequenceNode last = this;
			for (int i = 1; i < left.size(); i++)
			{
				last.next = new SequenceNode(left.getUnit(i));
				last = last.next;
			}
			for (int i = 0; i < right.size(); i++)
			{
				last.next = new SequenceNode(right.getUnit(i));
				last = last.next;
			}
			last.next = this;
		}

		SequenceNode getNextAvailable()
		{
			SequenceNode node = next;
			while (!node.cur.isAlive())
				node = node.next;

			next = node;
			return node;
		}

		SequenceNode(Unit cur)
		{
			this.cur = cur;
		}
	}
}
