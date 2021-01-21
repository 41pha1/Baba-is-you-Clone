package controller;

import java.awt.event.KeyEvent;

import input.Keyboard;
import main.Main;
import world.World;

public class Controller
{
	public World world;

	public Controller(World world)
	{
		this.world = world;
	}

	public void update()
	{
		if (!Main.simulation.inTurn && !Main.simulation.transitioning)
		{
			if (Keyboard.keys[KeyEvent.VK_SPACE])
			{
				Main.simulation.startTurn(-1);
			}
			boolean w = Keyboard.keys[KeyEvent.VK_W] || Keyboard.keys[KeyEvent.VK_UP];
			boolean a = Keyboard.keys[KeyEvent.VK_A] || Keyboard.keys[KeyEvent.VK_LEFT];
			boolean s = Keyboard.keys[KeyEvent.VK_S] || Keyboard.keys[KeyEvent.VK_DOWN];
			boolean d = Keyboard.keys[KeyEvent.VK_D] || Keyboard.keys[KeyEvent.VK_RIGHT];

			if (w || a || s || d)
			{
				int dir = -1;
				if (w)
					dir = 1;
				else if (a)
					dir = 2;
				else if (s)
					dir = 3;
				else if (d)
					dir = 0;

				Main.simulation.startTurn(dir);
				return;
			}
		}

	}
}
