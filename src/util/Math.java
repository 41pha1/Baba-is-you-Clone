package util;

public class Math
{
	public static int map(int value, int start1, int stop1, int start2, int stop2)
	{
		return start2 + (((stop2 - start2) * (value - start1)) / (stop1 - start1));
	}
}
