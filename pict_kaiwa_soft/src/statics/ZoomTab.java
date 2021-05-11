package statics;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import test_tool.l;

public class ZoomTab extends JComponent implements Icon {

	private static final long serialVersionUID = 1L;
	private ImageIcon icon = null;
	private int x = 0;
	private int y = 0;
	private int h = 0;
	private int w = 0;
	private double scale = 1.0d;

	public ZoomTab(ImageIcon icon, int x, int y, int w, int h) {
		super();
		//ConsorlLog.class_(this.getClass(), "ZoomTab");

		this.icon = icon;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = g2.getTransform();
		g2.scale(scale, scale);
		g2.drawImage(icon.getImage(), x, y, w, h, this);
		g2.setTransform(at);
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;

		//ここら辺のことは、やった方がいいのか、やらなくていいのか、よくわかりません。
		AffineTransform at = g2.getTransform();

		g2.drawImage(icon.getImage(), x, y, w, h, c);

		g2.setTransform(at);
		//↑これも
	}

	public int getIconWidth() {
		//scale を変更したときは、このままでは、まずいかも。
		return w;
	}

	public int getIconHeight() {
		//ここも
		return h;
	}
}
