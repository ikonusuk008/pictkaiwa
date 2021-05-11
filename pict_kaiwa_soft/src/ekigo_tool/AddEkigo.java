package ekigo_tool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import main_frame.Prop;
import statics.CommonColor;
import statics.Koe;
import statics.Zoom;
import test_tool.l;

public class AddEkigo extends JFrame implements ActionListener {

	public static String category_from_property[] = new String[11];// プロパティから取り出したカテゴリー名
	public static String category_img_from_property[] = new String[11];// プロパティから取り出したカテゴリー画像名

	JButton mokujiButton[] = new JButton[9];// 目次ボタン・絵記号ボタン
	JButton mokujiButtonList[] = new JButton[9];// 目次ボタン・絵記号ボタン

	static String mokujiString[] = { "tab2", "tab3", "tab4", "tab5", "tab6", "tab7", "tab8", "tab9", "tab10" };

	JPanel pict_main_panel = new JPanel(new GridLayout(2, 1, 0, 0));

	JPanel pict_category_panel = new JPanel(new GridLayout(1, 1));

	JPanel category_button = new JPanel(new GridLayout(3, 3, 5, 5));

	JPanel pict_category_syousai_panel = new JPanel(new GridLayout(1, 1));
	JPanel ekigoPanelSouth_w = new JPanel();
	JPanel category_botton_list = new JPanel(new GridLayout(3, 3, 5, 5));

	JButton addEkigoEnd = new JButton();

	public AddEkigo() throws FileNotFoundException, IOException {
		super();

	    get_property();

		set_class_property();

		make_end_button();

		make_panels();

		for (int i = 0; i < mokujiButton.length; i++) {
			mokujiButton[i] = new JButton(category_from_property[i]);
			mokujiButton[i].addActionListener(this);
			mokujiButton[i].setActionCommand(mokujiButton[i].getText());
			mokujiButton[i].setForeground(new Color(255, 255, 255));
			mokujiButton[i].setBackground(Color.black);
			mokujiButton[i].setBorder(new LineBorder(Color.black, 3, true));
			mokujiButton[i].addMouseListener(new MyMouse());
			mokujiButton[i].setHorizontalAlignment(SwingConstants.RIGHT);
			category_button.add(mokujiButton[i]);

			mokujiButtonList[i] = new JButton("<html>"+category_from_property[i] + "<font color=#ffa500>_詳細</html>");
			mokujiButtonList[i].addActionListener(this);
			mokujiButtonList[i].setActionCommand(mokujiButton[i].getText() + "_詳細");
			mokujiButtonList[i].setForeground(new Color(255, 255, 255));
			mokujiButtonList[i].setBackground(Color.black);
			mokujiButtonList[i].setBorder(new LineBorder(Color.black, 3, true));
			mokujiButtonList[i].addMouseListener(new MyMouse());
			mokujiButtonList[i].setHorizontalAlignment(SwingConstants.RIGHT);
			category_botton_list.add(mokujiButtonList[i]);
		}

		pict_category_panel.add(category_button, BorderLayout.EAST);
		pict_category_syousai_panel.add(category_botton_list, BorderLayout.EAST);

		pict_main_panel.add(pict_category_panel);
		pict_main_panel.add(pict_category_syousai_panel);

		JPanel endPanel = new JPanel(new GridLayout());
		endPanel.setPreferredSize(new Dimension(0, 100));
		endPanel.add(addEkigoEnd);

		this.add(pict_main_panel, BorderLayout.CENTER);

		this.add(endPanel, BorderLayout.SOUTH);
		this.setVisible(true);

		for (int i = 0; i < mokujiButton.length; i++) {
			this.setVisible(true);
			new l(this, "mokujiButton[i]====" + mokujiButton[i].getHeight());

			mokujiButton[i].setFont(new Font("", Font.BOLD, mokujiButton[i].getHeight() / 5));
			mokujiButtonList[i].setFont(new Font("", Font.BOLD, mokujiButton[i].getHeight() / 5));

			Zoom zoomImage = new Zoom(new ImageIcon("./resource/img/tab1/" + category_img_from_property[i] + ".jpg"), 10, 0, mokujiButton[i]
					.getHeight(), mokujiButton[i].getHeight());
			mokujiButton[i].add(zoomImage);

			Zoom zoomImage2 = new Zoom(new ImageIcon("./resource/img/tab1/" + category_img_from_property[i] + ".jpg"), 10, 0, mokujiButton[i]
					.getHeight(), mokujiButton[i].getHeight());
			mokujiButtonList[i].add(zoomImage2);
		}
	}

	/**
	 *
	 */
	private void make_panels() {
		pict_category_panel.setBackground(Color.white);
		pict_category_panel.setPreferredSize(new Dimension(1000, 600));

		pict_category_syousai_panel.setPreferredSize(new Dimension(1000, 600));
		pict_category_syousai_panel.setBackground(Color.white);
	}

	/**
	 *
	 */
	private void make_end_button() {
		addEkigoEnd.setText("変更・閉じる(テンキー又はクリック)");
		addEkigoEnd.setFont(new Font("", Font.BOLD, 30));
		addEkigoEnd.addActionListener(this);
		addEkigoEnd.setBackground(CommonColor.end);// MyKeyEvent
		addEkigoEnd.addKeyListener(new MyKeyEvent());
		addEkigoEnd.setBorder(new LineBorder(CommonColor.endBorder, 5, true));
		addEkigoEnd.setActionCommand("end");
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void get_property() throws IOException, FileNotFoundException {
		Prop prop = new Prop();

		/*
		 * 目次項目
		 */
		for (int j = 1; j < 11; j++) {
			category_from_property[j-1] = new String();
			category_from_property[j-1] = prop.getPict().getProperty("seting.category_name" + String.valueOf(j));// カテゴリ名文字列

			category_img_from_property[j-1] = new String();
			category_img_from_property[j-1] = prop.getPict().getProperty("seting.category_img_name" + String.valueOf(j));// カテゴリ画像名文字列

			System.out.println("category_from_property[" + j + "]==" + category_from_property[j]);
			System.out.println("category_img_from_property[" + j + "]==" + category_img_from_property[j]);
		}
	}

	/**
	 *
	 */
	private void set_class_property() {

//		String type = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//	    try {
//	      UIManager.setLookAndFeel(type);
//	    } catch ( Exception e ) {
//	      System.out.println("例外発生：" + e );
//	    }


		ImageIcon icon = new ImageIcon("./resource/img/tab_img/kurumaisu.jpg");
		// this.setAlwaysOnTop(true);
		this.setUndecorated(false);
		this.setIconImage(icon.getImage());
		this.setLayout(new BorderLayout());
		this.setTitle("絵記号の追加");

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		this.setBounds(rect);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		for (int i = 0; i < mokujiButton.length; i++) {

			if (cmd.equals(mokujiButton[i].getText())) {

				new l(this, "mokujiButton[i].getText())==" + mokujiButton[i].getText());
				new l(this, "cmd==" + cmd);

				String result;
				try {
					Runtime rt = Runtime.getRuntime();
					// Process p = rt.exec("CMD.EXE /C DIR .\\resource\\img\\" + mokujiString[i]);
					Process p = rt.exec("cmd /c start,　.\\resource\\img\\" + mokujiString[i]);
					InputStream is = p.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					while ((result = br.readLine()) != null) {
						System.out.println(result);
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			if (cmd.equals(mokujiButton[i].getText() + "_詳細")) {

				String result;
				try {
					Runtime rt = Runtime.getRuntime();
					// Process p = rt.exec("CMD.EXE /C DIR .\\resource\\img\\" + mokujiString[i]);
					Process p = rt.exec("cmd /c start,　.\\resource\\img\\" + mokujiString[i] + "_詳細");
					InputStream is = p.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					while ((result = br.readLine()) != null) {
						System.out.println(result);
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		if (cmd.equals("end")) {
			Koe.oto("そふとをさいきどうするとせっていがはんえいされます");
			this.dispose();
		}

		if (cmd.equals("目次")) {
			JFileChooser filechooser = new JFileChooser(".\\resource\\img\\tab1");
			filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int selected = filechooser.showSaveDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				new l(this, "file.getAbsolutePath()==" + file.getAbsolutePath());

				try {
					copyTransfer(file.getAbsolutePath(), ".\\resource\\img\\tab1\\" + file.getName());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * コピー元のパス[srcPath]から、コピー先のパス[destPath]へ ファイルのコピーを行います。 コピー処理にはFileChannel#transferToメソッドを利用します。 尚、コピー処理終了後、入力・出力のチャネルをクローズします。
	 *
	 * @param srcPath
	 *            コピー元のパス
	 * @param destPath
	 *            コピー先のパス
	 * @throws IOException
	 *             何らかの入出力処理例外が発生した場合
	 */
	public static void copyTransfer(String srcPath, String destPath) throws IOException {

		FileChannel srcChannel = new FileInputStream(srcPath).getChannel();
		FileChannel destChannel = new FileOutputStream(destPath).getChannel();
		try {
			srcChannel.transferTo(0, srcChannel.size(), destChannel);
		} finally {
			srcChannel.close();
			destChannel.close();
		}
	}

	public class MyKeyEvent extends KeyAdapter {

		public void keyTyped(KeyEvent e) {

			new l(this, "KeyEvent.getKeyText: " + KeyEvent.getKeyText(e.getKeyChar()));

			for (int i = 0; i < 10; i++) {
				if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i)) || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
						|| KeyEvent.getKeyText(e.getKeyChar()).equals("Enter") || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
						|| KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a") || KeyEvent.getKeyText(e.getKeyChar()).equals("Slash")
						|| KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
				// Num Lockは制御不可

				) {// ボタンを決定
					Koe.oto("そふとをさいきどうするとせっていがはんえいされます");
					System.exit(0);
				}
			}
		}
	}

	class MyMouse extends MouseAdapter {

		public void mouseEntered(MouseEvent e1) {

			new l(this, "test");

			for (int i = 0; i < mokujiButtonList.length; i++) {
				if (e1.getSource() == mokujiButtonList[i]) {
					mokujiButtonList[i].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
				}
			}

			for (int i = 0; i < mokujiButton.length; i++) {
				if (e1.getSource() == mokujiButton[i]) {
					mokujiButton[i].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
				}
			}
		}

		public void mouseExited(MouseEvent e1) {

			for (int i = 0; i < mokujiButtonList.length; i++) {
				if (e1.getSource() == mokujiButtonList[i]) {
					mokujiButtonList[i].setBorder(new LineBorder(Color.black, 8, false));
				}
			}

			for (int i = 0; i < mokujiButton.length; i++) {
				if (e1.getSource() == mokujiButton[i]) {
					mokujiButton[i].setBorder(new LineBorder(Color.black, 8, false));
				}
			}
		}
	}

	public static void main(String a[]) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new AddEkigo();
				} catch (FileNotFoundException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		});
	}
}
