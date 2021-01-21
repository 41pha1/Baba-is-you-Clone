package util;

import java.io.Serializable;

public class Camera implements Serializable
{
	public int scale;
	public float x, y;

	public Camera(int scale, float x, float y)
	{
		this.scale = scale;
		this.x = x;
		this.y = y;
	}
}
