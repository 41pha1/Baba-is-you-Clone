package entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import logic.LogicData;
import texture.TextureLoader;
import world.World;

public class LogicBlock extends Entity
{
	public static final int NOUN = 0, ADJECTIVE = 1, OPERATOR = 2;
	public boolean isActive;
	public boolean isFalse;

	public LogicBlock(int x, int y, int type, World world)
	{
		super(x, y, type, world);
		isLogic = true;
		lType = LogicData.getLType(type);
		if (lType == NOUN || lType == ADJECTIVE)
		{
			refType = LogicData.getRef(type);
		}
	}

	@Override
	public void updateLogic()
	{
		if (type == Entity.L_IS)
		{
			isFalse = false;
			List<Entity>[] targets = getTargets();
			applyLogic(targets[3], targets[1]);
			applyLogic(targets[2], targets[0]);
		}
	}

	public List<Entity>[] getTargets()
	{
		List<Entity>[] targets = new List[4];

		int[] xcoords = { 0, 1, 0, -1 };
		int[] ycoords = { 1, 0, -1, 0 };

		for (int i = 0; i < 4; i++)
		{
			targets[i] = new ArrayList<Entity>();

			int xp = x + xcoords[i];
			int yp = y + ycoords[i];
			while (true)
			{
				for (Entity e : world.getGrid(xp, yp))
					if (e.isLogic && e.lType != OPERATOR)
						targets[i].add(e);

				xp += xcoords[i];
				yp += ycoords[i];

				boolean andExists = false;

				for (Entity e : world.getGrid(xp, yp))
					if (e.type == Entity.L_AND)
						andExists = true;

				if (!andExists)
					break;

				xp += xcoords[i];
				yp += ycoords[i];
			}
		}
		return targets;
	}

	@Override
	public void setActive(boolean active)
	{
		isActive = active;
	}

	public void applyLogic(List<Entity> f, List<Entity> s)
	{
		isActive = true;

		for (Entity first : f)
		{
			for (Entity second : s)
			{
				if (first.lType == NOUN && second.lType != OPERATOR)
				{
					first.setActive(true);
					second.setActive(true);

					for (int editType : first.refType)
					{
						if (second.refType.size() == 1)
						{
							int otherType = second.refType.get(0);
							if (second.lType == NOUN)
							{
								for (int i = 0; i < world.objs.size(); i++)
								{
									if (world.objs.get(i).type == editType)
									{
										int prev = world.rules.entityMap[editType];

										if (prev == -1 || prev == otherType)
										{
											world.rules.nextEntityMap[editType] = otherType;
										} else
										{
											isFalse = true;
											first.setActive(false);
											second.setActive(false);
											isActive = false;
										}
									}
								}
							}
							if (second.lType == ADJECTIVE)
							{
								world.rules.rules[otherType].add(editType);
							}
						} else
						{
							if (second.type == first.type)
							{
								if (world.rules.entityMap[editType] == -1)
								{
									world.rules.nextEntityMap[editType] = editType;
								}
							} else if (second.type == L_TEXT)
							{
								if (world.rules.entityMap[editType] == -1)
								{
									world.rules.nextEntityMap[editType] = editType + 1;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void show(Graphics2D g)
	{
		int scale = world.cam.scale;
		int wobble = world.anim.getWobbleState();

		g.drawImage(TextureLoader.textures[type][isActive ? 0 : 1][wobble], getDX(), getDY(), scale, scale, null);

		if (isFalse)
		{
			g.drawImage(TextureLoader.icons[TextureLoader.CROSS][wobble], getDX(), getDY(), scale, scale, null);
		}
	}
}
