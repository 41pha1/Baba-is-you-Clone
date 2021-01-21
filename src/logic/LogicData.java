package logic;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.LogicBlock;

public class LogicData
{
	public static int getLType(int type)
	{
		if (type <= 129)
			return LogicBlock.NOUN;
		else if (type <= 162)
			return LogicBlock.ADJECTIVE;
		else
			return LogicBlock.OPERATOR;
	}

	public static List<Integer> getRef(int type)
	{
		List<Integer> refs = new ArrayList<Integer>();

		if (type == Entity.L_TEXT)
		{
			for (int i = 0; i < 65; i++)
			{
				refs.add(i * 2 + 1);
			}
			for (int i = 130; i < 172; i++)
			{
				refs.add(i);
			}
		} else if (type == Entity.L_ALL)
		{
			for (int i = 0; i < 65; i++)
			{
				refs.add(i * 2);
			}
		} else
		{
			int ref;
			if (type <= 129)
				ref = type - 1;
			else
				ref = type - 130;
			refs.add(ref);
		}
		return refs;
	}
}
