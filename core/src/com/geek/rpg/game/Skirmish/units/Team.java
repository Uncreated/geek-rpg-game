package com.geek.rpg.game.Skirmish.units;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Team
{
	private Vector2[] cells = new Vector2[]{
			new Vector2(150, 0),
			new Vector2(225, 90),
			new Vector2(330, -105),
			new Vector2(455, 40),
			new Vector2(535, -90)
	};
	private ArrayList<Unit> units;

	private boolean left;

	public Team(Vector2 center, boolean left)
	{
		this.left = left;
		this.units = new ArrayList<Unit>();
		for (int i = 0; i < cells.length; i++)
		{
			if (left)
				cells[i].x = -cells[i].x;
			cells[i].add(center);
		}
	}

	public void addUnit(Unit unit)
	{
		unit.setCenter(cells[units.size()]);
		unit.setFlip(!left);
		units.add(unit);
	}

	public Unit getUnit(int i)
	{
		return units.get(i);
	}

	public int size()
	{
		return units.size();
	}

	public boolean isAlive()
	{
		for (Unit unit : units)
			if (unit.isAlive())
				return true;
		return false;
	}

	public ArrayList<Unit> getUnits()
	{
		return units;
	}

	public boolean isLeft()
	{
		return left;
	}
}
