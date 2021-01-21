package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener
{
	public static int x, y;
	public static int dx = 0, dy = 0;
	public static boolean left, right, middle;
	public static boolean leftR, rightR, middleR;
	public static int dw = 0;

	@Override
	public void mouseDragged(MouseEvent e)
	{
		dx += x - e.getX();
		dy += y - e.getY();
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		dx += x - e.getX();
		dy += y - e.getY();
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == 1)
		{
			left = true;
		}
		if (e.getButton() == 3)
		{
			right = true;
		}
		if (e.getButton() == 2)
		{
			middle = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == 1)
		{
			if (left)
				leftR = true;
			left = false;
		}
		if (e.getButton() == 3)
		{
			if (right)
				rightR = true;
			right = false;
		}
		if (e.getButton() == 2)
		{
			if (middle)
				middleR = true;
			middle = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		dw += e.getWheelRotation();
	}

	public static void reset()
	{
		dw = 0;
		dx = 0;
		dy = 0;
		leftR = false;
		rightR = false;
		middleR = false;
	}
}
