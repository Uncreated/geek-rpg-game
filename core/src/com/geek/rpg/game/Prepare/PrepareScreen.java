package com.geek.rpg.game.Prepare;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.geek.rpg.game.App;
import com.geek.rpg.game.Assets;
import com.geek.rpg.game.Progress.ProgressCell;
import com.geek.rpg.game.ScreenManager;
import com.geek.rpg.game.ScreenTemplate;
import com.geek.rpg.game.Skirmish.units.Hero;
import com.geek.rpg.game.Skirmish.units.Monster;
import com.geek.rpg.game.Skirmish.units.Unit;
import com.geek.rpg.game.factory.templates.Level;
import com.geek.rpg.game.factory.templates.LevelTemplate;
import com.geek.rpg.game.factory.templates.ProgressTemplate;
import com.geek.rpg.game.primitives.GameObject;

import java.util.ArrayList;

public class PrepareScreen extends ScreenTemplate
{
	private ArrayList<Hero> heroes = new ArrayList<Hero>();
	private ArrayList<Unit> drawableUnits = new ArrayList<Unit>();
	private int maxHeroes;
	private int selectedCount;

	public PrepareScreen(SpriteBatch batch)
	{
		super(ScreenManager.ScreenType.PREPARE, batch);
	}

	@Override
	public void show()
	{
		super.show();
		setBackground("backgroundMenu.png");

		Level level = App.Selections.getLevel();
		ProgressCell selectedCell = App.Selections.getProgressCell();

		maxHeroes = level.getFriendlyCharactersCount();
		heroes.clear();
		drawableUnits.clear();
		selectedCount = 0;

		int c = 0;
		int x = ScreenManager.WIDTH / 2 - 100;
		int y = ScreenManager.HEIGHT / 2 - 90;
		for (ProgressTemplate.ProgressHero progressHero : selectedCell.getHeroes())
		{
			Hero hero = new Hero(new Vector2(x, y), progressHero);
			drawableUnits.add(hero);
			heroes.add(hero);

			x -= 200;
			c++;
			if (c % 3 == 0)
			{
				x = ScreenManager.WIDTH / 2 - 100;
				y += 250;
			}
		}

		c = 0;
		x = ScreenManager.WIDTH / 2 + 100;
		y = ScreenManager.HEIGHT / 2 - 90;
		for (LevelTemplate.Enemy enemy : level.getEnemies())
		{
			Monster monster = new Monster(new Vector2(x, y), enemy.getId(), enemy.getLevel());
			monster.setFlip(true);
			drawableUnits.add(monster);

			x += 200;
			c++;
			if (c % 3 == 0)
			{
				x = ScreenManager.WIDTH / 2 + 100;
				y += 250;
			}
		}

		Texture buttonTexture = Assets.getInstance().getTexture("buttonMenu.png");

		skin.add("textureButton", buttonTexture);
		skin.add("font", font);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("textureButton");
		textButtonStyle.font = font;
		skin.add("tbs", textButtonStyle);

		Button btn = new TextButton("GO BATTLE >", skin, "tbs");
		btn.setPosition(ScreenManager.WIDTH - 300, 50);
		stage.addActor(btn);
		btn.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				if (selectedCount >= 1)
				{
					ProgressTemplate.ProgressHero[] progressHeroes = App.Selections.getProgressCell().getHeroes();
					ArrayList<ProgressTemplate.ProgressHero> selectedHeroes = new ArrayList<ProgressTemplate.ProgressHero>();
					for (int i = 0; i < heroes.size(); i++)
						if (heroes.get(i).isSelected())
							selectedHeroes.add(progressHeroes[i]);

					App.Selections.setHeroes(selectedHeroes);

					ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.SKIRMISH);
				}
			}
		});

		btn = new TextButton("< BACK", skin, "tbs");
		btn.setPosition(20, 50);
		stage.addActor(btn);
		btn.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.WORLD_MAP);
			}
		});
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);

		for (Unit unit : drawableUnits)
			unit.update(dt);

		for (Hero hero : heroes)
			if (hero.isClick())
			{
				if (hero.isSelected())
				{
					hero.setSelected(false);
					selectedCount--;
				} else if (selectedCount < maxHeroes)
				{
					hero.setSelected(true);
					selectedCount++;
				}
			}

	}

	@Override
	protected void doRender()
	{
		super.doRender();

		for (int i = GameObject.LAYER_MIN; i <= GameObject.LAYER_MAX; i++)
		{
			for (Unit unit : drawableUnits)
				unit.render(batch, i);

			if (i == GameObject.LAYER_MAX)
				for (Unit unit : drawableUnits)
					font.draw(batch,
							unit.getInfoText(),
							unit.getRectangle().x, unit.getRectangle().y - 20,
							1280, Align.left,
							false);
		}
	}
}
