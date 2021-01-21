package levelEditor;

import java.io.File;

import world.World;

public class LevelConverter
{
	public static void main(String[] args)
	{
		String[] levelNames = { "island1", "island2", "island3", "island4", "island5", "island6", "island7", "island8", "lake1", "lake2", "lake3", "lake4", "lake5", "lake6" };

		int i = 0;
		for (String s : levelNames)
		{
			World world = new World(new File(levelNames[i] + ".lvl"));
			world.save(new File(levelNames[i++] + ".lvl"));
		}
	}
}
