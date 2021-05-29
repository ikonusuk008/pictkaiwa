package main_frame;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import test_tool.l;
import ekigo_tool.DispEkigo;
import statics.CommonColor;
import statics.Koe;
import statics.StringFormat;
import statics.Zoom;
import statics.ZoomTab;
public class Kaiwa extends JFrame implements Runnable {

   Robot                     robot             = new Robot();
   /**
    */
   private static final long serialVersionUID  = 1L;
   int                       HistorySizeSec    = 0;
   int                       ekigoH            = 0;
   int                       next_tab_count    = 0;            // �^�u���X�y�[�X�L�[�Ő؂�ւ���B
   Rectangle                 rect              = null;
   JViewport                 view              = null;
   JButton                   button            = new JButton();
   JPanel                    jPanel            = new JPanel();
   static boolean            keyPressAvailable = true;
   int                       moveEkigoCount;                   // �ڎ�����G�L���ړ����̎w��C���f�b�N�X
   Thread                    thread            = null;
   int                       handleLag         = 2000;
   /*
    * ���[�v�̃C���f�b�N�X
    */
   static int loop_index = 1;// 1�`9 ���[�vindex
   /*
    * 1. �^�u���ς�����Ƃ��ɁAloop_index������������B
    */
   static int tab_change_init_num = 1;// 0�ȊO�������Ƃ���
   // ���[�v�\������ϐ�
   int        yoko_Inc  = 0;// yoko_Ink:��s�̍��v
   int        yoko_Inc2 = 0;// �s�C���f�b�N�X�i1�`4�j
   int        tate_Inc  = 0;// ��C���f�b�N�X�i1�`yoko_Num�j
   static int yoko_Num  = 0;// �s�̌�
   // �v���p�e�B�ϐ�
   static int           speed                        = 0;             // ���[�v���x
   static int           tabCount                     = 0;
   static int           tabNum[]                     = new int[10];
   public static String category_from_property[]     = new String[10];// �v���p�e�B������o�����J�e�S���[��
   public static String category_img_from_property[] = new String[10];// �v���p�e�B������o�����J�e�S���[�摜��
   /*
    * �ڎ��ϐ� 1. mokujiName_10�𓮓I�ɂ�����@�F�����������ɂ���d�g�݂��K�v�����A�܂��ł��Ȃ��B 2. tab1�F�ڎ�
    */
   static int            tab_num                = 10;
   public static String  category_directory_9[] = { "tab1", "tab2", "tab3", "tab4", "tab5", "tab6", "tab7", "tab8",
                                                      "tab9", "tab10" };
   public static JButton category_button_9[]    = new JButton[tab_num];
   static JPanel         nextPanel;
   static int            nextMousepressIndex    = 1;
   static int            nextMousepressIndex2   = 1;
   static int            nextKeyTypeIndex       = 1;
   static JPanel         toolPanel;
   /*
    * �^�u
    */
   static JTabbedPane tabbedPane     = null;
   JPanel             tabPanel_0_9[] = new JPanel[tab_num];
   /*
    * �{�^���i�A�C�R���E���O�j
    */
   static JButton[] b0, b1, b2, b3, b4, b5, b6, b7, b8, b9 = null;
   static JButton   buttons_0_9[][]     = { b0, b1, b2, b3, b4, b5, b6, b7, b8, b9 };
   static JButton[] nab0, nab1, nab2, nab3, nab4, nab5, nab6, nab7, nab8, nab9 = null;
   static JButton   nameButtons_0_9[][] = { nab0, nab1, nab2, nab3, nab4, nab5, nab6, nab7, nab8, nab9 };
   /*
    * �p�l��
    */
   static JPanel[]      p0, p1, p2, p3, p4, p5, p6, p7, p8, p9 = null;
   public static JPanel panels_0_9[][] = { p0, p1, p2, p3, p4, p5, p6, p7, p8, p9 };
   JPanel               ekigoHistoryP;
   JButton              ekigoHistoryB;
   JScrollPane          ekigoHistorySP;
   /*
    * �G�x���g���[�v
    */
   public static JButton mainLoopButton = new JButton();
   /*
    * �v���p�e�B
    */
   static int           trueCount                         = 0;
   static int           gyouI                             = 0;
   String               ekigoHenSt                        = null;
   public static String category_visible_boolean_string[] = new String[10]; // �J�e�S���^�u�̕\���E��\����boolean�̕�����i�v���p�e�B������o���j
   static Boolean       category_visible_boolean[]        = new Boolean[10];// �J�e�S���^�u�̕\���E��\����boolean�l
   static String        gyouIs                            = new String();
   /*
    * �t�@�C��
    */
   static int           category_tate_kakeru_yoko_num = 0;
   static int           category_files_num            = 0;
   public static File[] f0, f1, f2, f3, f4, f5, f6, f7, f8, f9;
   public static File   bunyaFiles_0_9[][]            = { f0, f1, f2, f3, f4, f5, f6, f7, f8, f9 };
   /*
    * �t�H���_�[�����擾�p
    */
   public File  d0, d1, d2, d3, d4, d5, d6, d7, d8, d9;
   private File bunyaFolder_0_9[] = { d0, d1, d2, d3, d4, d5, d6, d7, d8, d9, };
   /*
    * �C���[�W
    */
   Zoom    ZoomImg;
   ZoomTab ZoomTabImg;
   /*
    * �C�x���g
    */
   MyMouse mouse = new MyMouse();
   MyKey   key   = new MyKey();
   /*
    * ���C�A�E�g
    */
   GridBagLayout      category_button_panel_gridbaglayout       = new GridBagLayout();
   GridBagConstraints category_button_panel_gridbag_constrainrs = new GridBagConstraints();
   /*
    * Flag
    */
   static boolean yelllow_loop_select_flag = false;     //
   static boolean list_select_flag         = false;     //
   Prop           prop                     = new Prop();// �O���[�o���ŃC���X�^���X���͏o���Ȃ��B

   /**
    * @return
    */
   private JButton meke_help_button() {
      JButton buttton = new JButton("<html><b><h2>�g����</h2><b><html>");
      buttton.setBackground(Color.black);
      buttton.setForeground(Color.white);
      buttton.addKeyListener(key);
      buttton.setPreferredSize(new Dimension(100, 50));
      buttton.addActionListener(new ActionListener(){

         public void actionPerformed(ActionEvent actionEvent) {
            meke_help_frame();
         }
      });
      return buttton;
   }

   /**
    * @param t
    * @return
    */
   private JTextArea read_help_text(JTextArea t) {
      String s = new String();
      try {
         FileReader     in = new FileReader("resource/txt/help.txt");
         BufferedReader br = new BufferedReader(in);
         String         line;
         while ((line = br.readLine()) != null) {
            System.out.println(line);
            s += line + "\n";
         }
         br.close();
         in.close();
      } catch (FileNotFoundException e) {
         System.out.println(e);
      } catch (IOException e) {
         System.out.println(e);
      }
      t.setText(s);
      return t;
   }

   public Kaiwa() throws IOException, AWTException {
      super();
      /*
       * ��ɍ��p���N���b�N����B
       */
      robot_crick robot  = new robot_crick();
      Thread      thread = new Thread(robot);
      thread.start();
      double_start_lock();// 2�d�N�����b�N
      // set_tab_ui_manerger();// �^�u�v���p�e�B�ݒ�
      // Look&Feel�̐ݒ�
      // String type = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
      // try {
      // UIManager.setLookAndFeel(type);
      // } catch (Exception e) {
      // System.out.println("��O�����F" + e);
      // }
      /*
       * Insets
       */
      UIManager.put("TabbedPane.tabInsets", new Insets(2, 8, 2, 8));// �^�u�̏㉺��Ԑݒ�
      // UIManager.put("TabbedPane.tabAreaInsets", new
      // Insets(8,8,8,8));//�^�u�p�l���̏㉺��Ԑݒ�
      // UIManager.put("TabbedPane.contentBorderInsets", new Insets(8,8,8,8));
      // UIManager.put("TabbedPane.selectedTabPadInsets", new
      // Insets(8,8,8,8));
      /*
       * Color
       */
      UIManager.put("TabbedPane.shadow", Color.GRAY);
      UIManager.put("TabbedPane.darkShadow", Color.GRAY);
      UIManager.put("TabbedPane.light", Color.GRAY);
      UIManager.put("TabbedPane.highlight", Color.GRAY);
      UIManager.put("TabbedPane.tabAreaBackground", new Color(255, 255, 255));
      UIManager.put("TabbedPane.unselectedBackground", new Color(255, 255, 255));
      UIManager.put("TabbedPane.selectedForeground", Color.black);
      UIManager.put("TabbedPane.background", Color.black);
      UIManager.put("TabbedPane.foreground", Color.white);
      UIManager.put("TabbedPane.focus", new Color(255, 255, 255));
      UIManager.put("TabbedPane.contentAreaColor", new Color(255, 255, 255));
      UIManager.put("TabbedPane.selected", new Color(255, 255, 255));// �^�u�I�����A�F������B
      UIManager.put("TabbedPane.selectHighlight", new Color(0, 0, 0));// �^�u����̐��̐F
      UIManager.put("TabbedPane.borderHightlightColor", new Color(0, 0, 0));
      /*
       * Opaque
       */
      UIManager.put("TabbedPane.tabRunOverlay", Boolean.TRUE);
      UIManager.put("TabbedPane.tabsOverlapBorder", Boolean.TRUE);
      UIManager.put("TabbedPane.selectionFollowsFocus", Boolean.TRUE);
      tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
      prop       = get_property(prop);
      ImageIcon icon = set_maximum_window_bounds();// �t���[������ʂɍ��킹��
      set_class_property(icon);
      tabbedPane.addKeyListener(key);// �S�̂�Component�ɃL�[�����f����鎖���������B
      tabbedPane = make_pict_tab();// �^�u�𐶐��E���H
      /*
       * �^�u�̑O�i�F��؂�ւ��C�x���g���A�ݒ肷��B
       */
      tabbedPane.addChangeListener(new ChangeListener(){

         public void stateChanged(ChangeEvent e) {
            JTabbedPane jtab   = (JTabbedPane) e.getSource();
            int         sindex = jtab.getSelectedIndex();
            for (int i = 0; i < jtab.getTabCount(); i++) {
               if (i == sindex && jtab.getTitleAt(sindex).endsWith("1")) {
                  // jtab.setForegroundAt(i, Color.GREEN);// �H
               } else if (i == sindex) {
                  // Color sc = (sindex % 2 == 0) ? Color.RED :
                  // Color.BLUE;
                  jtab.setForegroundAt(i, Color.black);
               } else {
                  jtab.setForegroundAt(i, Color.white);
               }
            }
         }
      });
      make_rireki();// �������쐬
      make_pict_button();// ��Ő��������^�u�p�l���ɑ΂��A�p�l���ƃ{�^����ǉ�����B
      /*
       * ���[�v�I��
       */
      try {
         if (prop.getPict().getProperty("seting.roop").equals("ON")) {// ���[�v�C�x���g���N������B
            thread = new Thread(this);
            thread.start();
         } else {// ���[�v�C�x���g���N�����Ȃ��B
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      // Windows �V �ł́A�{�^�����t�H���g�ɂȂ�B
      this.setVisible(true);// �Ō�ɉ�������B
   }

   /**
    *
    */
   private void make_rireki() {
      /*
       * �G�L������
       */
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
      new l(this, "ekigoHistorySP>getLayout==" + ekigoHistorySP.getLayout().toString());
      new l(this, "ekigoHistoryP>getLayout==" + ekigoHistoryP.getLayout().toString());
      // -------------------------------------------�G�L������
      ekigoHistoryP_main.add(ekigoHistorySP, BorderLayout.CENTER);
      ekigoHistoryP_main.add(meke_help_button(), BorderLayout.EAST);
      this.getContentPane().add(ekigoHistoryP_main, BorderLayout.NORTH);
      this.getContentPane().add(tabbedPane, BorderLayout.CENTER);// Frame�{�̂ɏ悹��B
      tabbedPane.setSelectedIndex(1);// �R���|�[�l���g���������A�ڎ��^�u�̃t�H���g�����ɂ���B
      tabbedPane.setSelectedIndex(0);// �R���|�[�l���g���������A�ڎ��^�u�̃t�H���g�����ɂ���B
   }

   /**
    * @return
    */
   private void meke_help_frame() {
      JFrame frame = new JFrame();
      frame.setTitle("�g����");
      frame.setAlwaysOnTop(true);
      FlowLayout          help_panel_layout = new FlowLayout(FlowLayout.LEFT);
      JPanel              help_panel        = new JPanel(help_panel_layout);
      JTextArea           help_textarea     = new JTextArea();
      GraphicsEnvironment env               = GraphicsEnvironment.getLocalGraphicsEnvironment();
      rect = env.getMaximumWindowBounds();
      help_panel.setBounds(rect);
      help_textarea.setBounds(rect);
      help_textarea = read_help_text(help_textarea);
      help_textarea.setForeground(Color.white);
      help_textarea.setBackground(Color.black);
      help_textarea.setFont(new Font("Serif", Font.BOLD, 20));
      JScrollPane scrollpane = new JScrollPane();
      scrollpane.setViewportView(help_textarea);
      scrollpane.setBounds(rect);
      System.out.println("ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttrect.width=="
                                                         + rect.width);
      System.out.println("rect.height==" + rect.height);
      JScrollBar JScrollBar = new JScrollBar();
      // JScrollBar.set
      scrollpane.setPreferredSize(new Dimension(rect.width - 15, rect.height));// �X�N���[���o�[�̕��̕������A�k�߂�B
      scrollpane.setVerticalScrollBar(JScrollBar);
      scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      help_panel.add(scrollpane);
      frame.add(help_panel);
      frame.setBounds(rect);
      frame.setVisible(true);
   }

   /**
    * @param icon
    */
   private void set_class_property(ImageIcon icon) {
      this.setAlwaysOnTop(true);
      this.setBounds(rect);
      this.setIconImage(icon.getImage());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("�G�L����b�\�t�g");
      this.setLayout(new BorderLayout());
   }

   /**
    * @throws FileNotFoundException
    * @throws IOException
    */
   private void double_start_lock() throws FileNotFoundException, IOException {
      /*
       * �N���`�F�b�N 2�d�N�����Ȃ�
       */
      final FileOutputStream fos  = new FileOutputStream(new File("./resource/lock_control"));
      final FileChannel      fc   = fos.getChannel();
      final FileLock         lock = fc.tryLock();
      if (lock == null) {
         /*
          * ���ɋN������Ă���̂ŏI������
          */
         try {
            "hello".charAt(-1);
         } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "�G�L����b�\�t�g�͂��łɋN�����Ă��܂��B");
            System.exit(0);
         }
         return;
      }
      Runtime.getRuntime().addShutdownHook(new Thread(){// ���b�N�J��������o�^

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
    * @return
    */
   private ImageIcon set_maximum_window_bounds() {
      ImageIcon           icon = new ImageIcon("./resource/img/tab_img/kurumaisu.jpg");
      GraphicsEnvironment env  = GraphicsEnvironment.getLocalGraphicsEnvironment();
      rect = env.getMaximumWindowBounds();
      robot.mouseMove(rect.width - 300, 10);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      return icon;
   }

   /**
    * @param prop
    * @return
    */
   private Prop get_property(Prop prop) {
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
      /*
       * �G�L���̍s����ݒ�
       */
      try {
         if (prop.getPict().getProperty("seting.gyou").equals("3")) {
            gyouI = 3;
         } else if (prop.getPict().getProperty("seting.gyou").equals("4")) {
            gyouI = 4;
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      try {
         /*
          * �G�L���̍s�����擾
          */
         try {
            if (prop.getPict().getProperty("seting.list").equals("�@������\��")) {
               gyouIs = "�@������\��";
               new l(this, "Kaiwa>getProp>else if (prop.getPict().getProperty(seting.list).equals(����))");
            } else if (prop.getPict().getProperty("seting.list").equals("�A�G�L�����g��\��")) {
               gyouIs = "�A�G�L�����g��\��";
               new l(this, "Kaiwa>getProp>else if (prop.getPict().getProperty(seting.list).equals(�G�L��))");
            } else if (prop.getPict().getProperty("seting.list").equals("�B�G�L���ꎞ�I�ɕ\��")) {
               gyouIs = "�B�G�L���ꎞ�I�ɕ\��";
               new l(this, "Kaiwa>getProp>else if (prop.getPict().getProperty(seting.list).equals(�G�L���ꎞ))");
            }
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
         /*
          * �ڎ�����
          */
         for (int j = 0; j < 10; j++) {
            category_from_property[j]          = new String();
            category_from_property[j]          = prop.getPict().getProperty("seting.category_name" + String.valueOf(j));// �J�e�S����������
            category_img_from_property[j]      = new String();
            category_img_from_property[j]      = prop.getPict().getProperty(
                                                               "seting.category_img_name" + String.valueOf(j));         // �J�e�S���摜��������
            category_visible_boolean_string[j] = new String();
            category_visible_boolean_string[j] = prop.getPict().getProperty("seting.mokujiBool" + String.valueOf(j));   // �J�e�S��
            // �\��true
            // or
            // false
            category_visible_boolean[j] = Boolean.parseBoolean(category_visible_boolean_string[j]);
            new l(this, "category_from_property[" + j + "] �F mokujiBool[" + j + "] == " + category_from_property[j]
                                                               + " �F " + category_visible_boolean_string[j]);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return prop;
   }
   // private String makeTitle(String t, String p) {
   // return "<html><center><img src='" + getClass().getResource(p) +
   // "'/><br/>" + t;
   // }

   /**
    * @return
    */
   private JTabbedPane make_pict_tab() {
      for (int i = 0; i < tabPanel_0_9.length; i++) {
         if (category_visible_boolean[i].equals(true)) {
            tabNum[i] = trueCount;
            // truePpanelColor[trueCount - 1] = panelColor_0_9[i];
            trueCount++;
            tabPanel_0_9[i] = new JPanel();
            tabPanel_0_9[i].setPreferredSize(new Dimension(900, 500));
            tabPanel_0_9[i].setBackground(new Color(255, 255, 255));
            tabPanel_0_9[i].setBorder(new LineBorder(new Color(255, 255, 255), 1, false));
            if (0 == i) {
               ZoomTabImg = new ZoomTab(new ImageIcon("./resource/img/tab1/" + category_img_from_property[i] + ".jpg"),
                                                                  0, 0, 50, 50);
            } else {
               ZoomTabImg = new ZoomTab(new ImageIcon("./resource/img/tab1/" + category_img_from_property[i] + ".jpg"),
                                                                  0, 0, 50, 50);
            }
            tabbedPane.add(tabPanel_0_9[i], ZoomTabImg);
            tabbedPane.setForeground(Color.white);
            tabbedPane.setFont(new Font("", Font.BOLD, 22));
            tabbedPane.setTitleAt(tabCount, category_from_property[i]);
            // tabbedPane.setTitleAt(tabCount, String.valueOf(i));
            /*
             * �^�u��html���C�A�E�g�ŉ摜�ƕ������ݒ肵�������A�^�u�͊G�L�������I�ɋ�ʂł���悤�ɂ��Ȃ���΂Ȃ�Ȃ��A�Ǝv�����B �������A�^�u��I������̂�
             * ���S�҂̉��҂̏ꍇ������̂ŁA�^�u�ɕ���̕���������邱�ƂƂ���B
             */
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
    *
    */
   public void make_pict_button() {
      new l(this, "public void button()");
      for (int i = 0; i < tabPanel_0_9.length; i++) {
         /*
          * ���얈�̃t�@�C�������擾
          */
         if (category_visible_boolean[i].equals(true)) {
            new l(this, "if (bool[i].equals(true))");
            bunyaFolder_0_9[i] = new File("./resource/img/" + category_directory_9[i]);
            bunyaFiles_0_9[i]  = bunyaFolder_0_9[i].listFiles();                       // �t�H���_�[���̃t�@�C����z��ŕԂ��B
            category_files_num = bunyaFiles_0_9[i].length;
            /*
             * �G�L���̍s����ݒ�
             */
            tabPanel_0_9[i].setLayout(new GridLayout(gyouI, 5, 1, 1));
            tabPanel_0_9[i].setLayout(new GridLayout(gyouI, 5, 1, 1));
            /*
             * �c�Ɖ��Ƀ{�^����~���l�߂邽�߁A category_tate_kakeru_yoko_num: �c�~���̐����擾����B
             */
            category_tate_kakeru_yoko_num = (int) (gyouI * (Math.floor(category_files_num / gyouI) + 1));// �c�~���̐�
            panels_0_9[i]                 = new JPanel[category_tate_kakeru_yoko_num];                   // �G�L���p�l��
            buttons_0_9[i]                = new JButton[category_tate_kakeru_yoko_num];                  // �G�L���{�^��
            nameButtons_0_9[i]            = new JButton[category_tate_kakeru_yoko_num];                  // �G�L�����{�^��
            /*
             * �G�L���{�^���𐶐�
             */
            for (int j = 0; j < category_tate_kakeru_yoko_num; j++) {
               /*
                * �����A�����ɂ��܂��܂ȃ{�^��1�ɂ�鑀��\�ȃ\�t�g������B
                */
               if (i == 9) {// �A�v������
                  buttons_0_9[i][j]     = new JButton();//
                  nameButtons_0_9[i][j] = new JButton();//
                  buttons_0_9[i][j].setBackground(new Color(0, 0, 0));
                  if (category_files_num <= j) {
                     /*
                      * �A�v����jar���Ȃ��{�^�� �����\������B
                      */
                     nameButtons_0_9[i][j].setText(" ");
                     nameButtons_0_9[i][j].setBackground(Color.black);
                     nameButtons_0_9[i][j].addMouseListener(mouse);
                     nameButtons_0_9[i][j].addKeyListener(key);
                     nameButtons_0_9[i][j].setForeground(Color.white);
                     nameButtons_0_9[i][j].setFont(new Font("", Font.BOLD, 50));
                     buttons_0_9[i][j].addMouseListener(mouse);
                     buttons_0_9[i][j].setHorizontalAlignment(JButton.LEFT);
                     buttons_0_9[i][j].addKeyListener(key);
                     buttons_0_9[i][j].setActionCommand(buttons_0_9[i][j].getText());
                     buttons_0_9[i][j].setText(category_directory_9[i]);
                     buttons_0_9[i][j].setFont(new Font("", Font.BOLD, 50));
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 0;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 1.0d;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(buttons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 1;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 0.001;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(nameButtons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     panels_0_9[i][j] = new JPanel(category_button_panel_gridbaglayout);
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     panels_0_9[i][j].add(nameButtons_0_9[i][j]);
                     tabPanel_0_9[i].add(panels_0_9[i][j]);
                  } else {
                     /*
                      * �A�v���̃{�^��
                      */
                     int point = bunyaFiles_0_9[i][j].getName().lastIndexOf(".");// �|�C���g�̕����Ԗڂ�Ԃ��B
                     nameButtons_0_9[i][j].setText(bunyaFiles_0_9[i][j].getName().substring(0, point));
                     nameButtons_0_9[i][j].setBackground(Color.black);
                     nameButtons_0_9[i][j].addMouseListener(mouse);
                     nameButtons_0_9[i][j].addKeyListener(key);
                     nameButtons_0_9[i][j].setForeground(Color.white);
                     nameButtons_0_9[i][j].setFont(new Font("", Font.BOLD, 50));
                     buttons_0_9[i][j].addMouseListener(mouse);
                     buttons_0_9[i][j].addKeyListener(key);
                     buttons_0_9[i][j].setHorizontalAlignment(JButton.LEFT);
                     buttons_0_9[i][j].setText(bunyaFiles_0_9[i][j].getName().substring(0, point));
                     buttons_0_9[i][j].setActionCommand(bunyaFiles_0_9[i][j].getName().substring(0, point));
                     buttons_0_9[i][j].setFont(new Font("", Font.BOLD, 50));
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 0;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.gridwidth  = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 1.0d;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(buttons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 1;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.gridwidth  = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 0.001;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(nameButtons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     panels_0_9[i][j] = new JPanel(category_button_panel_gridbaglayout);
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     panels_0_9[i][j].add(nameButtons_0_9[i][j]);
                     tabPanel_0_9[i].add(panels_0_9[i][j]);
                  }
               } else if (1 <= i) {
                  /*
                   * �G�L������i�A�v���ȊO�̕���j �C���f�b�N�X0�Ԗڂ͖ڎ��{�^��
                   */
                  buttons_0_9[i][j] = new JButton();//
                  buttons_0_9[i][j].setBackground(new Color(0, 0, 0));
                  nameButtons_0_9[i][j] = new JButton();//
                  if (category_files_num <= j) {
                     nameButtons_0_9[i][j].setText(" ");
                     nameButtons_0_9[i][j].addMouseListener(mouse);
                     nameButtons_0_9[i][j].setBackground(Color.black);
                     nameButtons_0_9[i][j].setForeground(Color.white);
                     buttons_0_9[i][j].addMouseListener(mouse);
                     buttons_0_9[i][j].setHorizontalAlignment(JButton.LEFT);
                     buttons_0_9[i][j].addKeyListener(key);
                     buttons_0_9[i][j].setActionCommand(buttons_0_9[i][j].getText());
                     buttons_0_9[i][j].setText(category_directory_9[i]);
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 0;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 1.0d;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(buttons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 1;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 0.001;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(nameButtons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     panels_0_9[i][j] = new JPanel(category_button_panel_gridbaglayout);
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     panels_0_9[i][j].add(buttons_0_9[i][j]);
                     panels_0_9[i][j].add(nameButtons_0_9[i][j]);
                     tabPanel_0_9[i].add(panels_0_9[i][j]);
                     ZoomImg = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/" + "non.jpg"), 0,
                                                                        0, buttons_0_9[i][j].getHeight(),
                                                                        buttons_0_9[i][j].getHeight());
                     buttons_0_9[i][j].add(ZoomImg);
                     new l(this, "buttons_0_9[i][j].getWidth()==" + buttons_0_9[i][j].getWidth());
                     new l(this, "buttons_0_9[i][j].getHeight()==" + buttons_0_9[i][j].getHeight());
                     this.setVisible(true);
                  } else {
                     /*
                      * �G�L����ʂ�true
                      */
                     int point = bunyaFiles_0_9[i][j].getName().lastIndexOf(".");// �|�C���g�̕����Ԗڂ�Ԃ��B
                     nameButtons_0_9[i][j].setText(bunyaFiles_0_9[i][j].getName().substring(0, point));
                     nameButtons_0_9[i][j].setBackground(Color.black);
                     nameButtons_0_9[i][j].setForeground(Color.white);
                     nameButtons_0_9[i][j].addMouseListener(mouse);
                     buttons_0_9[i][j].addMouseListener(mouse);
                     buttons_0_9[i][j].setHorizontalAlignment(JButton.LEFT);
                     buttons_0_9[i][j].addKeyListener(key);
                     buttons_0_9[i][j].setText(bunyaFiles_0_9[i][j].getName().substring(0, point));
                     buttons_0_9[i][j].setActionCommand(bunyaFiles_0_9[i][j].getName().substring(0, point));
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 0;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.gridwidth  = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 1.0d;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(buttons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     category_button_panel_gridbag_constrainrs.gridx      = 0;
                     category_button_panel_gridbag_constrainrs.gridy      = 1;
                     category_button_panel_gridbag_constrainrs.gridheight = 1;
                     category_button_panel_gridbag_constrainrs.gridwidth  = 1;
                     category_button_panel_gridbag_constrainrs.weightx    = 1.0d;
                     category_button_panel_gridbag_constrainrs.weighty    = 0.001;
                     category_button_panel_gridbag_constrainrs.fill       = GridBagConstraints.BOTH;
                     category_button_panel_gridbaglayout.setConstraints(nameButtons_0_9[i][j],
                                                                        category_button_panel_gridbag_constrainrs);
                     panels_0_9[i][j] = new JPanel(category_button_panel_gridbaglayout);
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     panels_0_9[i][j].add(buttons_0_9[i][j]);
                     panels_0_9[i][j].add(nameButtons_0_9[i][j]);
                     tabPanel_0_9[i].add(panels_0_9[i][j]);
                     this.setVisible(true);
                     ZoomImg = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/"
                                                                        + bunyaFiles_0_9[i][j].getName()), 0, 0,
                                                                        buttons_0_9[i][j].getHeight(),
                                                                        buttons_0_9[i][j].getHeight());
                     new l(this, "nameButtons_0_9[" + i + "][" + j + "].getWidth()=="
                                                                        + nameButtons_0_9[i][j].getWidth());
                     new l(this, "buttons_0_9[" + i + "][" + j + "].getHeight()==" + buttons_0_9[i][j].getHeight());
                     buttons_0_9[i][j].add(ZoomImg);
                  }
               }
            }
         }
      }
      mokuji(tabPanel_0_9);
   }

   /**
    * �ڎ�������
    *
    * @param category_botton_panel
    */
   public void mokuji(JPanel[] category_botton_panel) {
      /*
       * �ڎ��{�^���ݒ�i�j
       */
      for (int i = 1; i < 10; i++) {
         /*
          * B�F�C���f�b�N�X�̎g�������s�K���Ǝv����
          */
         if (category_visible_boolean[i].equals(true)) {
            category_button_9[i] = new JButton(category_from_property[i]);
            category_button_9[i].setPreferredSize(new Dimension(290, 108));
            category_button_9[i].addMouseListener(mouse);
            category_button_9[i].setHorizontalAlignment(SwingConstants.RIGHT);
            category_button_9[i].addKeyListener(key);
            category_button_9[i].setFont(new Font("", Font.BOLD, 25));
            category_button_9[i].setForeground(Color.white);
            category_button_9[i].setMargin(new Insets(0, 0, 0, 0));
            category_button_9[i].setBackground(Color.black);
            category_button_9[i].setBorder(new LineBorder(Color.black, 8, false));
            category_button_9[i].setSelected(true);
            category_botton_panel[0].add(category_button_9[i]);
            this.setVisible(true);
            Zoom zoomImage = new Zoom(new ImageIcon("./resource/img/tab1/" + category_img_from_property[i] + ".jpg"), 0,
                                                               0, category_button_9[i].getHeight(),
                                                               category_button_9[i].getHeight());
            category_button_9[i].add(zoomImage);
         }
      }
      category_botton_panel[0].setLayout(new GridLayout(3, 3, 1, 1));
      category_botton_panel[0].setBackground(new Color(255, 255, 255));
   }

   /**
    *
    */
   public void run() {
      mainLoop();
   }

   /**
    *
    */
   private void mainLoop() {
      while (true) {
         if (category_visible_boolean[loop_index].equals(true) && loop_index == 1) {
            yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
         }
         mokujiLoop();
         if ((tabbedPane.getSelectedIndex() == 0) == false) {
            ekigoLoop(tabbedPane.getSelectedIndex());
         }
         loop_index++;
         if (loop_index == 10) {
            loop_index = 1;
         }
         /*
          * �^�u�̍s�{�^�������擾
          */
         if (category_visible_boolean[loop_index].equals(true)) {
            yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
         }
      }
   }

   /**
    * @param tabIndex
    */
   private void ekigoLoop(int tabIndex) {
      new l(this, "ekigoLoop �@");
      /*
       * �㉺���[�v
       */
      while (true) {
         /*
          * ���[�v�� playWave("resource/Windows Feed Discovered.wav");//�s��
          */
         playWave("resource/Windows Information Bar.wav");// �|��
         new l(this, "while (true)>mokujiBIndexI  yoko_Inc  yoko_Num==" + loop_index + "  " + yoko_Inc + "  "
                                                            + yoko_Num);
         /*
          * �^�u���ڎ��ɂȂ����烋�[�v���o��B
          */
         if (tabbedPane.getSelectedIndex() == 0) {
            new l(this, "�^�u���ڎ��ɂȂ����̂Ń��[�v���o��Fif (tabbedPane.getSelectedIndex() == 0)");
            new l(this, "mokujiBIndexI_save==" + tab_change_init_num);
            loop_index = tab_change_init_num;
            break;
         }
         /*
          * �F�ړ��F����
          */
         for (int p = 0; p < yoko_Num; p++) {
            panels_0_9[loop_index][yoko_Inc + p].setBorder(new LineBorder(Color.white, 8, false));
            nameButtons_0_9[loop_index][yoko_Inc + p].setForeground(Color.white);
         }
         try {
            Thread.sleep(speed);
         } catch (InterruptedException e1) {
            e1.printStackTrace();
         }
         /*
          * ���E���[�v
          */
         while (yelllow_loop_select_flag) {
            new l(this, "while (tateFlag)��yoko_Inc2�Fyoko_Inc�Fyoko_Num�Ftate_Inc==" + yoko_Inc2 + "�F" + yoko_Inc + "�F"
                                                               + yoko_Num + "�F" + tate_Inc);
            list_select_flag = false;// �I�������܂Ń��[�v
            /*
             * �F�ړ��F����F���Ɉړ������Ƃ��A�F�����Ƃɖ߂��B
             */
            panels_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setBorder(new LineBorder(Color.white, 0, false));
            nameButtons_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setForeground(Color.white);
            // �d�Ȃ镔���͐ԂɁiyoko�F�����[�v�̃C���N�������g�j
            // yoko_Ink:��s�̍��v
            // �F�ړ��F����F���Ɉړ�
            panels_0_9[loop_index][tate_Inc + yoko_Inc].setBorder(new LineBorder(Color.orange, 10, false));
            /*
             * ���Ĉړ����ɉ���炷
             */
            Koe.oto(buttons_0_9[loop_index][tate_Inc + yoko_Inc].getText());
            try {
               Thread.sleep(speed);
            } catch (InterruptedException e1) {
               e1.printStackTrace();
            }
            /*
             * �F�ړ��F����F���Ɉړ�����B ���Əd�Ȃ��Ă��������͍����物�F�ɖ߂��B
             */
            panels_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setBorder(new LineBorder(Color.white, 10, false));
            nameButtons_0_9[loop_index][yoko_Num * yoko_Inc2 + tate_Inc].setForeground(Color.white);
            /*
             * �^�u�C���f�b�N�X���ω�������ڎ����[�v��
             */
            if (tabbedPane.getSelectedIndex() != tabIndex) {
               tate_Inc  = 0;
               yoko_Inc2 = 0;
               break;
            }
            /*
             * ���X�g��ʂ�
             */
            if (list_select_flag) {
               /*
                * �A�v���p�l���i�ŏI�p�l���j
                */
               if (tabbedPane.getSelectedIndex() == trueCount - 1) {
                  /*
                   * �ڎ��{�^��
                   */
                  if (buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText().equals("������")) {
                     Koe.oto(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     try {
                        Thread.sleep(1000);
                     } catch (InterruptedException e1) {
                        e1.printStackTrace();
                     }
                     yelllow_loop_select_flag = false;
                     tabbedPane.setSelectedIndex(0);
                     /*
                      * Kaiwa����̃C�x���g�ƈႢ�n�߂���Ăяo���Ă��Ȃ����߁A����������
                      */
                     tate_Inc  = 0;
                     yoko_Inc2 = 0;
                     break;
                  }
                  new l(this, "buttons_0_9==" + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                  if (buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText().equals("tab10")) {
                  } else {
                     /*
                      * ��PC�ɓ����Ƃ��́AC�F�ɋ߂����ɓ���Ȃ��ƁAexec�G���[�ɂȂ�B
                      */
                     File   f       = new File("./resource/img/tab10/" + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num
                                                                        + tate_Inc].getText() + ".jar");
                     String command = f.getAbsolutePath();
                     new l(this, "command==" + command);
                     new l(this, "test==" + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     Runtime runtime = Runtime.getRuntime();
                     Process process = null;
                     /*
                      * jar�̎��s
                      */
                     new l(this, "jar����");
                     new l(this, "==" + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     new l(this, "yoko_Inc2�Fyoko_Inc�Fyoko_Num�Ftate_Inc==" + yoko_Inc2 + "�F" + yoko_Inc + "�F" + yoko_Num
                                                                        + "�F" + tate_Inc);
                     try {
                        process = runtime.exec("cmd /c start " + command);
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                     Reader in = new InputStreamReader(process.getInputStream());
                     int    c  = -1;
                     System.out.print("oto");
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
                  /*
                   * �A�v���ȊO�̕���
                   */
               } else {
                  /*
                   * �ڎ��{�^����I��
                   */
                  if (buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText().equals("������")) {
                     Koe.oto(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                     yelllow_loop_select_flag = false;
                     tabbedPane.setSelectedIndex(0);
                     /*
                      * KaiwaPictList����̃C�x���g�ƈႢ�n�߂���Ăяo���Ă��Ȃ����߁A����������
                      */
                     tate_Inc  = 0;
                     yoko_Inc2 = 0;
                     break;
                     /*
                      * �G�L����I��
                      */
                  } else {
                     if (bunyaFiles_0_9[loop_index].length > yoko_Inc2 * yoko_Num + tate_Inc) {
                        /*
                         * �{�^����_�ł�����
                         */
                        Thread thread = new Thread(new PointerColorTHread());
                        thread.start();
                        panels_0_9[loop_index][tate_Inc + yoko_Inc].setBorder(new LineBorder(Color.black, 8, false));
                        nameButtons_0_9[loop_index][tate_Inc + yoko_Inc].setForeground(Color.white);
                        if (gyouIs.equals("�A�G�L�����g��\��")) {
                           new l(this, "if (gyouIs.equals(�A�G�L�����g��\��) {");
                           /*
                            * �G�L���I�𗚗� �N���b�N��
                            */
                           Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg"), 0, 0, 70, 60);
                           new l(this, "./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
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
                           // -----------------------------------�G�L���I�𗚗�
                           // �N���b�N��
                           /*
                            * �G�L����\������O�ɁA���Ԃ�����
                            */
                           try {
                              Thread.sleep(handleLag);
                           } catch (InterruptedException e1) {
                              e1.printStackTrace();
                           }
                           try {
                              new DispEkigo(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText(),
                                                                                 category_directory_9[loop_index], 1);
                           } catch (HeadlessException e) {
                              e.printStackTrace();
                           }
                           Koe.oto(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                        } else if (gyouIs.equals("�@������\��")) {
                           new l(this, "if (gyouIs.equals(�@������\��)) {");
                           /*
                            * �G�L���I�𗚗� �N���b�N��
                            */
                           Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg"), 0, 0, 70, 60);
                           new l(this, "./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
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
                           // -----------------------------------
                           /*
                            * �G�L����\������O�ɁA���Ԃ������B
                            */
                           try {
                              Thread.sleep(handleLag);
                           } catch (InterruptedException e1) {
                              e1.printStackTrace();
                           }
                           try {
                              /*
                               * public List(String ekigoName, String folderName)
                               */
                              new List(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText(),
                                                                                 category_directory_9[loop_index]);
                           } catch (HeadlessException e) {
                              e.printStackTrace();
                           } catch (SQLException e) {
                              e.printStackTrace();
                           }
                           Koe.oto(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                        } else if (gyouIs.equals("�B�G�L���ꎞ�I�ɕ\��")) {
                           new l(this, "if (gyouIs.equals(�B�G�L���ꎞ�I�ɕ\��)) {");
                           /*
                            * �G�L���I�𗚗� �N���b�N��
                            */
                           Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
                                                                                                                                 .getText()
                                                                              + ".jpg"), 0, 0, 70, 60);
                           new l(this, "./resource/img/" + category_directory_9[loop_index] + "/"
                                                                              + buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc]
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
                           // -----------------------------------
                           /*
                            * �G�L����\������O�ɁA���Ԃ������B
                            */
                           try {
                              Thread.sleep(handleLag);
                           } catch (InterruptedException e1) {
                              e1.printStackTrace();
                           }
                           /*
                            * �����ȊG�L�����Q�b�ԕ\������B
                            */
                           try {
                              // public BigEkigo(String
                              // ekigoFileName,String ekigoFolderName)
                              new DispEkigo(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText(),
                                                                                 category_directory_9[loop_index], 2);
                           } catch (HeadlessException e) {
                              e.printStackTrace();
                           }
                           // -----------------------------------------------�����ȊG�L�����Q�b�ԕ\������B
                           Koe.oto(buttons_0_9[loop_index][yoko_Inc2 * yoko_Num + tate_Inc].getText());
                        }
                        /*
                         * yoko_Inc2=0;//�c���[�v���o���ꍇ�́A���C���f�b�N�X��0�i�ォ�烋�[�v�J�n�j
                         */
                        yelllow_loop_select_flag = false;
                        break;
                     } else {// ��{�^���͂Ȃɂ����Ȃ��B
                        new l(this, "yoko_Inc2�Fyoko_Inc�Fyoko_Num�Ftate_Inc==" + yoko_Inc2 + "�F" + yoko_Inc + "�F"
                                                                           + yoko_Num + "�F" + tate_Inc);
                        /*
                         * yoko_Inc2=0;//�c���[�v���o���ꍇ�́A���C���f�b�N�X��0�i�ォ�烋�[�v�J�n�j
                         */
                        yelllow_loop_select_flag = false;
                        break;
                     }
                  }
               }
            }
            tate_Inc++;
            if (tate_Inc == yoko_Num) {// �Ō�̃C���f�b�N�X�ԍ��ŏ�����
               tate_Inc = 0;
            }
         } // -------------------------------------------------------------------------���ă��[�v�I�[
         /*
          * TODO �F�ړ� �c���[�v�ŁA�g�������ɖ߂��B
          */
         for (int i = 0; i < yoko_Num; i++) {
            panels_0_9[loop_index][yoko_Inc + i].setBorder(new LineBorder(Color.black, 0, false));
            nameButtons_0_9[loop_index][yoko_Inc + i].setForeground(Color.white);
         }
         /*
          * ���[�v���̃^�u�ł͂Ȃ��Ȃ����ꍇ�A�㉺���[�v�𔲂���
          */
         if (tabbedPane.getSelectedIndex() != tabIndex && tabbedPane.getSelectedIndex() != 0) {// �^�u�C���f�b�N�X���ω�������ڎ����[�v��
            yoko_Inc  = 0;
            yoko_Inc2 = 0;
            new l(this, "mokujiBIndexI_save==" + tab_change_init_num);
            loop_index = tab_change_init_num;
            break;
         } else
            /*
             * ���[�v���̃^�u�ł͂Ȃ��Ȃ����ꍇ�A�㉺���[�v�𔲂���
             */
            if (tabbedPane.getSelectedIndex() == 0) {// �^�u�C���f�b�N�X���ω�������ڎ����[�v��
               yoko_Inc = 0;
               yoko_Inc2 = 0;
               // Bag�FNullPointerException
               new l(this, "mokujiBIndexI_save==" + tab_change_init_num);
               /*
                * mokujiB_10[mokujiBIndexI_save].setBorder(new LineBorder(Color.black, 0, false));//NG
                */
               for (int i = 1; i < 10; i++) {
                  if (category_visible_boolean[i].equals(true)) {
                     category_button_9[i].setBorder(new LineBorder(Color.black, 8, false));// NG
                     category_button_9[i].setForeground(Color.white);
                  }
               }
               loop_index = tab_change_init_num;
               break;
            }
         yoko_Inc = yoko_Inc + yoko_Num;// ���[�v���邽�тɁAindex�����Z���A�ԃ��[�v������
         yoko_Inc2++;
         /*
          * �Ō�̃{�^���C���f�b�N�X�ԍ��ŏ������i��F36��yoko_Inc��0�`35�j
          */
         if (yoko_Inc == buttons_0_9[loop_index].length) {
            yoko_Inc = 0;
         }
         /*
          * �Ō�̃C���f�b�N�X�ԍ��ŏ������i��Fyoko_Inc2��1�`4/gyouI��3or4�j
          */
         if (yoko_Inc2 == gyouI) {
            yoko_Inc2 = 0;
         }
         tate_Inc = 0;// �����[�v���͏c���[�v��0�i���͎����烋�[�v���J�n�j
      } // --------------------------------------------------------------------------------------
        // �����[�v�I�[
   }

   /**
    *
    */
   void mokujiLoop() {
      playWave("resource/Windows Information Bar.wav");
      try {
         /*
          * �ڎ��{�^���ɐF��t����
          */
         if (category_visible_boolean[loop_index].equals(true)) {
            // mokuji_button_9[loop_index].setBorder(new
            // LineBorder(panelColor_0_9[loop_index], 8, false));
            category_button_9[loop_index].setBorder(new LineBorder(Color.white, 15, false));
            category_button_9[loop_index].setForeground(Color.white);
            new l(this, "mokujiLoop>mokujiName_10[mokujiBIndexI] == " + StringFormat
                                                               .stringFormat(category_from_property[loop_index], 10)
                                                               + "�F" + loop_index);
            Thread.sleep(speed);
         }
         /*
          * �^�u���A�ڎ��ȊO�̏ꍇ�A�񐔂��擾����B
          */
         if (tabbedPane.getSelectedIndex() != 0) {
            // Consorlnew log(Thread.currentThread().getStackTrace(),"void
            // mokuji()��if (tabbedPane.getSelectedIndex() != 0)");
            yelllow_loop_select_flag = true;
            new l(this, "mokujiBIndexI==" + loop_index);
            tab_change_init_num = loop_index;
            /*
             * �^�u�̔ԍ��i������j��int�Ŏ擾 �^�u�̃C���f�b�N�X�ԍ����擾���A�^�C�g���Ƃ��Ĉ����B
             */
            // loop_index =
            // Integer.valueOf((tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())));
            loop_index = Integer.valueOf(tabbedPane.getSelectedIndex());
            /*
             * �^�u�̍s�{�^�������擾
             */
            if (category_visible_boolean[loop_index].equals(true)) {
               yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
            }
         }
         /*
          * �ڎ�����A�G�L���փL�[�ړ�����F5�b�Ԉȏ㒷���������ꍇ�A�ڎ��ɖ߂�@�\ �� �L�[release�̎��Afalse �ɂ��邱�Ƃɂ��A�ڎ��ɍs���ď���ɕ���ɍs�����Ƃ�h���B
          */
         if (yelllow_loop_select_flag) {
            new l(this, "keyFlag : mokujiBIndexI == " + yelllow_loop_select_flag + " : " + loop_index);
            if (category_visible_boolean[loop_index].equals(true)) {
               tabbedPane.setSelectedIndex(tabNum[loop_index]);
               /*
                * �ڎ��̉�
                */
               Koe.oto(category_from_property[loop_index]);
               yelllow_loop_select_flag = false;
            }
         }
         /*
          * ���ɖ߂�
          */
         if (category_visible_boolean[loop_index].equals(true)) {
            category_button_9[loop_index].setBorder(new LineBorder(Color.black, 8, true));
            category_button_9[loop_index].setForeground(Color.white);
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

      int returnMokujiCount = 0;// �L�[�������ŁA�ڎ��ɖ߂�@�\

      public void keyPressed(KeyEvent e) {
         /*
          * 10�b�ԉ���������ƁA�ڎ��ɖ߂�B
          */
         returnMokujiCount++;
         System.out.println("returnMokujiCount==" + returnMokujiCount);
         new l(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
         /*
          * �^�u��؂�ւ���B�i�X�y�[�X�L�[�j
          */
         if (KeyEvent.getKeyText(e.getKeyChar()).equals("Space")) {
            next_tab_count++;
            if (next_tab_count == 10) {
               next_tab_count = 0;
            }
            tabbedPane.setSelectedIndex(next_tab_count);
            new l(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
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
               // Num Lock�͐���s��
               ) {
                  /*
                   * keyPressAvailable �L�[�������Ă���Ԃ�2��ڈڍs�́Apress�C�x���g�ɓ���Ȃ��B
                   */
                  keyPressAvailable = false;
                  new l(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
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
            // Num Lock�͐���s��
            ) {
               /*
                *
                * */
               if (returnMokujiCount >= 100) {
                  tabbedPane.setSelectedIndex(0);
                  yelllow_loop_select_flag = false;
                  keyPressAvailable        = true;
                  returnMokujiCount        = 0;
               }
               /*
                * keyPressAvailable �L�[��b�������AkeyPressAvailable��true�ɂ��A�Ă�Press�C�x���g�ɓ����B
                */
               keyPressAvailable = true;
               new l(this, "moveEkigo>keyReleased == " + KeyEvent.getKeyText(e.getKeyChar()));
               returnMokujiCount = 0;// �L�[�������Ŗڎ��ɖ߂�B�����l��0�ɏ�����
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

      public void mouseEntered(MouseEvent e1) {
         /*
          * �^�u����P�`9���Ώ�
          */
         for (int i = 1; i < tabPanel_0_9.length; i++) {
            // true�^�u��I��
            if (category_visible_boolean[i].equals(true)) {
               // ���얈�̃{�^�������[�v���A�����ꂽ�{�^���𒊏o
               for (int j = 0; j < buttons_0_9[i].length; j++) {
                  // �����ꂽ�{�^���ł���
                  if (i != 9 && e1.getSource() == buttons_0_9[i][j] || i != 9
                                                                     && e1.getSource() == nameButtons_0_9[i][j]) {
                     panels_0_9[i][j].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
                  }
                  if ((i == 9 && e1.getSource() == buttons_0_9[9][j]) || (i == 9
                                                                     && e1.getSource() == nameButtons_0_9[9][j])) {
                     panels_0_9[i][j].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
                  }
               }
            }
         }
         for (int i = 1; i < tab_num; i++) {
            // MOKUJI BUTTON
            if (category_visible_boolean[i].equals(true)) {
               if (e1.getSource() == category_button_9[i]) {
                  category_button_9[i].setBorder(new LineBorder(CommonColor.enteredColor, 3, false));
               }
            }
         }
      }

      public void mouseExited(MouseEvent e1) {
         /*
          * �^�u����P�`9���Ώ�
          */
         for (int i = 1; i < tabPanel_0_9.length; i++) {
            // true�^�u��I��
            if (category_visible_boolean[i].equals(true)) {
               // ���얈�̃{�^�������[�v���A�����ꂽ�{�^���𒊏o
               for (int j = 0; j < buttons_0_9[i].length; j++) {
                  // �����ꂽ�{�^���ł���
                  if (i != 9 && e1.getSource() == buttons_0_9[i][j] || i != 9
                                                                     && e1.getSource() == nameButtons_0_9[i][j]) {
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     nameButtons_0_9[i][j].setForeground(Color.white);
                  }
                  if ((i == 9 && e1.getSource() == buttons_0_9[9][j]) || (i == 9
                                                                     && e1.getSource() == nameButtons_0_9[9][j])) {
                     panels_0_9[i][j].setBorder(new LineBorder(Color.black, 0, false));
                     nameButtons_0_9[i][j].setForeground(Color.white);
                  }
               }
            }
         }
         for (int i = 1; i < tab_num; i++) {
            // MOKUJI BUTTON
            if (category_visible_boolean[i].equals(true)) {
               if (e1.getSource() == category_button_9[i]) {
                  category_button_9[i].setBorder(new LineBorder(Color.black, 8, false));
               }
            }
         }
      }

      public void mousePressed(MouseEvent e) {
         /*
          * �G�L���̉����Đ� �^�u����P�`9���Ώ�
          */
         for (int i = 1; i < tabPanel_0_9.length; i++) {
            // true�^�u��I��
            if (category_visible_boolean[i].equals(true)) {
               // ���얈�̃{�^�������[�v���A�����ꂽ�{�^���𒊏o
               for (int j = 0; j < buttons_0_9[i].length; j++) {
                  // �����ꂽ�{�^���ł���
                  if (i != 9 && e.getSource() == buttons_0_9[i][j] || i != 9
                                                                     && e.getSource() == nameButtons_0_9[i][j]) {
                     // �A�v���ȊO�̕���
                     try {
                        // ���X�g��\��
                        if (buttons_0_9[i][j].getText().equals(category_directory_9[i])) {
                           // �摜�Ȃ������ꍇ�͉������Ȃ�
                        } else {
                           if (buttons_0_9[i][j].getText().equals("������")) {
                              Koe.oto("������");
                              tabbedPane.setSelectedIndex(0);
                           } else {
                              // /////////////////////////////////////////
                              // ���X�g�E���̕\��
                              if (gyouIs.equals("�G�L��")) {
                                 // ///////////////////////////////////�G�L���I�𗚗�
                                 // �N���b�N��
                                 Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/"
                                                                                    + buttons_0_9[i][j].getText()
                                                                                    + ".jpg"), 0, 0, 70, 60);
                                 new l(this, "./resource/img/" + category_directory_9[i] + "/"
                                                                                    + buttons_0_9[i][j].getText()
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
                                 new DispEkigo(buttons_0_9[i][j].getText(), category_directory_9[i], 2);
                                 Koe.oto(buttons_0_9[i][j].getText());
                              } else {
                                 // ///////////////////////////////////�G�L���I�𗚗�
                                 // �N���b�N��
                                 Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + category_directory_9[i] + "/"
                                                                                    + buttons_0_9[i][j].getText()
                                                                                    + ".jpg"), 0, 0, 70, 60);
                                 new l(this, "./resource/img/" + category_directory_9[i] + "/"
                                                                                    + buttons_0_9[i][j].getText()
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
                                 // -----------------------------------
                                 new List(buttons_0_9[i][j].getText(), category_directory_9[i]);
                                 Koe.oto(buttons_0_9[i][j].getText());
                              }
                              // ------------------------------------------
                           }
                        }
                     } catch (Exception e2) {
                        e2.printStackTrace();
                        System.exit(1);
                     }
                     // �A�v������
                  } else if ((i == 9 && e.getSource() == buttons_0_9[9][j]) || (i == 9
                                                                     && e.getSource() == nameButtons_0_9[9][j])) {
                                                                        new l(this, "else if(i==9)");
                                                                        if (buttons_0_9[9][j].getText().equals("������")) {
                                                                           tabbedPane.setSelectedIndex(0);
                                                                           break;
                                                                        }
                                                                        if (buttons_0_9[i][j].getText().equals("tab10")) {
                                                                        } else {
                                                                           try {
                                                                              Koe.oto(buttons_0_9[i][j].getText());
                                                                              // /////////////////////////////////////////////////////////////////
                                                                              // ��PC�ɓ����Ƃ��́AC�F�ɋ߂����ɓ���Ȃ��ƁAexec�G���[�ɂȂ�B
                                                                              File f = new File("./resource/img/tab10/" + buttons_0_9[i][j].getText() + ".jar");
                                                                              String command = f.getAbsolutePath();
                                                                              new l(this, "command==" + command);
                                                                              Runtime runtime = Runtime.getRuntime();
                                                                              Process process = runtime.exec("cmd /c start " + command);
                                                                              // //////////////////////////////////////////////////////////////////
                                                                              // �_�C�A���O�́A�v���O�������ꎞ��~������B
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
         // -------------------------------------------------------------�G�L���̉����Đ�
         /*
          * �G�L�������ʂɈړ�����B
          */
         new l(this, "MousePressed_before");
         try {
            moveEkigo(e);
         } catch (FileNotFoundException e1) {
            e1.printStackTrace();
         } catch (IOException e1) {
            e1.printStackTrace();
         }
         new l(this, "MousePressed_after");
         // ---------------------------------------------------------------------�G�L�������ʂɈړ�����B
      }

      /*
       * MOKUJI BUTTON�ŊG�L�������ʂɈړ�����B
       */
      private void moveEkigo(MouseEvent e) throws FileNotFoundException, IOException {
         new l(this, "moveEkigo");
         for (moveEkigoCount = 1; moveEkigoCount < tab_num; moveEkigoCount++) {
            if (category_visible_boolean[moveEkigoCount].equals(true)) {
               if (e.getSource() == category_button_9[moveEkigoCount]) {
                  yoko_Inc = 0;
                  new l(this, category_from_property[moveEkigoCount]);
                  Koe.oto(category_from_property[moveEkigoCount]);
                  tab_change_init_num = loop_index;
                  loop_index          = moveEkigoCount;
                  // �ڎ��{�^���̐F�����ɖ߂��B
                  if (category_visible_boolean[loop_index].equals(true)) {
                     category_button_9[loop_index].setBorder(new LineBorder(Color.black, 0, true));
                     category_button_9[loop_index].setForeground(Color.white);
                  }
                  if (category_visible_boolean[loop_index].equals(true)) {
                     yoko_Num = (int) Math.floor(bunyaFiles_0_9[loop_index].length / gyouI) + 1;
                  }
                  // �񃋁[�v��ԂŃ^�u�ړ�
                  if (prop.getPict().getProperty("seting.roop").equals("OFF")) {
                     new l(this, "�񃋁[�v��ԂŃ^�u�ړ�");
                     if (category_visible_boolean[loop_index].equals(true)) {
                        tabbedPane.setSelectedIndex(tabNum[loop_index]);
                     }
                  } else {
                     // ���[�v���Ń^�u�ړ�
                     new l(this, "���[�v��ԂŃ^�u�ړ�");
                     yelllow_loop_select_flag = true;
                  }
                  // ���[�v��ԏo�Ȃ����keyFlag�̓A�v���Ɋ֌W�Ȃ��B
                  new l(this, "moveEkigo>mokujiBIndexI==" + loop_index);
               }
            }
         }
      }
      // ----------------------------------------------------------------------MOKUJI
      // BUTTON�ŊG�L�������ʂɈړ�����B
   }
   /**
    * @author User
    *
    */
   class PointerColorTHread implements Runnable {

      public void run() {
         /*
          * �{�^�����[�v�I�����Aborder���I�����W�ɕω�������B
          */
         panels_0_9[loop_index][tate_Inc + yoko_Inc].setBorder(new LineBorder(Color.white, 20, false));
      }
   }

   /**
    * ���[�v��
    *
    * @param fileName
    */
   public static void playWave(String fileName) {
      final int EXTERNAL_BUFFER_SIZE = 128000;
      try {
         // File�N���X�̃C���X�^���X�𐶐�
         File soundFile = new File(fileName);
         // �I�[�f�B�I���̓X�g���[�����擾���܂�
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
         // �I�[�f�B�I�`�����擾���܂�
         AudioFormat audioFormat = audioInputStream.getFormat();
         // �f�[�^���C���̏��I�u�W�F�N�g�𐶐����܂�
         DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
         // �w�肳�ꂽ�f�[�^���C�����Ɉ�v���郉�C�����擾���܂�
         SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
         // �w�肳�ꂽ�I�[�f�B�I�`���Ń��C�����J���܂�
         line.open(audioFormat);
         // ���C���ł̃f�[�^���o�͂��\�ɂ��܂�
         line.start();
         int    nBytesRead = 0;
         byte[] abData     = new byte[EXTERNAL_BUFFER_SIZE];
         while (nBytesRead != -1) {
            // �I�[�f�B�I�X�g���[������f�[�^��ǂݍ��݂܂�
            nBytesRead = audioInputStream.read(abData, 0, abData.length);
            if (nBytesRead >= 0) {
               // �I�[�f�B�I�f�[�^���~�L�T�[�ɏ������݂܂�
               int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
         }
         // ���C������L���[�ɓ����Ă���f�[�^��r�o���܂�
         line.drain();
         // ���C������܂�
         line.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * @param args
    */
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable(){

         public void run() {
            try {
               new Kaiwa();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (AWTException e) {
               // TODO �����������ꂽ catch �u���b�N
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * @author User
    *
    */
   class robot_crick implements Runnable {

      public void run() {
         while (true) {
            try {
               Thread.sleep(30000);
            } catch (InterruptedException e) {
               // TODO �����������ꂽ catch �u���b�N
               e.printStackTrace();
            }
            robot.mouseMove(rect.width - 100, 10);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
         }
      }
   }
}
