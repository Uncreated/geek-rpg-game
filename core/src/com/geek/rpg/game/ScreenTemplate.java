package com.geek.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public abstract class ScreenTemplate implements Screen
{
	private ScreenManager.ScreenType screenType;
	private Texture background;

	protected SpriteBatch batch;
	protected BitmapFont font;

	protected Stage stage;
	protected Skin skin;

	public ScreenTemplate(ScreenManager.ScreenType screenType, SpriteBatch batch)
	{
		this.screenType = screenType;
		this.batch = batch;
	}

	protected void setBackButton(Runnable runnable)
	{
		initButton("Back", 50, 50, runnable);
	}

	protected void setNextButton(Runnable runnable)
	{
		initButton("Next", Gdx.graphics.getWidth() - 320, 50, runnable);
	}

	protected Button initButton(String text, int x, int y, final Runnable runnable)
	{
		Button button = new TextButton(text, skin, "btn");
		button.setPosition(x, y);
		button.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				runnable.run();
			}
		});
		stage.addActor(button);

		return button;
	}

	@Override
	public void show()
	{
		Assets.getInstance().load(screenType);
		this.font = Assets.getInstance().getFont("font.fnt");
		this.background = Assets.getInstance().getTexture("background.png");

		stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
		Gdx.input.setInputProcessor(stage);

		skin = new Skin();

		skin.add("buttonMenu", Assets.getInstance().getTexture("buttonMenu.png"));

		TextButton.TextButtonStyle availableTBS = new TextButton.TextButtonStyle();
		availableTBS.up = skin.getDrawable("buttonMenu");
		availableTBS.font = font;
		skin.add("btn", availableTBS);
	}

	@Override
	public void render(float delta)
	{
		update(delta);
		preRender();
		doRender();
		postRender();
	}

	public void update(float dt)
	{
		stage.act(dt);
	}

	private void preRender()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(1f, 1f, 1f, 1f);
		batch.draw(background, 0, 0);
	}

	protected void doRender()
	{
	}

	private void postRender()
	{
		batch.end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height)
	{
		ScreenManager.getInstance().onResize(width, height);
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void dispose()
	{
		Assets.getInstance().clear();
	}

	protected void setBackground(String backgroundName)
	{
		this.background = Assets.getInstance().getTexture(backgroundName);
	}
}
