package com.geek.rpg.game.Skirmish.units;

public class UnitStats
{
	//main baseStats
	private int stamina;
	private int strength;
	private int agility;
	private int intellect;

	//secondary baseStats
	private int armor;
	//private int fireResistance;
	//private int iceResistance;

	private transient int curHP;
	private transient int curMana;

	public UnitStats(int stamina, int strength, int agility, int intellect, int armor/*, int fireResistance, int iceResistance*/)
	{
		this.stamina = stamina;
		this.strength = strength;
		this.agility = agility;
		this.intellect = intellect;
		this.armor = armor;
		//this.fireResistance = fireResistance;
		//this.iceResistance = iceResistance;
		this.curHP = getMaxHP();
		this.curMana = getMaxMana();
	}

	public UnitStats scale(int level)
	{
		float k = 1.0f;
		for (int i = 1; i < level; i++)
			k *= 1.1f;

		return new UnitStats((int) (stamina * k),
				(int) (strength * k),
				(int) (agility * k),
				(int) (intellect * k),
				(int) (armor * k));
	}

	public int getMaxHP()
	{
		return 100 + stamina * 10;
	}

	public int getMaxMana()
	{
		return intellect * 10;
	}

	public void setCurHP(int curHP)
	{
		this.curHP = curHP;
	}

	public int getCurHP()
	{
		return curHP;
	}

	public int getCurMana()
	{
		return curMana;
	}

	public void setCurMana(int curMana)
	{
		this.curMana = curMana;
	}

	public int getStamina()
	{
		return stamina;
	}

	public int getStrength()
	{
		return strength;
	}

	public int getAgility()
	{
		return agility;
	}

	public int getIntellect()
	{
		return intellect;
	}

	public int getArmor()
	{
		return armor;
	}
}
