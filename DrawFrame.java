package art;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class DrawFrame extends JFrame {

    private JPanel contentPane;
    private PaintPanel inkPanel;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JToolBar cc1;

    private JScrollPane sp;

    private final int CONTENT_PANE_WIDTH = 700;
    private final int CONTENT_PANE_HEIGHT = 500;

    private int inkPanelWidth;
    private int inkPanelHeight;
    private final Color background = Color.GRAY;

    public DrawFrame() {
        
        inkPanelWidth = 700;
        inkPanelHeight = 500;
        contentPane = new JPanel();
        contentPane.setLayout(null);
        toolBar = (new ActionsBar(this)).getToolBar();

        cc1 = (new ColorBar(this)).getToolBar();

        inkPanel = new PaintPanel(0, this, inkPanelWidth, inkPanelHeight, 30);

        this.add(cc1, BorderLayout.PAGE_START);
        sp = new JScrollPane();
        sp.setLocation(10, 10);
        sp.setViewportView(inkPanel);
        sp.setSize(inkPanelWidth, inkPanelHeight);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(sp);
        contentPane.setBackground(background);

        this.addWindowListener(new WindowCloser());

        this.setJMenuBar(menuBar);

        this.add(toolBar, BorderLayout.WEST);
        this.add(contentPane);

        this.setSize(CONTENT_PANE_WIDTH, CONTENT_PANE_HEIGHT);
        this.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH, CONTENT_PANE_HEIGHT));
    }

    private class WindowCloser extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent event) {
            System.exit(0);
        }
    }

    public PaintPanel getInkPanel() {
        return this.inkPanel;
    }

    public DrawFrame getDrawFrame() {
        return this;
    }

    public JScrollPane getSP() {
        return this.sp;
    }

}
