package art;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JSlider;

public class ColorBar extends JPanel implements ActionListener {   
    private JButton turquoise;
    private JButton emerald;
    private JButton peter_river;
    private JButton amethyst;
    private JButton wet_asphalt;

    private JButton primaryColor;    

    private Color turquoiseC = new Color(26, 188, 156);
    private Color emeraldC = new Color(46, 204, 113);
    private Color peter_riverC = new Color(52, 152, 219);
    private Color amethystC = new Color(155, 89, 182);
    private Color wet_asphaltC = new Color(52, 73, 94);

    private JToolBar cc;
    private DrawFrame frame;

    private JSlider red;
    private JSlider green;
    private JSlider blue;
    
    private JLabel redLabel;
    private JLabel greenLabel;
    private JLabel blueLabel;
    
    public ColorBar(DrawFrame frame) {
        cc = new JToolBar(JToolBar.HORIZONTAL);
        cc.setLayout(new FlowLayout());
        cc.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        cc.setFloatable(false);
        
        this.frame = frame;
        this.initializeColorChooser(cc);

        turquoise.addActionListener(this);
        emerald.addActionListener(this);
        peter_river.addActionListener(this);
        amethyst.addActionListener(this);
        wet_asphalt.addActionListener(this);

        primaryColor.addActionListener(this);        
    }

    private void initializeColorChooser(JToolBar panel) {
        // create color buttons
        turquoise = new JButton("");
        turquoise.setPreferredSize(new Dimension(50, 25));
        turquoise.setBackground(turquoiseC);

        emerald = new JButton("");
        emerald.setPreferredSize(new Dimension(50, 25));
        emerald.setBackground(emeraldC);

        peter_river = new JButton("");
        peter_river.setPreferredSize(new Dimension(50, 25));
        peter_river.setBackground(peter_riverC);
        amethyst = new JButton("");
        amethyst.setPreferredSize(new Dimension(50, 25));
        amethyst.setBackground(amethystC);
        wet_asphalt = new JButton("");
        wet_asphalt.setPreferredSize(new Dimension(50, 25));
        wet_asphalt.setBackground(wet_asphaltC);

        // create primary color button
        primaryColor = new JButton("");
        primaryColor.setPreferredSize(new Dimension(50, 25));
        primaryColor.setBackground(Color.black);

        // create sliders with labels
        red = new JSlider(0, 255, primaryColor.getBackground().getRed());
        JLabel redLabel = new JLabel("Red: " + red.getValue());
        blue = new JSlider(0, 255, primaryColor.getBackground().getBlue());
        JLabel blueLabel = new JLabel("Blue: " + blue.getValue());
        green = new JSlider(0, 255, primaryColor.getBackground().getGreen());
        JLabel greenLabel = new JLabel("Green: " + green.getValue());

        // add change listeners to update primary color and ink panel color
        red.addChangeListener((e) -> {
            Color newColor = new Color(red.getValue(), green.getValue(), blue.getValue());
            primaryColor.setBackground(newColor);
            frame.getInkPanel().setColor(primaryColor.getBackground());
            redLabel.setText("Red: " + red.getValue());
        });
        blue.addChangeListener((e) -> {
            Color newColor = new Color(red.getValue(), green.getValue(), blue.getValue());
            primaryColor.setBackground(newColor);
            frame.getInkPanel().setColor(primaryColor.getBackground());
            blueLabel.setText("Blue: " + blue.getValue());
        });
        green.addChangeListener((e) -> {
            Color newColor = new Color(red.getValue(), green.getValue(), blue.getValue());
            primaryColor.setBackground(newColor);
            frame.getInkPanel().setColor(primaryColor.getBackground());
            greenLabel.setText("Green: " + green.getValue());
        });

        // add components to panel
        panel.add(turquoise);
        panel.add(emerald);
        panel.add(peter_river);
        panel.add(amethyst);
        panel.add(wet_asphalt);
        
        panel.add(new JLabel("Actual Color: "));
        panel.add(primaryColor);
        panel.add(red);
        panel.add(redLabel);
        panel.add(blue);
        panel.add(blueLabel);
        panel.add(green);
        panel.add(greenLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        primaryColor.setBackground(b.getBackground());
        frame.getInkPanel().setColor(primaryColor.getBackground());
    }
    public JToolBar getToolBar() {
        return this.cc;
    }
}
