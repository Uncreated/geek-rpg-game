package com.geek.rpg.game.Skirmish.units.frames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.geek.rpg.game.Assets;

public class SimpleText
{
	private BitmapFont font;
	private String text;
	private Color color;

	public SimpleText()
	{
		this("", Color.WHITE);
	}

	public SimpleText(String text, Color color)
	{
		this.font = Assets.getInstance().getFont("font.fnt");
		this.text = text;
		this.color = new Color(color);
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public void render(SpriteBatch batch, Rectangle rectangle)
	{
		render(batch, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public void render(SpriteBatch batch, float x, float y, float width, float height)
	{
		font.setColor(color);
		font.draw(batch, text, x + 1, y + height - 1, width, Align.center, false);
	}

	public void setA(float a)
	{
		color.a = a;
	}
}
