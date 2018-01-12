package com.geek.rpg.game.Skirmish.units.frames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.primitives.GameObject;

import java.util.LinkedList;

public class FlyingText extends GameObject
{
	private static final int WIDTH = 90;
	private static final int HEIGHT = 20;

	private float lifeTime = 2.0f;
	private SimpleText simpleText;

	public FlyingText(Unit unit, String text, Color color)
	{
		super(unit.getRectangle().getCenter(new Vector2()).add(0, unit.getHeight()), WIDTH, HEIGHT, 5);
		this.simpleText = new SimpleText(text, color);

		checkCollision(unit);
	}

	private void checkCollision(Unit unit)
	{
		LinkedList<GameObject> subObjects = unit.getSubObjects();
		FlyingText last = this;
		for (int i = subObjects.size() - 1; i >= 0; i--)//subObjects.size() - 1 == this
		{
			if (subObjects.get(i) instanceof FlyingText)
			{
				FlyingText flyingText = (FlyingText) subObjects.get(i);
				float difY = flyingText.getRectangle().y - last.getRectangle().y;
				difY -= HEIGHT;
				if (difY < 0)
					flyingText.getRectangle().y += -difY;
				last = flyingText;
			}
		}
	}

	@Override
	public void onRender(SpriteBatch batch)
	{
		simpleText.setA(lifeTime > 0.5f ? 1.0f : lifeTime / 0.5f);
		simpleText.render(batch, getRectangle());
	}

	public void update(float dt)
	{
		getRectangle().y += 60 * dt;
		lifeTime -= dt;
		if (lifeTime <= 0)
			getParent().getSubObjects().remove(this);
	}
}
