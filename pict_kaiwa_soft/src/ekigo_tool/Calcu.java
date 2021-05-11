package ekigo_tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import main_frame.Kaiwa;
import statics.CommonColor;
import statics.Koe;

public class Calcu extends JFrame implements ActionListener, KeyListener {

	Calcu frame;
	JLabel display; // ディスプレイ
	JButton button[]; // ボタン配列
	String op1; // オペランド１
	String op2; // オペランド２
	String operator; // 演算子
	JPanel endPanel = new JPanel();
	JButton endButton = new JButton("閉じる(クリック)");
	int opMode; // 入力モード（0:op1初回、1:op1、2:op2初回、3:op2）
	final String buttonSt[] = {
	"7", "8", "9", "/", "AC", "4", "5", "6", "*", "C", "1", "2", "3", "-", " ", "0", ".", "=", "+", " " };

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Calcu();
			}
		});
	}

	public Calcu() {
		this.setAlwaysOnTop(true);
		this.setUndecorated(false);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		setBounds(rect);
		this.setTitle("でんたく");
		initial();
		this.setVisible(true);
	}

	void initial() {
		display = new JLabel("0.", JLabel.RIGHT);
		display.setFont(new Font("Monospaced", Font.BOLD, 70));
		display.setPreferredSize(new Dimension(1025, 100));
		display.setForeground(Color.white);
		display.setBorder(new LineBorder(Color.white,2,true));
		display.setBackground(Color.black);
		display.setOpaque(true);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 5, 4, 4));
		button = new JButton[20];

		endButton.setActionCommand(endButton.getText());
		endButton.addActionListener(this);
		endButton.setFont(new Font("", Font.PLAIN, 25));
		endButton.setBackground(CommonColor.end);
		endButton.setBorder(new LineBorder(CommonColor.endBorder,5,true));

		endPanel.setLayout(new GridLayout());
		endPanel.setPreferredSize(new Dimension(1025, 100));
		endPanel.add(endButton);
		for (int i = 0; i < 20; i++) {
			button[i] = new JButton(buttonSt[i]);
			button[i].setFont(new Font("Monospaced", Font.BOLD, 70));
			button[i].setBackground(Color.black);
			button[i].setForeground(Color.GREEN);
			if ((i % 5) == 3)
				button[i].setForeground(Color.yellow);
			if ((i % 5) == 4)
				button[i].setForeground(Color.red);
			button[i].setFocusPainted(false);
			button[i].addActionListener(this);
			button[i].addKeyListener(this);
			if (buttonSt[i].equals(" "))
				button[i].setVisible(false);
			panel.add(button[i]);

			Container container = getContentPane();
			container.setLayout(new BorderLayout());
			container.add(display, BorderLayout.NORTH);
			container.add(panel, BorderLayout.CENTER);
			container.add(endPanel, BorderLayout.SOUTH);
			addKeyListener(this);
			op1 = "0";
			op2 = "0";
			operator = "";
			opMode = 0;
		}
	}

	public void actionPerformed(ActionEvent arg0) {

		String s = arg0.getActionCommand();
		if (Character.isDigit(s.charAt(0)))
			digit(s);
		if (s.equals("."))
			decimalPoint(s);
		if (s.equals("/") || s.equals("*") || s.equals("-") || s.equals("+"))
			operator(s);
		if (s.equals("="))
			equal(s);
		if (s.equals("C"))
			clear(s);
		if (s.equals("AC"))
			allClear(s);
		if (s.equals(endButton.getText())) {
			Koe.oto("でんたくをとじます");
			this.dispose();
		}
	}

	public void keyTyped(KeyEvent e) {
		clickButton(String.valueOf(e.getKeyChar()));
	}

	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
			clickButton("=");
		if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
			clickButton("AC");
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			clickButton("C");
	}

	public void keyReleased(KeyEvent arg0) {
	}

	//	 clickButton
	void clickButton(String s) {
		for (int i = 0; i < 20; i++) {
			if (s.equals(buttonSt[i]))
				button[i].doClick(100);
		}
	}

	// 数字ボタン処理
	void digit(String s) {
		switch (opMode) {
		case 0:
			op1 = s;
			setDisplay(op1);
			if (!(s.equals("0")))
				opMode = 1;
			break;
		case 1:
			if (op1.length() < 30) {
				op1 += s;
				setDisplay(op1);
			}
			break;
		case 2:
			op2 = s;
			setDisplay(op2);
			if (!(s.equals("0")))
				opMode = 3;
			break;
		case 3:
			if (op2.length() < 30) {
				op2 += s;
				setDisplay(op2);
			}
			break;
		}
	}

	// 小数点ボタン処理
	void decimalPoint(String s) {
		switch (opMode) {
		case 0:
			op1 = "0" + s;
			setDisplay(op1);
			opMode = 1;
			break;
		case 1:
			if (op1.indexOf('.') == -1) {
				op1 += s;
				setDisplay(op1);
			}
			break;
		case 2:
			op2 = "0" + s;
			setDisplay(op2);
			opMode = 3;
			break;
		case 3:
			if (op2.indexOf('.') == -1) {
				op2 += s;
				setDisplay(op2);
			}
			break;
		}
	}

	// 演算子ボタン処理
	void operator(String s) {
		switch (opMode) {
		case 0:
		case 1:
			opMode = 2;
			break;
		case 3:
			String answer = calculate(op1, op2, operator);
			setDisplay(answer);
			op1 = answer;
			opMode = 2;
			break;
		}
		operator = s;
	}

	// イコールボタン処理
	void equal(String s) {
		if (!(operator.equals(""))) {
			String answer = calculate(op1, op2, operator);
			setDisplay(answer);
			op1 = answer;
		}
		opMode = 0;
	}

	// クリアボタン処理
	void clear(String s) {
		if (opMode == 0 || opMode == 1) {
			op1 = "0";
			opMode = 0;
		}
		if (opMode == 2 || opMode == 3) {
			op2 = "0";
			opMode = 2;
		}
		setDisplay("0");
	}

	// オールクリアボタン処理
	void allClear(String s) {
		op1 = "0";
		op2 = "0";
		operator = "";
		opMode = 0;
		setDisplay("0");
	}

	// 数値表示ディスプレイへの表示
	void setDisplay(String s) {
		if (Character.isDigit(s.charAt(0))) {
			if (s.indexOf('.') == -1)
				s += ".";
		}
		display.setText(s);
	}

	// 演算実行
	String calculate(String s1, String s2, String s3) {

		BigDecimal op1BD;
		BigDecimal op2BD;
		BigDecimal answerBD;
		String answer;

		try {
			op1BD = new BigDecimal(s1);
			op2BD = new BigDecimal(s2);
			answerBD = new BigDecimal("0");

			if (s3.equals("/"))
				answerBD = op1BD.divide(op2BD, 16, BigDecimal.ROUND_HALF_UP);
			if (s3.equals("*"))
				answerBD = op1BD.multiply(op2BD);
			if (s3.equals("-"))
				answerBD = op1BD.subtract(op2BD);
			if (s3.equals("+"))
				answerBD = op1BD.add(op2BD);
			answer = answerBD.toString();
			if (answer.indexOf('.') != -1) {
				int i = answer.length();
				while (answer.charAt(i - 1) == '0')
					i--;
				answer = answer.substring(0, i);
			}
		} catch (ArithmeticException ae) {
			answer = "error";//ゼロ除算
		} catch (NumberFormatException nfe) {
			answer = "error";//非数値
		}
		return answer;
	}
	void o(String s){
		System.out.println(s);
	}

	void o(String s,String ss){
		System.out.println(s+ss);
	}

}
