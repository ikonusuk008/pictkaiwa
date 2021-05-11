package main_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import statics.CommonColor;
import statics.Koe;
import statics.Read;
import statics.Write;
import statics.Zoom;
import test_tool.l;

public class List extends JFrame implements Runnable, ActionListener {
	private boolean keyPressReleaseAvailable = false;

	Connection conn = null;
	Statement stmt = null;

	/*
	 * DB��row�i���o�[�́A�P����n�܂�B
	 */
	final int table_row_start_num = 1;
	final int table_row_finish_num = 7;

	String fileNameList = null;
	String jpgFileName = null;
	static Frame frame = null;

	JTextArea textArea[] = new JTextArea[7];
	JTextArea insertListText = new JTextArea();

	JButton end_button = new JButton("<html>����@<u>�L�[2��</u></html>");
	JButton imgButton[] = new JButton[7];
	JButton playButton[] = new JButton[7];
	JButton listTitleButton = new JButton();// ��̍���
	JButton deleteButtons[] = new JButton[7];

	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel panels[] = new JPanel[7];
	JPanel listPanel = new JPanel(new GridLayout(6, 1));
	JPanel sourthPanel = new JPanel();

	String ekigo = new String();
	String folder = new String();
	String file_name_list[] = new String[7];
	String listURL[] = new String[7];
	String ristNO[] = new String[7];
	static String name = new String();

	MyKeyEvent myKeyEvent = new MyKeyEvent();
	MyMouse myMouse = new MyMouse();

	String ekigoName=null;
	String folderName=null;

	// �����F�t�H���_�[��,���͂���P��
	public List(String ekigoName, String folderName) throws HeadlessException, SQLException {
		super();

		this.ekigoName=ekigoName;
		this.folderName=folderName;

		set_class_property(this.ekigoName, this.folderName);
		set_db_property();

		make_new_pict_table(this.ekigoName);// �G�L���e�[�u���H

		selectTable(this.ekigoName);// �e�[�u���f�[�^�����o��

		make_buttons();

		meke_title_button(this.ekigoName);

		JPanel koePanel = new JPanel(new GridLayout(1, 2));
		koePanel.setBackground(Color.black);
		JPanel listTitlePanel = new JPanel(new BorderLayout());
		listTitlePanel.add(listTitleButton, BorderLayout.NORTH);
		mainPanel.add(listTitlePanel, BorderLayout.NORTH);
		mainPanel.setBackground(Color.BLACK);
		sourthPanel.setBackground(Color.BLACK);
		sourthPanel.setPreferredSize(new Dimension(1000, 200));
		mainPanel.add(listPanel, BorderLayout.CENTER);

		this.add(mainPanel);
		this.add(makeEndButton(), BorderLayout.SOUTH);
		this.setVisible(true);

		Zoom zoom0 = new Zoom(new ImageIcon("./resource/img/" + folderName + "/" + ekigoName + ".jpg"), 0, 0, listTitleButton.getHeight(),
				listTitleButton.getHeight());
				listTitleButton.add(zoom0);
				listTitleButton.setFont(new Font("", Font.PLAIN, (int) (listTitleButton.getHeight() / 2.5)));

		for (int i = table_row_start_num; i < table_row_finish_num; i++) {
			new l(this, "imgButton[j].getWidth()==" + imgButton[i].getWidth());
			Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + folderName + "_�ڍ�/" + listURL[i]), 0, 0, imgButton[i].getWidth(), imgButton[i]
					.getHeight());
			imgButton[i].add(zoom);

			Zoom zoom2 = new Zoom(new ImageIcon("./resource/img/list/play.jpg"), 0, 0, playButton[i].getWidth(), playButton[i].getHeight());
			playButton[i].add(zoom2);

			Zoom zoom3 = new Zoom(new ImageIcon("./resource/img/list/delete.jpg"), 0, 0, deleteButtons[i].getWidth(), deleteButtons[i].getHeight());
			deleteButtons[i].add(zoom3);

			textArea[i].setFont(new Font("", Font.PLAIN, (int) (textArea[i].getHeight() / 2.0)));
		}
		this.setVisible(true);
	}

	/**
	 * @param ekigoName
	 */
	private void meke_title_button(String ekigoName) {
		listTitleButton = new JButton();
		listTitleButton.setPreferredSize(new Dimension(700, 100));
		listTitleButton.addKeyListener(myKeyEvent);
		listTitleButton.setHorizontalAlignment(JButton.CENTER);
		listTitleButton.setFont(new Font("", Font.PLAIN, 50));
		listTitleButton.setForeground(Color.white);
		listTitleButton.setBackground(Color.black);
		listTitleButton.setBorder(new LineBorder(Color.white, 1, true));
		listTitleButton.setText(ekigoName);
	}

	/**
	 *
	 */
	private void make_buttons() {
		/*
		 * ���X�g�摜�E�e�L�X�g�G���A�E�폜�{�^��
		 */
		for (int i = table_row_start_num; i < table_row_finish_num; i++) {
			/*
			 * �Đ��{�^��
			 */
			playButton[i] = new JButton();
			playButton[i].setPreferredSize(new Dimension(120, 60));
			playButton[i].setBorder(new LineBorder(Color.white, 1, false));
			playButton[i].setBackground(Color.black);
			playButton[i].setForeground(Color.white);
			playButton[i].addMouseListener(myMouse);
			playButton[i].addKeyListener(myKeyEvent);
			playButton[i].setActionCommand(String.valueOf(i));
			playButton[i].addActionListener(new KoeAction());
			playButton[i].setMargin(new Insets(0, 0, 0, 0));

			/*
			 * �摜�{�^��
			 */
			imgButton[i] = new JButton();
			imgButton[i].setBorder(new LineBorder(Color.white, 1, false));
			imgButton[i].setBackground(Color.black);
			imgButton[i].addMouseListener(myMouse);
			imgButton[i].addKeyListener(myKeyEvent);
			imgButton[i].setActionCommand(ristNO[i - 1]);
			imgButton[i].setText(ristNO[i - 1]);// �e�[�u���C���f�b�N�X��������ɓo�^�i�摜�ύX���Ɏg�p����B�j
			imgButton[i].addActionListener(this);
			imgButton[i].setMargin(new Insets(0, 0, 0, 0));

			/*
			 * �e�L�X�g�G���A
			 */
			textArea[i] = new JTextArea();
			textArea[i].addKeyListener(myKeyEvent);
			textArea[i].putClientProperty("caretAspectRatio", Float.valueOf(0.1F));
			textArea[i].setForeground(Color.white);
			textArea[i].setBackground(Color.black);
			textArea[i].setCaretColor(Color.white);
			textArea[i].setBorder(new LineBorder(Color.white, 1, true));
			textArea[i].setText(file_name_list[i]);
			textArea[i].setMargin(new Insets(0, 0, 0, 0));

			/*
			 * �폜�{�^��
			 */
			deleteButtons[i] = new JButton();
			deleteButtons[i].setPreferredSize(new Dimension(80, 60));
			deleteButtons[i].setBorder(new LineBorder(Color.white, 1, false));
			deleteButtons[i].setBackground(Color.black);
			deleteButtons[i].addMouseListener(myMouse);
			deleteButtons[i].addKeyListener(myKeyEvent);
			deleteButtons[i].setAlignmentX(CENTER_ALIGNMENT);
			deleteButtons[i].addActionListener(new Deleat_ActionEvent());
			deleteButtons[i].setActionCommand(file_name_list[i]);
			deleteButtons[i].setText(file_name_list[i]);
			deleteButtons[i].setMargin(new Insets(0, 0, 0, 0));

			JPanel playImgPanel = new JPanel(new GridLayout(1, 2));

			playImgPanel.add(playButton[i]);
			playImgPanel.add(imgButton[i]);

			panels[i] = new JPanel(new BorderLayout());
			panels[i].addKeyListener(myKeyEvent);
			panels[i].setPreferredSize(new Dimension(700, 54));
			panels[i].add(playImgPanel, BorderLayout.WEST);
			panels[i].add(textArea[i], BorderLayout.CENTER);
			panels[i].add(deleteButtons[i], BorderLayout.EAST);

			listPanel.add(panels[i]);
		}
	}

	/**
	 * @param ekigoName
	 */
	private void make_new_pict_table(String ekigoName) {
		// 1.�J�����̃t�@�C�����Ȃ��ꍇ�́A�e�[�u�����쐬����B
		// �Q�Djava_mysql_test.java���C���X�^���X�����A fileName���J�����ƂȂ郊�X�g�𒊏o����B
		boolean isFile = new Read(ekigoName).read();
		if (isFile) {// true:���łɃt�@�C��������
			new l(this, "isFile>true: �e�[�u���L��F�쐬���Ȃ�");
		} else {
			/*
			 * false:�t�@�C�����Ȃ��̂ō쐬����
			 */
			new l(this, "isFile>false: �e�[�u���Ȃ��F�쐬����");
			new Write(ekigoName);

			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("create table " + ekigoName + "_t" + "(NO int," + ekigoName + " text, url text)");
			} catch (SQLException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}

			/*
			 * �e�[�u���쐬�@���@DB�f�[�^��o�^����B
			 */
			for (int j = table_row_start_num; j < table_row_finish_num; j++) {
				try {
					if (file_name_list[j] == null) {// null�ł���΁E�������
						stmt.executeUpdate("insert into " + ekigo + "_t" + "(NO," + ekigo + ", url)" + "values(" + String.valueOf(j) + ",'" + " "
								+ "','default.jpg');");//
					}
					stmt.close();
				} catch (Exception e1) {
					System.out.print(e1.toString());
					if (e1 instanceof SQLException) {
						// o("Error Code:" + ((SQLException) e1).getErrorCode());
					}
					e1.printStackTrace();
					if (conn != null) {
						try {
							conn.rollback();
							conn.close();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
				}
			}
			new l(this, "folderName::" + ekigoName);
		}
	}

	/**
	 * @throws SQLException
	 */
	private void set_db_property() throws SQLException {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (Exception e) {
//			System.out.println("��O�����F" + e);
//		}

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

		File file = new File("resource/db/pict");// sqlite�̃f�[�^�i�[�t�@�C��
		String path = file.getAbsolutePath();
		try {
			conn = DriverManager.getConnection("jdbc:sqlite://" + path);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stmt = conn.createStatement();
	}

	/**
	 * @param ekigoName
	 * @param folderName
	 */
	private void set_class_property(String ekigoName, String folderName) {
		this.setTitle(ekigoName + " �ڍ׉��");
		this.setIconImage(new ImageIcon("./resource/img/tab_img/kurumaisu.jpg").getImage());
		this.setAlwaysOnTop(true);
		this.addKeyListener(myKeyEvent);

		this.ekigo = ekigoName;// �N���X�����F�G�L����
		this.folder = folderName;// �N���X�����F�t�H���_��

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		this.setBounds(rect);
	}

	JButton makeEndButton() {
		end_button.setPreferredSize(new Dimension(300, 100));
		end_button.setBackground(CommonColor.end);
		end_button.addMouseListener(myMouse);
		end_button.addKeyListener(myKeyEvent);
		end_button.addNotify();
		end_button.setFont(new Font("", Font.PLAIN, 30));
		end_button.setBorder(new LineBorder(CommonColor.endBorder, 7, true));
		end_button.addActionListener(this);
		end_button.setActionCommand("end");
		return end_button;
	};

	public void selectTable(String fileName) {

		try {
			Statement stmt = conn.createStatement();

			new l(this, "select " + "*" + " from " + fileName + "_t");
			// ORDER BY no:����
			ResultSet executequery_resultset = stmt.executeQuery("select " + "*" + " from " + fileName + "_t");

			/*
			 * �ڍ� �e�[�u�� �\��
			 */
			System.out.println("----------------------------------------");
			System.out.print("NO. | ");
			System.out.print("�G�L���� | ");
			System.out.println("�摜��");

			int j = 0;
			while (executequery_resultset.next()) {

				int num = executequery_resultset.getInt("NO");

				ristNO[j] = new String();
				ristNO[j] = String.valueOf(num);

				fileNameList = executequery_resultset.getString(fileName);// file_name is culum

				file_name_list[executequery_resultset.getRow()] = new String();
				file_name_list[executequery_resultset.getRow()] = fileNameList;

				System.out.println("rs.getRow()==" + executequery_resultset.getRow());

				jpgFileName = executequery_resultset.getString("url");// file_name is culum

				listURL[executequery_resultset.getRow()] = new String();
				listURL[executequery_resultset.getRow()] = jpgFileName;

				System.out.print(num + " |");
				System.out.print(fileNameList + " | ");
				System.out.println(jpgFileName);
				if (j == 5) {
					break;
				}
				j++;
			}

			executequery_resultset.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void run() {
		try {
			Thread.sleep(5000);
			this.setVisible(false);
		} catch (InterruptedException e) {
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		for (int i = table_row_start_num; i < table_row_finish_num; i++) {

			/*
			 * �摜�ύX��ύX����B
			 */
			if (cmd.equals(imgButton[i].getText())) {

				JFileChooser filechooser = new JFileChooser("./resource/img/" + folder + "_�ڍ�/");

				new l(this, "------------------------------------------ImgAction>URL==" + "./resource/img/" + folder + "_�ڍ�/");

				int selected = filechooser.showOpenDialog(this);

				if (selected == JFileChooser.APPROVE_OPTION) {

					/*
					 * sql���s0 �ォ��摜������ƕ�������������
					 */
					try {
						Statement stmt = conn.createStatement();
						stmt.executeUpdate("update " + ekigo + "_t set " + " url" + "= '" + filechooser.getSelectedFile().getName() + "' where "
								+ " NO" + "=" + imgButton[i].getText() + ";");

						imgButton[i].removeAll();
						imgButton[i].add(new Zoom(new ImageIcon("./resource/img/" + folder + "_�ڍ�/" + filechooser.getSelectedFile().getName()), 0, 0,
								imgButton[i].getWidth(), imgButton[i].getHeight()));
						imgButton[i].validate();

					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					break;
				}
			}
		}
	}

	class KoeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd = e.getActionCommand();

			textArea[Integer.parseInt(cmd)].getText().replaceAll(" ", "");

			// o("ListKoeAction:" + listTextArea[Integer.parseInt(cmd)].getText());

			if (textArea[Integer.parseInt(cmd)].getText().equals(" ") | textArea[Integer.parseInt(cmd)].getText().equals("")
					| textArea[Integer.parseInt(cmd)].getText().equals("�@")) {
				// �Ȃɂ����Ȃ�
			} else {
				Koe.oto(textArea[Integer.parseInt(cmd)].getText().replaceAll(" ", ""));
			}
		}
	}

	class Deleat_ActionEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();

			for (int i = table_row_start_num; i < table_row_finish_num; i++) {
				// �����폜
				if (false == textArea[i].getText().equals(" ")) {
					if (textArea[i].getText().equals(cmd)) {
						try {
							Statement stmt = conn.createStatement();

							/*
							 * ���R�[�h���폜����F���R�[�h���̂������Ă��܂�����NG stmt.executeUpdate("DELETE  FROM " + ekigo + "_t WHERE " + ekigo + "='" +
							 * textArea[i].getText() + "';"); ���R�[�h���X�V����B
							 */
							stmt.executeUpdate("update " + ekigo + "_t set " + ekigo + "='" + "" + "' where " + "no" + "=" + String.valueOf(i) + ";");

							stmt.executeUpdate("insert into " + ekigo + "_t" + "(NO," + ekigo + ")" + "values(" + String.valueOf(ristNO[i - 1])
									+ ",'" + " " + "');");// �C���f�b�N�X�����̔ԍ��ɏ���������-20100429
							stmt.executeUpdate("update " + ekigo + "_t set " + "url" + "= '" + "default.jpg" + "' where " + " NO" + "="
									+ imgButton[i].getText() + ";");
							/*
							 * executeInsert("DELETE  FROM ����_t WHERE ����='test';"); o("DELETE  FROM " + fileName + "_t WHERE " + fileName + "='" +
							 * listTextArea[i].getText() + "';"); o("listTextButton[i].getText():::" + listTextArea[i].getText());
							 */
							stmt.close();

							setVisible(false);
							new List(ekigo, folder);
						} catch (SQLException e1) { // Error Message and Error Code
							System.out.print(e1.toString());
							e1.printStackTrace();
							if (conn != null) {
								try {
									conn.rollback();
									conn.close();
								} catch (SQLException e2) {
									e2.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}

	int keyEventCout = 0;

	public class MyKeyEvent extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			if (keyPressReleaseAvailable == true && keyEventCout == 1) {

				for (int i = 0; i < 10; i++) {
					if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i)) || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Slash") || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")) {
						keyPressReleaseAvailable = false;

						Kaiwa.keyPressAvailable = false;// �����Ƃ��ɁAKaiwa�t���[���ɃL�[�C�x���g���s���Ȃ��悤�ɂ���B

						new l(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
						Koe.oto("�Ƃ���");
						update_sql_data();
						break;
					} else {
						// �������Ȃ�
					}
				}
			}
		}

		public void keyReleased(KeyEvent e) {

			if (keyPressReleaseAvailable == false) {

				for (int i = 0; i < 10; i++) {
					if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i)) || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
							|| KeyEvent.getKeyText(e.getKeyChar()).equals("Slash") || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
					// Num Lock�͐���s��
					) {
						keyPressReleaseAvailable = true;
						keyEventCout++;
						new l(this, "moveEkigo>keyReleased == " + KeyEvent.getKeyText(e.getKeyChar()));
						break;
					}
				}
			}
		}
	}

	public void update_sql_data() {
		try {
			Statement stmt = conn.createStatement();
			for (int i = table_row_start_num; i < table_row_finish_num; i++) {
				stmt.executeUpdate("update " + ekigo + "_t set " + ekigo + "='" + textArea[i].getText() + "' where " + "no" + "=" + String.valueOf(i)
						+ ";");
			}
			stmt.close();
			this.dispose();

		} catch (Exception e1) {
			System.out.print(e1.toString());
			if (e1 instanceof SQLException) {
				// o("Error Code:" + ((SQLException) e1).getErrorCode());
			}
			e1.printStackTrace();
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	class MyMouse extends MouseAdapter {

		public void mousePressed(MouseEvent e1) {
			if (end_button == e1.getSource()) {
				Koe.oto("�Ƃ���");
				update_sql_data();
			}
		}

		public void mouseEntered(MouseEvent e1) {

			for (int i = table_row_start_num; i < table_row_finish_num; i++) {
				if (e1.getSource() == playButton[i]) {
					playButton[i].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
				} else if (e1.getSource() == imgButton[i]) {
					imgButton[i].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
				} else if (e1.getSource() == deleteButtons[i]) {
					deleteButtons[i].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
				}
			}
		}

		public void mouseExited(MouseEvent e1) {
			for (int i = table_row_start_num; i < table_row_finish_num; i++) {
				if (e1.getSource() == playButton[i]) {
					playButton[i].setBorder(new LineBorder(Color.white, 1, false));
				} else if (e1.getSource() == imgButton[i]) {
					imgButton[i].setBorder(new LineBorder(Color.white, 1, false));
				} else if (e1.getSource() == deleteButtons[i]) {
					deleteButtons[i].setBorder(new LineBorder(Color.white, 1, false));
				}
			}
		}
	}


	public static void main(String[] args) {
		try {
			new List("�ς�", "tab7");
		} catch (HeadlessException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
		// o("n------------------------------------------" + n_);
	}
}
