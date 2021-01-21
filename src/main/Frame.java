package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import input.Keyboard;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public int width, height;
	public BufferedImage screen;
	public Color frameColor = new Color(0, 0, 0);

	public Frame(int w, int h)
	{
		this.width = w;
		this.height = h;
		screen = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		setSize(w, h);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setResizable(false);
		addKeyListener(new Keyboard());
	}

	public void setV()
	{
		setVisible(true);
	}

	@Override
	public void paint(Graphics fin)
	{
		Graphics2D g = screen.createGraphics();
		g.setColor(frameColor);
		g.fillRect(0, 0, width, height);

		g.translate(width / 2, height / 2);
		Main.simulation.render(g);

		fin.drawImage(screen, 0, 0, null);
	}
}
