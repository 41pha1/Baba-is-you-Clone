package helper;

public class CodeHelper
{

	public static void main(String[] args)
	{
		String input = "\n" + "    BEST\n" + "    BLUE\n" + "    BONUS\n" + "\n" + "D\n" + "\n" + "    DEFEAT\n"
				+ "    DONE\n" + "    DOWN\n" + "\n" + "E\n" + "\n" + "    END\n" + "\n" + "F\n" + "\n" + "    FALL\n"
				+ "    FLOAT\n" + "\n" + "H\n" + "\n" + "    HIDE\n" + "    HOT\n" + "    \n" + "\n" + "L\n" + "\n"
				+ "    LEFT\n" + "\n" + "M\n" + "\n" + "    MELT\n" + "    MORE\n" + "    MOVE\n" + "\n" + "O\n" + "\n"
				+ "    OPEN\n" + "\n" + "P\n" + "\n" + "   " + "    PULL\n" + "    PUSH\n" + "\n" + "R\n" + "\n"
				+ "    RED\n" + "    RIGHT\n" + "\n" + "S\n" + "\n" + "    SAFE\n" + "    SHIFT\n" + "    SHUT\n"
				+ "    SINK\n" + "    SLEEP\n" + "    STOP\n" + "    SWAP\n" + "\n" + "T\n" + "\n" + "    TELE\n" + "\n"
				+ "U\n" + "\n" + "    UP\n" + "\n" + "W\n" + "\n" + "    WEAK\n" + "    WIN\n" + "    WORD\n" + "\n"
				+ "Y\n" + "\n" + "    YOU\n" + "";
		String s = "";
		for (int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);
			if (c != ' ')
				s += c;

		}

		String[] items = s.split("\n");
		String code = "";

		int index = 0;
		for (int i = 0; i < items.length; i++)
		{
			if (items[i].length() <= 1)
				continue;
			code += items[i] + " = " + index++ + ", ";
		}
		System.out.println(code);
	}

}
