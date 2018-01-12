package com.geek.rpg.game.Skirmish;

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

//Только рисует
public class SkirmishScreen extends ScreenTemplate
{
	private SkirmishLogic skirmishLogic;
	private boolean isEnded;

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
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		if (!isEnded)
		{
			skirmishLogic.update(dt);
			if (isEnded = skirmishLogic.isEnded())
			{
				showStatistics();
			}
		}
	}

	String statisticsTitle;

	private void showStatistics()
	{
		boolean leftWinner = skirmishLogic.isLeftWinner();

		if(leftWinner)
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
				//повторить этот уровень ещё раз
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
