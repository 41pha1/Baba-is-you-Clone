package texture;

import java.io.Serializable;

public class Animator implements Serializable
{
	private int speed;
	private int frame;
	private int step;
	public int time;
	public int turnTex;

	public Animator(int speed)
	{
		this.speed = speed;
	}

	public void udpate()
	{
		time++;
		frame++;
		if (frame > speed)
		{
			frame = 0;
			step++;
			step %= 3;
		}
	}

	public int getWobbleState()
	{
		return step;
	}
}
