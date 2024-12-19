package pictkaiwa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.Constants;
import util.Koe;
import util.Lg;
import util.Zoom;
import util.ZoomTab;



public class Set extends JFrame implements ActionListener {

	static JTabbedPane tabbedPane;

	MyMouse myMouse = new MyMouse();
	JPanel mainP;
	JPanel mainP2;
	Prop prop_ = new Prop();

	/*設定画面コンポーネント
	 */
	JPanel mokujiP;
	JPanel mokujiPP;
	JPanel mokujiPPB;

	/*以下4つで一つのカテゴリをのコンポーネント
	 */
	JPanel one_category_panel_on_3conponent[] = new JPanel[10];//１つのカテゴリ（チェックボックス、絵記号、絵記号名）
	JCheckBox mokujiPPPCb[] = new JCheckBox[10];// チェックボックス
	JButton mokujiPPPnB[] = new JButton[10];//　絵記号
	JTextArea category_name_textarea[] = new JTextArea[10];//　絵記号名　//変更：　テキストエリアにする

	/*
	 *
	 */
	public static String category_name[] = new String[10];//カテゴリ名前　プロパティ
	public static String category_img_name[] = new String[10];//カテゴリ名前　プロパティ
	String category_boolean_string[] = new String[10];//カテゴリ　表示フラグ　文字列型
	Boolean category_boolean[] = new Boolean[10];//カテゴリ　表示フラグ　boolea型

	/*
	 *
	 */
	JPanel gyouP;
	JButton gyouB;
	JRadioButton gyouRadio1;
	JRadioButton gyouRadio2;
	String radioGyouS;
	// ///////////////////////////////////////
	JPanel roopP;
	JButton roopB;
	JRadioButton roopRadio1;
	JRadioButton roopRadio2;
	String radioRoopS;
	// ///////////////////////////////////////
	JPanel roopPl;
	JButton roopBl;
	JRadioButton roopRadio1l;
	JRadioButton roopRadio2l;
	JRadioButton roopRadio3l;
	String radioRoopSl;

	// ///////////////////////////////////////
	JPanel speedP;
	JPanel setEkigoSpeedP;
	JPanel setEkigoSpeedPB;
	JButton ekigoSpeedB;
	JRadioButton sizeSpeed1;
	JRadioButton sizeSpeed2;
	JRadioButton sizeSpeed3;
	JRadioButton sizeSpeed4;
	JRadioButton sizeSpeed5;
	String speedIndexS;
	// ///////////////////////////////////////
	JPanel sizeP;
	JPanel setEkigoSizeP;
	JPanel setEkigoSizePB;
	JPanel endP;
	JButton ekigoSizeB;
	JRadioButton sizeRadio1;
	JRadioButton sizeRadio2;
	JRadioButton sizeRadio3;
	JRadioButton sizeRadio4;
	JRadioButton sizeRadio5;
	String radioIndexS;

	JButton setEndButton;

	String mokujiDir_10_pro[] = new String[10];

	Zoom zoomImg;

	Rectangle rect;

	public Set() throws FileNotFoundException, IOException {

		ImageIcon icon = new ImageIcon("./resource/img/tab_img/kurumaisu.jpg");
		this.setIconImage(icon.getImage());

		/*
		 * Insets
		 */
		UIManager.put("TabbedPane.tabInsets", new Insets(5, 8, 5, 8));// タブの上下空間設定
		// UIManager.put("TabbedPane.tabAreaInsets", new Insets(8,8,8,8));//タブパネルの上下空間設定
		// UIManager.put("TabbedPane.contentBorderInsets", new Insets(8,8,8,8));
		// UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(8,8,8,8));

		/*
		 * Color
		 */
		UIManager.put("TabbedPane.shadow", Color.GRAY);
		UIManager.put("TabbedPane.darkShadow", Color.GRAY);
		UIManager.put("TabbedPane.light", Color.GRAY);
		UIManager.put("TabbedPane.highlight", Color.GRAY);
		UIManager.put("TabbedPane.tabAreaBackground", new Color(255, 255, 255));
		UIManager.put("TabbedPane.unselectedBackground", Color.black);
		UIManager.put("TabbedPane.selectedForeground", new Color(255, 255, 255));
		UIManager.put("TabbedPane.background",Color.LIGHT_GRAY);
		UIManager.put("TabbedPane.foreground",  new Color(0, 0, 0));
		UIManager.put("TabbedPane.focus", new Color(255, 255, 255));
		UIManager.put("TabbedPane.contentAreaColor", new Color(255, 255, 255));
		UIManager.put("TabbedPane.selected",  new Color(255, 255, 255));// タブ選択時、色をつける。
		UIManager.put("TabbedPane.selectHighlight", new Color(0, 0, 0));// タブ左上の線の色
		UIManager.put("TabbedPane.borderHightlightColor", new Color(0, 0, 0));


		/*
		 * Opaque
		 */
		// UIManager.put("TabbedPane.tabsOpaque", Boolean.FALSE);
		// UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
		/*
		 * ???
		 */
		UIManager.put("TabbedPane.tabRunOverlay", Boolean.TRUE);
		UIManager.put("TabbedPane.tabsOverlapBorder", Boolean.TRUE);
		UIManager.put("TabbedPane.selectionFollowsFocus", Boolean.TRUE);

		tabbedPane = new JTabbedPane(SwingConstants.NORTH);


		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		rect = env.getMaximumWindowBounds();
		this.setAlwaysOnTop(true);
		this.setBounds(rect);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("絵記号会話ソフト　設定");
		this.setLayout(new BorderLayout());

		mainP = new JPanel(new GridLayout(3, 1));
		mainP.setBackground(Color.black);

		mainP2 = new JPanel(new GridLayout(3, 1));
		mainP2.setBackground(Color.black);

		mainP.add(roopOnOff());
		mainP.add(ekigoGyou());
		mainP.add(mokujiSelect());

		mainP2.add(showList());
		mainP2.add(loopSpeed());

		endP.add(endButton());
		this.add(endP, BorderLayout.SOUTH);

		ZoomTab ZoomTabImg1 = new ZoomTab(new ImageIcon("./resource/img/setting/tab1.jpg"), 0, 0, 90, 40);
		ZoomTab ZoomTabImg2 = new ZoomTab(new ImageIcon("./resource/img/setting/tab2.jpg"), 0, 0, 90, 40);


		tabbedPane.add("<html><h2><b>絵記号選択方法・絵記号表示数・分野表示</b></h2></html>",mainP);
		tabbedPane.add("<html><h2><b>詳細表示内容・選択枠移動速度</b></h2></html>",mainP2);


		/*
		 * タブの前景色を切り替えイベント時、設定する。//効かない。
		 */
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JTabbedPane jtab = (JTabbedPane) e.getSource();
				int sindex = jtab.getSelectedIndex();

				for (int i = 0; i < jtab.getTabCount(); i++) {
					if (i == sindex && jtab.getTitleAt(sindex).endsWith("1")) {
						// jtab.setForegroundAt(i, Color.GREEN);//　？
					} else if (i == sindex) {
						// Color sc = (sindex % 2 == 0) ? Color.RED : Color.BLUE;
						jtab.setForegroundAt(i, Color.white);
					} else {
						jtab.setForegroundAt(i, Color.white);
					}
				}

			}
		});

		this.add(tabbedPane);
		this.setVisible(true);
	}

	// 詳細画面の表示方法（リスト・絵記号・絵記号一時）
	JPanel showList() {
		JPanel setEkigoRoopP;
		JPanel setEkigoRoopPB;
		JButton ekigoRoopB;

		setEkigoRoopP = new JPanel(new BorderLayout());
		setEkigoRoopP.setBorder(new LineBorder(Color.white, 3, true));
		setEkigoRoopPB = new JPanel(new GridLayout());

		endP = new JPanel(new GridLayout());
		endP.setPreferredSize(new Dimension(0, 100));

		ekigoRoopB = new JButton("<html>【詳細表示】<br>" + "①絵記号の意味をリストアップ<br>" + "②絵記号拡大表示<br>" + "③絵記号拡大表示し５秒で消える</html>");
		ekigoRoopB.setFont(new Font("", Font.BOLD, 25));
		ekigoRoopB.setForeground(Color.white);
		ekigoRoopB.setBackground(Color.black);
		setEkigoRoopPB.add(ekigoRoopB);

		ImageIcon icon1 = new ImageIcon("./resource/img/setting/listON.jpg");
		ImageIcon sicon1 = new ImageIcon("./resource/img/setting/listONon.jpg");
		roopRadio1l = new JRadioButton("①説明を表示", icon1);

		ImageIcon icon2 = new ImageIcon("./resource/img/setting/listOFF.jpg");
		ImageIcon sicon2 = new ImageIcon("./resource/img/setting/listOFFon.jpg");
		roopRadio2l = new JRadioButton("②絵記号を拡大表示", icon2);

		ImageIcon icon3 = new ImageIcon("./resource/img/setting/listNO.jpg");
		ImageIcon sicon3 = new ImageIcon("./resource/img/setting/listNOon.jpg");
		roopRadio3l = new JRadioButton("③絵記号一時的に表示", icon2);

		try {
			if (prop_.getPict().getProperty("seting.list").equals("①説明を表示")) {
				roopRadio1l = new JRadioButton("①説明を表示", icon1, true);

			} else if (prop_.getPict().getProperty("seting.list").equals("②絵記号を拡大表示")) {
				roopRadio2l = new JRadioButton("②絵記号を拡大表示", icon2, true);

			} else if (prop_.getPict().getProperty("seting.list").equals("③絵記号一時的に表示")) {
				roopRadio3l = new JRadioButton("③絵記号一時的に表示", icon3, true);
			}
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		roopRadio1l.setSelectedIcon(sicon1);
		roopRadio2l.setSelectedIcon(sicon2);
		roopRadio3l.setSelectedIcon(sicon3);

		ButtonGroup group = new ButtonGroup();
		group.add(roopRadio1l);
		group.add(roopRadio2l);
		group.add(roopRadio3l);

		roopRadio1l.setFont(new Font("", Font.BOLD, 25));
		roopRadio2l.setFont(new Font("", Font.BOLD, 25));
		roopRadio3l.setFont(new Font("", Font.BOLD, 25));

		roopRadio1l.setForeground(Color.white);
		roopRadio2l.setForeground(Color.white);
		roopRadio3l.setForeground(Color.white);

		roopRadio1l.setBorder(new LineBorder(Color.white, 2, true));
		roopRadio2l.setBorder(new LineBorder(Color.white, 2, true));
		roopRadio3l.setBorder(new LineBorder(Color.white, 2, true));

		roopRadio1l.setBackground(Color.black);
		roopRadio2l.setBackground(Color.black);
		roopRadio3l.setBackground(Color.black);

		roopPl = new JPanel(new GridLayout());
		roopPl.setBackground(Color.black);

		roopPl.add(roopRadio1l);
		roopPl.add(roopRadio2l);
		roopPl.add(roopRadio3l);

		setEkigoRoopP.add(setEkigoRoopPB, BorderLayout.WEST);
		setEkigoRoopP.add(roopPl, BorderLayout.CENTER);

		return setEkigoRoopP;
	}

	JPanel mokujiSelect() {
		ImageIcon icon1 = new ImageIcon("./resource/img/setting/mokuji.jpg");
		ImageIcon sicon1 = new ImageIcon("./resource/img/setting/smokuji.jpg");
		try {
			for (int i = 0; i < 10; i++) {
				category_name[i] = new String();
				category_name[i] = prop_.getPict().getProperty("seting.category_name" + String.valueOf(i));

				category_img_name[i] = new String();
				category_img_name[i] = prop_.getPict().getProperty("seting.category_img_name" + String.valueOf(i));
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~category_img_name["+i+"]=="+category_img_name[i] );

				category_boolean_string[i] = new String();
				category_boolean_string[i] = prop_.getPict().getProperty("seting.mokujiBool" + String.valueOf(i));
				category_boolean[i] = Boolean.parseBoolean(category_boolean_string[i]);
			}
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		JPanel category_visual_setting_main_panel = new JPanel(new BorderLayout());
		category_visual_setting_main_panel.setBorder(new LineBorder(Color.white, 3, false));
		JPanel category_next_of_main_panel = new JPanel(new GridLayout(1, 9, 2, 2));

		JButton mokujiPPB = new JButton("<html>①分野 選択<br>" + "②分野 画像変更<br>" + "③画像名 変更</html>");

		mokujiPPB.setForeground(Color.white);
		mokujiPPB.setBackground(Color.black);
		mokujiPPB.setFont(new Font("", Font.BOLD, 25));

		/*
		 * 目次表示設定 for (int i = 1; i < 10; i++) {　i=0は目次だった。
		 */

		for (int i = 0; i < 10; i++) {
			one_category_panel_on_3conponent[i] = new JPanel(new BorderLayout());
			one_category_panel_on_3conponent[i].setBackground(Color.red);

			mokujiPPPCb[i] = new JCheckBox(icon1);

			if(i == 0){
				//目次は設定しない（常にfalse）
				mokujiPPPCb[i].setSelected(false);
				mokujiPPPCb[i].setPreferredSize(new Dimension(30, 30));
				mokujiPPPCb[i].setBackground(Color.black);
				mokujiPPPCb[i].setSelectedIcon(icon1);
				mokujiPPPCb[i].setHorizontalTextPosition(JButton.CENTER);
				mokujiPPPCb[i].setBorder(new LineBorder(Color.white, 5, false));
			}else{
				mokujiPPPCb[i].setSelected(category_boolean[i]);
				mokujiPPPCb[i].setPreferredSize(new Dimension(30, 30));
				mokujiPPPCb[i].setBackground(Color.black);
				mokujiPPPCb[i].setSelectedIcon(sicon1);
				mokujiPPPCb[i].setHorizontalTextPosition(JButton.CENTER);
				mokujiPPPCb[i].setBorder(new LineBorder(Color.white, 5, false));
			}



			mokujiPPPnB[i] = new JButton(String.valueOf(i));
			mokujiPPPnB[i].setBackground(Color.black);
			mokujiPPPnB[i].addActionListener(this);
			mokujiPPPnB[i].addMouseListener(myMouse);
			mokujiPPPnB[i].setBorder(new LineBorder(Color.white, 1, false));

			new Lg(this,"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~category_name["+i+"]"+category_name[i]);

			category_name_textarea[i] = new JTextArea(category_name[i]);//カテゴリの 文字列設定
			category_name_textarea[i].setPreferredSize(new Dimension(30, 30));
			category_name_textarea[i].setBackground(Color.black);
			category_name_textarea[i].setForeground(Color.white);
			category_name_textarea[i].setCaretColor(Color.white);
			category_name_textarea[i].setBorder(new LineBorder(Color.white, 0,true));
			category_name_textarea[i].setFont(new Font("", Font.BOLD, 15));
			category_name_textarea[i].putClientProperty("caretAspectRatio", Float.valueOf(0.3F));

			// ※mokujiPP_pが影響して、BorderLayout+GridLayoutになっている？
			one_category_panel_on_3conponent[i].add(mokujiPPPCb[i], BorderLayout.NORTH);
			one_category_panel_on_3conponent[i].add(mokujiPPPnB[i], BorderLayout.CENTER);
			one_category_panel_on_3conponent[i].add(category_name_textarea[i], BorderLayout.SOUTH);

			category_next_of_main_panel.add(one_category_panel_on_3conponent[i]);

			new Lg(this, "(int)rect.getWidth()==" + (int) rect.getWidth());

			zoomImg = new Zoom(new ImageIcon("./resource/img/tab1/" + category_img_name[i] + ".jpg"), 0, 0, ((int) rect.getWidth() - 150) / 10, ((int) rect
					.getWidth() - 150) / 10);

			mokujiPPPnB[i].add(zoomImg);
			this.setVisible(true);
		}

		category_visual_setting_main_panel.setBackground(Color.black);
		category_visual_setting_main_panel.add(mokujiPPB, BorderLayout.WEST);
		category_visual_setting_main_panel.add(category_next_of_main_panel, BorderLayout.CENTER);
		return category_visual_setting_main_panel;
	}

	// ループ速度
	private JPanel loopSpeed() throws FileNotFoundException, IOException {
		setEkigoSpeedP = new JPanel(new BorderLayout());
		setEkigoSpeedPB = new JPanel(new GridLayout());
		ekigoSpeedB = new JButton("移動枠の速度(秒)");
		ekigoSpeedB.setFont(new Font("", Font.BOLD, 25));
		ekigoSpeedB.setForeground(Color.white);
		ekigoSpeedB.setBackground(Color.black);
		setEkigoSpeedPB.add(ekigoSpeedB);
		setEkigoSpeedP.setBorder(new LineBorder(Color.white, 3, true));

		ImageIcon icon1 = new ImageIcon("./resource/img/setting/speed.jpg");
		ImageIcon icon2 = new ImageIcon("./resource/img/setting/speed.jpg");
		ImageIcon icon3 = new ImageIcon("./resource/img/setting/speed.jpg");
		ImageIcon icon4 = new ImageIcon("./resource/img/setting/speed.jpg");
		ImageIcon icon5 = new ImageIcon("./resource/img/setting/speed.jpg");

		ImageIcon sicon1 = new ImageIcon("./resource/img/setting/speedon.jpg");
		ImageIcon sicon2 = new ImageIcon("./resource/img/setting/speedon.jpg");
		ImageIcon sicon3 = new ImageIcon("./resource/img/setting/speedon.jpg");
		ImageIcon sicon4 = new ImageIcon("./resource/img/setting/speedon.jpg");
		ImageIcon sicon5 = new ImageIcon("./resource/img/setting/speedon.jpg");

		sizeSpeed1 = new JRadioButton("2", icon1);
		sizeSpeed2 = new JRadioButton("3", icon2);
		sizeSpeed3 = new JRadioButton("4", icon3);
		sizeSpeed4 = new JRadioButton("5", icon4);
		sizeSpeed5 = new JRadioButton("6", icon5);

		if (prop_.getPict().getProperty("seting.speed").equals("2")) {
			sizeSpeed1 = new JRadioButton("2", icon1, true);
		} else if (prop_.getPict().getProperty("seting.speed").equals("3")) {
			sizeSpeed2 = new JRadioButton("3", icon2, true);
		} else if (prop_.getPict().getProperty("seting.speed").equals("4")) {
			sizeSpeed3 = new JRadioButton("4", icon3, true);
		} else if (prop_.getPict().getProperty("seting.speed").equals("5")) {
			sizeSpeed4 = new JRadioButton("5", icon4, true);
		} else if (prop_.getPict().getProperty("seting.speed").equals("6")) {
			sizeSpeed5 = new JRadioButton("6", icon5, true);
		}

		sizeSpeed1.setSelectedIcon(sicon1);
		sizeSpeed2.setSelectedIcon(sicon2);
		sizeSpeed3.setSelectedIcon(sicon3);
		sizeSpeed4.setSelectedIcon(sicon4);
		sizeSpeed5.setSelectedIcon(sicon5);

		ButtonGroup group = new ButtonGroup();
		group.add(sizeSpeed1);
		group.add(sizeSpeed2);
		group.add(sizeSpeed3);
		group.add(sizeSpeed4);
		group.add(sizeSpeed5);

		sizeSpeed1.setFont(new Font("", Font.BOLD, 50));
		sizeSpeed2.setFont(new Font("", Font.BOLD, 50));
		sizeSpeed3.setFont(new Font("", Font.BOLD, 50));
		sizeSpeed4.setFont(new Font("", Font.BOLD, 50));
		sizeSpeed5.setFont(new Font("", Font.BOLD, 50));

		sizeSpeed1.setForeground(Color.white);
		sizeSpeed2.setForeground(Color.white);
		sizeSpeed3.setForeground(Color.white);
		sizeSpeed4.setForeground(Color.white);
		sizeSpeed5.setForeground(Color.white);

		sizeSpeed1.setBorder(new LineBorder(Color.white, 2, true));
		sizeSpeed2.setBorder(new LineBorder(Color.white, 2, true));
		sizeSpeed3.setBorder(new LineBorder(Color.white, 2, true));
		sizeSpeed4.setBorder(new LineBorder(Color.white, 2, true));
		sizeSpeed5.setBorder(new LineBorder(Color.white, 2, true));

		sizeSpeed1.setBackground(Color.black);
		sizeSpeed2.setBackground(Color.black);
		sizeSpeed3.setBackground(Color.black);
		sizeSpeed4.setBackground(Color.black);
		sizeSpeed5.setBackground(Color.black);

		speedP = new JPanel(new GridLayout());
		speedP.setBackground(Color.black);

		speedP.add(sizeSpeed1);
		speedP.add(sizeSpeed2);
		speedP.add(sizeSpeed3);
		speedP.add(sizeSpeed4);
		speedP.add(sizeSpeed5);

		setEkigoSpeedP.add(setEkigoSpeedPB, BorderLayout.WEST);
		setEkigoSpeedP.add(speedP, BorderLayout.CENTER);

		return setEkigoSpeedP;
	}

	// 上下左右のループ選択　ON・OFF切り替え
	private JPanel roopOnOff() throws IOException, FileNotFoundException {
		JPanel roopP;
		JPanel setEkigoRoopP;
		JPanel setEkigoRoopPB;
		JButton ekigoRoopB;

		setEkigoRoopP = new JPanel(new BorderLayout());
		setEkigoRoopP.setBorder(new LineBorder(Color.white, 3, true));
		setEkigoRoopPB = new JPanel(new GridLayout());
		endP = new JPanel(new GridLayout());
		endP.setPreferredSize(new Dimension(0, 100));
		ekigoRoopB = new JButton("<html>【選択方法】<br>ON：枠移動選択<br>OFF：画面タッチ選択</html>");
		ekigoRoopB.setFont(new Font("", Font.BOLD, 25));
		ekigoRoopB.setForeground(Color.white);
		ekigoRoopB.setBackground(Color.black);
		setEkigoRoopPB.add(ekigoRoopB);

		ImageIcon icon1 = new ImageIcon("./resource/img/setting/roopON.jpg");
		ImageIcon icon2 = new ImageIcon("./resource/img/setting/roopOFF.jpg");

		ImageIcon sicon1 = new ImageIcon("./resource/img/setting/roopONon.jpg");
		ImageIcon sicon2 = new ImageIcon("./resource/img/setting/roopOFFon.jpg");

		roopRadio1 = new JRadioButton("ON", icon1);
		roopRadio2 = new JRadioButton("OFF", icon2);

		if (prop_.getPict().getProperty("seting.roop").equals("ON")) {
			roopRadio1 = new JRadioButton("ON", icon1, true);
		} else if (prop_.getPict().getProperty("seting.roop").equals("OFF")) {
			roopRadio2 = new JRadioButton("OFF", icon2, true);
		}
		roopRadio1.setSelectedIcon(sicon1);
		roopRadio2.setSelectedIcon(sicon2);

		ButtonGroup group = new ButtonGroup();
		group.add(roopRadio1);
		group.add(roopRadio2);

		roopRadio1.setFont(new Font("", Font.BOLD, 50));
		roopRadio2.setFont(new Font("", Font.BOLD, 50));

		roopRadio1.setForeground(Color.white);
		roopRadio2.setForeground(Color.white);

		roopRadio1.setBorder(new LineBorder(Color.white, 2, true));
		roopRadio2.setBorder(new LineBorder(Color.white, 2, true));

		roopRadio1.setBackground(Color.black);
		roopRadio2.setBackground(Color.black);

		roopP = new JPanel(new GridLayout());
		roopP.setBackground(Color.black);

		roopP.add(roopRadio1);
		roopP.add(roopRadio2);

		setEkigoRoopP.add(setEkigoRoopPB, BorderLayout.WEST);
		setEkigoRoopP.add(roopP, BorderLayout.CENTER);

		return setEkigoRoopP;
	}

	// 行数：上下の絵記号の数
	private JPanel ekigoGyou() throws IOException, FileNotFoundException {
		JPanel gyouP;
		JPanel setEkigoRoopP;
		JPanel setEkigoRoopPB;
		JButton ekigoRoopB;

		setEkigoRoopP = new JPanel(new BorderLayout());
		setEkigoRoopP.setBorder(new LineBorder(Color.white, 3, true));
		setEkigoRoopPB = new JPanel(new GridLayout());
		endP = new JPanel(new GridLayout());
		endP.setPreferredSize(new Dimension(0, 100));
		ekigoRoopB = new JButton("<html>【表示数】<br>３：縦３個×横５個<br>４：縦４個×横７個<br>（28個まで画像を表示できます）</html>");
		ekigoRoopB.setFont(new Font("", Font.BOLD, 25));
		ekigoRoopB.setForeground(Color.white);
		ekigoRoopB.setBackground(Color.black);
		setEkigoRoopPB.add(ekigoRoopB);

		ImageIcon icon1 = new ImageIcon("./resource/img/setting/3.jpg");
		ImageIcon icon2 = new ImageIcon("./resource/img/setting/4.jpg");

		ImageIcon sicon1 = new ImageIcon("./resource/img/setting/3on.jpg");
		ImageIcon sicon2 = new ImageIcon("./resource/img/setting/4on.jpg");

		gyouRadio1 = new JRadioButton("3", icon1);
		gyouRadio2 = new JRadioButton("4", icon2);

		if (prop_.getPict().getProperty("seting.gyou").equals("3")) {
			gyouRadio1 = new JRadioButton("3", icon1, true);
		} else if (prop_.getPict().getProperty("seting.gyou").equals("4")) {
			gyouRadio2 = new JRadioButton("4", icon2, true);
		}

		gyouRadio1.setSelectedIcon(sicon1);
		gyouRadio2.setSelectedIcon(sicon2);

		ButtonGroup group = new ButtonGroup();
		group.add(gyouRadio1);
		group.add(gyouRadio2);

		gyouRadio1.setFont(new Font("", Font.BOLD, 50));
		gyouRadio2.setFont(new Font("", Font.BOLD, 50));

		gyouRadio1.setForeground(Color.white);
		gyouRadio2.setForeground(Color.white);

		gyouRadio1.setBorder(new LineBorder(Color.white, 2, true));
		gyouRadio2.setBorder(new LineBorder(Color.white, 2, true));

		gyouRadio1.setBackground(Color.black);
		gyouRadio2.setBackground(Color.black);

		gyouP = new JPanel(new GridLayout());
		gyouP.setBackground(Color.black);

		gyouP.add(gyouRadio1);
		gyouP.add(gyouRadio2);

		setEkigoRoopP.add(setEkigoRoopPB, BorderLayout.WEST);
		setEkigoRoopP.add(gyouP, BorderLayout.CENTER);

		return setEkigoRoopP;
	}

	// 変更実行
	private JButton endButton() {
		setEndButton = new JButton("変更・閉じる(クリック)");
		setEndButton.setFont(new Font("", Font.BOLD, 30));
		setEndButton.setBackground(Constants.end);
		setEndButton.setBorder(new LineBorder(Constants.endBorder, 5, true));
		setEndButton.addMouseListener(myMouse);

		return setEndButton;
	}

	class MyMouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {

			if (e.getSource() == setEndButton) {

				// ///////////////////////////////////////////////////
				speedIndexS = new String();

				if (sizeSpeed1.isSelected()) {
					speedIndexS = sizeSpeed1.getText();

				} else if (sizeSpeed2.isSelected()) {
					speedIndexS = sizeSpeed2.getText();

				} else if (sizeSpeed3.isSelected()) {
					speedIndexS = sizeSpeed3.getText();

				} else if (sizeSpeed4.isSelected()) {
					speedIndexS = sizeSpeed4.getText();

				} else if (sizeSpeed5.isSelected()) {
					speedIndexS = sizeSpeed5.getText();

				}

				// /////////////////////////////////////////////////
				if (roopRadio1.isSelected()) {
					radioRoopS = roopRadio1.getText();
				} else if (roopRadio2.isSelected()) {
					radioRoopS = roopRadio2.getText();
				}

				// /////////////////////////////////////////////////
				if (roopRadio1l.isSelected()) {
					radioRoopSl = roopRadio1l.getText();
				} else if (roopRadio2l.isSelected()) {
					radioRoopSl = roopRadio2l.getText();
				} else if (roopRadio3l.isSelected()) {
					radioRoopSl = roopRadio3l.getText();
				}

				// /////////////////////////////////////////////////
				if (gyouRadio1.isSelected()) {
					radioGyouS = gyouRadio1.getText();
				} else if (gyouRadio2.isSelected()) {
					radioGyouS = gyouRadio2.getText();
				}

				/*
				 *
				 */
				for (int j = 0; j < 10; j++) {

					if(j == 0){
						//目次のプロパティ―は常にtrue
						category_boolean[j] = true;
					}else{
						category_boolean[j] = mokujiPPPCb[j].isSelected();
					}

					category_name[j]=category_name_textarea[j].getText();	//テキストエリアの文字列をcategory_name[]に設定

					System.out.println("-----------------------------------------category_img_name["+j+"]"+category_img_name[j]);
					System.out.println("-----------------------------------------category_name["+j+"]"+category_name[j]);
				}

				// ////////////////////////////////////////////////
				try {
					prop_.setRoop(radioRoopS);
					prop_.setGyou(radioGyouS);
					prop_.setCategoryName(category_name);//変更：　テキストエリアから取り出す
					prop_.setCategoryImgName(category_img_name);//変更：　テキストエリアから取り出す
					prop_.setTabBool(category_boolean);
					prop_.setList(radioRoopSl);
					System.out.println("prop_.setList(radioRoopSl);");

					prop_.setSpeed(speedIndexS);

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				Koe.oto("そふとをさいきどうするとせっていがはんえいされます");
				Set.this.dispose();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 10; i++) {
			if (e.getSource() == mokujiPPPnB[i]) {

				JFileChooser filechooser = new JFileChooser("./resource/img/tab1/");
				int selected = filechooser.showOpenDialog(this);
				if (selected == JFileChooser.APPROVE_OPTION) {

					String name = filechooser.getSelectedFile().getName();
					int p = name.lastIndexOf(".");
					name = name.substring(0, p);

					category_img_name[i]=name;

					zoomImg = new Zoom(new ImageIcon("./resource/img/tab1/" + category_img_name[i] + ".jpg"), 5, 5, 60, 60);
					mokujiPPPnB[i].removeAll();
					mokujiPPPnB[i].add(zoomImg);
					mokujiPPPnB[i].validate();
					mokujiPPPnB[i].paintImmediately(50, 50, 100, 100);// ng
					break;
				}
			}
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Set();
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
