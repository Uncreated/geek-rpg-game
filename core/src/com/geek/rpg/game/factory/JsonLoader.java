package com.geek.rpg.game.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class JsonLoader<T extends JsonTemplate>
{
	public static Gson gson;
	protected T[] generatedArray;
	private String fileName;

	public JsonLoader(String fileName, Class<T[]> tClass)
	{
		this.fileName = fileName;
		try
		{
			if (gson == null)
				gson = new GsonBuilder().setPrettyPrinting().create();
			File file = new File("files/" + fileName + ".json");
			generatedArray = gson.fromJson(new FileReader(file), tClass);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public T get(int id)
	{
		for (T obj : generatedArray)
			if (obj.id == id)
				return obj;

		if (id != 0)
			return get(0);
		return null;
	}

	public T[] getGeneratedArray()
	{
		return generatedArray;
	}

	public void save()
	{
		try
		{
			FileWriter fileWriter = new FileWriter("files/" + fileName + ".json");
			String json = gson.toJson(generatedArray);
			fileWriter.write(json);
			fileWriter.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
