package com.geek.rpg.game.primitives;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Skirmish.InputHandler;

import java.util.LinkedList;

public class GameObject
{
	public static final int LAYER_NONE = 0;
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 10;

	protected int layer;

	private Rectangle rectangle;

	private GameObject parent;
	private LinkedList<GameObject> subObjects = new LinkedList<GameObject>();

	private Painted painted;

	private Vector2 tmp = new Vector2();

	public GameObject(Vector2 center, float width, float height)
	{
		this(center, width, height, LAYER_NONE);
	}

	public GameObject(Vector2 center, float width, float height, int layer)
	{
		rectangle = new Rectangle().setSize(width, height).setCenter(center);
		this.layer = layer;
	}

	public void setPainted(Painted painted)
	{
		this.painted = painted;
	}

	public Painted getPainted()
	{
		return painted;
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}

	public void setFlip(boolean flip)
	{
		if (getPainted() != null)
			getPainted().setFlip(flip);
		for (GameObject gameObject : subObjects)
			gameObject.setFlip(flip);
	}

	public void setCenter(Vector2 center)
	{
		Vector2 vector = rectangle.getCenter(new Vector2());
		vector = center.sub(vector);
		move(vector);
	}

	public Vector2 getCenter()
	{
		return rectangle.getCenter(tmp);
	}

	public Rectangle getRectangle()
	{
		return rectangle;
	}

	public float getWidth()
	{
		return rectangle.width;
	}

	public float getHeight()
	{
		return rectangle.height;
	}

	private void move(Vector2 vector)
	{
		rectangle.x += vector.x;
		rectangle.y += vector.y;
		for (GameObject gameObject : subObjects)
			gameObject.move(vector);
	}

	public void update(float dt)
	{
		if (painted != null)
			painted.update(dt);
		for (int i = subObjects.size() - 1; i >= 0; i--)
			subObjects.get(i).update(dt);
	}

	public void render(SpriteBatch batch, int layer)
	{
		if (this.layer == layer)
			onRender(batch);
		for (GameObject gameObject : subObjects)
			gameObject.render(batch, layer);
	}

	protected void onRender(SpriteBatch batch)
	{
		if (painted != null)
			painted.render(batch, getCenter());
	}

	public void addSubObject(GameObject gameObject)
	{
		gameObject.parent = this;
		subObjects.add(gameObject);
	}

	public void removeSubObject(GameObject gameObject)
	{
		subObjects.remove(gameObject);
	}

	public LinkedList<GameObject> getSubObjects()
	{
		return subObjects;
	}

	public boolean isClick()
	{
		if (InputHandler.checkClickInRect(rectangle))
		{
			onClick();
			return true;
		}
		for (GameObject gameObject : subObjects)
			if (gameObject.isClick())
				return true;
		return false;
	}

	protected void onClick()
	{

	}

	public GameObject getParent()
	{
		return parent;
	}
}
