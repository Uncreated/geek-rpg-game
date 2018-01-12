package com.geek.rpg.game.Skirmish.units.frames;

import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.factory.templates.Ability;
import com.geek.rpg.game.primitives.GameObject;

import java.util.ArrayList;

public class AbilityBar extends GameObject
{
	private static final float WIDTH = (40.0f + 92.191f) * 2.0f;

	private Unit target;

	public AbilityBar(Unit initiator, Unit target)
	{
		super(target.getCenter(), 0, 0);
		this.target = target;
		ArrayList<Ability> abilities = Ability.getSystem().getAvailableAbilities(initiator, target);

		Vector2 targetCenter = target.getCenter();
		Vector2 centerRoundButton = new Vector2(0, 92.191f);//Считал на бумажке, поднять нужно на столько

		for (int i = 0; i < abilities.size() && i < 7; i++)
		{
			Vector2 curPosition = centerRoundButton.cpy().add(targetCenter);
			addSubObject(new AbilityButton(curPosition, abilities.get(i), initiator, target));
			centerRoundButton.rotate(-51.428f);//А вертеть нужно на столько
		}
	}

	@Override
	protected void onClick()
	{
		for (GameObject abilityButton : getSubObjects())
			if (abilityButton.isClick())
				return;
	}

	public void dispose()
	{
		target.setSelected(false);
	}
}
