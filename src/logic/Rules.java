package logic;

import java.io.Serializable;
import java.util.HashSet;

import entities.Entity;
import entities.Thing;
import texture.TextureLoader;
import world.World;

public class Rules implements Serializable
{

	public static final int BEST = 0, BLUE = 1, BONUS = 2, DEFEAT = 3, DONE = 4, DOWN = 5, END = 6, FALL = 7, FLOAT = 8, HIDE = 9, HOT = 10, LEFT = 11, MELT = 12, MORE = 13, MOVE = 14, OPEN = 15,
			PULL = 16, PUSH = 17, RED = 18, RIGHT = 19, SAFE = 20, SHIFT = 21, SHUT = 22, SINK = 23, SLEEP = 24, STOP = 25, SWAP = 26, TELE = 27, UP = 28, WEAK = 29, WIN = 30, WORD = 31, YOU = 32;

	public static final int nRules = 33;

	public HashSet<Integer>[] rules;
	public int[] nextEntityMap;
	public int[] entityMap;
	public int[] colorMap;

	public boolean inRule(int rule, int type)
	{
		return rules[rule].contains(type);
	}

	public void updateEntityMap()
	{
		for (int i = 0; i < 172; i++)
		{
			entityMap[i] = nextEntityMap[i];
		}
	}

	public void updateColorMap()
	{
		for (int i = 0; i < 172; i++)
		{
			int next = -1;
			if (inRule(RED, i))
			{
				next = 16;
			} else if (inRule(BLUE, i))
			{
				next = 15;
			}
			if (inRule(RED, i) && inRule(BLUE, i))
			{
				next = 10;
			}
			if (colorMap[i] >= 0 && next == -1)
				colorMap[i] = -2;
			if (next >= 0)
				colorMap[i] = next;
		}
	}

	public void reset()
	{
		for (int i = 0; i < 172; i++)
		{
			nextEntityMap[i] = -1;
		}
	}

	public void applyColor()
	{
		updateColorMap();
		for (int i = 0; i < entityMap.length; i++)
		{
			if (colorMap[i] >= 0)
			{
				TextureLoader.setColor(i, colorMap[i]);
			} else if (colorMap[i] == -2)
			{
				TextureLoader.resetColor(i);
			}
		}
	}

	public void applyTransforms(World world)
	{
		applyColor();
		updateEntityMap();
		for (int i = 0; i < entityMap.length; i++)
		{
			if (entityMap[i] != -1)
			{
				for (int j = 0; j < world.objs.size(); j++)
				{
					Entity old = world.objs.get(j);
					if (old.type == i)
					{
						Entity next = new Thing(old.x, old.y, entityMap[i], world);
						world.objs.set(j, next);
						world.getGrid(old.x, old.y).remove(old);
						world.getGrid(old.x, old.y).add(next);
					}
				}
			}
		}

	}

	public Rules()
	{
		entityMap = new int[172];
		colorMap = new int[172];
		nextEntityMap = new int[172];

		for (int i = 0; i < 172; i++)
		{
			colorMap[i] = -1;
			entityMap[i] = -1;
			nextEntityMap[i] = -1;
		}

		rules = new HashSet[nRules];

		for (int i = 0; i < nRules; i++)
		{
			rules[i] = new HashSet<Integer>();
		}
	}
}
