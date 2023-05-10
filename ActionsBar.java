package pixelArt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ActionsBar implements ActionListener {

    private JToolBar toolBar;
    private JButton save;
    private JButton open;
    private JButton clear;
    private Dimension newDimensions = new Dimension(700, 500);

    private JFileChooser fc;
    private File f;
    private DrawFrame frame;

    public ActionsBar(DrawFrame frame) {
        this.frame = frame;
        fc = new JFileChooser(new File("."));
        fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
        this.initializeToolBar();

        clear.addActionListener(this);
        save.addActionListener(this);
        open.addActionListener(this);
    }

    private void initializeToolBar() {

        toolBar = new JToolBar(JToolBar.VERTICAL);
        
        toolBar.setFloatable(false);
        toolBar.setLayout(new GridLayout(18, 0));

        open = new JButton("Abrir");
        save = new JButton("Guardar");
        clear = new JButton("Limpiar");

        toolBar.add(open);
        toolBar.add(save);
        toolBar.add(clear);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == clear) {
            frame.getInkPanel().clear();
        } else if (source == open) {
            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                f = fc.getSelectedFile();
                openFile(f);
            }
        } else if (source == save) {            
            if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                f = new File(fc.getSelectedFile() + ".png");
                try {
                    saveFile(f);
                } catch (IOException e1) {                    
                    e1.printStackTrace();
                }
            }
        } else {
            JButton b = (JButton) source;
            frame.getInkPanel().setColor(b.getBackground());
        }
    }

    public JToolBar getToolBar() {
        return this.toolBar;
    }

    private void openFile(File f) {
        try {
            frame.getInkPanel().setImage(ImageIO.read(f));
            newDimensions = new Dimension(ImageIO.read(f).getWidth(), ImageIO.read(f).getHeight());
            frame.getSP().setSize(newDimensions.width, newDimensions.height);
        } catch (IOException e) {            
            e.printStackTrace();
        }
    }

    private void saveFile(File f) throws IOException {
        BufferedImage im = makePanel(frame.getInkPanel());
        ImageIO.write(im, "png", f);
    }

    private BufferedImage makePanel(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.print(g);
        return bi;
    }
}
