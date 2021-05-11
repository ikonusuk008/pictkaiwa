package test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class InternalFrameJFrame {
  public JComponent makeUI() {
    final JInternalFrame internal = new JInternalFrame("@title@");
    BasicInternalFrameUI ui = (BasicInternalFrameUI)internal.getUI();
    Component title = ui.getNorthPane();
    for (MouseMotionListener l:title.getListeners(MouseMotionListener.class)) {
      title.removeMouseMotionListener(l);
    }
    DragWindowListener dwl = new DragWindowListener();
    title.addMouseListener(dwl);
    title.addMouseMotionListener(dwl);
    JPanel p = new JPanel(new BorderLayout());
    p.add(new JScrollPane(new JTree()));
    p.add(new JButton(new AbstractAction("close") {
      @Override public void actionPerformed(ActionEvent e) {
        Window w = SwingUtilities.windowForComponent((Component)e.getSource());
        //w.dispose();
        w.getToolkit().getSystemEventQueue().postEvent(
          new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
      }
    }), BorderLayout.SOUTH);
    internal.getContentPane().add(p);
    internal.setVisible(true);

    KeyboardFocusManager focusManager =
        KeyboardFocusManager.getCurrentKeyboardFocusManager();
    focusManager.addPropertyChangeListener(new PropertyChangeListener() {
      @Override public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
        //System.out.println(prop);
        if ("activeWindow".equals(prop)) {
          try {
            internal.setSelected(e.getNewValue()!=null);
          } catch (PropertyVetoException ex) {
            ex.printStackTrace();
          }
          //System.out.println("---------------------");
        }
      }
    });

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(internal);
    //panel.setOpaque(false);
    panel.setBorder(BorderFactory.createEmptyBorder(0,0,4,4));
    panel.setBackground(new Color(1,1,1,.01f));
    panel.setPreferredSize(new Dimension(320, 200));
    return panel;
  }
  static class DragWindowListener extends MouseAdapter {
    private MouseEvent start;
    private Point  loc;
    private Window window;
    @Override public void mousePressed(MouseEvent me) {
      start = me;
    }
    @Override public void mouseDragged(MouseEvent me) {
      if (window==null) {
        window = SwingUtilities.windowForComponent(me.getComponent());
      }
      loc = window.getLocation(loc);
      int x = loc.x - start.getX() + me.getX();
      int y = loc.y - start.getY() + me.getY();
      window.setLocation(x, y);
    }
  }
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        createAndShowGUI();
      }
    });
  }
  public static void createAndShowGUI() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    JFrame frame = new JFrame();
    //frame.setUndecorated(true);
  // com.sun.awt.AWTUtilities.setWindowOpaque(frame, false);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      for (UIManager.LookAndFeelInfo laf:UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(laf.getName()))
          UIManager.setLookAndFeel(laf.getClassName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    JRootPane root = frame.getRootPane();
    JLayeredPane layeredPane = root.getLayeredPane();
    Component c = layeredPane.getComponent(1);
    if (c instanceof JComponent) {
      JComponent oldTitlePane = (JComponent)c;
      System.out.println(c);
      oldTitlePane.setVisible(false);
      layeredPane.remove(oldTitlePane);
    }
    JComponent titlePane = new JLabel();
    layeredPane.add(titlePane, JLayeredPane.FRAME_CONTENT_LAYER);
    titlePane.setVisible(true);

    frame.setMinimumSize(new Dimension(300, 120));
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new InternalFrameJFrame().makeUI());
    //JDK 1.7 frame.setBackground(new Color(0,0,0,0));
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
