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

	// �����Ղ̃t�H���g�Ƃ��̃T�C�Y
	Font numFont = new Font("serif", Font.ROMAN_BASELINE, 30);
	// �����b��\���ϐ�
	int hour, minute, second;
	// �����b�̐j�̊p�x
	double hour_angle, minute_angle, second_angle;
	// �����b�̐j�̐�[�̈ʒu
	int hour_x, hour_y;
	int minute_x, minute_y;
	int second_x, second_y;
	// �����b�̐j�̒���
	int hour_hand_length = 110;
	int minute_hand_length = 145;
	int second_hand_length = 150;
	// ���v�̒��S�̈ʒu
	int xcenter = 235;
	int ycenter = 230;
	// �����Ղ̔��a
	int mojibanradius = 200;
	// ���v�̊O���̔��a
	int gaisyuradius = 220;
	// ���S���當���܂ł̋���
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

		ImageIcon icon = new ImageIcon("./resource/img/tab_img/�A�v��.jpg");
		this.setIconImage(icon.getImage());
		this.setAlwaysOnTop(true);
		this.setBounds(rect);
		this.setTitle("���v");

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

		end_button = new JButton("����(�e���L�[���̓N���b�N)");
		end_button.setFont(new Font("", Font.PLAIN, 35));
		end_button.addActionListener(this);
		end_button.addMouseListener(this);
		end_button.setActionCommand("end");
		end_button.addKeyListener(this);
		end_button.setBackground(CommonColor.end);
		end_button.setPreferredSize(new Dimension(100, 70));
		end_button.setBorder(new LineBorder(CommonColor.endBorder, 7, true));

		this.add(end_button, BorderLayout.SOUTH);

		// Thread�N���X�̃I�u�W�F�N�g�𐶐�
		th = new Thread(this);
		th1 = new Thread(this);

		// �X���b�h�����s�\�ɂ���
		th.start();
		th1.start();

		this.setVisible(true);
	}

	private JPanel time(JPanel p) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		p.setBackground(Color.black);
		p.setLayout(new GridLayout());

		JLabel l = new JLabel();
		TimeZone tz2 = TimeZone.getTimeZone("Asia/Tokyo");

		Calendar cal1 = Calendar.getInstance(tz2);
		// (6)TimeZone�I�u�W�F�N�g�̕ύX
		cal1.setTimeZone(tz2);

		o(cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE) + ":" + cal1.get(Calendar.SECOND));
		l.setText(cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE) + ":" + cal1.get(Calendar.SECOND));
		l.setForeground(Color.yellow);
		l.setFont(new Font("", Font.BOLD, 100));

		p.add(l);
		return p;
	}

	private JPanel week(JPanel p) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		p.setBackground(Color.black);
		p.setLayout(new GridLayout());

		JLabel l = new JLabel();
		l.setBorder(new LineBorder(Color.white, 2, true));
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setForeground(Color.green);
		if (getDayOfTheWeek().equals("��")) {
			l.setForeground(Color.pink);
		} else if (getDayOfTheWeek().equals("�y")) {
			l.setForeground(Color.cyan);
		}
		l.setText(getDayOfTheWeek() + " �j��");

		l.setFont(new Font("", Font.BOLD, 60));

		p.add(l);
		return p;
	}

	private JPanel year_day(JPanel p) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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
			return "��";
		case Calendar.MONDAY:
			return "��";
		case Calendar.TUESDAY:
			return "��";
		case Calendar.WEDNESDAY:
			return "��";
		case Calendar.THURSDAY:
			return "��";
		case Calendar.FRIDAY:
			return "��";
		case Calendar.SATURDAY:
			return "�y";
		}
		throw new IllegalStateException();
	}

	// �����Ղ�`��
	public void Mojiban(Graphics g) {
		// �����Ղ̕����̊Ԋu�������邽�߂̊p�x
		double anglespace = Math.PI / 6.0;
		// 1���̈ʒu�̊p�x
		double theta = Math.PI / 2.0 - anglespace;
		// �t�H���g�̐ݒ�
		g.setFont(numFont);
		// �t�H���g�̕����I�T�C�Y�𒲂ׂ�B�w��̃t�H���g�̃��g���b�N(�K��)��Ԃ�
		FontMetrics fm = g.getFontMetrics(numFont);
		// �����Ղ̕����F
		g.setColor(Color.green);
		for (int i = 1; i <= 12; i++) {
			// ���l�𕶎��ɂ���
			String num = Integer.toString(i);
			// �t�H���g�̕����̍����̔���
			int cheight = fm.getHeight() / 2;
			// �t�H���g�̕����̕�
			int cwidth = fm.stringWidth(num);
			// �����Ղ̐����̈ʒu
			int x = xcenter + (int) (cdistance * Math.cos(theta));
			int y = ycenter - (int) (cdistance * Math.sin(theta));
			// �������ʒu�̒��S�ɏ���
			g.drawString(num, x - cwidth / 2, y + cheight / 2);
			// ���̕����̊p�x���v�Z
			theta -= anglespace;
		}
	}

	// �����b�̐j�̐�[�̈ʒu���v�Z
	public void HandCoordinate() {
		// ���݂̎��������߂�
		TimeKnow();
		// �����̌v�Z
		double h = hour + minute / 60.0;
		double m = minute + second / 60.0;
		double s = second;
		// �����b�̐j�̊p�x���v�Z
		hour_angle = Math.PI * (3.0 - h) / 6.0;
		minute_angle = Math.PI * (15.0 - m) / 30.0;
		second_angle = Math.PI * (15.0 - s) / 30.0;
		// �����b�̐j�̐�[�̈ʒu�̌v�Z
		hour_x = xcenter + (int) (hour_hand_length * Math.cos(hour_angle));
		hour_y = ycenter - (int) (hour_hand_length * Math.sin(hour_angle));
		minute_x = xcenter + (int) (minute_hand_length * Math.cos(minute_angle));
		minute_y = ycenter - (int) (minute_hand_length * Math.sin(minute_angle));
		second_x = xcenter + (int) (second_hand_length * Math.cos(second_angle));
		second_y = ycenter - (int) (second_hand_length * Math.sin(second_angle));
	}

	// ���v�̐j��`��
	public void ClockHandDraw(Graphics g) {
		// �����b�̐j�̐�[�̈ʒu���v�Z
		HandCoordinate();

		int handwidth = 5;// �j�̕�
		// �����j�̒�ӂ̐����A��������
		int xhourhanddis = (int) (handwidth * Math.cos(Math.PI / 2.0 - hour_angle));
		int yhourhanddis = (int) (handwidth * Math.sin(Math.PI / 2.0 - hour_angle));
		int xminuhanddis = (int) (handwidth * Math.cos(Math.PI / 2.0 - minute_angle));
		int yminuhanddis = (int) (handwidth * Math.sin(Math.PI / 2.0 - minute_angle));
		// �����j�̒��_�̍��W
		int[] pxhour = { xcenter + xhourhanddis, hour_x, xcenter - xhourhanddis };
		int[] pyhour = { ycenter + yhourhanddis, hour_y, ycenter - yhourhanddis };
		int[] pxminute = { xcenter + xminuhanddis, minute_x, xcenter - xminuhanddis };
		int[] pyminute = { ycenter + yminuhanddis, minute_y, ycenter - yminuhanddis };
		// ���v�̊O����`�����߂̍���̍��W
		int xgaisyu = xcenter - gaisyuradius;
		int ygaisyu = ycenter - gaisyuradius;
		// ���v�̕����Ղ�`�����߂̍���̍��W
		int xdial = xcenter - mojibanradius;
		int ydial = ycenter - mojibanradius;
		// ���v�̊O����`��
		g.setColor(Color.black);
		g.fillOval(xgaisyu, ygaisyu, 2 * gaisyuradius, 2 * gaisyuradius);
		// ���v�̕����Ղ�`��
		g.setColor(Color.black);
		g.fillOval(xdial, ydial, 2 * mojibanradius, 2 * mojibanradius);
		// ���v�̐j������
		g.setColor(Color.yellow);
		g.fillPolygon(pxhour, pyhour, pxhour.length);
		g.fillPolygon(pxminute, pyminute, pxminute.length);
		g.drawLine(xcenter, ycenter, second_x, second_y);
	}

	// ���݂̎��������߂�
	public void TimeKnow() {
		// Calendar�N���X�̐���
		Calendar cal = new GregorianCalendar();
		// �����b�����߂�
		hour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
	}

	// �`��̂��߂̃N���X�BSwing��JPanel�͂�����̂Ȃ��`��������ł���
	public class WPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		// �R���e�i�[���̃R���|�[�l���g��`�悷��
		public void paintComponent(Graphics g) {
			this.setBackground(Color.black);
			// paintComponent���g���Ƃ��́A�X�[�p�[�N���X��paintComponent���\�b�h���Ăяo��
			super.paintComponent(g);
			// ���v�̐j��`��
			ClockHandDraw(g);
			// �����Ղ�`��
			Mojiban(g);
		}
	}

	// �`��̂��߂̃N���X�BSwing��JPanel�͂�����̂Ȃ��`��������ł���
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
						//Num Lock�͐���s��

				) {// �{�^��������
					Koe.oto("�Ƃ������Ƃ��܂�");
					System.exit(0);
				}
			}

		}
	}

	// �X���b�h�̖{��
	public void run() {
		// �X���b�h���ς�Ȃ��Ԃ͑�����
		while (th != null) {
			// �`��
			TimeZone tz2 = TimeZone.getTimeZone("Asia/Tokyo");
			Calendar cal1 = Calendar.getInstance(tz2);

			this.repaint();
			timeL.setText(cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE) + ":" + cal1.get(Calendar.SECOND));
			try {
				// 1�b�҂�
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (start.equals("start")) {

			while (th != null) {
				// �`��
				this.repaint();
				try {
					// 1�b�҂�
					Thread.sleep(1000);
				} catch (InterruptedException e1) {

				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void mouseEntered(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void mouseExited(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void mousePressed(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if (e.getSource() == end_button) {
			Koe.oto("�Ƃ������Ƃ��܂�");
			this.dispose();
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void keyPressed(KeyEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void keyReleased(KeyEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void keyTyped(KeyEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if (e.getSource() == end_button) {
			Koe.oto("�Ƃ������Ƃ��܂�");
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
