package ekigo_tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import statics.CommonColor;
import statics.Koe;

public class Tokei extends JFrame implements Runnable, ActionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	JLabel timeL;

	JPanel mainP = new JPanel(new GridLayout(1, 2));
	JPanel mainP_rightP = new JPanel(new GridLayout(3, 1));
	JPanel mainP_rightP_1P = new JPanel();
	JPanel mainP_rightP_2P = new JPanel();
	JPanel mainP_rightP_3P = new JPanel();

	JButton start = new JButton();

	Thread th;
	Thread th1;

	// 文字盤のフォントとそのサイズ
	Font numFont = new Font("serif", Font.ROMAN_BASELINE, 30);
	// 時分秒を表す変数
	int hour, minute, second;
	// 時分秒の針の角度
	double hour_angle, minute_angle, second_angle;
	// 時分秒の針の先端の位置
	int hour_x, hour_y;
	int minute_x, minute_y;
	int second_x, second_y;
	// 時分秒の針の長さ
	int hour_hand_length = 110;
	int minute_hand_length = 145;
	int second_hand_length = 150;
	// 時計の中心の位置
	int xcenter = 235;
	int ycenter = 230;
	// 文字盤の半径
	int mojibanradius = 200;
	// 時計の外周の半径
	int gaisyuradius = 220;
	// 中心から文字までの距離
	int cdistance = 160;

	JButton end_button;

	public static void main(String[] a) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Tokei();
			}
		});
	}

	public Tokei() {

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();

		ImageIcon icon = new ImageIcon("./resource/img/tab_img/アプリ.jpg");
		this.setIconImage(icon.getImage());
		this.setAlwaysOnTop(true);
		this.setBounds(rect);
		this.setTitle("時計");

		//this.setAlwaysOnTop(true);
		//this.setResizable(false);
		this.setLayout(new BorderLayout());
		th = null;
		th1 = null;

		start.addActionListener(this);
		start.setActionCommand("start");
		start.doClick();

		WPanel wp = new WPanel();
		wp.setBackground(Color.WHITE);
		wp.setLayout(new BorderLayout());

		mainP_rightP.add(mainP_rightP_1P = year_day(mainP_rightP_1P));
		mainP_rightP.add(mainP_rightP_2P = week(mainP_rightP_2P));

		WLanel wl = new WLanel();
		mainP_rightP.add(wl);

		mainP.add(mainP_rightP);
		mainP.add(wp);
		this.add(mainP, BorderLayout.CENTER);

		end_button = new JButton("閉じる(テンキー又はクリック)");
		end_button.setFont(new Font("", Font.PLAIN, 35));
		end_button.addActionListener(this);
		end_button.addMouseListener(this);
		end_button.setActionCommand("end");
		end_button.addKeyListener(this);
		end_button.setBackground(CommonColor.end);
		end_button.setPreferredSize(new Dimension(100, 70));
		end_button.setBorder(new LineBorder(CommonColor.endBorder, 7, true));

		this.add(end_button, BorderLayout.SOUTH);

		// Threadクラスのオブジェクトを生成
		th = new Thread(this);
		th1 = new Thread(this);

		// スレッドを実行可能にする
		th.start();
		th1.start();

		this.setVisible(true);
	}

	private JPanel time(JPanel p) {
		// TODO 自動生成されたメソッド・スタブ
		p.setBackground(Color.black);
		p.setLayout(new GridLayout());

		JLabel l = new JLabel();
		TimeZone tz2 = TimeZone.getTimeZone("Asia/Tokyo");

		Calendar cal1 = Calendar.getInstance(tz2);
		// (6)TimeZoneオブジェクトの変更
		cal1.setTimeZone(tz2);

		o(cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE) + ":" + cal1.get(Calendar.SECOND));
		l.setText(cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE) + ":" + cal1.get(Calendar.SECOND));
		l.setForeground(Color.yellow);
		l.setFont(new Font("", Font.BOLD, 100));

		p.add(l);
		return p;
	}

	private JPanel week(JPanel p) {
		// TODO 自動生成されたメソッド・スタブ
		p.setBackground(Color.black);
		p.setLayout(new GridLayout());

		JLabel l = new JLabel();
		l.setBorder(new LineBorder(Color.white, 2, true));
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setForeground(Color.green);
		if (getDayOfTheWeek().equals("日")) {
			l.setForeground(Color.pink);
		} else if (getDayOfTheWeek().equals("土")) {
			l.setForeground(Color.cyan);
		}
		l.setText(getDayOfTheWeek() + " 曜日");

		l.setFont(new Font("", Font.BOLD, 60));

		p.add(l);
		return p;
	}

	private JPanel year_day(JPanel p) {
		// TODO 自動生成されたメソッド・スタブ
		final Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
		o(dateFormat.format(calendar.getTime()));

		p.setBackground(Color.black);
		p.setLayout(new GridLayout());
		JLabel l = new JLabel();
		l.setBorder(new LineBorder(Color.white, 2, true));
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setForeground(Color.green);
		l.setText(dateFormat.format(calendar.getTime()));
		l.setFont(new Font("", Font.BOLD, 50));
		// l.setBorder(new Border(10,10,10,10));

		p.add(l);
		return p;
	}

	public static String getDayOfTheWeek() {
		Calendar cal = Calendar.getInstance();
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			return "日";
		case Calendar.MONDAY:
			return "月";
		case Calendar.TUESDAY:
			return "火";
		case Calendar.WEDNESDAY:
			return "水";
		case Calendar.THURSDAY:
			return "木";
		case Calendar.FRIDAY:
			return "金";
		case Calendar.SATURDAY:
			return "土";
		}
		throw new IllegalStateException();
	}

	// 文字盤を描く
	public void Mojiban(Graphics g) {
		// 文字盤の文字の間隔をあけるための角度
		double anglespace = Math.PI / 6.0;
		// 1時の位置の角度
		double theta = Math.PI / 2.0 - anglespace;
		// フォントの設定
		g.setFont(numFont);
		// フォントの物理的サイズを調べる。指定のフォントのメトリック(規準)を返す
		FontMetrics fm = g.getFontMetrics(numFont);
		// 文字盤の文字色
		g.setColor(Color.green);
		for (int i = 1; i <= 12; i++) {
			// 数値を文字にする
			String num = Integer.toString(i);
			// フォントの文字の高さの半分
			int cheight = fm.getHeight() / 2;
			// フォントの文字の幅
			int cwidth = fm.stringWidth(num);
			// 文字盤の数字の位置
			int x = xcenter + (int) (cdistance * Math.cos(theta));
			int y = ycenter - (int) (cdistance * Math.sin(theta));
			// 数字を位置の中心に書く
			g.drawString(num, x - cwidth / 2, y + cheight / 2);
			// 次の文字の角度を計算
			theta -= anglespace;
		}
	}

	// 時分秒の針の先端の位置を計算
	public void HandCoordinate() {
		// 現在の時刻を求める
		TimeKnow();
		// 時刻の計算
		double h = hour + minute / 60.0;
		double m = minute + second / 60.0;
		double s = second;
		// 時分秒の針の角度を計算
		hour_angle = Math.PI * (3.0 - h) / 6.0;
		minute_angle = Math.PI * (15.0 - m) / 30.0;
		second_angle = Math.PI * (15.0 - s) / 30.0;
		// 時分秒の針の先端の位置の計算
		hour_x = xcenter + (int) (hour_hand_length * Math.cos(hour_angle));
		hour_y = ycenter - (int) (hour_hand_length * Math.sin(hour_angle));
		minute_x = xcenter + (int) (minute_hand_length * Math.cos(minute_angle));
		minute_y = ycenter - (int) (minute_hand_length * Math.sin(minute_angle));
		second_x = xcenter + (int) (second_hand_length * Math.cos(second_angle));
		second_y = ycenter - (int) (second_hand_length * Math.sin(second_angle));
	}

	// 時計の針を描く
	public void ClockHandDraw(Graphics g) {
		// 時分秒の針の先端の位置を計算
		HandCoordinate();

		int handwidth = 5;// 針の幅
		// 時分針の底辺の水平、垂直距離
		int xhourhanddis = (int) (handwidth * Math.cos(Math.PI / 2.0 - hour_angle));
		int yhourhanddis = (int) (handwidth * Math.sin(Math.PI / 2.0 - hour_angle));
		int xminuhanddis = (int) (handwidth * Math.cos(Math.PI / 2.0 - minute_angle));
		int yminuhanddis = (int) (handwidth * Math.sin(Math.PI / 2.0 - minute_angle));
		// 時分針の頂点の座標
		int[] pxhour = { xcenter + xhourhanddis, hour_x, xcenter - xhourhanddis };
		int[] pyhour = { ycenter + yhourhanddis, hour_y, ycenter - yhourhanddis };
		int[] pxminute = { xcenter + xminuhanddis, minute_x, xcenter - xminuhanddis };
		int[] pyminute = { ycenter + yminuhanddis, minute_y, ycenter - yminuhanddis };
		// 時計の外周を描くための左上の座標
		int xgaisyu = xcenter - gaisyuradius;
		int ygaisyu = ycenter - gaisyuradius;
		// 時計の文字盤を描くための左上の座標
		int xdial = xcenter - mojibanradius;
		int ydial = ycenter - mojibanradius;
		// 時計の外周を描く
		g.setColor(Color.black);
		g.fillOval(xgaisyu, ygaisyu, 2 * gaisyuradius, 2 * gaisyuradius);
		// 時計の文字盤を描く
		g.setColor(Color.black);
		g.fillOval(xdial, ydial, 2 * mojibanradius, 2 * mojibanradius);
		// 時計の針を書く
		g.setColor(Color.yellow);
		g.fillPolygon(pxhour, pyhour, pxhour.length);
		g.fillPolygon(pxminute, pyminute, pxminute.length);
		g.drawLine(xcenter, ycenter, second_x, second_y);
	}

	// 現在の時刻を求める
	public void TimeKnow() {
		// Calendarクラスの生成
		Calendar cal = new GregorianCalendar();
		// 時分秒を求める
		hour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
	}

	// 描画のためのクラス。SwingのJPanelはちらつきのない描画を実現できる
	public class WPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		// コンテナー内のコンポーネントを描画する
		public void paintComponent(Graphics g) {
			this.setBackground(Color.black);
			// paintComponentを使うときは、スーパークラスのpaintComponentメソッドを呼び出す
			super.paintComponent(g);
			// 時計の針を描く
			ClockHandDraw(g);
			// 文字盤を描く
			Mojiban(g);
		}
	}

	// 描画のためのクラス。SwingのJPanelはちらつきのない描画を実現できる
	public class WLanel extends JPanel {
		private static final long serialVersionUID = 1L;

		WLanel() {
			setBackground(Color.black);
			this.setLayout(new GridLayout());

			timeL = new JLabel();
			timeL.setHorizontalAlignment(JLabel.CENTER);
			timeL.setBorder(new LineBorder(Color.white, 2, true));
			// timeL.setText(cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE) + ":" + cal1.get(Calendar.SECOND));
			timeL.setForeground(Color.yellow);
			timeL.setFont(new Font("", Font.BOLD, 80));
			add(timeL);
		}
	}

	public class MyKeyEvent extends KeyAdapter {
		public void keyTyped(KeyEvent e) {

			//o("key:" + KeyEvent.getKeyText(e.getKeyChar()));
			System.out.println("KeyEvent.getKeyText: "+KeyEvent.getKeyText(e.getKeyChar()));

			for (int i = 0; i < 10; i++) {
				if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i))||
						KeyEvent.getKeyText(e.getKeyChar()).equals("Period")||
						KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")||
						KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")||
						KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")||
						KeyEvent.getKeyText(e.getKeyChar()).equals("Slash")||
						KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
						//Num Lockは制御不可

				) {// ボタンを決定
					Koe.oto("とけいをとじます");
					System.exit(0);
				}
			}

		}
	}

	// スレッドの本体
	public void run() {
		// スレッドが変らない間は続ける
		while (th != null) {
			// 描画
			TimeZone tz2 = TimeZone.getTimeZone("Asia/Tokyo");
			Calendar cal1 = Calendar.getInstance(tz2);

			this.repaint();
			timeL.setText(cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE) + ":" + cal1.get(Calendar.SECOND));
			try {
				// 1秒待つ
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (start.equals("start")) {

			while (th != null) {
				// 描画
				this.repaint();
				try {
					// 1秒待つ
					Thread.sleep(1000);
				} catch (InterruptedException e1) {

				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getSource() == end_button) {
			Koe.oto("とけいをとじます");
			this.dispose();
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getSource() == end_button) {
			Koe.oto("とけいをとじます");
			this.dispose();
		}
	}

	void o(String s) {
		System.out.println(s);
	}

	void o(String s, String ss) {
		// System.out.println(s+ss);
	}
}
