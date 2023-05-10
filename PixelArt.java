package pixelArt;

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
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

		frame.setVisible(true);
	}
}

