package util;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.LogicBlock;
import entities.Thing;
import world.World;

public class Backup
{
	public List<Entity> objects;

	public Backup(World world)
	{
		objects = clone(world.objs);
	}

	public void applyTo(World world)
	{
		world.objs = objects;
		world.updateGrid();
	}

	public Entity clone(Entity e)
	{
		Entity cloned;
		if (e.isLogic)
		{
			cloned = new LogicBlock(e.x, e.y, e.type, e.world);
		} else
		{
			cloned = new Thing(e.x, e.y, e.type, e.world);
		}
		cloned.dir = e.dir;

		return cloned;
	}

	public ArrayList<Entity> clone(List<Entity> list)
	{
		ArrayList<Entity> cloned = new ArrayList<Entity>();

		for (Entity e : list)
		{
			cloned.add(clone(e));
		}
		return cloned;
	}
}
