package levelEditor;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import entities.Entity;
import entities.LogicBlock;
import entities.Thing;
import input.Keyboard;
import input.Mouse;
import texture.TextureLoader;
import world.World;

public class LevelEditor
{
	static World world;
	static EditorFrame frame;
	static int selectedBlock;
	static Store store;
	static File saveFile = new File("res/lvls/solitaryExtra2.lvl");
	static boolean loadSave = true;
	static int ldx = -1, ldy = -1;

	static int background = 28;
	static int pallet = 4;

	public static void main(String[] args)
	{
		frame = new EditorFrame(1000, 1000);

		if (loadSave)
			world = new World(saveFile);
		else
			world = new World(15, 15, 48);

		world.pallet = pallet;
		world.background = background;
		TextureLoader.loadSprites(pallet);
		world.bgc = TextureLoader.getColor(background);

		store = new Store();
		frame.setV();

		long lastFrame = System.currentTimeMillis();
		while (true)
		{
			long time = System.currentTimeMillis();
			if (time - lastFrame > (1000 / 60))
			{
				lastFrame = time;
				frame.repaint();
				update();
			}
		}
	}

	public static void update()
	{
		store.update();

		world.cam.scale *= Math.pow(0.9f, Mouse.dw);
		if (Mouse.middle)
		{
			world.cam.x -= Mouse.dx / (float) world.cam.scale;
			world.cam.y -= Mouse.dy / (float) world.cam.scale;
		}
		if (Mouse.left)
		{
			placeItem: if (store.selected != -1 && !store.isMouseOver())
			{
				int wx = (int) world.screenToWorldX(Mouse.x, frame.width);
				int wy = (int) world.screenToWorldY(Mouse.y, frame.height);

				for (Entity e : world.getGrid(wx, wy))
				{
					if (e.type == store.getSelectedID())
						break placeItem;
				}
				if (store.cat == 0)
					world.add(new Thing(wx, wy, store.getSelectedID(), world));
				else
					world.add(new LogicBlock(wx, wy, store.getSelectedID(), world));
			}
		}
		if (Mouse.middleR)
		{
			if (!store.isMouseOver())
			{
				Mouse.middle = false;

				int wx = (int) world.screenToWorldX(Mouse.x, frame.width);
				int wy = (int) world.screenToWorldY(Mouse.y, frame.height);

				List<Entity> grid = world.getGrid(wx, wy);
				if (grid.size() > 0)
				{
					grid.get(grid.size() - 1).dir++;
					grid.get(grid.size() - 1).dir %= 4;
				}
			}

		}
		if (Mouse.right)
		{
			if (!store.isMouseOver())
			{
				int wx = (int) world.screenToWorldX(Mouse.x, frame.width);
				int wy = (int) world.screenToWorldY(Mouse.y, frame.height);

				if (!(wx == ldx && wy == ldy))
				{
					List<Entity> grid = world.getGrid(wx, wy);
					if (grid.size() > 0)
					{
						world.remove(grid.get(grid.size() - 1));
					}
					ldx = wx;
					ldy = wy;
				}
			}
		}
		if (Keyboard.keys[KeyEvent.VK_RIGHT])
		{
			world.sizeX++;
			Keyboard.keys[KeyEvent.VK_RIGHT] = false;
		}
		if (Keyboard.keys[KeyEvent.VK_LEFT])
		{
			world.sizeX--;
			Keyboard.keys[KeyEvent.VK_LEFT] = false;
		}
		if (Keyboard.keys[KeyEvent.VK_UP])
		{
			world.sizeY--;
			Keyboard.keys[KeyEvent.VK_UP] = false;
		}
		if (Keyboard.keys[KeyEvent.VK_DOWN])
		{
			world.sizeY++;
			Keyboard.keys[KeyEvent.VK_DOWN] = false;
		}
		world.updateGrid();
		if (Keyboard.keys[KeyEvent.VK_S])
		{
			world.save(saveFile);
			Keyboard.keys[KeyEvent.VK_S] = false;
		}
		if (Mouse.rightR)
		{
			ldx = -1;
			ldy = -1;
		}
		Mouse.reset();
	}

	public static void render(Graphics2D g)
	{

		g.translate(frame.width / 2, frame.height / 2);
		world.render(g, true);
		g.translate(-frame.width / 2, -frame.height / 2);

		store.show(g);

	}
}
