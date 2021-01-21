package entities;

import java.awt.Graphics2D;

import texture.TextureLoader;
import world.World;

public class Thing extends Entity
{
	int conInd;

	@Override
	public void updateNeigbours()
	{
		if (isConnected(type))
		{
			conInd = 0;

			int[] xoff = { 1, 0, -1, 0 };
			int[] yoff = { 0, -1, 0, 1 };

			for (int i = 0; i < 4; i++)
			{
				int xp = x + xoff[i];
				int yp = y + yoff[i];

				if (xp < 0 || yp < 0 || xp >= world.sizeX || yp >= world.sizeY)
				{
					conInd |= 1 << i;
				}

				for (Entity e : world.getGrid(xp, yp))
				{
					if (e.type == type)
					{
						conInd |= 1 << i;
					}
				}
			}
		}
	}

	public Thing(int x, int y, int type, World world)
	{
		super(x, y, type, world);
	}

	@Override
	public void show(Graphics2D g)
	{
		int scale = world.cam.scale;
		int wobble = world.anim.getWobbleState();

		if (TextureLoader.textures[type].length > 0)
			g.drawImage(TextureLoader.textures[type][getTexID(type)][wobble], getDX(), getDY(), scale, scale, null);
	}

	public static boolean isConnected(int type)
	{
		if (TextureLoader.textures[type].length == 16 && type != Entity.ROBOT)
			return true;
		return false;
	}

	public int getTexID(int type)
	{
		int texID = 0;
		boolean isRotating = false;
		boolean isConnected = false;
		boolean smallRotating = false;
		if (type == Entity.BAT || type == Entity.COG || type == Entity.BELT)
		{
			return world.anim.turnTex;
		} else if (type == Entity.ROBOT)
		{
			smallRotating = true;
		} else
		{
			isRotating = TextureLoader.textures[type].length == 20;
			isConnected = TextureLoader.textures[type].length == 16;
		}
		if (TextureLoader.textures[type].length == 4)
			return dir;
		if (isRotating)
		{
			texID = dir * 5 + (moving ? step : 0) + 1;
		} else if (isConnected)
		{
			texID = conInd;

		} else if (smallRotating)
		{
			texID = dir * 4 + (moving ? step % 4 : 0);
		}
		return texID;
	}

}
