package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import controller.Controller;
import entities.Entity;
import gui.TransitionRenderer;
import input.Keyboard;
import logic.Rules;
import util.Backup;
import world.World;

public class Simulation
{
	public World world;
	public Controller controller;
	public ArrayList<Backup> backups;
	public boolean inTurn;
	private int step;
	private int frame;
	private int speed = 2;
	private TransitionRenderer transition;
	public boolean transitioning;
	private int currentLevel = 0;

	public static String[] levelNames = { "island1", "island2", "island3", "island4", "island5", "island6", "island7", "island8", "lake1", "lake2", "lake3", "lake4", "lake5", "lake6", "lake7",
			"lake8", "lake9", "lake10", "lake11", "lake12", "lake13", "lakeExtra1", "lakeExtra2", "solitary1", "solitary2", "solitary3", "solitary4", "solitary5", "solitary6", "solitary7",
			"solitary8", "solitary9", "solitary10", "solitary11", "solitary12", "solitary13", "solitaryExtra1", "solitaryExtra2", "solitaryExtra3", "solitaryExtra4", "solitaryExtra5" };

	public Simulation()
	{
		transition = new TransitionRenderer();
		loadNextLevel();
	}

	public void addbackup()
	{
		backups.add(new Backup(world));
		if (backups.size() > 500)
			backups.remove(0);
	}

	public void undo()
	{
		if (backups.size() > 0)
		{
			backups.get(backups.size() - 1).applyTo(world);
			backups.remove(backups.size() - 1);
			world.rules.reset();
			world.updateRules();
			world.rules.applyColor();
		}
	}

	public void startTurn(int dir)
	{
		addbackup();
		inTurn = true;
		if (dir != -1)
			for (int i = 0; i < world.objs.size(); i++)
			{
				Entity e = world.objs.get(i);
				if (world.rules.inRule(Rules.YOU, e.type))
				{
					e.move(dir);
				}
			}

		for (int i = 0; i < world.objs.size(); i++)
		{
			world.objs.get(i).move();
		}
		world.anim.turnTex++;
		world.anim.turnTex %= 4;
	}

	public void endTurn()
	{
		for (int i = 0; i < world.objs.size(); i++)
		{
			world.objs.get(i).applyMovement();
		}

		world.rules.reset();
		world.updateRules();
		world.rules.applyTransforms(world);

		for (int i = 0; i < world.objs.size(); i++)
		{
			world.objs.get(i).endTurn();
		}
		world.endTurn();
		world.updateRules();
		inTurn = false;

		if (world.won)
		{
			transition.start(Main.frame.frameColor, 0.01f);
			transitioning = true;
		}
	}

	public void loadNextLevel()
	{
		backups = new ArrayList<Backup>();
		world = new World(new File("res/lvls/" + levelNames[currentLevel++] + ".lvl"));
		world.updateRules();
		world.rules.applyColor();
		addbackup();
		controller = new Controller(world);
	}

	public void update()
	{
		if (inTurn)
		{
			frame++;
			if (frame > speed)
			{
				step++;
				frame = 0;
				if (step >= 4)
				{
					step = 0;
					endTurn();
				}
				Entity.step = step;
			}
		}
		if (Keyboard.keys[KeyEvent.VK_BACK_SPACE])
		{
			undo();
			Keyboard.keys[KeyEvent.VK_BACK_SPACE] = false;
		}
		controller.update();
		world.update();
	}

	public void render(Graphics2D g)
	{
		world.render(g, false);

		if (transitioning)
		{
			if (transition.render(g))
			{
				loadNextLevel();
				transition.reverse();
			}
			if (transition.progress < 0)
			{
				transitioning = false;
			}
		}
	}
}
