package com.geek.rpg.game.factory;

import java.util.ArrayList;

public abstract class JsonTemplate
{
	protected int id;

	/*public T copy()
	{
		return (T) JsonLoader.gson.fromJson(JsonLoader.gson.toJson(this), getClass());
	}*/

	public int getId()
	{
		return id;
	}
}
