package com.gogo.mcqsimulator;

import javax.swing.SwingUtilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class UseMCQ
{
	public static void main(String... args)
	{
		final String defaultFiles[] = { "20", "20", "600", "resources\\",
				"resources\\q", "resources\\sol",
				"resources\\participants.txt", "newuser", "adminPanel" };
		SwingUtilities.invokeLater(new Runnable()
		{
			String fileData[] = new String[9];

			public void run()
			{
				BufferedReader br = null;
				try
				{
					br = new BufferedReader(new FileReader("config.txt"));
					for (int i = 0; i < 9; i++)
						try
						{
							fileData[i] = br.readLine();
						}
						catch (IOException ioe)
						{
							System.out
									.println("error reading contents of file, attempting to read from default path");
							new MCQ(defaultFiles);
							br.close();
							return;
						}
					br.close();
				}
				catch (IOException ioe)
				{
					System.out
							.println("Error reading configration file, attempting to read from default path");
					new MCQ(defaultFiles);
					return;
				}
				new MCQ(fileData);
			}
		});
	}
}