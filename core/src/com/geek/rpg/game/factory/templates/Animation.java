package com.geek.rpg.game.factory.templates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.Assets;
import com.geek.rpg.game.factory.JsonLoader;

public class Animation
{
	public static final int ANIMATION_SIMPLE = 0;
	public static final int ANIMATION_ATTACK = 1;
	public static final int ANIMATION_SPELL = 2;
	public static final int ANIMATION_INPUT_DAMAGE = 3;
	public static final int ANIMATION_INPUT_HEAL = 4;
	public static final int ANIMATION_POTION = 5;
	public static final int ANIMATION_DEAD = 10;
	public static final int ANIMATION_ALONE = 100;

	private AnimationTemplate template;

	private float curDuration;

	private TextureRegion[] regions;
	private float frameDuration;
	private int width;
	private int height;

	private float r = 1.0f;
	private float g = 1.0f;
	private float b = 1.0f;
	private float a = 1.0f;

	public Animation(int animationId)
	{
		template = AnimationTemplate.jsonLoader.get(animationId);
		load();
		reset();
	}

	public void update(float dt)
	{
		curDuration += dt;
	}

	public void render(SpriteBatch batch, Vector2 center, boolean flip)
	{
		if (isActive())
		{
			batch.setColor(r, g, b, a);
			TextureRegion curTextureRegion = getTextureRegion(curDuration);
			if (flip && !curTextureRegion.isFlipX())
				curTextureRegion.flip(true, false);
			batch.draw(curTextureRegion, center.x - getWidth() / 2, center.y - getHeight() / 2);
		}
	}

	public boolean isActive()
	{
		return curDuration < template.getDuration();
	}

	public void reset()
	{
		curDuration = 0.0f;
	}

	public float getDuration()
	{
		return template.getDuration();
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public void load()
	{
		if (regions == null)
		{
			this.frameDuration = template.getDuration() / template.getFrames();

			Texture texture = Assets.getInstance().getTexture(template.getTextureName());

			width = texture.getWidth() / template.getFrames();
			height = texture.getHeight();

			TextureRegion[][] textureRegions = new TextureRegion(texture).split(width, height);
			regions = new TextureRegion[template.getFrames()];
			for (int i = 0; i < template.getFrames(); i++)
				regions[i] = textureRegions[0][i];
		}
	}

	public TextureRegion getTextureRegion(float curDuration)
	{
		return regions[(int) (curDuration / frameDuration)];
	}

	public int getAction(int action)
	{
		int id = template.getId();
		return id < 100 ? id : (id - id % 100) + action;
	}

	public void setColor(float rk, float gk, float bk, float ak)
	{
		this.r = rk;
		this.g = gk;
		this.b = bk;
		this.a = ak;
	}
}
