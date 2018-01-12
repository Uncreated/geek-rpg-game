package com.geek.rpg.game.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.geek.rpg.game.App;
import com.geek.rpg.game.Assets;
import com.geek.rpg.game.Progress.ProgressCell;
import com.geek.rpg.game.ScreenManager;
import com.geek.rpg.game.ScreenTemplate;

public class MainMenuScreen extends ScreenTemplate
{
	public MainMenuScreen(SpriteBatch batch)
	{
		super(ScreenManager.ScreenType.MAIN_MENU, batch);
	}

	@Override
	public void show()
	{
		super.show();

		setBackground("backgroundMenu.png");
		Texture buttonTexture = Assets.getInstance().getTexture("buttonMenu.png");

		skin.add("textureButton", buttonTexture);
		skin.add("font", font);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("textureButton");
		textButtonStyle.font = font;
		skin.add("tbs", textButtonStyle);

		int x = 500;
		int y = 530;
		ProgressCell[] cells = ProgressCell.createAll();
		for (int i = 0; i < cells.length; i++)
		{
			final ProgressCell cell = cells[i];

			String title = "Cell " + i + "\n" +
					"Available levels: " + cell.getLevels();
			Button btn = new TextButton(title, skin, "tbs");
			btn.setPosition(x, y);
			stage.addActor(btn);
			y -= 100;

			btn.addListener(new ChangeListener()
			{
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					App.Selections.setProgressCell(cell);
					ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.WORLD_MAP);
				}
			});
		}

		Button btnExitGame = new TextButton("EXIT", skin, "tbs");
		btnExitGame.setPosition(x, y - 50);
		stage.addActor(btnExitGame);


		btnExitGame.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				Gdx.app.exit();
			}
		});
	}
}
