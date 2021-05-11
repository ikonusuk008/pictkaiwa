package test;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.GridLayout;

public class test extends JFrame {

	private JPanel jContentPane = null;
	private JButton jButton = null;
	private JButton jButton1 = null;

	public test() {
		// TODO 自動生成されたコンストラクター・スタブ
		initialize();
	}


	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
        this.setSize(new Dimension(200, 200));
        this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setSelected(true);
			jButton.setName("jButton");
			jButton.setText("test");
		}
		return jButton;
	}


	/**
	 * This method initializes jButton1
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setName("jButton1");
			jButton1.requestFocus(true);
			jButton1.setEnabled(true);
			jButton1.setText("test");
		}
		return jButton1;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		test test =new test();
		test.setVisible(true);
	}

}
