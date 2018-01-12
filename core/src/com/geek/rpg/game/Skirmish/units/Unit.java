package com.geek.rpg.game.Skirmish.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Skirmish.units.frames.FlyingText;
import com.geek.rpg.game.Skirmish.units.frames.InfoBar;
import com.geek.rpg.game.factory.templates.*;
import com.geek.rpg.game.primitives.GameObject;
import com.geek.rpg.game.primitives.Painted;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Unit extends GameObject
{
	public static final int WARRIOR = 0;
	public static final int MAGE = 1;
	public static final int PALADIN = 2;
	public static final int PRIEST = 3;
	public static final int WARLOCK = 4;
	public static final int ROGUE = 5;

	private static final int UNIT_WIDTH = 90;
	private static final int UNIT_HEIGHT = 150;
	private static final int SELECTOR_HEIGHT = 30;

	private UnitTemplate template;

	private int level;
	private UnitStats stats;

	private ArrayList<Effect> effects = new ArrayList<Effect>();
	private ArrayList<Ability> abilities = new ArrayList<Ability>();

	private Team friendlyTeam;
	private Team enemyTeam;

	private boolean moving = false;
	private boolean selected = false;

	private GameObject selector;
	private InfoBar infoBar;

	public int getLevel()
	{
		return level;
	}

	public boolean isAlive()
	{
		return stats.getCurHP() > 0;
	}

	private LinkedList<PrepareBattleText> prepareList = new LinkedList<PrepareBattleText>();

	public Unit(Vector2 center, int id, int level)
	{
		super(center, UNIT_WIDTH, UNIT_HEIGHT, 3);
		template = UnitTemplate.jsonLoader.get(id);
		this.stats = template.getBaseStats().scale(level);
		this.level = level;

		this.setPainted(new Painted(template.getAnimationId()));
		this.getPainted().setOnActionEndEvent(new Runnable()
		{
			@Override
			public void run()
			{
				getPainted().setAction(stats.getCurHP() > 0 ? Animation.ANIMATION_SIMPLE : Animation.ANIMATION_DEAD);
			}
		});

		Vector2 pivot = new Vector2(center);

		pivot.y -= UNIT_HEIGHT / 2;
		pivot.y += SELECTOR_HEIGHT / 2;

		selector = new GameObject(pivot, UNIT_WIDTH, SELECTOR_HEIGHT, 2);
		selector.setPainted(new Painted(1002));
		addSubObject(selector);
		updateSelector();

		GameObject shadow = new GameObject(pivot, UNIT_WIDTH, SELECTOR_HEIGHT, 2);
		shadow.setPainted(new Painted(1001));
		addSubObject(shadow);

		infoBar = new InfoBar(this);
		addSubObject(infoBar);

		addAbilities(template.getAbilities());
	}

	public void setTeams(Team friendlyTeam, Team enemyTeam)
	{
		this.friendlyTeam = friendlyTeam;
		this.enemyTeam = enemyTeam;
	}

	public Team getFriendlyTeam()
	{
		return friendlyTeam;
	}

	public Team getEnemyTeam()
	{
		return enemyTeam;
	}

	public void healthEffects(EffectTemplate effectTemplate, int power)
	{
		int hp = stats.getCurHP() + power;
		if (power < 0)
		{
			stats.setCurHP(hp < 0 ? 0 : hp);
			getPainted().setAction(Animation.ANIMATION_INPUT_DAMAGE);
		} else
			stats.setCurHP(hp > stats.getMaxHP() ? stats.getMaxHP() : hp);

		addBattleText(effectTemplate, power);
	}

	private void addBattleText(EffectTemplate effectTemplate, int power)
	{
		for (PrepareBattleText prepareBattleText : prepareList)
			if (prepareBattleText.isUnited(effectTemplate, power))
				return;

		prepareList.add(new PrepareBattleText(effectTemplate, power));
	}

	public void newTurn()
	{
		int szi = effects.size();
		for (int i = szi - 1; i >= 0; i--)
			tickEffect(effects.get(i));

		for (Ability ability : abilities)
			if (ability.getCurCooldown() > 0)
				ability.setCurCooldown(ability.getCurCooldown() - 1);
	}

	private void tickEffect(Effect effect)
	{
		effect.tick();
		if (effect.isEnded())
			effects.remove(effect);
	}

	public UnitStats getStats()
	{
		return stats;
	}

	private void addAbilities(Integer... abilitiesId)
	{
		for (Integer id : abilitiesId)
			abilities.add(new Ability(id));
	}

	public abstract boolean yourMove();

	@Override
	protected void onRender(SpriteBatch batch)
	{
		for (PrepareBattleText prepareBattleText : prepareList)
			prepareBattleText.makeSimpleText(this);
		prepareList.clear();
		super.onRender(batch);
	}

	public ArrayList<Ability> getAbilities()
	{
		return abilities;
	}

	public void addEffect(Unit initiator, Unit target, int effectId)
	{
		if (effectId == 0)
			return;

		Effect effect = new Effect(effectId);

		effects.add(effect);

		effect.setUnits(initiator, target);

		if (effect.isInstant())
			tickEffect(effect);
	}

	public boolean isEnemy(Unit unit)
	{
		return getFriendlyTeam().isLeft() != unit.getFriendlyTeam().isLeft();
	}

	public void setMoving(boolean moving)
	{
		this.moving = moving;
		updateSelector();
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
		updateSelector();
	}

	private void updateSelector()
	{
		if (selected)
			selector.getPainted().getAnimation().setColor(1.0f, 0.0f, 0.0f, 1.0f);
		else if (moving)
			selector.getPainted().getAnimation().setColor(1.0f, 1.0f, 0.0f, 1.0f);
		else
			selector.getPainted().getAnimation().setColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public String getInfoText()
	{
		return template.getName();
	}

	public int getUnitClass()
	{
		return template.getUnitClass();
	}

	public InfoBar getInfoBar()
	{
		return infoBar;
	}

	@Override
	public boolean isClick()
	{
		return isAlive() ? super.isClick() : false;
	}

	class PrepareBattleText
	{
		EffectTemplate effectTemplate;
		int power;
		int stacks;

		PrepareBattleText(EffectTemplate effectTemplate, int power)
		{
			this.effectTemplate = effectTemplate;
			this.power = power;
			this.stacks = 1;
		}

		void makeSimpleText(Unit unit)
		{
			String text = Math.abs(power) + "";// + "(" + effectTemplate.getName() + ")";
			if (stacks > 1)
				text += "(x" + stacks + ")";
			unit.addSubObject(new FlyingText(unit, text, power > 0 ? Color.GREEN : Color.RED));
		}

		public boolean isUnited(EffectTemplate effectTemplate, int power)
		{
			if (this.effectTemplate.getId() == effectTemplate.getId())
			{
				this.power += power;
				stacks++;
				return true;
			}
			return false;
		}
	}
}
