package com.geek.rpg.game.Skirmish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.geek.rpg.game.App;
import com.geek.rpg.game.Assets;
import com.geek.rpg.game.ScreenManager;
import com.geek.rpg.game.ScreenTemplate;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.Skirmish.units.frames.AbilityBar;
import com.geek.rpg.game.primitives.GameObject;

import java.util.LinkedList;

public class SkirmishScreen extends ScreenTemplate
{
	private SkirmishLogic skirmishLogic;
	private boolean isEnded;

	private String statisticsTitle;

	private Button menuButton;
	private LinkedList<Button> menuButtons = new LinkedList<Button>();

	public SkirmishScreen(SpriteBatch batch)
	{
		super(ScreenManager.ScreenType.SKIRMISH, batch);
	}

	@Override
	public void show()
	{
		isEnded = false;
		this.skirmishLogic = new SkirmishLogic();

		super.show();

		int x = Gdx.graphics.getWidth() / 2 - 140;
		int y = Gdx.graphics.getHeight() - 200;

		menuButtons.add(initButton("Continue", x, y, new Runnable()
		{
			@Override
			public void run()
			{
				menuButton.setVisible(true);
				for (Button button : menuButtons)
					button.setVisible(false);
			}
		}));

		y -= 100;
		menuButtons.add(initButton("Restart", x, y, new Runnable()
		{
			@Override
			public void run()
			{
				ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.SKIRMISH);
			}
		}));

		y -= 100;
		menuButtons.add(initButton("Back to World Map", x, y, new Runnable()
		{
			@Override
			public void run()
			{
				ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.WORLD_MAP);
			}
		}));

		menuButton = initButton("Menu", Gdx.graphics.getWidth() - 320, Gdx.graphics.getHeight() - 130,
				new Runnable()
				{
					@Override
					public void run()
					{
						menuButton.setVisible(false);
						for (Button button : menuButtons)
							button.setVisible(true);
					}
				});

		for (Button button : menuButtons)
			button.setVisible(false);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		if (!isEnded && menuButton.isVisible())
		{
			skirmishLogic.update(dt);
			if (isEnded = skirmishLogic.isEnded())
			{
				showStatistics();
			}
		}
	}

	private void showStatistics()
	{
		boolean leftWinner = skirmishLogic.isLeftWinner();

		if (leftWinner)
			App.Selections.getLevel().complete();

		statisticsTitle = leftWinner ? "You are winner! :)" : "You are loser! :(";
		statisticsTitle += "\n" + "Total experience: " + skirmishLogic.getTotalExp();

		skin.add("textureButton", Assets.getInstance().getTexture("buttonMenu.png"));
		skin.add("font", font);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("textureButton");
		textButtonStyle.font = font;
		skin.add("tbs", textButtonStyle);

		Button buttonBack = new TextButton("BACK TO WORLD MAP", skin, "tbs");
		buttonBack.setPosition(500, 410);
		stage.addActor(buttonBack);

		Button buttonRestart = new TextButton("RESTART LEVEL", skin, "tbs");
		buttonRestart.setPosition(500, 330);
		stage.addActor(buttonRestart);

		Button buttonNext = new TextButton("NEXT LEVEL", skin, "tbs");
		buttonNext.setPosition(500, 250);
		stage.addActor(buttonNext);

		buttonBack.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.WORLD_MAP);
			}
		});

		buttonRestart.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.SKIRMISH);
			}
		});

		buttonNext.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				App.Selections.setLevel(App.Selections.getLevel().getNext());
				ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.SKIRMISH);
			}
		});
	}

	@Override
	protected void doRender()
	{
		for (int i = GameObject.LAYER_MIN; i <= GameObject.LAYER_MAX; i++)
		{
			for (Unit unit : skirmishLogic.getUnits())
				unit.render(batch, i);


			if (!isEnded)
			{
				AbilityBar abilityBar = SkirmishLogic.get().getAbilityBar();
				if (abilityBar != null)
					abilityBar.render(batch, i);
			}

			if (isEnded)
				font.draw(batch, statisticsTitle, 0, 600, 1280, 1, false);
		}
	}
}
