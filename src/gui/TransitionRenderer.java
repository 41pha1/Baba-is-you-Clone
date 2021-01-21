package gui;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Main;

public class TransitionRenderer
{
	private Color color;
	private float speed;
	public float progress;
	private int w, h;

	public void start(Color base, float speed)
	{
		color = base;
		this.speed = speed;
		progress = 0;
		w = Main.frame.width;
		h = Main.frame.height;
	}

	public void reverse()
	{
		speed = -speed;
	}

	public void renderCloudPart(Graphics2D g, float a, float d, float r)
	{
		float cx = (float) Math.cos(Math.toRadians(a)) * d;
		float cy = (float) Math.sin(Math.toRadians(a)) * d;
		cx *= w / 2;
		cy *= h / 2;
		int cr = (int) (r * progress * w);
		g.fillOval((int) (cx - cr / 2), (int) (cy - cr / 2), cr, cr);
	}

	public boolean render(Graphics2D g)
	{
		g.setColor(color);

		renderCloudPart(g, 20, 2, 3);
		renderCloudPart(g, 70, 2, 2);
		renderCloudPart(g, 90, 2, 2);
		renderCloudPart(g, 140, 2, 2);
		renderCloudPart(g, 190, 2, 3);
		renderCloudPart(g, 240, 2, 2);
		renderCloudPart(g, 280, 2, 2);
		renderCloudPart(g, 310, 2, 2);
		renderCloudPart(g, 350, 2, 2);

		progress += speed;

		if (progress >= 0.7)
		{
			progress = 0.7f;
			return true;
		}
		return false;
	}
}
