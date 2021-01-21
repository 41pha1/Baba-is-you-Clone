package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import entities.Entity;
import entities.LogicBlock;
import entities.Thing;
import logic.Rules;
import texture.Animator;
import texture.TextureLoader;
import util.Camera;

public class World implements Serializable
{
	public Camera cam;
	public Animator anim;
	public Rules rules;
	private List<Entity>[][] grid;
	public List<Entity> objs;
	public Color bgc;
	public boolean won;
	public int background;
	public int pallet;
	public int sizeX, sizeY;

	public World(File loadFile)
	{
		loadWorld(loadFile);
	}

	public World(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		init(48);
	}

	public void add(Entity e)
	{
		if (e.x < 0 || e.y < 0 || e.x >= sizeX || e.y >= sizeY)
			return;
		objs.add(e);
		grid[e.x][e.y].add(e);
	}

	public World(int sizeX, int sizeY, int camscale)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		init(camscale);
	}

	public void init(int camscale)
	{
		TextureLoader.loadSprites(pallet);
		bgc = TextureLoader.getColor(background);

		grid = new ArrayList[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++)
		{
			for (int y = 0; y < sizeY; y++)
			{
				grid[x][y] = new ArrayList<Entity>();
			}
		}
		objs = new ArrayList<Entity>();
		rules = new Rules();
		cam = new Camera(camscale, 0, 0);
		anim = new Animator(20);
	}

	public void updateGrid()
	{
		grid = new ArrayList[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++)
		{
			for (int y = 0; y < sizeY; y++)
			{
				grid[x][y] = new ArrayList<Entity>();
			}
		}
		for (Entity e : objs)
		{
			getGrid(e.x, e.y).add(e);
		}
	}

	public void updateRules()
	{
		for (int i = 0; i < rules.rules.length; i++)
		{
			rules.rules[i] = new HashSet<Integer>();
		}
		for (Entity e : objs)
		{
			if (e.isLogic)
			{
				e.setActive(false);
			}
		}
		for (Entity e : objs)
		{
			if (e.isLogic)
			{
				e.updateLogic();
			}
			e.updateNeigbours();
		}
	}

	public void update()
	{
		anim.udpate();
	}

	public void endTurn()
	{
		int length = objs.size();
		for (int i = 0; i < length; i++)
		{
			int r = objs.get(i).applyTurnRules() ? -1 : 0;
			i += r;
			length += r;
		}
	}

	public void remove(Entity e)
	{
		objs.remove(e);
		getGrid(e.x, e.y).remove(e);
	}

	public float screenToWorldX(int sx, int width)
	{
		sx -= width / 2 - sizeX * (cam.scale / 2);
		return sx / (float) cam.scale - cam.x;
	}

	public float screenToWorldY(int sy, int height)
	{
		sy -= height / 2 - sizeY * (cam.scale / 2);
		return sy / (float) cam.scale - cam.y;
	}

	public void save(File saveFile)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			byte[] data = new byte[6 + objs.size() * 4];
			int i = 0;
			data[i++] = (byte) sizeX;
			data[i++] = (byte) sizeY;
			data[i++] = (byte) background;
			data[i++] = (byte) pallet;
			data[i++] = (byte) (objs.size() >> 8);
			data[i++] = (byte) (objs.size() & 0xFF);

			for (Entity e : objs)
			{
				data[i++] = (byte) e.type;
				data[i++] = (byte) e.x;
				data[i++] = (byte) e.y;
				data[i++] = (byte) e.dir;
			}
			fileOut.write(data);
			fileOut.close();
			System.out.println("Saved the world!");
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void loadWorld(File loadFile)
	{
		FileInputStream fi;
		try
		{
			fi = new FileInputStream(loadFile);
			sizeX = fi.read();
			sizeY = fi.read();
			background = fi.read();
			pallet = fi.read();
			init(48);
			int count = fi.read() * 256 + fi.read();

			for (int i = 0; i < count; i++)
			{
				int type = fi.read();
				int x = fi.read();
				int y = fi.read();
				int dir = fi.read();

				Entity toAdd;
				if (type < 130 && type % 2 == 0)
					toAdd = new Thing(x, y, type, this);
				else
					toAdd = new LogicBlock(x, y, type, this);

				toAdd.dir = dir;
				add(toAdd);
			}
			fi.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public List<Entity> getGrid(int x, int y)
	{
		if (x < 0 || y < 0 || x >= sizeX || y >= sizeY)
			return new ArrayList<Entity>();
		return grid[x][y];
	}

	public void render(Graphics2D g, boolean drawGrid)
	{
		int dx = -sizeX * (cam.scale / 2);
		int dy = -sizeY * (cam.scale / 2);
		g.translate(dx, dy);
		g.setColor(bgc);
		g.fillRect((int) (cam.x * cam.scale), (int) (cam.y * cam.scale), sizeX * cam.scale, sizeY * cam.scale);

		for (int x = 0; x < sizeX; x++)
		{
			for (int y = 0; y < sizeY; y++)
			{
				for (int i = 0; i < grid[x][y].size(); i++)
				{
					if (!grid[x][y].get(i).moving && !grid[x][y].get(i).isFloat())
						grid[x][y].get(i).show(g);
				}

			}
		}
		for (int x = 0; x < sizeX; x++)
		{
			for (int y = 0; y < sizeY; y++)
			{
				for (int i = 0; i < grid[x][y].size(); i++)
				{
					if (grid[x][y].get(i).moving && !grid[x][y].get(i).isFloat())
						grid[x][y].get(i).show(g);
				}
			}
		}
		for (int x = 0; x < sizeX; x++)
		{
			for (int y = 0; y < sizeY; y++)
			{
				for (int i = 0; i < grid[x][y].size(); i++)
				{
					if (!grid[x][y].get(i).moving && grid[x][y].get(i).isFloat())
						grid[x][y].get(i).show(g);
				}
			}
		}
		for (int x = 0; x < sizeX; x++)
		{
			for (int y = 0; y < sizeY; y++)
			{
				for (int i = 0; i < grid[x][y].size(); i++)
				{
					if (grid[x][y].get(i).moving && grid[x][y].get(i).isFloat())
						grid[x][y].get(i).show(g);
				}
			}
		}

		if (drawGrid)
		{
			g.setColor(new Color(255, 255, 255, 50));
			for (int x = 0; x < sizeX; x++)
			{
				int px = (int) ((x + cam.x) * cam.scale);
				int py = (int) (cam.y * cam.scale);
				g.drawLine(px, py, px, py + sizeY * cam.scale);
			}
			for (int y = 0; y < sizeY; y++)
			{
				int px = (int) (cam.x * cam.scale);
				int py = (int) ((y + cam.y) * cam.scale);
				g.drawLine(px, py, px + sizeX * cam.scale, py);
			}

		}

		g.translate(-dx, -dy);
	}
}
