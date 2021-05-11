package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

class MainPanel extends JPanel {

	private static final int W = 4;
	private JLabel left, right, top, bottom, topleft, topright, bottomleft, bottomright;
	private JPanel contentPanel = new JPanel(new BorderLayout());


	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		new MainPanel();
	}
	public MainPanel() {
		super();

		setPreferredSize(new Dimension(320, 240));

		JFrame frame = make_frame("CustomDecoratedFrame");
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setMinimumSize(new Dimension(rect.width, rect.height));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel resizePanel = new JPanel(new BorderLayout()) {
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			int w = getWidth();
			int h = getHeight();
			g2.setPaint(Color.ORANGE);
			g2.fillRect(0, 0, w, h);
			g2.setPaint(new Color(100, 100, 100));
			g2.drawRect(0, 0, w - 1, h - 1);
			g2.drawLine(0, 2, 2, 0);
			g2.drawLine(w - 3, 0, w - 1, 2);
			g2.clearRect(0, 0, 2, 1);
			g2.clearRect(0, 0, 1, 2);
			g2.clearRect(w - 2, 0, 2, 1);
			g2.clearRect(w - 1, 0, 1, 2);

			g2.dispose();
		}

	};

	public JFrame make_frame(String str) {
		final JFrame frame = new JFrame(str) {
			@Override
			public Container getContentPane() {
				return contentPanel;
			}
		};
		frame.setUndecorated(true);
		frame.setBackground(new Color(255, 255, 255, 0));

		JButton button = new JButton(new CloseIcon());
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setOpaque(true);
		button.setBackground(Color.ORANGE);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});

		JPanel title = new JPanel(new BorderLayout());
		title.setOpaque(false);
		title.setBorder(BorderFactory.createEmptyBorder(W, W, W, W));
		title.add(new JLabel(str, JLabel.CENTER));
		title.add(button, BorderLayout.EAST);

		ResizeWindowListener rwl = new ResizeWindowListener(frame);
		for (JLabel l : java.util.Arrays.asList(left = new JLabel(), right = new JLabel(), top = new JLabel(), bottom = new JLabel(),
				topleft = new JLabel(), topright = new JLabel(), bottomleft = new JLabel(), bottomright = new JLabel())) {
			l.addMouseListener(rwl);
			l.addMouseMotionListener(rwl);
		}

		Dimension d = new Dimension(W, 0);
		left.setPreferredSize(d);
		left.setMinimumSize(d);
		right.setPreferredSize(d);
		right.setMinimumSize(d);

		d = new Dimension(0, W);
		top.setPreferredSize(d);
		top.setMinimumSize(d);
		bottom.setPreferredSize(d);
		bottom.setMinimumSize(d);

		d = new Dimension(W, W);
		topleft.setPreferredSize(d);
		topleft.setMinimumSize(d);
		topright.setPreferredSize(d);
		topright.setMinimumSize(d);
		bottomleft.setPreferredSize(d);
		bottomleft.setMinimumSize(d);
		bottomright.setPreferredSize(d);
		bottomright.setMinimumSize(d);

		left.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		right.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		top.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		bottom.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		topleft.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
		topright.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
		bottomleft.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
		bottomright.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));

		JPanel titlePanel = new JPanel(new BorderLayout(0, 0));
		titlePanel.add(top, BorderLayout.NORTH);
		titlePanel.add(title, BorderLayout.CENTER);

		JPanel northPanel = new JPanel(new BorderLayout(0, 0));
		northPanel.add(topleft, BorderLayout.WEST);
		northPanel.add(titlePanel, BorderLayout.CENTER);
		northPanel.add(topright, BorderLayout.EAST);

		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(bottomleft, BorderLayout.WEST);
		southPanel.add(bottom, BorderLayout.CENTER);
		southPanel.add(bottomright, BorderLayout.EAST);

		resizePanel.add(left, BorderLayout.WEST);
		resizePanel.add(right, BorderLayout.EAST);
		resizePanel.add(northPanel, BorderLayout.NORTH);
		resizePanel.add(southPanel, BorderLayout.SOUTH);
		resizePanel.add(contentPanel, BorderLayout.CENTER);

		titlePanel.setOpaque(false);
		northPanel.setOpaque(false);
		southPanel.setOpaque(false);

		contentPanel.setOpaque(false);
		resizePanel.setOpaque(false);
		frame.setContentPane(resizePanel);
		return frame;
	}

	class ResizeWindowListener extends MouseAdapter {
		private final JFrame frame;

		public ResizeWindowListener(JFrame frame) {
			this.frame = frame;
		}
	}
}

class DragWindowListener extends MouseAdapter {
	private MouseEvent start;

	private Window window;

	@Override
	public void mousePressed(MouseEvent me) {
		start = me;
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (window == null) {
			window = SwingUtilities.windowForComponent(me.getComponent());
		}
		Point eventLocationOnScreen = me.getLocationOnScreen();
		window.setLocation(eventLocationOnScreen.x - start.getX(), eventLocationOnScreen.y - start.getY());
	}
}

class CloseIcon implements Icon {
	private int width;
	private int height;

	public CloseIcon() {
		width = 16;
		height = 16;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.translate(x, y);
		g.setColor(Color.BLACK);
		g.drawLine(4, 4, 11, 11);
		g.drawLine(4, 5, 10, 11);
		g.drawLine(5, 4, 11, 10);
		g.drawLine(11, 4, 4, 11);
		g.drawLine(11, 5, 5, 11);
		g.drawLine(10, 4, 4, 10);
		g.translate(-x, -y);
	}

	@Override
	public int getIconWidth() {
		return width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}
}
