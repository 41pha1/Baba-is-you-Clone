package entities;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.List;

import logic.Rules;
import world.World;

public abstract class Entity implements Serializable
{
	public static final int ALGAE = 0, L_ALGAE = 1, ALL = 2, L_ALL = 3, ANNI = 4, L_ANNI = 5, BABA = 6, L_BABA = 7, BAT = 8, L_BAT = 9, BELT = 10, L_BELT = 11, BIRD = 12, L_BIRD = 13, BOG = 14,
			L_BOG = 15, BOLT = 16, L_BOLT = 17, BOX = 18, L_BOX = 19, BRICK = 20, L_BRICK = 21, BUBBLE = 22, L_BUBBLE = 23, BUG = 24, L_BUG = 25, CAKE = 26, L_CAKE = 27, CLIFF = 28, L_CLIFF = 29,
			CLOUD = 30, L_CLOUD = 31, COG = 32, L_COG = 33, CRAB = 34, L_CRAB = 35, CURSOR = 36, L_CURSOR = 37, DOOR = 38, L_DOOR = 39, DUST = 40, L_DUST = 41, EMPTY = 42, L_EMPTY = 43, FENCE = 44,
			L_FENCE = 45, FIRE = 46, L_FIRE = 47, FLAG = 48, L_FLAG = 49, FLOWER = 50, L_FLOWER = 51, FOLIAGE = 52, L_FOLIAGE = 53, FRUIT = 54, L_FRUIT = 55, FUNGUS = 56, L_FUNGUS = 57, GHOST = 58,
			L_GHOST = 59, GRASS = 60, L_GRASS = 61, GROUP = 62, L_GROUP = 63, HAND = 64, L_HAND = 65, HEDGE = 66, L_HEDGE = 67, ICE = 68, L_ICE = 69, IMAGE = 70, L_IMAGE = 71, JELLY = 72,
			L_JELLY = 73, KEKE = 74, L_KEKE = 75, KEY = 76, L_KEY = 77, LAVA = 78, L_LAVA = 79, LEAF = 80, L_LEAF = 81, LEVEL = 82, L_LEVEL = 83, LINE = 84, L_LINE = 85, LOVE = 86, L_LOVE = 87,
			ME = 88, L_ME = 89, MOON = 90, L_MOON = 91, ORB = 92, L_ORB = 93, PILLAR = 94, L_PILLAR = 95, PIPE = 96, L_PIPE = 97, ROBOT = 98, L_ROBOT = 99, ROCK = 100, L_ROCK = 101, ROCKET = 102,
			L_ROCKET = 103, ROSE = 104, L_ROSE = 105, RUBBLE = 106, L_RUBBLE = 107, SKULL = 108, L_SKULL = 109, STAR = 110, L_STAR = 111, STATUE = 112, L_STATUE = 113, SUN = 114, L_SUN = 115,
			TEXT = 116, L_TEXT = 117, TILE = 118, L_TILE = 119, TREE = 120, L_TREE = 121, UFO = 122, L_UFO = 123, VIOLET = 124, L_VIOLET = 125, WALL = 126, L_WALL = 127, WATER = 128, L_WATER = 129;
	public static final int L_BEST = 130, L_BLUE = 131, L_BONUS = 132, L_DEFEAT = 133, L_DONE = 134, L_DOWN = 135, L_END = 136, L_FALL = 137, L_FLOAT = 138, L_HIDE = 139, L_HOT = 140, L_LEFT = 141,
			L_MELT = 142, L_MORE = 143, L_MOVE = 144, L_OPEN = 145, L_PULL = 146, L_PUSH = 147, L_RED = 148, L_RIGHT = 149, L_SAFE = 150, L_SHIFT = 151, L_SHUT = 152, L_SINK = 153, L_SLEEP = 154,
			L_STOP = 155, L_SWAP = 156, L_TELE = 157, L_UP = 158, L_WEAK = 159, L_WIN = 160, L_WORD = 161, L_YOU = 162;

	public static final int L_AND = 163, L_FACING = 164, L_HAS = 165, L_IDLE = 166, L_IS = 167, L_LONELY = 168, L_MAKE = 169, L_NEAR = 170, L_NOT = 171, L_ON = 172;

	public int dir = 3;
	public int x, y;
	public int type;

	public World world;
	public boolean isLogic;
	public int lType;
	public List<Integer> refType;
	public boolean gotTeled;
	public boolean moving;

	public static int step;

	public Entity(int x, int y, int type, World world)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public void updateLogic()
	{
	}

	public void updateNeigbours()
	{
	}

	public void setActive(boolean active)
	{
	}

	public void endTurn()
	{
		applyCollsionRules();
	}

	public void applyMovement()
	{
		if (moving)
		{
			moving = false;

			world.getGrid(x, y).remove(this);
			if (dir == 0)
			{
				x++;
			} else if (dir == 1)
			{
				y--;
			} else if (dir == 2)
			{
				x--;
			} else if (dir == 3)
			{
				y++;
			}
			world.getGrid(x, y).add(this);
		}
	}

	public boolean applyTurnRules()
	{
		if (applyRuleFall())
			return true;
		applyRuleMore();
		return false;
	}

	public void applyRuleMore()
	{
		if (world.rules.inRule(Rules.MORE, type))
		{
			int[] xcoords = { x - 1, x, x + 1, x };
			int[] ycoords = { y, y + 1, y, y - 1 };
			for (int i = 0; i < 4; i++)
			{
				boolean free = true;
				for (Entity e : world.getGrid(xcoords[i], ycoords[i]))
					if (e.isSolid() || e.type == type)
						free = false;
				if (free)
				{
					Entity copy = new Thing(xcoords[i], ycoords[i], type, world);
					copy.dir = dir;
					world.add(copy);
				}
			}
		}
	}

	public boolean applyRuleFall()
	{
		if (world.rules.inRule(Rules.FALL, type))
		{
			for (int gy = y + 1; gy < world.sizeY; gy++)
			{
				for (Entity e : world.getGrid(x, gy))
				{
					if (e.isSolid())
					{
						world.getGrid(x, y).remove(this);
						y = gy - 1;
						world.getGrid(x, y).add(this);
						return false;
					}
				}
			}
			world.remove(this);
			return true;
		}
		return false;
	}

	public void applyCollsionRules()
	{
		List<Entity> onSameTile = world.getGrid(x, y);
		for (int i = 0; i < onSameTile.size(); i++)
		{
			Entity e = onSameTile.get(i);

			if (e.isFloat() == isFloat())
			{
				if (e != this)
				{
					applyRuleTele(e);
					applyRuleShift(e);
				}

				if (applyDestroyingRules(e))
					return;

				checkWin(e);
			}
		}
	}

	public void applyRuleShift(Entity e)
	{
		if (world.rules.inRule(Rules.SHIFT, e.type))
		{
			e.move(e.dir);
		}
		if (world.rules.inRule(Rules.SHIFT, type))
		{
			move(dir);
		}
	}

	public void applyRuleTele(Entity e)
	{
		if (!gotTeled && world.rules.inRule(Rules.TELE, e.type))
		{
			int k = 0;
			for (; k < world.objs.size(); k++)
			{
				if (world.objs.get(k) == e)
					break;
			}
			for (int j = k + 1; j < world.objs.size() + k - 1; j++)
			{
				int index = j % world.objs.size();
				if (world.rules.inRule(Rules.TELE, world.objs.get(index).type))
				{
					world.getGrid(x, y).remove(this);
					Entity des = world.objs.get(index);
					x = des.x;
					y = des.y;
					gotTeled = true;
					world.getGrid(x, y).add(this);
					applyCollsionRules();
					return;
				}
			}
		}
	}

	public void checkWin(Entity e)
	{
		if (world.rules.inRule(Rules.YOU, type) && world.rules.inRule(Rules.WIN, e.type))
		{
			world.won = true;
		}
	}

	public boolean applyDestroyingRules(Entity e)
	{
		if (e != this && e.type == type && e.dir == dir)
		{
			world.remove(this);
			return true;
		}
		if ((world.rules.inRule(Rules.OPEN, e.type) && world.rules.inRule(Rules.SHUT, this.type)) || (world.rules.inRule(Rules.SHUT, e.type) && world.rules.inRule(Rules.OPEN, this.type)))
		{
			world.remove(this);
			world.remove(e);
			return true;
		}
		if (world.rules.inRule(Rules.WEAK, e.type))
		{
			world.remove(e);
		}
		if (world.rules.inRule(Rules.WEAK, this.type))
		{
			world.remove(this);
			return true;
		}
		if (this != e && (world.rules.inRule(Rules.SINK, e.type) || world.rules.inRule(Rules.SINK, type)))
		{
			world.remove(this);
			world.remove(e);
			return true;
		}
		if (world.rules.inRule(Rules.HOT, e.type) && world.rules.inRule(Rules.MELT, this.type))
		{
			world.remove(this);
			return true;
		}
		if (world.rules.inRule(Rules.MELT, e.type) && world.rules.inRule(Rules.HOT, this.type))
		{
			world.remove(e);
		}
		if (world.rules.inRule(Rules.DEFEAT, e.type) && world.rules.inRule(Rules.YOU, this.type))
		{
			world.remove(this);
			return true;
		}
		if (world.rules.inRule(Rules.YOU, e.type) && world.rules.inRule(Rules.DEFEAT, this.type))
		{
			world.remove(e);
		}
		return false;
	}

	public int getDX()
	{
		int dx = (int) ((x + world.cam.x) * world.cam.scale);

		if (moving)
		{
			if (dir == 0)
			{
				dx += (int) (step / 4f * world.cam.scale);
			} else if (dir == 2)
			{
				dx -= (int) (step / 4f * world.cam.scale);
			}
		}
		return dx;
	}

	public int getDY()
	{
		int dy = (int) ((y + world.cam.y) * world.cam.scale);

		if (world.rules.inRule(Rules.FLOAT, type))
		{
			dy += (int) (world.cam.scale * 0.05f * Math.sin(world.anim.time / 10f));
		}

		if (moving)
		{
			if (dir == 1)
			{
				dy -= (int) (step / 4f * world.cam.scale);
			} else if (dir == 3)
			{
				dy += (int) (step / 4f * world.cam.scale);
			}
		}
		return dy;
	}

	public boolean move(int dir)
	{
		this.dir = dir;

		int zx = x, zy = y;
		if (dir == 0)
			zx++;
		else if (dir == 1)
			zy--;
		else if (dir == 2)
			zx--;
		else if (dir == 3)
			zy++;

		if (zx < 0 || zy < 0 || zx >= world.sizeX || zy >= world.sizeY)
			return false;

		if (!canMoveTo(zx, zy))
			return false;

		if (applyRulePush(zx, zy))
			return false;

		moving = true;
		gotTeled = false;

		applyRuleSwap(zx, zy);
		applyRulePull();
		return true;
	}

	public void move()
	{
		if (!moving && world.rules.inRule(Rules.MOVE, type))
		{
			if (!move(dir))
			{
				move((dir + 2) % 4);
			}
		}
	}

	private boolean isSolid()
	{
		return isLogic || world.rules.inRule(Rules.STOP, type) || world.rules.inRule(Rules.PULL, type) || world.rules.inRule(Rules.PUSH, type);
	}

	private boolean canMoveTo(int zx, int zy)
	{
		for (Entity e : world.getGrid(zx, zy))
		{
			if (!e.moving)
			{
				if (world.rules.inRule(Rules.OPEN, type) && world.rules.inRule(Rules.SHUT, e.type))
					return true;
				if ((world.rules.inRule(Rules.STOP, e.type) || world.rules.inRule(Rules.PULL, e.type)) && !world.rules.inRule(Rules.PUSH, e.type) && !e.isLogic)
					return false;
			}
		}
		return true;
	}

	private boolean applyRulePush(int zx, int zy)
	{
		for (Entity e : world.getGrid(zx, zy))
		{
			if (!e.moving)
			{
				if (e.isLogic || world.rules.inRule(Rules.PUSH, e.type))
				{
					if ((world.rules.inRule(Rules.OPEN, type) && world.rules.inRule(Rules.SHUT, e.type)) || (world.rules.inRule(Rules.OPEN, e.type) && world.rules.inRule(Rules.SHUT, type)))
						return false;
					if (!e.move(dir))
						return true;
				}
			}
		}
		return false;
	}

	private void applyRuleSwap(int zx, int zy)
	{
		for (Entity e : world.getGrid(zx, zy))
		{
			if (!e.moving && (world.rules.inRule(Rules.SWAP, type) || world.rules.inRule(Rules.SWAP, e.type)))
			{
				e.move((dir + 2) % 4);
			}
		}
	}

	public boolean isFloat()
	{
		return world.rules.inRule(Rules.FLOAT, type);
	}

	private void applyRulePull()
	{
		int lx = x, ly = y;
		if (dir == 0)
			lx--;
		else if (dir == 1)

			ly++;
		else if (dir == 2)

			lx++;
		else if (dir == 3)

			ly--;

		for (Entity e : world.getGrid(lx, ly))
		{
			if (world.rules.inRule(Rules.PULL, e.type))
			{
				e.move(dir);
			}
		}
	}

	public abstract void show(Graphics2D g);
}
