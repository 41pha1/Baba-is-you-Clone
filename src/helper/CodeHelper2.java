package helper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CodeHelper2
{

	public static void main(String[] args)
	{
		String input = "Text ALGAE 0 ALGAE 0 ALGAE #5c8339 Text AND 0 AND #ffffff Text BEST 0 BEST #ede285\n"
				+ "Text ALL 0 ALL #ffffff Text FACING 0 FACING #ffffff Text BLUE 0 BLUE #9183d7\n"
				+ "Text ANNI 0 ANNI 0 ANNI #90673e Text HAS 0 HAS #ffffff Text BONUS 0 BONUS #d9396a\n"
				+ "Text BABA 0 BABA 0 BABA #d9396a Text IS 0 IS #ffffff Text DEFEAT 0 DEFEAT #82261c\n"
				+ "Text BAT 0 BAT 0 BAT #682e4c Text LONELY 0 LONELY #e5533b Text DONE 0 DONE #ffffff\n"
				+ "Text BELT 0 BELT 0 BELT #5f9dd1 Text MAKE 0 MAKE #ffffff Text DOWN 0 DOWN #83c8e5\n"
				+ "Text BIRD 0 BIRD 0 BIRD #e5533b Text NEAR 0 NEAR #ffffff Text END 0 END #ffffff\n"
				+ "Text BOG 0 BOG 0 BOG #5c8339 Text NOT 0 NOT #e5533b Text FALL 0 FALL #a5b13f\n"
				+ "Text BOLT 0 BOLT 0 BOLT #e49950 Text ON 0 ON #ffffff Text FLOAT 0 FLOAT #83c8e5\n"
				+ "Text BOX 0 BOX 0 BOX #90673e Text HIDE 0 HIDE #9183d7\n"
				+ "Text BRICK 0 BRICK 0 BRICK #90673e Text HOT 0 HOT #d6ad63\n"
				+ "Text BUG 0 BUG 0 BUG #503f24 Text LEFT 0 LEFT #83c8e5\n"
				+ "Text CAKE 0 CAKE 0 CAKE #d9396a Text MELT 0 MELT #397394\n"
				+ "Text CLIFF 0 CLIFF 0 CLIFF #293141 Text MORE 0 MORE #d9396a\n"
				+ "Text CLOUD 0 CLOUD 0 CLOUD #83c8e5 Text MOVE 0 MOVE #a5b13f\n"
				+ "Text COG 0 COG 0 COG #737373 Text OPEN 0 OPEN #ede285\n"
				+ "Text CRAB 0 CRAB 0 CRAB #82261c Text PULL 0 PULL #c29e46\n"
				+ "Text CURSOR 0 CURSOR #e49950 Text PUSH 0 PUSH #90673e\n"
				+ "Text DOOR 0 DOOR 0 DOOR #397394 Text RED 0 RED #e5533b\n"
				+ "Text DUST 0 DUST 0 DUST #ede285 Text RIGHT 0 RIGHT #83c8e5\n"
				+ "Text EMPTY 0 EMPTY #ffffff Text SHIFT 0 SHIFT #5f9dd1\n"
				+ "Text FENCE 0 FENCE 0 FENCE #90673e Text SHUT 0 SHUT #e5533bas\n"
				+ "Text FIRE 0 FIRE 0 FIRE #421910 Text SINK 0 SINK #5f9dd1\n"
				+ "Text FLAG 0 FLAG 0 FLAG #ede285 Text SLEEP 0 SLEEP #83c8e5\n"
				+ "Text FLOWER 0 FLOWER 0 FLOWER #9183d7 Text STOP 0 STOP #4b5c1c\n"
				+ "Text FOLIAGE 0 FOLIAGE 0 FOLIAGE #4b5c1c Text SWAP 0 SWAP #8e5e9c\n"
				+ "Text FRUIT 0 FRUIT 0 FRUIT #e5533b Text TELE 0 TELE #83c8e5\n"
				+ "Text FUNGUS 0 FUNGUS 0 FUNGUS #90673e Text UP 0 UP #83c8e5\n"
				+ "Text GHOST 0 GHOST 0 GHOST #eb91ca Text WEAK 0 WEAK #3e7688\n"
				+ "Text GRASS 0 GRASS 0 GRASS #a5b13f Text WIN 0 WIN #ede285\n"
				+ "Text GROUP 0 GROUP #9183d7 Text WORD 0 WORD #ffffff\n"
				+ "Text HAND 0 HAND 0 HAND #ffffff Text YOU 0 YOU #d9396a ";

		String[] lines = input.split("\n");
		String code = "";

		BufferedImage sheet = loadSpriteSheet(new File("res/textures.png"));
		Color[][] pallet = loadPallets(sheet);

		for (int i = 0; i < lines.length; i++)
		{
			System.out.println(lines[i]);
			String[] tokens = lines[i].split(" ");

			for (int j = tokens.length - 1; j >= 0; j--)
			{
				if (tokens[j].charAt(0) == '#')
				{
					Color c = hex2Rgb(tokens[j]);
					int ColorID = -1;
					for (int x = 0; x < pallet.length; x++)
					{
						for (int y = 0; y < pallet[0].length; y++)
						{
							if (c.equals(pallet[x][y]))
								ColorID = x + y * 7;
						}
					}
					if (ColorID != -1)
					{
						code += ColorID + ", ";
					} else
					{
						if (c.getRed() == 214)
						{
							code += 20 + ", ";
						} else if (c.getRed() == 57)
						{
							code += 15 + ", ";
						} else if (c.getRed() == 206)
						{
							code += 23 + ", ";
						}
					}
					break;
				}
			}
		}
		System.out.println(code);
	}

	public static Color hex2Rgb(String colorStr)
	{
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	public static Color[][] loadPallets(BufferedImage spriteSheet)
	{
		Color[][] pallets = new Color[7][5];

		int px = 492;
		int py = 28;

		for (int dx = 0; dx < 7; dx++)
		{
			for (int dy = 0; dy < 5; dy++)
			{
				pallets[dx][dy] = new Color(spriteSheet.getRGB(px + dx * 8, py + dy * 8));
			}
		}

		return pallets;
	}

	public static BufferedImage loadSpriteSheet(File file)
	{
		try
		{
			return ImageIO.read(file);
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
