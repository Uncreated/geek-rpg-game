package com.geek.rpg.game.Skirmish;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geek.rpg.game.Assets;

public class Background
{
	private Texture texture;

	public Background()
	{
		this.texture = Assets.getInstance().getTexture("background.png");
	}

	public void render(SpriteBatch batch)
	{
		batch.setColor(1f, 1f, 1f, 1f);
		batch.draw(texture, 0, 0);
	}
}
