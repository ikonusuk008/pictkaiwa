package main_frame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import test_tool.l;

public class Prop {

	Properties prop = new Properties();

	public Prop() {
		super();
	}

	/***** 設定タブ（1） *****/
	// 選択モード（ON・OFF）
	public void setRoop(String s1) throws IOException, FileNotFoundException {

		// プロパティファイルからキーと値のリストを読み込みます
		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.roop", s1);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// 行数（3・4）
	public void setGyou(String Gyou) throws IOException, FileNotFoundException {

		// プロパティファイルからキーと値のリストを読み込みます
		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.gyou", Gyou);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// 各タブの表示（体調～アプリ）
	public void setTabBool(Boolean mokujiBool[]) throws IOException, FileNotFoundException {

		prop.load(new FileInputStream("./resource/seting.properties"));
		for (int i = 1; i < 10; i++) {

			prop.setProperty("seting.mokujiBool" + String.valueOf(i), mokujiBool[i].toString());

		}
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// カテゴリー名（体調～アプリ）
	public void setCategoryName(String category_name[]) throws IOException, FileNotFoundException {

		prop.load(new FileInputStream("./resource/seting.properties"));
		for (int i =0; i < 10; i++) {
			prop.setProperty("seting.category_name" + String.valueOf(i), category_name[i]);

		}
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// カテゴリー画像名（体調～アプリ）
	public void setCategoryImgName(String category_img_name[]) throws IOException, FileNotFoundException {
		prop.load(new FileInputStream("./resource/seting.properties"));
		for (int i = 1; i < 10; i++) {
			prop.setProperty("seting.category_img_name" + String.valueOf(i), category_img_name[i]);
		}
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// カテゴリー画像名（体調～アプリ）
	public void setCategoryImgName_(String category_img_name, int i) throws IOException, FileNotFoundException {
		prop.load(new FileInputStream("./resource/seting.properties"));
	   new l(this,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+category_img_name);
	   new l(this,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+i);
		prop.setProperty("seting.category_img_name" + String.valueOf(i), category_img_name);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	/***** 設定タブ（2）途中設定可能 *****/
	// 詳細画面の表示方法（リスト・絵記号・非表示）
	public void setList(String s1) throws IOException, FileNotFoundException {

		// プロパティファイルからキーと値のリストを読み込みます

		System.out.println("Prop()>setList(String s1)>s1==" + s1);
		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.list", s1);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");

		// 直接変数に設定：ソフト起動時
		Kaiwa.gyouIs = s1;
	}

	// ループ速度
	public void setSpeed(String s) throws IOException, FileNotFoundException {

		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.speed", s);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");

		// 直接変数に設定：ソフト起動時
		if (s.equals("1.5")) {
			Kaiwa.speed = 1500;
		} else if (s.equals("2")) {
			Kaiwa.speed = 2000;
		} else if (s.equals("2.5")) {
			Kaiwa.speed = 2500;
		} else if (s.equals("3")) {
			Kaiwa.speed = 3000;
		} else if (s.equals("3.5")) {
			Kaiwa.speed = 3500;
		}
	}

	public Properties getPict() throws IOException, FileNotFoundException {

		Properties prop = new Properties();
		prop.load(new FileInputStream("./resource/seting.properties"));
		return prop;
	}
}
