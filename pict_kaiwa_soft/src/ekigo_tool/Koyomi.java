package ekigo_tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import main_frame.Kaiwa;
import statics.CommonColor;
import statics.Koe;

public class Koyomi extends JFrame{

	MyKeyEvent myKeyEvent = new MyKeyEvent();

	private static final long serialVersionUID = 1L;

	private int today;

	JButton endButton = new JButton("閉じる(テンキー又はクリック)");

	MyMouse myMouse= new MyMouse();

	public static void main(String[] a) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Koyomi();
			}
		});
	}

	public Koyomi() {
		
		this.setAlwaysOnTop(true);
		this.setUndecorated(false);
		this.setTitle("こよみ");
		this.setResizable(false);
		this.setSize(1025, 570);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		this.setBounds(rect);

		// 終了処理（パネル・ボタン）
		JPanel endButtonPanel = makeEndButtonPanel();

		endButton.setFont(new Font("", Font.PLAIN, 35));
		endButton.setBackground(CommonColor.end);
		endButton.setPreferredSize(new Dimension(70, 100));
		endButton.setBorder(new LineBorder(CommonColor.endBorder, 7, true));
		endButton.addKeyListener(myKeyEvent);
		endButton.addMouseListener(myMouse);

		endButtonPanel.add(endButton);

		final Calendar calendar = Calendar.getInstance();

		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.GERMANY);

		DateFormat dateFormat1 = DateFormat.getDateInstance(DateFormat.FULL);
		o(dateFormat1.format(new Date()));

		setToday(calendar.get(Calendar.DATE));

		/////////////////////////////////////////////////////////////
		// テーブルにのせる場合
		//this.getContentPane().add(makeTable(calendar), BorderLayout.CENTER);

		/////////////////////////////////////////////////////////////
		// 日めくり風
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.CENTER);

		JPanel p1 = new JPanel(layout);
		p1.setBackground(Color.black);
		p1.setPreferredSize(new Dimension(0, 100));

		JLabel l1 = new JLabel();
		if (dateFormat1.format(new Date()).substring(5, 6).equals("10") || dateFormat1.format(new Date()).substring(5, 6).equals("11")
				|| dateFormat1.format(new Date()).substring(5, 6).equals("12")) {
			l1.setText(dateFormat1.format(new Date()).substring(0, 8));
		} else {
			l1.setText(dateFormat1.format(new Date()).substring(0, 7));
		}
		l1.setFont(new Font("", Font.BOLD, 70));
		l1.setForeground(Color.GREEN);

		JPanel p = new JPanel(layout);
		p.setBackground(Color.black);
		JLabel l = new JLabel();
		l.setText(dateFormat.format(new Date()).substring(0, 2));
		l.setFont(new Font("", Font.BOLD, 400));
		l.setForeground(Color.GREEN);

		p1.add(l1);
		p.add(l);

		this.getContentPane().add(p1, BorderLayout.NORTH);
		this.getContentPane().add(p, BorderLayout.CENTER);
		this.getContentPane().add(endButtonPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public JPanel makeEndButtonPanel() {
		JPanel endButtonPanel = new JPanel();
		endButtonPanel.setLayout(buttonMakeLayout());
		return endButtonPanel;
	}

	BorderLayout buttonMakeLayout() {
		BorderLayout b = new BorderLayout();
		return b;
	}

	public String getDivLocalandGMT(Calendar cal) {
		String s = null;
		TimeZone tz = TimeZone.getTimeZone("UTC");// 指定された TimeZone
		Calendar gmtcal = Calendar.getInstance(tz);// カレンダを取得

		int m = 60 * (24 * (cal.get(Calendar.DATE) - gmtcal.get(Calendar.DATE)) + cal.get(Calendar.HOUR_OF_DAY) - gmtcal.get(Calendar.HOUR_OF_DAY)
				+ cal.get(Calendar.MINUTE) - gmtcal.get(Calendar.MINUTE));

		int t = m / 60;
		m = m - t * 60;

		if (10 <= t) {
			s = "+" + "0" + Integer.toString(t) + ":";
		} else if (0 <= t && t < 10) {
			s = "+" + "0" + Integer.toString(t) + ":";
		} else if (-9 <= t && 0 < t) {
			s = "-" + "0" + Integer.toString(-t) + ":";
		} else {
			s = "-" + Integer.toString(-t) + ":";
		}

		if (10 <= m) {
			s += Integer.toString(m);
		} else if (0 < m && m < 10) {
			s += "0" + Integer.toString(m);
		} else {
			s += "00";
		}
		o("s(カレンダー値) = " + s);

		return s;
	}

	public String getISO8601(Calendar cal) {
		String s = Integer.toString(cal.get(Calendar.YEAR)) + "-" + Integer.toString(cal.get(Calendar.MONTH) + 1) + "-"
				+ Integer.toString(cal.get(Calendar.DATE)) + "T" + Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ Integer.toString(cal.get(Calendar.MINUTE)) + ":" + Integer.toString(cal.get(Calendar.SECOND)) + getDivLocalandGMT(cal);
		return s;
	}

	public void setWeekName(JTable t) {
		String week[] = { "日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日" };
		for (int i = 0; i < week.length; i++)
			t.setValueAt(week[i], 0, i);
	}

	public int getToday() {
		return today;
	}

	public void setToday(int t) {
		today = t;
	}

	public void setDayNumber(Calendar cal, JTable jTable) {
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DATE); i++) {
			cal.set(Calendar.DATE, i);
			if (cal.get(Calendar.DATE) != today) {
				jTable.setValueAt(cal.get(Calendar.DATE) + "", cal.get(Calendar.WEEK_OF_MONTH), cal.get(Calendar.DAY_OF_WEEK) - 1);

				jTable.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));

			} else {
				jTable.setValueAt("本日: " + cal.get(Calendar.DATE) + " ", cal.get(Calendar.WEEK_OF_MONTH), cal.get(Calendar.DAY_OF_WEEK) - 1);
				jTable.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
				jTable.setGridColor(Color.red);
			}
		}
	}

	private JTable makeTable(Calendar calendar) {
		JTable jTable = new JTable(7, 7);
		jTable.addKeyListener(myKeyEvent);
		//setWeekName(jTable);
		setDayNumber(calendar, jTable);
		jTable.setRowHeight(60);
		return jTable;
	}

	class MyMouse extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ

			if (e.getSource() == endButton) {
				Koe.oto("こよみをとじます");
				System.exit(0);
			}
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

				) {
					Koe.oto("こよみをとじます");
					System.exit(0);
				}
			}

		}
	}
	void o(String s){
		System.out.println(s);
	}

	void o(String s,String ss){
		//System.out.println(s+ss);
	}
}
