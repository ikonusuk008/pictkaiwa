package ekigo_tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main_frame.Kaiwa;
import main_frame.Kaiwa.MyKey;
import statics.Koe;
import statics.Zoom;

public class Ekigo extends JFrame{

	// イベント
	MyMouse mouse = new MyMouse();
	MyKey key = new MyKey();

	// レイアウト
	GridBagLayout Buttonlayout = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	JButton b = new JButton();
	JButton b2 = new JButton();
	JPanel p;

	public Ekigo(String title2,String title) {
		this.setUndecorated(true);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		this.setBounds(rect);

		b.setBackground(Color.black);
		b.addKeyListener(key);
		b.addMouseListener(mouse);

		b2.setText(title2);
		b2.setFont(new Font("",Font.BOLD,50));
		b2.setForeground(Color.GREEN);
		b2.setBackground(Color.black);
		b2.addMouseListener(mouse);
		b2.addKeyListener(key);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.0d;
		gbc.fill = GridBagConstraints.BOTH;
		Buttonlayout.setConstraints(b, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		gbc.weighty = 0.001;
		gbc.fill = GridBagConstraints.BOTH;
		Buttonlayout.setConstraints(b2, gbc);

		p =new JPanel(Buttonlayout);
		Zoom ZoomImg = new Zoom(new ImageIcon("./resource/img/" + title + "/" + title2+".jpg"), 5, 5,
				500, 500);
		b.add(ZoomImg);

		p.add(b);
		p.add(b2);

		this.add(p);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Ekigo("あつい","tab2");
	}

	public class MyKey extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			if (KeyEvent.getKeyText(e.getKeyChar()).equals("0") || KeyEvent.getKeyText(e.getKeyChar()).equals("1")
					|| KeyEvent.getKeyText(e.getKeyChar()).equals("2") || KeyEvent.getKeyText(e.getKeyChar()).equals("3")
					|| KeyEvent.getKeyText(e.getKeyChar()).equals("4") || KeyEvent.getKeyText(e.getKeyChar()).equals("5")
					|| KeyEvent.getKeyText(e.getKeyChar()).equals("6") || KeyEvent.getKeyText(e.getKeyChar()).equals("7")
					|| KeyEvent.getKeyText(e.getKeyChar()).equals("8") || KeyEvent.getKeyText(e.getKeyChar()).equals("9")) {
				Koe.oto("とじる");
				Ekigo.this.dispose();
			}
		}
	}

	class MyMouse extends MouseAdapter {

		public void mouseClicked(MouseEvent e1) {
			if (e1.getSource() == b||e1.getSource() == b2) {
					Ekigo.this.dispose();
				}
			}
		}
}
