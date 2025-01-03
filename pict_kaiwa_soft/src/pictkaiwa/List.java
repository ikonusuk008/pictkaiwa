package pictkaiwa;

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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import util.Constants;
import util.Koe;
import util.Lg;
import util.Read;
import util.Write;
import util.Zoom;

public class List extends JFrame implements Runnable,ActionListener {
    private boolean keyPressReleaseAvailable = false;

    Connection conn = null;

    /*
     * DBのrowに関する情報
     */
    final int table_row_start_num = 1;
    final int table_row_finish_num = 7;

    String fileNameList = null;
    String jpgFileName = null;
    static Frame frame = null;

    JTextArea textArea[] = new JTextArea[7];
    JTextArea insertListText = new JTextArea();

    JButton end_button = new JButton("<html>終了@<u>リスト2</u></html>");
    JButton imgButton[] = new JButton[7];
    JButton playButton[] = new JButton[7];
    JButton listTitleButton = new JButton();// タイトルボタン
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

    String ekigoName = null;
    String folderName = null;

    /**
     *
     * @param ekigoName
     * @param folderName
     * @throws HeadlessException
     * @throws SQLException
     */
    public List(String ekigoName, String folderName) throws HeadlessException, SQLException {

        super();

        this.ekigoName = ekigoName;
        this.folderName = folderName;

        mainFrameSetting(this.ekigoName, this.folderName);

        make_new_pict_table(this.ekigoName,getSqlStatement());// 新しい画像テーブルを作成

        selectTable(this.ekigoName);// テーブルからデータを選択

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

        Zoom zoom0 = new Zoom(new ImageIcon("./resource/img/" + folderName + "/" + ekigoName + ".jpg"), 0, 0,
                        listTitleButton.getHeight(), listTitleButton.getHeight());
        listTitleButton.add(zoom0);
        listTitleButton.setFont(new Font("", Font.PLAIN, (int) (listTitleButton.getHeight() / 2.5)));

        for (int i = table_row_start_num; i < table_row_finish_num; i++) {

            new Lg(this, "imgButton[j].getWidth()==" + imgButton[i].getWidth());
            Zoom zoom = new Zoom(new ImageIcon("./resource/img/" + folderName + "_サムネイル/" + listURL[i]), 0, 0,
                            imgButton[i].getWidth(), imgButton[i].getHeight());
            imgButton[i].add(zoom);

            Zoom zoom2 = new Zoom(new ImageIcon("./resource/img/list/play.jpg"), 0, 0, playButton[i].getWidth(),
                            playButton[i].getHeight());
            playButton[i].add(zoom2);

            Zoom zoom3 = new Zoom(new ImageIcon("./resource/img/list/delete.jpg"), 0, 0, deleteButtons[i].getWidth(),
                            deleteButtons[i].getHeight());
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
        listTitleButton.setForeground(Color.PINK);;
        listTitleButton.setBackground(Color.black);
        listTitleButton.setBorder(new LineBorder(Color.white, 1, true));
        listTitleButton.setText(ekigoName);

    }

    /**
     *
     */
    private void make_buttons() {

        /*
         * ボタンの設定
         */
        for (int i = table_row_start_num; i < table_row_finish_num; i++) {

            /*
             * 再生ボタン
             */
            playButton[i] = new JButton();
            playButton[i].setPreferredSize(new Dimension(120, 60));
            playButton[i].setBorder(new LineBorder(Color.white, 1, false));
            playButton[i].setBackground(Color.black);
            playButton[i].setForeground(Color.PINK);;
            playButton[i].addMouseListener(myMouse);
            playButton[i].addKeyListener(myKeyEvent);
            playButton[i].setActionCommand(String.valueOf(i));
            playButton[i].addActionListener(new KoeAction());
            playButton[i].setMargin(new Insets(0, 0, 0, 0));

            /*
             * サムネイルボタン
             */
            imgButton[i] = new JButton();
            imgButton[i].setBorder(new LineBorder(Color.white, 1, false));
            imgButton[i].setBackground(Color.black);
            imgButton[i].addMouseListener(myMouse);
            imgButton[i].addKeyListener(myKeyEvent);
            imgButton[i].setActionCommand(ristNO[i - 1]);
            imgButton[i].setText(ristNO[i - 1]);// テキストを設定
            imgButton[i].addActionListener(this);
            imgButton[i].setMargin(new Insets(0, 0, 0, 0));

            /*
             * テキストエリア
             */
            textArea[i] = new JTextArea();
            textArea[i].addKeyListener(myKeyEvent);
            textArea[i].putClientProperty("caretAspectRatio", Float.valueOf(0.1F));
            textArea[i].setForeground(Color.PINK);;
            textArea[i].setBackground(Color.black);
            textArea[i].setCaretColor(Color.white);
            textArea[i].setBorder(new LineBorder(Color.white, 1, true));
            textArea[i].setText(file_name_list[i]);
            textArea[i].setMargin(new Insets(0, 0, 0, 0));

            /*
             * 削除ボタン
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
     * @param statement
     */
    private void make_new_pict_table(String ekigoName, Statement statement) {

        // 1.テーブルが存在するか確認し、なければ作成する
        boolean isFile = new Read(ekigoName).read();

        if (isFile) {// true:テーブルが存在する

            new Lg(this, "isFile>true: テーブルが存在します");

        } else {

            /*
             * false:テーブルが存在しない
             */
            new Lg(this, "isFile>false: テーブルが存在しません");
            new Write(ekigoName);

            try {

                statement = conn.createStatement();
                statement.executeUpdate("create table " + ekigoName + "_t" + "(NO int," + ekigoName + " text, url text)");

            } catch (SQLException e) {

                e.printStackTrace();

            }

            /*
             * データをDBに挿入
             */
            for (int j = table_row_start_num; j < table_row_finish_num; j++) {

                try {

                    if (file_name_list[j] == null) {// nullの場合

                        statement.executeUpdate("insert into " + ekigo + "_t" + "(NO," + ekigo + ", url)" + "values("
                                        + String.valueOf(j) + ",'" + " " + "','default.jpg');");//

                    }

                    statement.close();

                } catch (Exception e1) {

                    System.out.print(e1.toString());

                    if (e1 instanceof SQLException) {

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

            new Lg(this, "folderName::" + ekigoName);

        }

    }

    /**
     *
     * @return
     * @throws SQLException
     */
    private Statement getSqlStatement() throws SQLException {

        try {

            Class.forName("org.sqlite.JDBC");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

        File file = new File("resource/db/pict");
        String path = file.getAbsolutePath();

        try {

            conn = DriverManager.getConnection("jdbc:sqlite://" + path);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        Statement stmt = conn.createStatement();

        return stmt;

    }

    /**
     *
     * @param ekigoName
     * @param folderName
     */
    private void mainFrameSetting(String ekigoName, String folderName) {

        this.setTitle(ekigoName + " サンプル");
        this.setIconImage(new ImageIcon("./resource/img/tab_img/kurumaisu.jpg").getImage());
        this.setAlwaysOnTop(true);
        this.addKeyListener(myKeyEvent);

        this.ekigo = ekigoName;
        this.folder = folderName;

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rect = env.getMaximumWindowBounds();
        this.setBounds(rect);

    }

    JButton makeEndButton() {

        end_button.setPreferredSize(new Dimension(300, 100));
        end_button.setBackground(Constants.end);
        end_button.addMouseListener(myMouse);
        end_button.addKeyListener(myKeyEvent);
        end_button.addNotify();
        end_button.setFont(new Font("", Font.PLAIN, 30));
        end_button.setBorder(new LineBorder(Constants.endBorder, 7, true));
        end_button.addActionListener(this);
        end_button.setActionCommand("end");
        return end_button;

    };

    public void selectTable(String fileName) {

        try {

            Statement stmt = conn.createStatement();

            new Lg(this, "select " + "*" + " from " + fileName + "_t");
            ResultSet executequery_resultset = stmt.executeQuery("select " + "*" + " from " + fileName + "_t");

            /*
             * データの表示
             */
            System.out.println("----------------------------------------");
            System.out.print("NO. | ");
            System.out.print("タイトル | ");
            System.out.println("サムネイル");

            int j = 0;

            while (executequery_resultset.next()) {

                int num = executequery_resultset.getInt("NO");

                ristNO[j] = new String();
                ristNO[j] = String.valueOf(num);

                fileNameList = executequery_resultset.getString(fileName);

                file_name_list[executequery_resultset.getRow()] = new String();
                file_name_list[executequery_resultset.getRow()] = fileNameList;

                System.out.println("rs.getRow()==" + executequery_resultset.getRow());

                jpgFileName = executequery_resultset.getString("url");

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
             * サムネイルの更新
             */
            if (cmd.equals(imgButton[i].getText())) {

                JFileChooser filechooser = new JFileChooser("./resource/img/" + folder + "_サムネイル/");

                new Lg(this, "------------------------------------------ImgAction>URL==" + "./resource/img/" + folder
                                + "_サムネイル/");

                int selected = filechooser.showOpenDialog(this);

                if (selected == JFileChooser.APPROVE_OPTION) {

                    /*
                     * SQLの更新
                     */
                    try {

                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate("update " + ekigo + "_t set " + " url" + "= '"
                                        + filechooser.getSelectedFile().getName() + "' where " + " NO" + "="
                                        + imgButton[i].getText() + ";");

                        imgButton[i].removeAll();
                        imgButton[i].add(new Zoom(
                                        new ImageIcon("./resource/img/" + folder + "_サムネイル/"
                                                        + filechooser.getSelectedFile().getName()),
                                        0, 0, imgButton[i].getWidth(), imgButton[i].getHeight()));
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

            if (textArea[Integer.parseInt(cmd)].getText().equals(" ")
                            | textArea[Integer.parseInt(cmd)].getText().equals("")
                            | textArea[Integer.parseInt(cmd)].getText().equals("　")) {

            } else {

                Koe.oto(textArea[Integer.parseInt(cmd)].getText().replaceAll(" ", ""));

            }

        }
    }

    class Deleat_ActionEvent implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String cmd = e.getActionCommand();

            for (int i = table_row_start_num; i < table_row_finish_num; i++) {

                if (false == textArea[i].getText().equals(" ")) {

                    if (textArea[i].getText().equals(cmd)) {

                        try {

                            Statement stmt = conn.createStatement();

                            stmt.executeUpdate("update " + ekigo + "_t set " + ekigo + "='" + "" + "' where " + "no"
                                            + "=" + String.valueOf(i) + ";");

                            stmt.executeUpdate("insert into " + ekigo + "_t" + "(NO," + ekigo + ")" + "values("
                                            + String.valueOf(ristNO[i - 1]) + ",'" + " " + "');");
                            stmt.executeUpdate("update " + ekigo + "_t set " + "url" + "= '" + "default.jpg"
                                            + "' where " + " NO" + "=" + imgButton[i].getText() + ";");
                            stmt.close();

                            setVisible(false);
                            new List(ekigo, folder);

                        } catch (SQLException e1) {

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

                    if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i))
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Slash")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")) {

                        keyPressReleaseAvailable = false;

                        KaiwaMain.keyPressAvailable = false;

                        new Lg(this, "moveEkigo>keyPressed == " + KeyEvent.getKeyText(e.getKeyChar()));
                        Koe.oto("再生");
                        update_sql_data();
                        break;

                    } else {

                    }

                }

            }

        }

        public void keyReleased(KeyEvent e) {

            if (keyPressReleaseAvailable == false) {

                for (int i = 0; i < 10; i++) {

                    if (KeyEvent.getKeyText(e.getKeyChar()).equals(String.valueOf(i))
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Period")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Enter")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2b")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Unknown keyCode: 0x2a")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Slash")
                                    || KeyEvent.getKeyText(e.getKeyChar()).equals("Minus")
                    ) {

                        keyPressReleaseAvailable = true;
                        keyEventCout++;
                        new Lg(this, "moveEkigo>keyReleased == " + KeyEvent.getKeyText(e.getKeyChar()));
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

                stmt.executeUpdate("update " + ekigo + "_t set " + ekigo + "='" + textArea[i].getText() + "' where "
                                + "no" + "=" + String.valueOf(i) + ";");

            }

            stmt.close();
            this.dispose();

        } catch (Exception e1) {

            System.out.print(e1.toString());

            if (e1 instanceof SQLException) {

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

                Koe.oto("終了");
                update_sql_data();

            }

        }

        public void mouseEntered(MouseEvent e1) {

            for (int i = table_row_start_num; i < table_row_finish_num; i++) {

                if (e1.getSource() == playButton[i]) {

                    playButton[i].setBorder(new LineBorder(Constants.enteredColor, 3, false));

                } else if (e1.getSource() == imgButton[i]) {

                    imgButton[i].setBorder(new LineBorder(Constants.enteredColor, 3, false));

                } else if (e1.getSource() == deleteButtons[i]) {

                    deleteButtons[i].setBorder(new LineBorder(Constants.enteredColor, 3, false));

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

            new List("サンプル", "tab7");

        } catch (HeadlessException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }
}
