package pictkaiwa;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.sql.SQLException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ekigo_tool.DispEkigo;
import util.Constants;
import util.Koe;
import util.Lg;
import util.StringFormat;
import util.Zoom;
import util.ZoomTab;
/**
 * ekigo kaiwa soft main class
 *
 * @author User
 *
 *
 *
 */
@SuppressWarnings("serial") public class KaiwaMain extends JFrame implements Runnable {

   Robot robot = new Robot();
   //HistorySizeSec ヒストリーパネル（画面上部のサイズ）
   int            HistorySizeSec    = 0;            //
   int            ekigoH            = 0;            //
   int            next_tab_count    = 0;            // タブをスペースキーで切り替える。
   Rectangle      rect              = null;         // max monitor w and h
   JViewport      view              = null;
   JButton        button            = new JButton();//
   JPanel         jPanel            = new JPanel(); //
   static boolean keyPressAvailable = true;         //
   int            moveEkigoCount;                   // 目次から絵記号移動時の指定インデックス
   Thread         thread            = null;         //
   int            handleLag         = 2000;         //.
   static int     loop_index        = 1;            // 1～9 main mokuji monitor loop
   // 1.　タブが変わったときに、loop_indexを初期化する。
   static int tab_change_init_num = 1; // 0以外を初期つとする
   // ループ表示制御変数
   int                  yoko_Inc           = 0;              // yoko_Ink:上行の合計
   int                  yoko_Inc2          = 0;              // 行インデックス（1～4）
   int                  tate_Inc           = 0;              // 列インデックス（1～yoko_Num）
   static int           yoko_Num           = 0;              // 行の個数
   /**
   *
   */
   public static int    speed              = 0;              // ループ速度
   /**
   *
   */
   public static int    tabCount           = 0;
   /**
   *
   */
   public static int    tabNum[]           = new int[10];
   /**
   *
   */
   public static String mokujiBtnNameTxt[] = new String[10]; // プロパティから取り出したカテゴリー名
   /**
   *
   */
   public static String mokujiImgNameTxt[] = new String[10]; // プロパティから取り出したカテゴリー画像名
   // 目次変数 1.　mokujiName_10を動的にする方法：漢字を音声にする仕組みが必要だが、まだできない。 2.　tab1：目次
   static int            tab_num                = 10;
   public static String  category_directory_9[] = { "tab1", "tab2", "tab3", "tab4", "tab5", "tab6", "tab7", "tab8",
                                                      "tab9", "tab10" };
   public static JButton mokuji0To9B[]          = new JButton[tab_num];
   static JPanel         nextPanel;
   static int            nextMousepressIndex    = 1;
   static int            nextMousepressIndex2   = 1;
   static int            nextKeyTypeIndex       = 1;
   static JPanel         toolPanel;
   static JTabbedPane    tabbedPane             = null;
   JPanel                tabPanel_0_9[]         = new JPanel[tab_num];
   static JButton[]      b0, b1, b2, b3, b4, b5, b6, b7, b8, b9 = null;
   static JButton        mokujiBtns0To9[][]     = { b0, b1, b2, b3, b4, b5, b6, b7, b8, b9 };
   static JButton[]      nab0, nab1, nab2, nab3, nab4, nab5, nab6, nab7, nab8, nab9 = null;
   static JButton        nameButtons_0_9[][]    = { nab0, nab1, nab2, nab3, nab4, nab5, nab6, nab7, nab8, nab9 };
   static JPanel[]       p0, p1, p2, p3, p4, p5, p6, p7, p8, p9 = null;
   public static JPanel  panels_0_9[][]         = { p0, p1, p2, p3, p4, p5, p6, p7, p8, p9 };
   JPanel                ekigoHistoryP;
   JButton               ekigoHistoryB;
   JScrollPane           ekigoHistorySP;
   public static JButton mainLoopButton         = new JButton();
   // Property
   static int           trueCount           = 0;
   static int           gyouI               = 0;
   String               ekigoHenSt          = null;
   public static String mokujiVisibleBool[] = new String[10];  // カテゴリタブの表示・非表示のbooleanの文字列（プロパティから取り出し）
   static Boolean       mokujiVisibleBl[]   = new Boolean[10]; // カテゴリタブの表示・非表示のboolean値
   static String        gyouIs              = new String();
   // files
   static int           category_tate_kakeru_yoko_num = 0;
   static int           category_files_num            = 0;
   public static File[] f0, f1, f2, f3, f4, f5, f6, f7, f8, f9;
   public static File   bunyaFiles_0_9[][]            = { f0, f1, f2, f3, f4, f5, f6, f7, f8, f9 };
   // フォルダー内情報取得
   public File  d0, d1, d2, d3, d4, d5, d6, d7, d8, d9;
   private File bunyaFolder_0_9[] = { d0, d1, d2, d3, d4, d5, d6, d7, d8, d9, };
   // イメージ
   Zoom           ZoomImg;
   ZoomTab        ZoomTabImg;
   MyMouse        mouse                    = new MyMouse();
   MyKey          key                      = new MyKey();
   GridBagLayout  cateBtnPaneGridbaglayout = new GridBagLayout();
   static boolean yelllow_loop_select_flag = false;              //
   static boolean list_select_flag         = false;              //
   Prop           prop                     = new Prop();         //

   /**
    * HELPボタン作成
    *
    * @return JButton buttton
    */
   private JButton mkBtnHelp() {
      JButton buttton = new JButton("<html><b><h2>使い方</h2><b><html>");
      buttton.setBackground(Color.black);
      buttton.setForeground(Color.white);
      buttton.addKeyListener(key);
      buttton.setPreferredSize(new Dimension(100, 50));
      buttton.addActionListener(new ActionListener(){

         /*
          * (非 Javadoc)
          *
          * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
          */
         public void actionPerformed(ActionEvent actionEvent) {
            helpFrame();
         }
      });
      return buttton;
   }

   /**
    * Help text of JTextArea
    *
    * @param jTextArea
    * @return JTextArea jTextArea
    */
   private JTextArea readMe(JTextArea jTextArea) {
      String readMetextLine = new String();
      try {
         FileReader     in = new FileReader("resource/help.txt");
         BufferedReader br = new BufferedReader(in);
         String         line;
         while ((line = br.readLine()) != null) {// Loop every one record
            System.out.println(line);
            readMetextLine += line + "\n";
         }
         br.close();
         in.close();
      } catch (FileNotFoundException e) {
         System.out.println(e);
      } catch (IOException e) {
         System.out.println(e);
      }
      jTextArea.setText(readMetextLine);
      return jTextArea;
   }

   /**
    * @throws IOException
    * @throws AWTException
    */
   @SuppressWarnings("javadoc")
   public KaiwaMain() throws IOException, AWTException {
      super();
      doubleStartLock();
      String type = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
      try {
         UIManager.setLookAndFeel(type);
      } catch (Exception e) {
         System.out.println("例外発生：" + e);
      }
      String tp = "TabbedPane.";
      UIManager.put(tp + "tabInsets", new Insets(2, 8, 2, 8)); // タブの上下空間設定
      UIManager.put(tp + "tabAreaInsets", new Insets(8, 8, 8, 8)); // タブパネルの上下空間設定
      UIManager.put(tp + "contentBorderInsets", new Insets(8, 8, 8, 8));
      UIManager.put(tp + "selectedTabPadInsets", new Insets(8, 8, 8, 8));
      UIManager.put(tp + "shadow", Color.GRAY);
      UIManager.put(tp + "darkShadow", Color.GRAY);
      UIManager.put(tp + "light", Color.GRAY);
      UIManager.put(tp + "highlight", Color.GRAY);
      UIManager.put(tp + "tabAreaBackground", new Color(255, 255, 255));
      UIManager.put(tp + "unselectedBackground", new Color(255, 255, 255));
      UIManager.put(tp + "selectedForeground", Color.black);
      UIManager.put(tp + "background", Color.black);
      UIManager.put(tp + "foreground", Color.white);
      UIManager.put(tp + "focus", new Color(255, 255, 255));
      UIManager.put(tp + "contentAreaColor", new Color(255, 255, 255));
      UIManager.put(tp + "selected", new Color(255, 255, 255)); // タブ選択時、色をつける。
      UIManager.put(tp + "selectHighlight", new Color(0, 0, 0)); // タブ左上の線の色
      UIManager.put(tp + "borderHightlightColor", new Color(0, 0, 0));
      // Opaque
      // UIManager.put(tp +"tabsOpaque", Boolean.FALSE);
      // UIManager.put(tp +"contentOpaque", Boolean.FALSE);
      UIManager.put(tp + "tabRunOverlay", Boolean.TRUE);
      UIManager.put(tp + "tabsOverlapBorder", Boolean.TRUE);
      UIManager.put(tp + "selectionFollowsFocus", Boolean.TRUE);
      tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
      prop       = getMoveSpeedProp(prop);
      ImageIcon icon = setMaximumWindowBounds(); // Matching monitor frame.
      setIconAndTitle(icon);
      tabbedPane.addKeyListener(key); // 全体のComponentにキーが反映される事が解った。
      tabbedPane = mkTabPict(); // Make mokuji tab
      // Setting when change mokuji tab background color.
      tabbedPane.addChangeListener(new ChangeListener(){

         public void stateChanged(ChangeEvent e) {
            JTabbedPane jtab   = (JTabbedPane) e.getSource();
            int         sindex = jtab.getSelectedIndex();
            for (int i = 0; i < jtab.getTabCount(); i++) {
               if (i == sindex && jtab.getTitleAt(sindex).endsWith("1")) {
                  // jtab.setForegroundAt(i, Color.GREEN); //　？
               } else if (i == sindex) {
                  Color sc = (sindex % 2 == 0) ? Color.RED : Color.BLUE;
                  jtab.setForegroundAt(i, Color.black);
               } else {
                  jtab.setForegroundAt(i, Color.white);
               }
            }
         }
      });
      historyPanel(); // make history panel
      mkBtnPict(); // Add buttons and property on pict panel
      // ループ選択
      try {
         if (prop.getPict().getProperty("seting.roop").equals("ON")) {// ループイベントを起動する。
            thread = new Thread(this);
            thread.start();
         } else {// ループイベントを起動しない。
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      // Windows ７　では、ボタンがフォルトになる。
      this.setVisible(true); // 最後に可視化する。
   }

   /**
    * Creating a history panel
    */
   private void historyPanel() {
      // Click history
      JPanel     ekigoHistoryP_main = new JPanel(new BorderLayout());
      FlowLayout layout1            = new FlowLayout(0, 3, 3);
      layout1.setAlignment(FlowLayout.LEFT);
      ekigoHistoryP = new JPanel(layout1);
      ekigoHistoryP.setBackground(Color.white);
      // ekigoHistoryP.setBorder(new LineBorder(Color.blue, 2, false));
      ekigoHistorySP = new JScrollPane(ekigoHistoryP);
      ekigoHistorySP.setPreferredSize(new Dimension(3000, 83));
      ekigoHistorySP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      ekigoHistorySP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      view = ekigoHistorySP.getViewport();
      new Lg(this, "ekigoHistorySP>getLayout==" + ekigoHistorySP.getLayout().toString());
      new Lg(this, "ekigoHistoryP>getLayout==" + ekigoHistoryP.getLayout().toString());
      ekigoHistoryP_main.add(ekigoHistorySP, BorderLayout.CENTER);
      ekigoHistoryP_main.add(mkBtnHelp(), BorderLayout.EAST);
      this.getContentPane().add(ekigoHistoryP_main, BorderLayout.NORTH);
      this.getContentPane().add(tabbedPane, BorderLayout.CENTER); // Frame本体に乗せる。
      tabbedPane.setSelectedIndex(1); // コンポーネント初期化時、目次タブのフォントを黒にする。
      tabbedPane.setSelectedIndex(0); // コンポーネント初期化時、目次タブのフォントを黒にする。
   }

   /**
    * Creating a help frame
    */
   private void helpFrame() {
      JFrame frame = new JFrame();
      frame.setTitle("使い方");
      frame.setAlwaysOnTop(true);
      FlowLayout          help_panel_layout = new FlowLayout(FlowLayout.LEFT);
      JPanel              help_panel        = new JPanel(help_panel_layout);
      JTextArea           help_textarea     = new JTextArea();
      GraphicsEnvironment env               = GraphicsEnvironment.getLocalGraphicsEnvironment();
      rect = env.getMaximumWindowBounds();
      help_panel.setBounds(rect);
      help_textarea.setBounds(rect);
      help_textarea = readMe(help_textarea);
      help_textarea.setForeground(Color.white);
      help_textarea.setBackground(Color.black);
      help_textarea.setFont(new Font("Serif", Font.BOLD, 20));
      JScrollPane scrollpane = new JScrollPane();
      scrollpane.setViewportView(help_textarea);
      scrollpane.setBounds(rect);
      System.out.println("rect.width==" + rect.width);
      System.out.println("rect.height==" + rect.height);
      JScrollBar JScrollBar = new JScrollBar();
      // JScrollBar.set
      scrollpane.setPreferredSize(new Dimension(rect.width - 15, rect.height)); // スクロールバーの幅の分だけ、縮める。
      scrollpane.setVerticalScrollBar(JScrollBar);
      scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      help_panel.add(scrollpane);
      frame.add(help_panel);
      frame.setBounds(rect);
      frame.setVisible(true);
   }

   /**
    * Icon and title settings
    *
    * @param icon
    */
   private void setIconAndTitle(ImageIcon icon) {
      this.setAlwaysOnTop(true);
      this.setBounds(rect);
      this.setIconImage(icon.getImage());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("絵記号会話ソフト");
      this.setLayout(new BorderLayout());
   }

   /**
    * Double start control
    *
    * @throws FileNotFoundException
    * @throws IOException
    */
   private void doubleStartLock() throws FileNotFoundException, IOException {
      final FileOutputStream fos  = new FileOutputStream(new File("./resource/lock_control"));
      final FileChannel      fc   = fos.getChannel();
      final FileLock         lock = fc.tryLock();
      if (lock == null) {
         // 既に起動されているので終了する
         try {
            "hello".charAt(-1);
         } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "絵記号会話ソフトはすでに起動しています。");
            System.exit(0);
         }
         return;
      }
      Runtime.getRuntime().addShutdownHook(new Thread(){// ロック開放処理を登録

         public void run() {
            if (lock != null && lock.isValid()) {
               try {
                  lock.release();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
            try {
               fc.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
            try {
               fos.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * To maximize the size, use the Robot class and click Maximize.
    *
    * @author ikonu
    * @return ImageIcon mainFramIcon
    */
   private ImageIcon setMaximumWindowBounds() {
      ImageIcon           mainFramIcon = new ImageIcon("./resource/img/tab_img/kurumaisu.jpg");
      GraphicsEnvironment env          = GraphicsEnvironment.getLocalGraphicsEnvironment();
      rect = env.getMaximumWindowBounds();
      robot.mouseMove(rect.width - 300, 10);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      return mainFramIcon;
   }

   /**
    *
    * @author ikonu
    * @return
    */
   private Prop getMoveSpeedProp(Prop prop) {
      try {
         if (prop.getPict().getProperty("seting.speed").equals("2")) {
            speed = 2000;
         } else if (prop.getPict().getProperty("seting.speed").equals("3")) {
            speed = 3000;
         } else if (prop.getPict().getProperty("seting.speed").equals("4")) {
            speed = 4000;
         } else if (prop.getPict().getProperty("seting.speed").equals("5")) {
            speed = 5000;
         } else if (prop.getPict().getProperty("seting.speed").equals("6")) {
            speed = 6000;
         }
      } catch (FileNotFoundException e1) {
         e1.printStackTrace();
      } catch (IOException e1) {
         e1.printStackTrace();
      }
      // Pict display of line number.
      // 3 line or 4 line
      try {
         if (prop.getPict().getProperty("seting.gyou").equals(Constants.PICT_LINE_IS_3)) {
            gyouI = Integer.valueOf(Constants.PICT_LINE_IS_3);
         } else if (prop.getPict().getProperty("seting.gyou").equals(Constants.PICT_LINE_IS_4)) {
            gyouI = Integer.valueOf(Constants.PICT_LINE_IS_4);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      try {
         try {
            if (prop.getPict().getProperty("seting.list").equals("①説明を表示")) {
               gyouIs = "①説明を表示";
               new Lg(this, "Kaiwa>getProp>else if (prop.getPict().getProperty(seting.list).equals(説明))");
            } else if (prop.getPict().getProperty("seting.list").equals("②絵記号を拡大表示")) {
               gyouIs = "②絵記号を拡大表示";
               new Lg(this, "Kaiwa>getProp>else if (prop.getPict().getProperty(seting.list).equals(絵記号))");
            } else if (prop.getPict().getProperty("seting.list").equals("③絵記号一時的に表示")) {
               gyouIs = "③絵記号一時的に表示";
               new Lg(this, "Kaiwa>getProp>else if (prop.getPict().getProperty(seting.list).equals(絵記号一時))");
            }
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
         // Get mokuji info from property.
         for (int j = 0; j < 10; j++) {
            mokujiBtnNameTxt[j]  = new String();
            mokujiBtnNameTxt[j]  = prop.getPict().getProperty("seting.category_name" + String.valueOf(j));     // Text mokuji name
            mokujiImgNameTxt[j]  = new String();
            mokujiImgNameTxt[j]  = prop.getPict().getProperty("seting.category_img_name" + String.valueOf(j)); // Text mokuji image neme
            mokujiVisibleBool[j] = new String();
            mokujiVisibleBool[j] = prop.getPict().getProperty("seting.mokujiBool" + String.valueOf(j));        // mokuji button that visible is true or false.
            mokujiVisibleBl[j]   = Boolean.parseBoolean(mokujiVisibleBool[j]);
            new Lg(this, "category_from_property[" + j + "] ： mokujiBool[" + j + "] == " + mokujiBtnNameTxt[j] + " ： "
                                                               + mokujiVisibleBool[j]);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return prop;
   }

   /**
    * @return
    */
   private JTabbedPane mkTabPict() {
      for (int i = 0; i < tabPanel_0_9.length; i++) {
         if (mokujiVisibleBl[i].equals(true)) {
            tabNum[i] = trueCount;
            // truePpanelColor[trueCount - 1] = panelColor_0_9[i];
            trueCount++;
            tabPanel_0_9[i] = new JPanel();
            tabPanel_0_9[i].setPreferredSize(new Dimension(900, 500));
            tabPanel_0_9[i].setBackground(new Color(255, 255, 255));
            tabPanel_0_9[i].setBorder(new LineBorder(new Color(255, 255, 255), 1, false));
            if (0 == i) {
               ZoomTabImg = new ZoomTab(new ImageIcon("./resource/img/tab1/" + mokujiImgNameTxt[i] + ".jpg"), 0, 0, 50,
                                                                  50);
            } else {
               ZoomTabImg = new ZoomTab(new ImageIcon("./resource/img/tab1/" + mokujiImgNameTxt[i] + ".jpg"), 0, 0, 50,
                                                                  50);
            }
            tabbedPane.add(tabPanel_0_9[i], ZoomTabImg);
            tabbedPane.setForeground(Color.white);
            tabbedPane.setFont(new Font("", Font.BOLD, 22));
            tabbedPane.setTitleAt(tabCount, mokujiBtnNameTxt[i]);
            // tabbedPane.setTitleAt(tabCount, String.valueOf(i));
            // タブにhtmlレイアウトで画像と文字列を設定したいが、タブは絵記号直感的に区別できるようにしなければならないと思った。
            // しかし、タブを選択するのは初心者の介護者の場合があるのでタブに分野の文字列を入れることとする。
            // tabbedPane.add(makeTitle(category_from_property[i],
            // "./resource/img/tab1/" + category_img_from_property[i] +
            // ".jpg"),
            // tabPanel_0_9[i]);
            // tabbedPane.setForegroundAt(i,Color.white);
            // tabbedPane.setAlignmentX(LEFT_ALIGNMENT);
            tabCount++;
         }
      }
      // tabbedPane.setBackgroundAt(0, Color.black);
      tabPanel_0_9[0].setBackground(new Color(255, 255, 255));
      tabPanel_0_9[0].setBorder(new LineBorder(new Color(255, 255, 255), 1, false));
      for (int i = 1; i < trueCount; i++) {
         // tabbedPane.setBackgroundAt(i, Color.black);
         // tabbedPane.setForegroundAt(i, Color.white);
         // tabbedPane.setAlignmentX(LEFT_ALIGNMENT);
      }
      return tabbedPane;
   }

   /**
    * 目次画面（タブ１）のループ
    */
   public void mkBtnPict() {
      new Lg(this, "public void button()");
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      for (int i = 0; i < tabPanel_0_9.length; i++) {
         // 分野毎のファイル数を取得
         if (mokujiVisibleBl[i].equals(true)) {
            new Lg(this, "if (bool[i].equals(true))");
            bunyaFolder_0_9[i] = new File("./resource/img/" + category_directory_9[i]);
            bunyaFiles_0_9[i]  = bunyaFolder_0_9[i].listFiles();                       // フォルダー内のファイルを配列で返す。
            category_files_num = bunyaFiles_0_9[i].length;
            // 絵記号の行数を設定
            tabPanel_0_9[i].setLayout(new GridLayout(gyouI, 5, 1, 1));
            tabPanel_0_9[i].setLayout(new GridLayout(gyouI, 5, 1, 1));
            // 縦と横にボタンを敷き詰めるため、 category_tate_kakeru_yoko_num: 縦×横の数を取得する。
            category_tate_kakeru_yoko_num = (int) (gyouI * (Math.floor(category_files_num / gyouI) + 1)); // 縦×横の数
            panels_0_9[i]                 = new JPanel[category_tate_kakeru_yoko_num];                    // 絵記号パネル
            mokujiBtns0To9[i]             = new JButton[category_tate_kakeru_yoko_num];                   // 絵記号ボタン
            nameButtons_0_9[i]            = new JButton[category_tate_kakeru_yoko_num];                   // 絵記号名ボタン
            // 絵記号ボタンを生成
            for (int pBtn_i = 0; pBtn_i < category_tate_kakeru_yoko_num; pBtn_i++) {
               // 将来、ここにさまざまなボタン1個による操作可能なソフトを入れる。
               if (i == 9) {// アプリ分野[Tab9]
                  mokujiBtns0To9[i][pBtn_i]  = new JButton(); //
                  nameButtons_0_9[i][pBtn_i] = new JButton(); //
                  mokujiBtns0To9[i][pBtn_i].setBackground(new Color(0, 0, 0));
                  if (category_files_num <= pBtn_i) {
                     // アプリのjarがないボタン. 黒く表示する.
                     ekigoNameButtons_0_9(i, pBtn_i);
                     mokujiBtn_0_9(i, pBtn_i);
                     cateBtnPaneGridbaglayout.setConstraints(mokujiBtns0To9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     cateBtnPaneGridbaglayout.setConstraints(nameButtons_0_9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     tab9AppPane_0_9(i, pBtn_i);
                     tabPanel_0_9[i].add(panels_0_9[i][pBtn_i]);
                  } else {
                     // アプリのボタン
                     int point = bunyaFiles_0_9[i][pBtn_i].getName().lastIndexOf("."); // ポイントの文字番目を返す。
                     appriNameBtn(i, pBtn_i, point);
                     appriMokujiBtn_0_9(i, pBtn_i, point);
                     cateBtnPaneGridbaglayout.setConstraints(mokujiBtns0To9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     cateBtnPaneGridbaglayout.setConstraints(nameButtons_0_9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     tab9AppPane_0_9(i, pBtn_i);
                     tabPanel_0_9[i].add(panels_0_9[i][pBtn_i]);
                  }
               } else if (1 <= i) {//Appri other tab1 to 8
                  // 絵記号分野（アプリ以外の分野） インデックス0番目は目次ボタン
                  mokujiBtns0To9[i][pBtn_i] = new JButton(); //
                  mokujiBtns0To9[i][pBtn_i].setBackground(new Color(0, 0, 0));
                  nameButtons_0_9[i][pBtn_i] = new JButton(); //
                  if (category_files_num <= pBtn_i) {
                     appriOtherNameBtn_0_9(i, pBtn_i);
                     appriMokujiBtn_0_9(i, pBtn_i);
                     cateBtnPaneGridbaglayout.setConstraints(mokujiBtns0To9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     cateBtnPaneGridbaglayout.setConstraints(nameButtons_0_9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     tab1_9AppriOtherPane_0_9(i, pBtn_i);
                     tabPanel_0_9[i].add(panels_0_9[i][pBtn_i]);
                     ZoomImg = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/" + "non.jpg"), 0,
                                                                        0, mokujiBtns0To9[i][pBtn_i].getHeight(),
                                                                        mokujiBtns0To9[i][pBtn_i].getHeight());
                     mokujiBtns0To9[i][pBtn_i].add(ZoomImg);
                     new Lg(this, "buttons_0_9[i][j].getWidth()==" + mokujiBtns0To9[i][pBtn_i].getWidth());
                     new Lg(this, "buttons_0_9[i][j].getHeight()==" + mokujiBtns0To9[i][pBtn_i].getHeight());
                     this.setVisible(true);
                  } else {
                     // 絵記号画面がtrue
                     int point = bunyaFiles_0_9[i][pBtn_i].getName().lastIndexOf("."); // ポイントの文字番目を返す。
                     ekigoNameBtn_0_9(i, pBtn_i, point);
                     ekigoMokujiBtn_0_9(i, pBtn_i, point);
                     cateBtnPaneGridbaglayout.setConstraints(mokujiBtns0To9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     cateBtnPaneGridbaglayout.setConstraints(nameButtons_0_9[i][pBtn_i],
                                                                        getGridBagConstraints(gridBagConstraints));
                     tab1_9AppriOtherPane_0_9(i, pBtn_i);
                     tabPanel_0_9[i].add(panels_0_9[i][pBtn_i]);
                     this.setVisible(true);
                     ZoomImg = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/"
                                                                        + bunyaFiles_0_9[i][pBtn_i].getName()), 0, 0,
                                                                        mokujiBtns0To9[i][pBtn_i].getHeight(),
                                                                        mokujiBtns0To9[i][pBtn_i].getHeight());
                     new Lg(this, "nameButtons_0_9[" + i + "][" + pBtn_i + "].getWidth()=="
                                                                        + nameButtons_0_9[i][pBtn_i].getWidth());
                     new Lg(this, "buttons_0_9[" + i + "][" + pBtn_i + "].getHeight()=="
                                                                        + mokujiBtns0To9[i][pBtn_i].getHeight());
                     mokujiBtns0To9[i][pBtn_i].add(ZoomImg);
                  }
               }
            }
         }
      }
      mKBtnMokuji(tabPanel_0_9);
   }

   /**
    * @param i
    * @param pBtn_i
    * @param point
    */
   private void ekigoMokujiBtn_0_9(int i, int pBtn_i, int point) {
      mokujiBtns0To9[i][pBtn_i].addMouseListener(mouse);
      mokujiBtns0To9[i][pBtn_i].setHorizontalAlignment(JButton.LEFT);
      mokujiBtns0To9[i][pBtn_i].addKeyListener(key);
      mokujiBtns0To9[i][pBtn_i].setText(bunyaFiles_0_9[i][pBtn_i].getName().substring(0, point));
      mokujiBtns0To9[i][pBtn_i].setActionCommand(bunyaFiles_0_9[i][pBtn_i].getName().substring(0, point));
   }

   /**
    * @param i
    * @param pBtn_i
    * @param point
    */
   private void ekigoNameBtn_0_9(int i, int pBtn_i, int point) {
      nameButtons_0_9[i][pBtn_i].setText(bunyaFiles_0_9[i][pBtn_i].getName().substring(0, point));
      nameButtons_0_9[i][pBtn_i].setBackground(Color.black);
      nameButtons_0_9[i][pBtn_i].setForeground(Color.white);
      nameButtons_0_9[i][pBtn_i].addMouseListener(mouse);
   }

   /**
    * @param i
    * @param pBtn_i
    */
   private void tab1_9AppriOtherPane_0_9(int i, int pBtn_i) {
      panels_0_9[i][pBtn_i] = new JPanel(cateBtnPaneGridbaglayout);
      panels_0_9[i][pBtn_i].setBorder(new LineBorder(Color.black, 0, false));
      panels_0_9[i][pBtn_i].add(mokujiBtns0To9[i][pBtn_i]);
      panels_0_9[i][pBtn_i].add(nameButtons_0_9[i][pBtn_i]);
   }

   /**
    * @param i
    * @param pBtn_i
    */
   private void appriMokujiBtn_0_9(int i, int pBtn_i) {
      mokujiBtns0To9[i][pBtn_i].addMouseListener(mouse);
      mokujiBtns0To9[i][pBtn_i].setHorizontalAlignment(JButton.LEFT);
      mokujiBtns0To9[i][pBtn_i].addKeyListener(key);
      mokujiBtns0To9[i][pBtn_i].setActionCommand(mokujiBtns0To9[i][pBtn_i].getText());
      mokujiBtns0To9[i][pBtn_i].setText(category_directory_9[i]);
   }

   /**
    * @param i
    * @param pBtn_i
    */
   private void appriOtherNameBtn_0_9(int i, int pBtn_i) {
      nameButtons_0_9[i][pBtn_i].setText(" ");
      nameButtons_0_9[i][pBtn_i].addMouseListener(mouse);
      nameButtons_0_9[i][pBtn_i].setBackground(Color.black);
      nameButtons_0_9[i][pBtn_i].setForeground(Color.white);
   }

   /**
    * @param i
    * @param pBtn_i
    * @param point
    */
   private void appriMokujiBtn_0_9(int i, int pBtn_i, int point) {
      mokujiBtns0To9[i][pBtn_i].addMouseListener(mouse);
      mokujiBtns0To9[i][pBtn_i].addKeyListener(key);
      mokujiBtns0To9[i][pBtn_i].setHorizontalAlignment(JButton.LEFT);
      mokujiBtns0To9[i][pBtn_i].setText(bunyaFiles_0_9[i][pBtn_i].getName().substring(0, point));
      mokujiBtns0To9[i][pBtn_i].setActionCommand(bunyaFiles_0_9[i][pBtn_i].getName().substring(0, point));
      mokujiBtns0To9[i][pBtn_i].setFont(new Font("", Font.BOLD, 50));
   }

   /**
    * @param i
    * @param pBtn_i
    */
   private void tab9AppPane_0_9(int i, int pBtn_i) {
      panels_0_9[i][pBtn_i] = new JPanel(cateBtnPaneGridbaglayout);
      panels_0_9[i][pBtn_i].setBorder(new LineBorder(Color.black, 0, false));
      panels_0_9[i][pBtn_i].add(nameButtons_0_9[i][pBtn_i]);
   }

   /**
    * アプリ起動用の目次タブ画面
    *
    * @param i
    * @param pBtn_i
    */
   private void mokujiBtn_0_9(int i, int pBtn_i) {
      appriMokujiBtn_0_9(i, pBtn_i);
      mokujiBtns0To9[i][pBtn_i].setFont(new Font("", Font.BOLD, 50));
   }

   /**
    * @param i
    * @param pBtn_i
    * @param point
    */
   private void appriNameBtn(int i, int pBtn_i, int point) {
      nameButtons_0_9[i][pBtn_i].setText(bunyaFiles_0_9[i][pBtn_i].getName().substring(0, point));
      nameButtons_0_9[i][pBtn_i].setBackground(Color.black);
      nameButtons_0_9[i][pBtn_i].addMouseListener(mouse);
      nameButtons_0_9[i][pBtn_i].addKeyListener(key);
      nameButtons_0_9[i][pBtn_i].setForeground(Color.white);
      nameButtons_0_9[i][pBtn_i].setFont(new Font("", Font.BOLD, 50));
   }

   /**
    * @param i
    * @param j
    */
   private void ekigoNameButtons_0_9(int i, int j) {
      nameButtons_0_9[i][j].setText(" ");
      nameButtons_0_9[i][j].setBackground(Color.black);
      nameButtons_0_9[i][j].addMouseListener(mouse);
      nameButtons_0_9[i][j].addKeyListener(key);
      nameButtons_0_9[i][j].setForeground(Color.white);
      nameButtons_0_9[i][j].setFont(new Font("", Font.BOLD, 50));
   }

   /**
    * @param category_button_panel_gridbag_constrainrs
    * @return
    */
   private GridBagConstraints getGridBagConstraints(GridBagConstraints category_button_panel_gridbag_constrainrs) {
      category_button_panel_gridbag_constrainrs.gridx   = 0;
      category_button_panel_gridbag_constrainrs.gridy   = 0;
      category_button_panel_gridbag_constrainrs.weightx = 1.0d;
      category_button_panel_gridbag_constrainrs.weighty = 1.0d;
      category_button_panel_gridbag_constrainrs.fill    = GridBagConstraints.BOTH;
      return category_button_panel_gridbag_constrainrs;
   }

   /**
    * make mokuji monitor. this is test.
    *
    * @param categoryBtnP1
    *           カテゴリボタンのパネル1
    * @param categoryBtnP2
    *           カテゴリボタンのパネル2
    * @param categoryBtnP3
    *           カテゴリボタンのパネル3
    * @return List<JPanel>
    */
   public void mKBtnMokuji(JPanel[] categoryBtnP) {
      // Set mokuji Button property.
      for (int i = 1; i < 10; i++) {
         // B：インデックスの使い方が不適当と思われる
         if (mokujiVisibleBl[i].equals(true)) {
            mokuji0To9B[i] = new JButton(mokujiBtnNameTxt[i]);
            mokuji0To9B[i].setPreferredSize(new Dimension(290, 108));
            mokuji0To9B[i].addMouseListener(mouse);
            mokuji0To9B[i].setHorizontalAlignment(SwingConstants.RIGHT);
            mokuji0To9B[i].addKeyListener(key);
            mokuji0To9B[i].setFont(new Font("", Font.BOLD, 100));
            mokuji0To9B[i].setForeground(Color.white);
            mokuji0To9B[i].setMargin(new Insets(0, 0, 0, 0));
            mokuji0To9B[i].setBackground(Color.BLACK);
            mokuji0To9B[i].setBorder(new LineBorder(Color.BLACK, 8, false));
            mokuji0To9B[i].setSelected(true);
            categoryBtnP[0].add(mokuji0To9B[i]);
            this.setVisible(true);
            Zoom zoomImage = new Zoom(new ImageIcon("./resource/img/tab1/" + mokujiImgNameTxt[i] + ".jpg"), 0, 0,
                                                               mokuji0To9B[i].getHeight(), mokuji0To9B[i].getHeight());
            mokuji0To9B[i].add(zoomImage);
         }
      }
      categoryBtnP[0].setLayout(new GridLayout(3, 3, 1, 1));
      categoryBtnP[0].setBackground(new Color(255, 255, 255));
   }

   /**
   *
   */
   public void run() {
      mainLoopStart();
   }

   /**
    * 目次画面。目次が白枠で遷移する。
    */
   private void mainLoopStart() {
      while (true) {
         if (mokujiVisibleBl[loop_index].equals(true) && loop_index == 1) {
            yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
         }
         mokujiLoop();
         // 目次画面から絵記号の詳細ループへ遷移
         if ((tabbedPane.getSelectedIndex() == 0) == false) {
            ekigoLoop(tabbedPane.getSelectedIndex());
         }
         loop_index++; // increment of main mokuji loop index
         //
         if (loop_index == 10) {
            loop_index = 1;
         }
         // タブの行ボタン数を取得
         if (mokujiVisibleBl[loop_index].equals(true)) {
            yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
         }
      }
   }

   /**
    * 絵記号の詳細ループ。これは、絵記号の上下ループと左右ループの制御する。
    *
    * @param tabIndex
    */
   private void ekigoLoop(int tabIndex) {
      new Lg(this, "ekigoLoop ①");
      // 上下ループ
      while (true) {
         // ループ音 playWave("resource/Windows Feed Discovered.wav"); //ピン
         playWave("resource/Windows Information Bar.wav"); // ポン
         new Lg(this, "while (true)>mokujiBIndexI  yoko_Inc  yoko_Num==" + loop_index + "  " + yoko_Inc + "  "
                                                            + yoko_Num);
         // タブが目次になったらループを出る。
         if (tabbedPane.getSelectedIndex() == 0) {
            new Lg(this, "タブが目次になったのでループを出る：if (tabbedPane.getSelectedIndex() == 0)");
            new Lg(this, "mokujiBIndexI_save==" + tab_change_init_num);
            loop_index = tab_change_init_num;
            break;
         }
         // 色移動：分野
         for (int p = 0; p < yoko_Num; p++) {
            panels_0_9[loop_index][yoko_Inc + p].setBorder(new LineBorder(Color.white, 8, false));
            nameButtons_0_9[loop_index][yoko_Inc + p].setForeground(Color.white);
         }
         try {
            Thread.sleep(speed);
         } catch (InterruptedException e1) {
            e1.printStackTrace();
         }
         // 左右ループ
         while (yelllow_loop_select_flag) {
            new Lg(this, "while (tateFlag)＞yoko_Inc2：yoko_Inc：yoko_Num：tate_Inc==" + yoko_Inc2 + "：" + yoko_Inc + "："
                                                               + yoko_Num + "：" + tate_Inc);
            list_select_flag = false; // 選択されるまでループ
            // 色移動：分野：横に移動したとき、色をもとに戻す。
            panels_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setBorder(new LineBorder(Color.white, 0, false));
            nameButtons_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setForeground(Color.white);
            // 重なる部分は赤に（yoko：横ループのインクリメント）
            // yoko_Ink:上行の合計
            // 色移動：分野：横に移動
            panels_0_9[loop_index][tate_Inc + yoko_Inc].setBorder(new LineBorder(Color.orange, 10, false));
            // たて移動毎に音を鳴らす
            Koe.oto(mokujiBtns0To9[loop_index][tate_Inc + yoko_Inc].getText());
            try {
               Thread.sleep(speed);
            } catch (InterruptedException e1) {
               e1.printStackTrace();
            }
            // 色移動：分野：横に移動する。 横と重なっていた部分は黒から黄色に戻す。
            //
            panels_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setBorder(new LineBorder(Color.white, 10, false));
            nameButtons_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setForeground(Color.white);
            // To mokuji loop when tab index is change.
            if (tabbedPane.getSelectedIndex() != tabIndex) {
               tate_Inc  = 0;
               yoko_Inc2 = 0;
               break;
            }
            // To list monitor
            if (list_select_flag) {
               // Apps panel（final panel）
               if (tabbedPane.getSelectedIndex() == trueCount - 1) {
                  // mokuji button
                  if (mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText().equals("もくじ")) {
                     Koe.oto(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     try {
                        Thread.sleep(1000);
                     } catch (InterruptedException e1) {
                        e1.printStackTrace();
                     }
                     yelllow_loop_select_flag = false;
                     tabbedPane.setSelectedIndex(0);
                     // Kaiwaからのイベントと違い始めから呼び出していないため、初期化する
                     tate_Inc  = 0;
                     yoko_Inc2 = 0;
                     break;
                  }
                  new Lg(this, "buttons_0_9==" + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                  if (mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText().equals("tab10")) {
                  } else {
                     // 他PCに入れるときは、C：に近い所に入れないと、execエラーになる。
                     File   f       = new File("./resource/img/tab10/" + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num
                                                                        + tate_Inc].getText() + ".jar");
                     String command = f.getAbsolutePath();
                     new Lg(this, "command==" + command);
                     new Lg(this, "test==" + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     Runtime runtime = Runtime.getRuntime();
                     Process process = null;
                     // Execution jar
                     new Lg(this, "jarあり");
                     new Lg(this, "==" + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     new Lg(this, "yoko_Inc2：yoko_Inc：yoko_Num：tate_Inc==" + yoko_Inc2 + "：" + yoko_Inc + "：" + yoko_Num
                                                                        + "：" + tate_Inc);
                     try {
                        process = runtime.exec("cmd /c start " + command);
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                     Reader in = new InputStreamReader(process.getInputStream());
                     int    c  = -1;
                     new Lg(this, "oto");
                     try {
                        while ((c = in.read()) != -1) {
                           System.out.print((char) c);
                        }
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                     System.out.print("\n");
                     try {
                        in.close();
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                  }
                  yelllow_loop_select_flag = false;
                  break;
               } else {
                  // Other apuri mokuji
                  // Select mokuji button.
                  if (mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText().equals("もくじ")) {
                     Koe.oto(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     yelllow_loop_select_flag = false;
                     tabbedPane.setSelectedIndex(0);
                     // KaiwaPictListからのイベントと違い始めから呼び出していないため、初期化する
                     tate_Inc  = 0;
                     yoko_Inc2 = 0;
                     break;
                     // Select pict
                  } else {
                     if (bunyaFiles_0_9[loop_index].length > yoko_Inc2 * yoko_Num + tate_Inc) {
                        // ボタンを点滅させる
                        Thread thread = new Thread(new PointerColorTHread());
                        thread.start();
                        panels_0_9[loop_index][tate_Inc + yoko_Inc].setBorder(new LineBorder(Color.black, 8, false));
                        nameButtons_0_9[loop_index][tate_Inc + yoko_Inc].setForeground(Color.white);
                        if (gyouIs.equals("②絵記号を拡大表示")) {
                           new Lg(this, "if (gyouIs.equals(②絵記号を拡大表示) {");
                           // 絵記号選択履歴　クリック時
                           Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg"), 0, 0, 70, 60);
                           new Lg(this, "./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg");
                           ekigoHistoryB = new JButton();
                           ekigoHistoryB.setBackground(Color.black);
                           ekigoHistoryB.setHorizontalAlignment(JButton.LEFT);
                           ekigoHistoryB.setMargin(new Insets(0, 0, 0, 0));
                           ekigoHistoryB.removeAll();
                           ekigoHistoryB.add(zoom);
                           ekigoHistoryB.validate();
                           view.setView(ekigoHistoryP);
                           view.setViewPosition(new Point(5000, 100));
                           if (HistorySizeSec == 0) {
                              ekigoH = ekigoHistoryP.getHeight() - 5;
                              ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                              HistorySizeSec++;
                           } else {
                              ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                           }
                           ekigoHistoryP.add(ekigoHistoryB);
                           this.setVisible(true);
                           // Sleep before open pict picture.
                           try {
                              Thread.sleep(handleLag);
                           } catch (InterruptedException e1) {
                              e1.printStackTrace();
                           }
                           try {
                              new DispEkigo(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText(),
                                                                                 category_directory_9[loop_index], 1);
                           } catch (HeadlessException e) {
                              e.printStackTrace();
                           }
                           Koe.oto(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                        } else if (gyouIs.equals("①説明を表示")) {
                           new Lg(this, "if (gyouIs.equals(①説明を表示)) {");
                           // Open pict picture monitor When pict history Click.
                           Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg"), 0, 0, 70, 60);
                           new Lg(this, "./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg");
                           ekigoHistoryB = new JButton();
                           ekigoHistoryB.setBackground(Color.black);
                           ekigoHistoryB.setHorizontalAlignment(JButton.LEFT);
                           ekigoHistoryB.setMargin(new Insets(0, 0, 0, 0));
                           ekigoHistoryB.removeAll();
                           ekigoHistoryB.add(zoom);
                           ekigoHistoryB.validate();
                           view.setView(ekigoHistoryP);
                           view.setViewPosition(new Point(5000, 100));
                           if (HistorySizeSec == 0) {
                              ekigoH = ekigoHistoryP.getHeight() - 5;
                              ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                              HistorySizeSec++;
                           } else {
                              ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                           }
                           ekigoHistoryP.add(ekigoHistoryB);
                           this.setVisible(true);
                           // 絵記号を表示する前に、時間をおく。
                           try {
                              Thread.sleep(handleLag);
                           } catch (InterruptedException e1) {
                              e1.printStackTrace();
                           }
                           try {
                              // public List(String ekigoName, String
                              // folderName)
                              new List(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText(),
                                                                                 category_directory_9[loop_index]);
                           } catch (HeadlessException e) {
                              e.printStackTrace();
                           } catch (SQLException e) {
                              e.printStackTrace();
                           }
                           Koe.oto(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                        } else if (gyouIs.equals("③絵記号一時的に表示")) {
                           new Lg(this, "if (gyouIs.equals(③絵記号一時的に表示)) {");
                           // 絵記号選択履歴　クリック時
                           Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg"), 0, 0, 70, 60);
                           new Lg(this, "./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg");
                           ekigoHistoryB = new JButton();
                           ekigoHistoryB.setBackground(Color.black);
                           ekigoHistoryB.setHorizontalAlignment(JButton.LEFT);
                           ekigoHistoryB.setMargin(new Insets(0, 0, 0, 0));
                           ekigoHistoryB.removeAll();
                           ekigoHistoryB.add(zoom);
                           ekigoHistoryB.validate();
                           view.setView(ekigoHistoryP);
                           view.setViewPosition(new Point(5000, 100));
                           if (HistorySizeSec == 0) {
                              ekigoH = ekigoHistoryP.getHeight() - 5;
                              ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                              HistorySizeSec++;
                           } else {
                              ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                           }
                           ekigoHistoryP.add(ekigoHistoryB);
                           this.setVisible(true);
                           // 絵記号を表示する前に、時間をおく。
                           try {
                              Thread.sleep(handleLag);
                           } catch (InterruptedException e1) {
                              e1.printStackTrace();
                           }
                           // 小さな絵記号を２秒間表示する。
                           try {
                              // public BigEkigo(String
                              // ekigoFileName,String ekigoFolderName)
                              new DispEkigo(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText(),
                                                                                 category_directory_9[loop_index], 2);
                           } catch (HeadlessException e) {
                              e.printStackTrace();
                           }
                           // -----------------------------------------------小さな絵記号を２秒間表示する。
                           Koe.oto(mokujiBtns0To9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                        }
                        // yoko_Inc2=0; //縦ループを出た場合は、横インデックスを0（上からループ開始）
                        yelllow_loop_select_flag = false;
                        break;
                     } else {// 空ボタンはなにもしない。
                        new Lg(this, "yoko_Inc2：yoko_Inc：yoko_Num：tate_Inc==" + yoko_Inc2 + "：" + yoko_Inc + "："
                                                                           + yoko_Num + "：" + tate_Inc);
                        // yoko_Inc2=0; //縦ループを出た場合は、横インデックスを0（上からループ開始）
                        yelllow_loop_select_flag = false;
                        break;
                     }
                  }
               }
            }
            tate_Inc++;
            if (tate_Inc == yoko_Num) {// 最後のインデックス番号で初期化
               tate_Inc = 0;
            }
         } // -------------------------------------------------------------------------たてループ終端
           // 色移動 縦ループで、枠線を黒に戻す。
         for (int i = 0; i < yoko_Num; i++) {
            panels_0_9[loop_index][yoko_Inc + i].setBorder(new LineBorder(Color.black, 0, false));
            nameButtons_0_9[loop_index][yoko_Inc + i].setForeground(Color.white);
         }
         // ループ中のタブではなくなった場合、上下ループを抜ける
         if (tabbedPane.getSelectedIndex() != tabIndex && tabbedPane.getSelectedIndex() != 0) {// タブインデックスが変化したら目次ループへ
            yoko_Inc  = 0;
            yoko_Inc2 = 0;
            new Lg(this, "mokujiBIndexI_save==" + tab_change_init_num);
            loop_index = tab_change_init_num;
            break;
         } else
            // ループ中のタブではなくなった場合、上下ループを抜ける
            if (tabbedPane.getSelectedIndex() == 0) {// タブインデックスが変化したら目次ループへ
               yoko_Inc = 0;
               yoko_Inc2 = 0;
               // Bag：NullPointerException
               new Lg(this, "mokujiBIndexI_save==" + tab_change_init_num);
               // mokujiB_10[mokujiBIndexI_save].setBorder(new
               // LineBorder(Color.black,
               // 0, false)); //NG
               for (int i = 1; i < 10; i++) {
                  if (mokujiVisibleBl[i].equals(true)) {
                     mokuji0To9B[i].setBorder(new LineBorder(Color.black, 8, false)); // NG
                     mokuji0To9B[i].setForeground(Color.white);
                  }
               }
               loop_index = tab_change_init_num;
               break;
            }
         yoko_Inc = yoko_Inc + yoko_Num; // ループするたびに、indexを加算し、赤ループをする
         yoko_Inc2++;
         // 最後のボタンインデックス番号で初期化（例：36個→yoko_Inc＝0～35）
         if (yoko_Inc == mokujiBtns0To9[loop_index].length) {
            yoko_Inc = 0;
         }
         // 最後のインデックス番号で初期化（例：yoko_Inc2＝1～4/gyouI＝3or4）
         if (yoko_Inc2 == gyouI) {
            yoko_Inc2 = 0;
         }
         tate_Inc = 0; // 横ループ中は縦ループは0（左は時からループを開始）
      }
      // 横ループ終端
   }

   /**
    * moving mokuji button color(Ten key can use as click button when button is active)
    *
    */
   void mokujiLoop() {
      // windows domestic sound
      playWave("resource/Windows Information Bar.wav");
      try {
         // change frame side w moving mokuji button.
         if (mokujiVisibleBl[loop_index].equals(true)) {
            // mokuji_button_9[loop_index].setBorder(new
            // LineBorder(panelColor_0_9[loop_index], 8, false));
            mokuji0To9B[loop_index].setBorder(new LineBorder(Color.GREEN, 16, false));
            mokuji0To9B[loop_index].setForeground(Color.white);
            new Lg(this, "mokujiLoop>mokujiName_10[mokujiBIndexI] == "
                                                               + StringFormat.stringFormat(mokujiBtnNameTxt[loop_index],
                                                                                                                  10)
                                                               + "：" + loop_index);
            Thread.sleep(speed);
         }
         // タブが、目次以外の場合、列数を取得する。
         if (tabbedPane.getSelectedIndex() != 0) {
            // Consorlnew
            // log(Thread.currentThread().getStackTrace(),"void mokuji()＞if (tabbedPane.getSelectedIndex() != 0)");
            yelllow_loop_select_flag = true;
            new Lg(this, "mokujiBIndexI==" + loop_index);
            tab_change_init_num = loop_index;
            // タブの番号（文字列）をintで取得 タブのインデックス番号を取得し、タイトルとして扱う。
            // loop_index =
            // Integer.valueOf((tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())));
            loop_index = Integer.valueOf(tabbedPane.getSelectedIndex());
            // タブの行ボタン数を取得
            if (mokujiVisibleBl[loop_index].equals(true)) {
               yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
            }
         }
         // 目次から、絵記号へキー移動事例：5秒間以上長押しした場合、目次に戻る機能　→　キーreleaseの時、false
         // にすることにより、目次に行って勝手に分野に行くことを防ぐ。
         if (yelllow_loop_select_flag) {
            new Lg(this, "keyFlag : mokujiBIndexI == " + yelllow_loop_select_flag + " : " + loop_index);
            if (mokujiVisibleBl[loop_index].equals(true)) {
               tabbedPane.setSelectedIndex(tabNum[loop_index]);
               // 目次の音
               Koe.oto(mokujiBtnNameTxt[loop_index]);
               yelllow_loop_select_flag = false;
            }
         }
         // 黒に戻す
         if (mokujiVisibleBl[loop_index].equals(true)) {
            mokuji0To9B[loop_index].setBorder(new LineBorder(Color.black, 8, true));
            mokuji0To9B[loop_index].setForeground(Color.white);
         }
      } catch (InterruptedException e1) {
         e1.printStackTrace();
      }
   }

   /**
    * @author User
    *
    */
   public class MyKey extends KeyAdapter {

      int returnMokujiCount = 0; // キー長押しで、目次に戻る機能

      public void keyPressed(KeyEvent e) {
         // 10秒間押し続けると、目次に戻る。
         returnMokujiCount++;
         System.out.println("returnMokujiCount==" + returnMokujiCount);
         new Lg(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
         // タブを切り替える。（スペースキー）
         if (KeyEvent.getKeyText(e.getKeyChar()).equals("Space")) {
            next_tab_count++;
            if (next_tab_count == 10) {
               next_tab_count = 0;
            }
            tabbedPane.setSelectedIndex(next_tab_count);
            new Lg(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
         }
         if (keyPressAvailable == true) {
            for (int i = 0; i < 10; i++) {
               if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i)) || KeyEvent.getKeyText(e.getKeyChar())
                                                                  .equals("Period")
                                                                  || KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
                                                                  || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
                                                                  || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
                                                                  || KeyEvent.getKeyText(e.getKeyChar()).equals("Slash")
                                                                  || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
               // Num Lock is unable to lock.
               ) {
                  // keyPressAvailable キーを押している間は2回目移行は、pressイベントに入らない。
                  keyPressAvailable = false;
                  new Lg(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
                  yelllow_loop_select_flag = true;
                  list_select_flag         = true;
                  break;
               }
            }
         }
      }

      public void keyReleased(KeyEvent e) {
         for (int i = 0; i < 10; i++) {
            if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i))
                                                               || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
                                                               || KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
                                                               || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
                                                               || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
                                                               || KeyEvent.getKeyText(e.getKeyChar()).equals("Slash")
                                                               || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
            // Num Lock is not control by ten key.
            ) {
               if (returnMokujiCount >= 100) {
                  tabbedPane.setSelectedIndex(0);
                  yelllow_loop_select_flag = false;
                  keyPressAvailable        = true;
                  returnMokujiCount        = 0;
               }
               // keyPressAvailable
               // キーを話した時、keyPressAvailableをtrueにし、再びPressイベントに入れる。
               keyPressAvailable = true;
               new Lg(this, "moveEkigo>keyReleased == " + KeyEvent.getKeyText(e.getKeyChar()));
               returnMokujiCount = 0; // キー長押しで目次に戻る。条件値を0に初期化
               break;
            }
         }
      }
   }
   /**
    * @author User
    *
    */
   class MyMouse extends MouseAdapter {

      /**
       * @param MouseEvent
       *           mouseEvent
       *
       */
      public void mouseEntered(MouseEvent mouseEvent) {
         // タブ分野１～9が対象
         for (int i = 1; i < tabPanel_0_9.length; i++) {
            // trueタブを選別
            if (mokujiVisibleBl[i].equals(true)) {//
               // 分野毎のボタン数ループし、押されたボタンを抽出
               for (int j = 0; j < mokujiBtns0To9[i].length; j++) {
                  // 押されたボタンである
                  if (i != 9 && mouseEvent.getSource() == mokujiBtns0To9[i][j] || i != 9 && mouseEvent
                                                                     .getSource() == nameButtons_0_9[i][j]) {
                     panels_0_9[i][j].setBorder(new LineBorder(Constants.enteredColor, 3, false));
                  }
                  if ((i == 9 && mouseEvent.getSource() == mokujiBtns0To9[9][j]) || (i == 9 && mouseEvent
                                                                     .getSource() == nameButtons_0_9[9][j])) {
                     panels_0_9[i][j].setBorder(new LineBorder(Constants.enteredColor, 3, false));
                  }
               }
            }
         }
         for (int i = 1; i < tab_num; i++) {
            // MOKUJI BUTTON
            if (mokujiVisibleBl[i].equals(true)) {
               if (mouseEvent.getSource() == mokuji0To9B[i]) {
                  mokuji0To9B[i].setBorder(new LineBorder(Constants.enteredColor, 3, false));
               }
            }
         }
      }

      /**
       *
       */
      public void mouseExited(MouseEvent e1) {
         // タブ分野１～9が対象
         for (int i = 1; i < tabPanel_0_9.length; i++) {
            // trueタブを選別
            if (mokujiVisibleBl[i].equals(true)) {
               // 分野毎のボタン数ループし、押されたボタンを抽出
               for (int j = 0; j < mokujiBtns0To9[i].length; j++) {
                  // 押されたボタンである
                  if (i != 9 && e1.getSource() == mokujiBtns0To9[i][j] || i != 9
                                                                     && e1.getSource() == nameButtons_0_9[i][j]) {
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     nameButtons_0_9[i][j].setForeground(Color.white);
                  }
                  if ((i == 9 && e1.getSource() == mokujiBtns0To9[9][j]) || (i == 9
                                                                     && e1.getSource() == nameButtons_0_9[9][j])) {
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     nameButtons_0_9[i][j].setForeground(Color.white);
                  }
               }
            }
         }
         for (int i = 1; i < tab_num; i++) {
            // MOKUJI BUTTON
            if (mokujiVisibleBl[i].equals(true)) {
               if (e1.getSource() == mokuji0To9B[i]) {
                  mokuji0To9B[i].setBorder(new LineBorder(Color.black, 8, false));
               }
            }
         }
      }

      /**
       *
       */
      public void mousePressed(MouseEvent e) {
         // 絵記号の音を再生 タブ分野１～9が対象
         for (int i = 1; i < tabPanel_0_9.length; i++) {
            // trueタブを選別
            if (mokujiVisibleBl[i].equals(true)) {
               // 分野毎のボタン数ループし、押されたボタンを抽出
               for (int j = 0; j < mokujiBtns0To9[i].length; j++) {
                  // 押されたボタンである
                  if (i != 9 && e.getSource() == mokujiBtns0To9[i][j] || i != 9
                                                                     && e.getSource() == nameButtons_0_9[i][j]) {
                     // アプリ以外の分野
                     try {
                        // リストを表示
                        if (mokujiBtns0To9[i][j].getText().equals(category_directory_9[i])) {
                           // 画像なが無い場合は何もしない
                        } else {
                           if (mokujiBtns0To9[i][j].getText().equals("もくじ")) {
                              Koe.oto("もくじ");
                              tabbedPane.setSelectedIndex(0);
                           } else {
                              // /////////////////////////////////////////
                              // リスト・音の表示
                              if (gyouIs.equals("絵記号")) {
                                 // ///////////////////////////////////絵記号選択履歴　クリック時
                                 Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/"
                                                                                    + mokujiBtns0To9[i][j].getText()
                                                                                    + ".jpg"), 0, 0, 70, 60);
                                 new Lg(this, "./resource/img/" + category_directory_9[i] + "/"
                                                                                    + mokujiBtns0To9[i][j].getText()
                                                                                    + ".jpg");
                                 ekigoHistoryB = new JButton(" ");
                                 ekigoHistoryB.setBackground(Color.black);
                                 ekigoHistoryB.setPreferredSize(new Dimension(90, 70));
                                 ekigoHistoryB.setHorizontalAlignment(JButton.LEFT);
                                 ekigoHistoryB.setMargin(new Insets(0, 0, 0, 0));
                                 ekigoHistoryB.removeAll();
                                 ekigoHistoryB.add(zoom);
                                 ekigoHistoryB.validate();
                                 view.setView(ekigoHistoryP);
                                 view.setViewPosition(new Point(5000, 100));
                                 if (HistorySizeSec == 0) {
                                    ekigoH = ekigoHistoryP.getHeight() - 5;
                                    ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                                    HistorySizeSec++;
                                 } else {
                                    ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                                 }
                                 ekigoHistoryP.add(ekigoHistoryB);
                                 setVisible(true);
                                 // -----------------------------------
                                 // public BigEkigo(String
                                 // ekigoFileName,String
                                 // ekigoFolderName, int
                                 // ekigoViewMode)
                                 new DispEkigo(mokujiBtns0To9[i][j].getText(), category_directory_9[i], 2);
                                 Koe.oto(mokujiBtns0To9[i][j].getText());
                              } else {
                                 // ///////////////////////////////////絵記号選択履歴　クリック時
                                 Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/"
                                                                                    + mokujiBtns0To9[i][j].getText()
                                                                                    + ".jpg"), 0, 0, 70, 60);
                                 new Lg(this, "./resource/img/" + category_directory_9[i] + "/"
                                                                                    + mokujiBtns0To9[i][j].getText()
                                                                                    + ".jpg");
                                 ekigoHistoryB = new JButton(" ");
                                 ekigoHistoryB.add(zoom);
                                 ekigoHistoryB.setBackground(Color.black);
                                 ekigoHistoryB.setPreferredSize(new Dimension(90, 70));
                                 ekigoHistoryB.setHorizontalAlignment(JButton.LEFT);
                                 ekigoHistoryB.setMargin(new Insets(0, 0, 0, 0));
                                 ekigoHistoryB.removeAll();
                                 ekigoHistoryB.add(zoom);
                                 ekigoHistoryB.validate();
                                 view.setView(ekigoHistoryP);
                                 view.setViewPosition(new Point(5000, 100));
                                 if (HistorySizeSec == 0) {
                                    ekigoH = ekigoHistoryP.getHeight() - 5;
                                    ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                                    HistorySizeSec++;
                                 } else {
                                    ekigoHistoryB.setPreferredSize(new Dimension(ekigoH, ekigoH));
                                 }
                                 ekigoHistoryP.add(ekigoHistoryB);
                                 setVisible(true);
                                 new List(mokujiBtns0To9[i][j].getText(), category_directory_9[i]);
                                 Koe.oto(mokujiBtns0To9[i][j].getText());
                              }
                           }
                        }
                     } catch (Exception e2) {
                        e2.printStackTrace();
                        System.exit(1);
                     }
                     // アプリ分野
                  } else if ((i == 9 && e.getSource() == mokujiBtns0To9[9][j]) || (i == 9
                                                                     && e.getSource() == nameButtons_0_9[9][j])) {
                                                                        new Lg(this, "else if(i==9)");
                                                                        if (mokujiBtns0To9[9][j].getText().equals("もくじ")) {
                                                                           tabbedPane.setSelectedIndex(0);
                                                                           break;
                                                                        }
                                                                        if (mokujiBtns0To9[i][j].getText().equals("tab10")) {
                                                                        } else {
                                                                           try {
                                                                              Koe.oto(mokujiBtns0To9[i][j].getText());
                                                                              // /////////////////////////////////////////////////////////////////
                                                                              // 他PCに入れるときは、C：に近い所に入れないと、execエラーになる。
                                                                              File f = new File("./resource/img/tab10/" + mokujiBtns0To9[i][j].getText() + ".jar");
                                                                              String command = f.getAbsolutePath();
                                                                              new Lg(this, "command==" + command);
                                                                              Runtime runtime = Runtime.getRuntime();
                                                                              Process process = runtime.exec("cmd /c start " + command);
                                                                              // //////////////////////////////////////////////////////////////////
                                                                              // ダイアログは、プログラムを一時停止させる。
                                                                              // JOptionPane.showMessageDialog(null,
                                                                              // command);
                                                                              Reader in = new InputStreamReader(process.getInputStream());
                                                                              int c = -1;
                                                                              System.out.print("oto==");
                                                                              while ((c = in.read()) != -1) {
                                                                                 System.out.print((char) c);
                                                                              }
                                                                              System.out.print("\n");
                                                                              in.close();
                                                                           } catch (IOException e1) {
                                                                              e1.printStackTrace();
                                                                           }
                                                                        }
                                                                     }
               }
            }
         }
         // Change tab main picts monitors by ten key.
         new Lg(this, "MousePressed_before");
         try {
            moveEkigo(e);
         } catch (FileNotFoundException e1) {
            e1.printStackTrace();
         } catch (IOException e1) {
            e1.printStackTrace();
         }
         new Lg(this, "MousePressed_after");
      }

      /**
       * Change tab main picts monitors by mouse choice.
       *
       * @param e
       * @throws FileNotFoundException
       * @throws IOException
       */
      private void moveEkigo(MouseEvent e) throws FileNotFoundException, IOException {
         new Lg(this, "moveEkigo");
         for (moveEkigoCount = 1; moveEkigoCount < tab_num; moveEkigoCount++) {
            if (mokujiVisibleBl[moveEkigoCount].equals(true)) {
               if (e.getSource() == mokuji0To9B[moveEkigoCount]) {
                  yoko_Inc = 0;
                  new Lg(this, mokujiBtnNameTxt[moveEkigoCount]);
                  Koe.oto(mokujiBtnNameTxt[moveEkigoCount]);
                  tab_change_init_num = loop_index;
                  loop_index          = moveEkigoCount;
                  // Return mokuji button color to brack.
                  if (mokujiVisibleBl[loop_index].equals(true)) {
                     mokuji0To9B[loop_index].setBorder(new LineBorder(Color.black, 0, true));
                     mokuji0To9B[loop_index].setForeground(Color.white);
                     yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
                  }
                  if (prop.getPict().getProperty("seting.roop").equals("OFF")) {
                     new Lg(this, "Change tab by mouse choice.");
                     if (mokujiVisibleBl[loop_index].equals(true)) {
                        tabbedPane.setSelectedIndex(tabNum[loop_index]);
                     }
                  } else {
                     new Lg(this, "Change tab by ten key.");
                     yelllow_loop_select_flag = true;
                  }
                  // ループ状態出なければkeyFlagはアプリに関係ない。
                  // i can not understand this comment. nessusaly to fix.
                  new Lg(this, "moveEkigo>mokujiBIndexI==" + loop_index);
               }
            }
         }
      }
   }
   /**
    * ボタンループ選択時、borderをオレンジに変化させる。
    *
    * @author User
    *
    */
   class PointerColorTHread implements Runnable {

      /*
       * (非 Javadoc)
       *
       * @see java.lang.Runnable#run()
       */
      public void run() {
         panels_0_9[loop_index][tate_Inc + yoko_Inc].setBorder(new LineBorder(Color.white, 20, false));
      }
   }

   // ループ音
   public static void playWave(String fileName) {
      final int EXTERNAL_BUFFER_SIZE = 128000;
      try {
         // Fileクラスのインスタンスを生成
         File soundFile = new File(fileName);
         // オーディオ入力ストリームを取得します
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
         // オーディオ形式を取得します
         AudioFormat audioFormat = audioInputStream.getFormat();
         // データラインの情報オブジェクトを生成します
         DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
         // 指定されたデータライン情報に一致するラインを取得します
         SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
         // 指定されたオーディオ形式でラインを開きます
         line.open(audioFormat);
         // ラインでのデータ入出力を可能にします
         line.start();
         int    nBytesRead = 0;
         byte[] abData     = new byte[EXTERNAL_BUFFER_SIZE];
         while (nBytesRead != -1) {
            // オーディオストリームからデータを読み込みます
            nBytesRead = audioInputStream.read(abData, 0, abData.length);
            if (nBytesRead >= 0) {
               // オーディオデータをミキサーに書き込みます
               int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
         }
         // ラインからキューに入っているデータを排出します
         line.drain();
         // ラインを閉じます
         line.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * 絵記号会話ソフト（絵記号の画面遷移による、会話支援ソフト）
    *
    * @param args
    */
   public static void main(String[] args) {
      System.out.println("--------------------------------------------------");
      try {
         new KaiwaMain();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (AWTException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      //
      //        SwingUtilities.invokeLater(new Runnable() {
      //            /*
      //             * (非 Javadoc)
      //             *
      //             * @see java.lang.Runnable#run()
      //             */
      //            public void run() {
      //                try {
      //                    new KaiwaMain();
      //                } catch (IOException e) {
      //                    // TODO 自動生成された catch ブロック
      //                    e.printStackTrace();
      //                } catch (AWTException e) {
      //                    // TODO 自動生成された catch ブロック
      //                    e.printStackTrace();
      //                }
      //            }
      //        });
   }

   /**
    *
    * @author ikonu
    */
   class MyRobot implements Runnable {

      public void run() {
         while (true) {
            try {
               Thread.sleep(30000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            robot.mouseMove(rect.width - 100, 10);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
         }
      }
   }
}