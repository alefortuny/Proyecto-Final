package pixelArt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final int BORRAR = 0;
    private final int PINTAR = 1;

    private BasicStroke stroke = new BasicStroke((float) 10);
    BufferedImage canvas;
    Graphics2D graphics2D;
    private int activeTool = 1;
    

    private Stack<Shape> shapes;
    private Stack<Shape> removed;
    private Stack<Shape> preview;

    private int grouped;

    int x1, y1, x2, y2;

    private Color currentColor;
    private Color fillColor;
    private int pixelSize;

    private int inkPanelWidth;
    private int inkPanelHeight;

    public PaintPanel() {
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setPreferredSize(new Dimension(250, 250));
        this.shapes = new Stack<>();
    }
    
    public PaintPanel(int f, DrawFrame frame, int width, int height, int pixelSize) {        
    	this.pixelSize = pixelSize;
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        inkPanelWidth = dim.width - 150;
        inkPanelHeight = dim.height - 160;
        this.setSize(inkPanelWidth - 3, inkPanelHeight - 3); 
        this.setPreferredSize(new Dimension(inkPanelWidth - 3, inkPanelHeight - 3));
        this.setLayout(null);
        setDoubleBuffered(true);
        setLocation(20, 20);
        setBackground(Color.WHITE);
        currentColor = Color.BLACK;
        this.fillColor = Color.white;
        setFocusable(true);
        requestFocus();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.shapes = new Stack<Shape>();
        this.removed = new Stack<Shape>();
        this.grouped = 1;
        this.preview = new Stack<Shape>();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (canvas == null) {
            canvas = new BufferedImage(inkPanelWidth, inkPanelHeight, BufferedImage.TYPE_INT_ARGB);
            graphics2D = canvas.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(canvas, 0, 0, null);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape s : shapes) {

            g2.setColor(s.getColor());
            g2.setStroke(s.getStroke());

            if (s.getShape() == PINTAR) {
                g2.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            }
        }
        if (preview.size() > 0) {
            Shape s = preview.pop();
            g2.setColor(s.getColor());
            g2.setStroke(s.getStroke());
            if (s.getShape() == PINTAR) {
                g2.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            }
        }
    }

    public void setTool(int tool) {
        this.activeTool = tool;
    }

    public void setImage(BufferedImage image) {
        graphics2D.dispose();
        this.setInkPanel(image.getWidth(), image.getHeight());
        canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        shapes.removeAllElements();
        removed.removeAllElements();
        repaint();
        graphics2D.setColor(currentColor);
        
        BasicStroke strokeLine = new BasicStroke((float) 1);
        for (int i = 0; i < getSize().width ; i += 10) {
            shapes.push(new Shape(i, getSize().width + i, i, 0, Color.BLACK, strokeLine , 1, grouped));
        }
       
        for (int i = 0; i < getSize().width ; i += 10) {
            shapes.push(new Shape(getSize().width + i, i, 0, i, Color.BLACK, strokeLine, 1, grouped));
        }
        
     
    }

    public void setColor(Color c) {
        currentColor = c;
        graphics2D.setColor(c);
    }



    @Override
    public void mouseDragged(MouseEvent e) {
    	int x = e.getX() / pixelSize;
        int y = e.getY() / pixelSize;
        fillPixel(x, y);
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX() / pixelSize;
        int y = e.getY() / pixelSize;
        fillPixel(x, y);
    }

    private void fillPixel(int x, int y) {
        int startX = x * pixelSize;
        int startY = y * pixelSize;
        graphics2D.setColor(currentColor);
        graphics2D.fillRect(startX, startY, pixelSize, pixelSize);
        repaint(startX, startY, pixelSize, pixelSize);
    }


    private void fill(int x, int y, Color oldColor) {
        if (x < 0 || y < 0 || x >= canvas.getWidth() / pixelSize || y >= canvas.getHeight() / pixelSize) {
            return;
        }
        if (canvas.getRGB(x * pixelSize, y * pixelSize) == oldColor.getRGB()) {
            graphics2D.setColor(currentColor);
            graphics2D.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
            canvas.setRGB(x * pixelSize, y * pixelSize, currentColor.getRGB());
            fill(x - 1, y, oldColor);
            fill(x + 1, y, oldColor);
            fill(x, y - 1, oldColor);
            fill(x, y + 1, oldColor);
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        grouped++;

        removed.removeAllElements();
        repaint();
    }

    public void setInkPanelWidth(int width) {
        this.inkPanelWidth = width;
    }

    public void setInkPanelHeight(int height) {
        this.inkPanelHeight = height;
    }

    public void setInkPanel(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.setSize(width - 3, height - 3);
        this.setPreferredSize(new Dimension(width - 3, height - 3));
        clear();

    }

}
