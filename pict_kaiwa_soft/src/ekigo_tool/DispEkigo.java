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

import statics.Koe;
import statics.Zoom;
import test_tool.l;

public class DispEkigo extends JFrame {

	private boolean keyPressAvailable = true;
	private boolean keyReleaseAvailable = true;

	// イベント
	MyMouse mouse = new MyMouse();
	MyKey key = new MyKey();

	// レイアウト
	GridBagLayout Buttonlayout = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	JButton b = new JButton();
	JButton b2 = new JButton();
	JPanel p;

	public DispEkigo(String ekigoFileName, String ekigoFolderName, int ekigoViewMode) {

		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		this.setBounds(rect);

		b.setBackground(Color.black);
		b.addKeyListener(key);
		b.addMouseListener(mouse);

		b2.setText(ekigoFileName);
		b2.setFont(new Font("", Font.BOLD, 50));
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

		p = new JPanel(Buttonlayout);
		Zoom ZoomImg = new Zoom(new ImageIcon("./resource/img/" + ekigoFolderName + "/" + ekigoFileName + ".jpg"), 200, 200, 500, 500);
		b.add(ZoomImg);

		p.add(b);
		p.add(b2);

		this.add(p);

		// ///////////////////////////////////////////////絵記号モード：画面いっぱい　一時表示モード：一時的
		if (ekigoViewMode == 1) {
			this.setVisible(true);
		} else if ((ekigoViewMode == 2)) {
			this.setVisible(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			this.setVisible(false);
		}
		// -----------------------------------------------絵記号モード：画面いっぱい　一時表示モード：一時的
	}

	public static void main(String[] args) {
		new DispEkigo("あつい", "tab2", 1);
	}

	public class MyKey extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			if (keyPressAvailable == true) {

				for (int i = 0; i < 10; i++) {
					if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i)) || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Slash") || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
					// Num Lockは制御不可
					) {
						keyPressAvailable = false;
						new l(this,"moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
						Koe.oto("とじる");
						DispEkigo.this.dispose();
						break;
					}
				}
			}
		}

		public void keyReleased(KeyEvent e) {

			if (keyReleaseAvailable == true) {

				for (int i = 0; i < 10; i++) {
					if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i)) || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Slash") || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
					// Num Lockは制御不可
					) {
						keyReleaseAvailable = false;
						new l(this,"moveEkigo>keyReleased == " + KeyEvent.getKeyText(e.getKeyChar()));
						break;
					}
				}
			}
		}
	}

	class MyMouse extends MouseAdapter {

		public void mouseClicked(MouseEvent e1) {
			if (e1.getSource() == b || e1.getSource() == b2) {
				DispEkigo.this.dispose();
			}
		}
	}
}
