package com.geek.rpg.game.WorldMap;

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
import com.geek.rpg.game.factory.templates.Level;

public class WorldMapScreen extends ScreenTemplate
{
	public WorldMapScreen(SpriteBatch batch)
	{
		super(ScreenManager.ScreenType.WORLD_MAP, batch);
	}

	@Override
	public void show()
	{
		super.show();
		setBackground("backgroundSky.png");

		setBackButton(new Runnable()
		{
			@Override
			public void run()
			{
				ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MAIN_MENU);
			}
		});

		skin.add("availableLevel", Assets.getInstance().getTexture("flyingIsland.png"));
		skin.add("unavailableLevel", Assets.getInstance().getTexture("flyingIslandUnavailable.png"));
		skin.add("font", font);

		TextButton.TextButtonStyle availableTBS = new TextButton.TextButtonStyle();
		availableTBS.up = skin.getDrawable("availableLevel");
		availableTBS.font = font;
		skin.add("tbs", availableTBS);

		TextButton.TextButtonStyle unavailableTBS = new TextButton.TextButtonStyle();
		unavailableTBS.up = skin.getDrawable("unavailableLevel");
		unavailableTBS.font = font;
		skin.add("utbs", unavailableTBS);

		int maxLevels = App.Selections.getProgressCell().getLevels();
		Level last = null;
		for (int i = 1; i <= 7; i++)
		{
			final Level level = new Level(i);
			if (last != null)
				last.setNext(level);

			last = level;
			Button btnIsland = new TextButton("\n\nLEVEL " + i + "\n" + level.getName(),
					skin,
					(maxLevels >= i) ? "tbs" : "utbs");
			btnIsland.setPosition(150 * i - 50, Gdx.graphics.getHeight() / 2 + 100 * ((i % 2) - 1));
			stage.addActor(btnIsland);

			if (maxLevels >= i)
				btnIsland.addListener(new ChangeListener()
				{
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						App.Selections.setLevel(level);
						ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.PREPARE);
					}
				});
		}
	}
}
