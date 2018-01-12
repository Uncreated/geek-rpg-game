package com.geek.rpg.game.Skirmish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class InputHandler
{
	public static int getX()
	{
		return Gdx.input.getX();
	}

	public static int getY()
	{
		return 720 - Gdx.input.getY();
	}

	public static boolean checkClickInRect(Rectangle rectangle)
	{
		return Gdx.input.justTouched() && rectangle.contains(getX(), getY());
	}
}
