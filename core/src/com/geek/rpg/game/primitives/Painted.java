package com.geek.rpg.game.primitives;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.factory.templates.Animation;

public class Painted
{
	private Animation animation;
	private boolean flip = false;

	private Runnable onActionEndEvent;

	public Painted(int id)
	{
		this.animation = new Animation(id);
	}

	public void setFlip(boolean flip)
	{
		this.flip = flip;
	}

	public void setAnimation(int id)
	{
		this.animation = new Animation(id);
	}

	public Animation getAnimation()
	{
		return animation;
	}

	public void setAction(int action)
	{
		this.animation = new Animation(animation.getAction(action));
	}

	public void update(float dt)
	{
		animation.update(dt);
		if (!animation.isActive())
			onAnimationEnd();
	}

	public void render(SpriteBatch batch, Vector2 center)
	{
		animation.render(batch, center, flip);
	}

	private void onAnimationEnd()
	{
		if (onActionEndEvent != null)
			onActionEndEvent.run();
	}

	public void setOnActionEndEvent(Runnable onActionEndEvent)
	{
		this.onActionEndEvent = onActionEndEvent;
	}
}
