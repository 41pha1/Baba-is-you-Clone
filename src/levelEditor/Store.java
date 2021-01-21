package levelEditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import input.Keyboard;
import input.Mouse;
import texture.TextureLoader;

public class Store
{
	public ArrayList<ArrayList<StoreIcon>> items;
	public int cat = 0;
	public int scale = 50;
	public int xoff = 0;
	public int yoff = 800;
	public int itemsPerRow = 20;
	public int width, height;

	public int selected;

	public Store()
	{
		items = new ArrayList<ArrayList<StoreIcon>>();
		items.add(new ArrayList<StoreIcon>());
		items.add(new ArrayList<StoreIcon>());
		items.add(new ArrayList<StoreIcon>());
		items.add(new ArrayList<StoreIcon>());

		width = scale * itemsPerRow;
		height = 200;

		int index = 0;
		for (int i = 0; i < 65; i++)
		{
			if (TextureLoader.textures[i * 2].length > 0)
			{
				int x = xoff + (index % itemsPerRow) * scale;
				int y = yoff + (index / itemsPerRow) * scale;
				items.get(0).add(new StoreIcon(x, y, scale, index, i * 2, this));
				index++;
			}
		}

		for (int i = 0; i < 65; i++)
		{
			int x = xoff + (i % itemsPerRow) * scale;
			int y = yoff + (i / itemsPerRow) * scale;
			items.get(1).add(new StoreIcon(x, y, scale, i, i * 2 + 1, this));
		}

		for (int i = 0; i < 33; i++)
		{
			int x = xoff + (i % itemsPerRow) * scale;
			int y = yoff + (i / itemsPerRow) * scale;
			items.get(2).add(new StoreIcon(x, y, scale, i, 130 + i, this));
		}
		for (int i = 0; i < 10; i++)
		{
			int x = xoff + (i % itemsPerRow) * scale;
			int y = yoff + (i / itemsPerRow) * scale;
			items.get(3).add(new StoreIcon(x, y, scale, i, 163 + i, this));
		}
	}

	public boolean isMouseOver()
	{
		return (Mouse.x > xoff && Mouse.x < xoff + width && Mouse.y > yoff && Mouse.y < yoff + height);
	}

	public int getSelectedID()
	{
		if (selected == -1)
			return 0;
		return items.get(cat).get(selected).itemId;
	}

	public void show(Graphics2D g)
	{
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(xoff, yoff, width, height);

		for (StoreIcon icon : items.get(cat))
		{
			icon.show(g);
		}
	}

	public void update()
	{
		if (Keyboard.keys[KeyEvent.VK_SPACE])
		{
			cat++;
			cat %= 4;
			selected = -1;
			Keyboard.keys[KeyEvent.VK_SPACE] = false;
		}
		for (StoreIcon icon : items.get(cat))
		{
			icon.update();
		}
	}
}
