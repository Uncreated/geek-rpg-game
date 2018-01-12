package com.geek.rpg.game.Skirmish.units.frames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Assets;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.primitives.GameObject;
import com.geek.rpg.game.primitives.Painted;

public class InfoBar extends GameObject
{
	private static final int WIDTH = 90;
	private static final int LVL_WIDTH = 40;
	private static final int ROW_HEIGHT = 20;

	private Unit unit;
	private SimpleText simpleText;
	private Texture fillingTexture;
	private GameObject lvlRow;
	private GameObject hpRow;
	private GameObject manaRow;

	public InfoBar(Unit unit)
	{
		super(unit.getCenter().add(0, (unit.getHeight() + ROW_HEIGHT) / 2),
				WIDTH,
				ROW_HEIGHT,
				5);
		this.unit = unit;

		//mana row
		if (unit.getStats().getMaxMana() > 0)
		{
			manaRow = new GameObject(getCenter().sub(0, ROW_HEIGHT), WIDTH, ROW_HEIGHT);
			//manaRow.setPainted(new Painted(1003));
			addSubObject(manaRow);
		} else
			getRectangle().y -= ROW_HEIGHT;

		//level row
		lvlRow = new GameObject(getCenter().add(0, ROW_HEIGHT), WIDTH, ROW_HEIGHT, 4);
		lvlRow.setPainted(new Painted(1004 + unit.getUnitClass()));
		addSubObject(lvlRow);

		//hp row
		hpRow = new GameObject(getCenter(), WIDTH, ROW_HEIGHT);
		//hpRow.setPainted(new Painted(1003));
		addSubObject(hpRow);

		getRectangle().height += ROW_HEIGHT;
		if (manaRow != null)
			getRectangle().height += ROW_HEIGHT;
		fillingTexture = Assets.getInstance().getTexture("infoBar/filling.png");

		simpleText = new SimpleText();
	}

	@Override
	public void setFlip(boolean flip)
	{

	}

	@Override
	public void update(float dt)
	{
		if (unit.isAlive())
			super.update(dt);
		else
			unit.getSubObjects().remove(this);
	}

	@Override
	public void onRender(SpriteBatch batch)
	{
		//lvl
		batch.setColor(0.7f, 0.7f, 0, 1);
		batch.draw(fillingTexture,
				lvlRow.getRectangle().x, lvlRow.getRectangle().y,
				0, 0,
				LVL_WIDTH, ROW_HEIGHT);

		simpleText.setText(String.valueOf(unit.getLevel()));
		simpleText.render(batch, lvlRow.getRectangle().x, lvlRow.getRectangle().y, LVL_WIDTH, ROW_HEIGHT);

		//hp
		batch.setColor(0, 0.5f, 0, 1);
		batch.draw(fillingTexture,
				hpRow.getRectangle().x, hpRow.getRectangle().y,
				0, 0,
				(int) ((float) unit.getStats().getCurHP() / (float) unit.getStats().getMaxHP() * WIDTH), ROW_HEIGHT);

		simpleText.setText(String.valueOf(unit.getStats().getCurHP()));
		simpleText.render(batch, hpRow.getRectangle().x, hpRow.getRectangle().y, WIDTH, ROW_HEIGHT);

		//mana
		if (manaRow != null)
		{
			batch.setColor(0, 0, 0.5f, 1);
			batch.draw(fillingTexture,
					manaRow.getRectangle().x, manaRow.getRectangle().y,
					0, 0,
					(int) ((float) unit.getStats().getCurMana() / (float) unit.getStats().getMaxMana() * WIDTH), ROW_HEIGHT);

			simpleText.setText(String.valueOf(unit.getStats().getCurMana()));
			simpleText.render(batch, manaRow.getRectangle().x, manaRow.getRectangle().y, WIDTH, ROW_HEIGHT);
		}
	}

	Vector2 getTop()
	{
		return getRectangle().getPosition(new Vector2()).add(WIDTH / 2, getRectangle().height);
	}
}
