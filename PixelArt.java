package art;

import java.awt.*;
import javax.swing.*;


public class PixelArt
{
	public static void main(String[] args)
	{
		DrawFrame frame = new DrawFrame();
		frame.setTitle("Draw");
		frame.setBackground( new Color(39,174,96));
		frame.pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(800 , 600 );
		

		frame.setVisible(true);
	}
}

