package texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader
{
	public static int CROSS = 0;
	public static BufferedImage[][] sprites;
	public static final int nSprites = 173;
	public static Color[][][] pallets;
	public static int currentPallet = 0;
	public static BufferedImage[][][] textures;
	public static BufferedImage[][] icons;

	public static int[] Color = { 19, 21, 13, 11, 4, 22, 16, 19, 23, 13, 13, 29, 6, 11, 8, 29, 7, 9, 23, 16, 30, 21, 13, 2, 30, 24, 12, 16, 13, 18, 26, 24, 21, 12, 22, 19, 22, 23, 30, 23, 30, 11, 21,
			18, 10, 30, 11, 7, 8, 7, 13, 23, 16, 6, 9, 30, 7, 23, 11, 7, 19, 11, 24, 7, 22, 30, 24, 11, 9, 21, 29, 21, 26, 29, 24, 20, 29, 15, 11, 26, 30, 20, 13, 16, 29, 21, 22, 16, 22, 29, 12, 10,
			29, 29, 15, 30, 21, 11, 21, 21, 21, 21, 21, 16, 21, 21, 16, 21 };

	public static int[] ThingColor = { 19, 21, 20, 21, 10, 8, 23, 19, 30, 20, 27, 29, 13, 18, 17, 29, 7, 16, 23, 16, 23, 21, 13, 23, 30, 24, 5, 16, 13, 18, 5, 24, 21, 12, 15, 19, 29, 23, 30, 23, 30,
			11, 21, 18, 10, 30, 11, 7, 8, 7, 20, 30, 16, 27, 9, 30, 7, 30, 11, 1, 19, 18, 19, 8, 22 };

	public static int[] xo = { 0, 0, 0, 0, 16, 16, 20, 16, 24, 1, 0, 28, 0, 21, 16, 0, 4, 8, 0, 3, 4, 0, 16, 5, 6, 7, 6, 8, 9, 0, 0, 0, 4, 16, 0, 0, 11, 0, 10, 16, 13, 0, 16, 11, 0, 12, 13, 14, 16, 0,
			15, 12, 16, 0, 8, 15, 12, 19, 0, 19, 20, 22, 7, 0, 16 };

	public static int[] yo = { 21, 0, 9, 0, 15, 18, 15, 57, 15, 21, 45, 15, 18, 21, 45, 48, 18, 18, 0, 21, 21, 0, 48, 24, 21, 21, 24, 21, 21, 15, 51, 0, 15, 51, 54, 0, 24, 3, 21, 57, 24, 0, 54, 21, 6,
			21, 21, 21, 60, 12, 21, 18, 21, 63, 15, 24, 15, 24, 0, 21, 21, 24, 21, 57, 57 };

	public static int[] nx = { 1, 0, 20, 20, 4, 16, 4, 16, 4, 1, 16, 4, 4, 1, 16, 16, 4, 4, 0, 1, 1, 0, 16, 1, 1, 1, 1, 1, 1, 4, 16, 0, 4, 16, 16, 0, 1, 20, 1, 16, 1, 0, 16, 1, 20, 1, 1, 1, 16, 16, 1,
			4, 1, 16, 4, 1, 4, 1, 0, 1, 1, 1, 1, 16, 16 };

	public static int[] textXo = { 1, 2, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 20, 21, 23, 24, 26, 28, 31, 0, 1, 2, 3, 5, 7, 8, 9, 10, 11, 13, 15, 17, 19, 20, 21, 24, 23, 25, 26, 28, 30,
			31, 4, 5, 6, 10, 11, 12, 13, 14, 16, 18, 19, 24, 21, 22, 23, 25, 26, 27, 28, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 1, 2, 3, 4, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 19,
			20, 3, 29, 12, 16, 18, 27, 29, 0, 1, 3 };

	public static int[] textYo = { 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
			30, 30, 30, 30, 30, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 42, 42, 42, 42, 42, 42, 42,
			42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 27, 27, 30, 30, 30, 30, 30, 33, 33, 33 };

	public static void loadSprites(int pallet)
	{
		currentPallet = pallet;
		BufferedImage sheet = loadSpriteSheet(new File("res/textures.png"));
		sprites = extractSprites(sheet, 24);
		pallets = loadPallets(sheet);
		textures = loadTextures();
		icons = loadIcons();
	}

	public static BufferedImage[][] loadIcons()
	{
		BufferedImage[][] result = new BufferedImage[1][3];

		for (int y = 0; y < 3; y++)
		{
			result[0][y] = giveColor(sprites[19][36 + y], pallets[currentPallet], 2, 0);
		}
		return result;
	}

	public static void setColor(int type, int col)
	{
		type /= 2;
		int Xo = xo[type];
		int Yo = yo[type];
		int Nx = nx[type];
		int col2 = 0;

		BufferedImage[][] result = new BufferedImage[Nx][3];

		for (int x = 0; x < Nx; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				result[x][y] = giveColor(sprites[Xo + x][Yo + y], pallets[currentPallet], col, col2);
			}
		}
		textures[type * 2] = result;
	}

	public static void resetColor(int type)
	{
		type /= 2;
		int Xo = xo[type];
		int Yo = yo[type];
		int Nx = nx[type];
		int col1 = ThingColor[type];

		BufferedImage[][] result = new BufferedImage[Nx][3];

		for (int x = 0; x < Nx; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				result[x][y] = giveColor(sprites[Xo + x][Yo + y], pallets[currentPallet], col1, 28);
			}
		}
		textures[type * 2] = result;
	}

	public static BufferedImage[][][] loadTextures()
	{
		BufferedImage[][][] textures = new BufferedImage[nSprites][][];

		for (int id = 0; id < 65; id++)
		{
			textures[id * 2] = getThingTextures(id);
			textures[id * 2 + 1] = getLogicTextures(id);
		}
		for (int id = 130; id < 173; id++)
		{
			textures[id] = getLogicTextures(id - 65);
		}

		return textures;
	}

	public static BufferedImage[][] getLogicTextures(int type)
	{
		int Xo = textXo[type];
		int Yo = textYo[type];
		int col1 = Color[type];
		// int col2 = 0;

		BufferedImage[][] result = new BufferedImage[2][3];

		for (int y = 0; y < 3; y++)
		{
			result[0][y] = giveColor(sprites[Xo][Yo + y], pallets[currentPallet], col1, 0);
			result[1][y] = giveColor(sprites[Xo][Yo + y], pallets[currentPallet], Math.max(0, col1 - 7), 0);
		}
		return result;
	}

	public static BufferedImage[][] getThingTextures(int type)
	{
		int Xo = xo[type];
		int Yo = yo[type];
		int Nx = nx[type];
		int col1 = ThingColor[type];
		int col2 = 28;

		BufferedImage[][] result = new BufferedImage[Nx][3];

		for (int x = 0; x < Nx; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				result[x][y] = giveColor(sprites[Xo + x][Yo + y], pallets[currentPallet], col1, col2);
			}
		}
		return result;
	}

	public static Color[][][] loadPallets(BufferedImage spriteSheet)
	{
		Color[][][] pallets = new Color[16][7][5];

		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				int px = 492 + x * 72;
				int py = 28 + y * 66;

				for (int dx = 0; dx < 7; dx++)
				{
					for (int dy = 0; dy < 5; dy++)
					{
						pallets[x % 4 + y * 4][dx][dy] = new Color(spriteSheet.getRGB(px + dx * 8, py + dy * 8));
					}
				}
			}
		}

		return pallets;
	}

	public static BufferedImage giveColor(BufferedImage input, Color[][] pallets, int color1, int color2)
	{
		BufferedImage colored = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < input.getWidth(); x++)
		{
			for (int y = 0; y < input.getHeight(); y++)
			{
				Color or = new Color(input.getRGB(x, y));

				if (or.getRed() >= 247)
				{
					colored.setRGB(x, y, pallets[color1 % 7][color1 / 7].getRGB());
				} else if (or.getRed() > 10)
				{
					colored.setRGB(x, y, pallets[color2 % 7][color2 / 7].getRGB());
				}
			}
		}

		return colored;
	}

	public static Color getColor(int col)
	{
		return pallets[currentPallet][col % 7][col / 7];
	}

	public static BufferedImage[][] extractSprites(BufferedImage sheet, int spriteScale)
	{
		int w = sheet.getWidth();
		int h = sheet.getHeight();
		int nw = w / spriteScale;
		int nh = h / spriteScale;

		BufferedImage[][] sprites = new BufferedImage[nw][nh];

		for (int x = 0; x < w; x += spriteScale)
		{
			for (int y = 0; y < h; y += spriteScale)
			{
				sprites[x / spriteScale][y / spriteScale] = new BufferedImage(spriteScale, spriteScale, BufferedImage.TYPE_INT_ARGB);
				sprites[x / spriteScale][y / spriteScale].getGraphics().drawImage(sheet, 0, 0, spriteScale, spriteScale, x, y, x + spriteScale, y + spriteScale, null);
			}
		}
		return sprites;
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
