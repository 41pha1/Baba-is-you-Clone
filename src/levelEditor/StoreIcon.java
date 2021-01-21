package levelEditor;

import java.awt.Color;
import java.awt.Graphics2D;

import input.Mouse;
import texture.TextureLoader;

public class StoreIcon
{
	int x, y;
	int scale;
	int itemId;
	int iconId;
	Store store;

	public StoreIcon(int x, int y, int scale, int iconId, int itemId, Store store)
	{
		super();
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.itemId = itemId;
		this.iconId = iconId;
		this.store = store;
	}

	public void show(Graphics2D g)
	{
		g.drawImage(TextureLoader.textures[itemId][0][0], x, y, scale, scale, null);

		if (iconId == store.selected)
		{
			g.setColor(Color.WHITE);
			g.drawRect(x, y, scale, scale);
		}

	}

	public void update()
	{
		if (Mouse.left)
		{
			if (Mouse.x > x && Mouse.x < x + scale && Mouse.y > y && Mouse.y < y + scale)
			{
				store.selected = iconId;
			}
		}
	}
}
