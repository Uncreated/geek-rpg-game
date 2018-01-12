package com.geek.rpg.game.Skirmish.units.frames;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.primitives.GameObject;

public class InfoBar extends GameObject
{
	private static final int WIDTH = 90;
	private static final int LVL_WIDTH = 40;
	private static final int ROW_HEIGHT = 20;

	private Unit unit;
	private Texture textureBar;
	private SimpleText simpleText;

	public InfoBar(Unit unit)
	{
		super(unit.getCenter().add(0, (unit.getHeight() + ROW_HEIGHT) / 2),
				WIDTH,
				unit.getStats().getMaxMana() > 0 ? (3 * ROW_HEIGHT - 4) : (2 * ROW_HEIGHT - 2),
				4);
		this.unit = unit;

		Pixmap pixmap = new Pixmap(WIDTH, ROW_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(0, 0, 0, 1);
		pixmap.fill();
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillRectangle(2, 2, WIDTH - 4, ROW_HEIGHT - 4);

		this.textureBar = new Texture(pixmap);
		simpleText = new SimpleText();
	}

	@Override
	public void onRender(SpriteBatch batch)
	{
		float x = getRectangle().x;
		float y = getRectangle().y;

		//mana
		if (unit.getStats().getMaxMana() > 0)
		{
			batch.setColor(0, 0, 0.5f, 1);
			batch.draw(textureBar, getRectangle().x, y);

			simpleText.setText(String.valueOf(unit.getStats().getCurMana()));
			simpleText.render(batch, getRectangle().x, y, WIDTH, ROW_HEIGHT);
			y += ROW_HEIGHT - 2;
		}

		//hp
		batch.setColor(0.5f, 0.0f, 0.0f, 1);
		batch.draw(textureBar, x, y);
		batch.setColor(0, 1, 0, 1);
		batch.draw(textureBar, x, y, 0, 0, (int) ((float) unit.getStats().getCurHP() / (float) unit.getStats().getMaxHP() * textureBar.getWidth()), ROW_HEIGHT);

		simpleText.setText(String.valueOf(unit.getStats().getCurHP()));
		simpleText.render(batch, getRectangle().x, y, WIDTH, ROW_HEIGHT);
		y += ROW_HEIGHT - 2;

		//lvl
		batch.setColor(0.7f, 0.7f, 0, 1);
		batch.draw(textureBar, x, y, 0, 0, LVL_WIDTH / 2, ROW_HEIGHT);
		batch.draw(textureBar, x + LVL_WIDTH / 2, y, WIDTH - LVL_WIDTH / 2, 0, LVL_WIDTH / 2, ROW_HEIGHT);

		simpleText.setText(String.valueOf(unit.getLevel()));
		simpleText.render(batch, getRectangle().x, y, LVL_WIDTH, ROW_HEIGHT);
	}
}
