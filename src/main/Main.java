package main;

public class Main
{
	public static int FPS = 60;
	public static Frame frame;
	public static Simulation simulation;

	public static void main(String[] args)
	{
		frame = new Frame(1920, 1080);
		simulation = new Simulation();
		frame.setV();

		long lastFrame = System.nanoTime();
		int count = 0;
		float avgFPS = 0;

		while (true)
		{
			long time = System.nanoTime();
			if (time - lastFrame > (1000000000 / FPS))
			{
				avgFPS += 1000000000f / (time - lastFrame);
				count++;
				if (count > 60)
				{
					System.out.println("FPS: " + (int) (avgFPS / 60));
					count = 0;
					avgFPS = 0;
				}
				lastFrame = time;
				frame.repaint();
				simulation.update();
			}
		}
	}
}
