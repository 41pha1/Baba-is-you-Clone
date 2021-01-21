package levelEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import input.Keyboard;
import input.Mouse;

public class EditorFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public int width, height;
	public BufferedImage screen;

	public EditorFrame(int w, int h)
	{
		this.width = w;
		this.height = h;
		screen = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		setSize(w, h);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setResizable(false);
		addKeyListener(new Keyboard());
		addMouseListener(new Mouse());
		addMouseWheelListener(new Mouse());

		addMouseMotionListener(new Mouse());
	}

	public void setV()
	{
		setVisible(true);
	}

	public void paint(Graphics fin)
	{
		Graphics2D g = screen.createGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);

		LevelEditor.render(g);

		fin.drawImage(screen, 0, 0, null);
	}
}
