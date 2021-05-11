package ekigo_tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class prop {

	Properties prop = new Properties();

	public void setSpeed(String s) throws IOException, FileNotFoundException {
		prop.load(new FileInputStream("seting.properties"));
		prop.setProperty("seting.speed", s);
		prop.store(new FileOutputStream("seting.properties"), "seting");
	}

	void setRoop(String s1) throws IOException, FileNotFoundException {
		// プロパティファイルからキーと値のリストを読み込みます
		prop.load(new FileInputStream("seting.properties"));
		prop.setProperty("seting.roop", s1);
		prop.store(new FileOutputStream("seting.properties"), "seting");
	}

	void setList(String s1) throws IOException, FileNotFoundException {
		// プロパティファイルからキーと値のリストを読み込みます
		prop.load(new FileInputStream("seting.properties"));
		prop.setProperty("seting.list", s1);
		prop.store(new FileOutputStream("seting.properties"), "seting");
	}

	void setGyou(String Gyou) throws IOException, FileNotFoundException {
		// プロパティファイルからキーと値のリストを読み込みます
		prop.load(new FileInputStream("seting.properties"));
		prop.setProperty("seting.gyou", Gyou);
		prop.store(new FileOutputStream("seting.properties"), "seting");
	}

	void setMokujiIcon(String mokujiIcon[]) throws IOException, FileNotFoundException {
		prop.load(new FileInputStream("seting.properties"));
		for (int i = 1; i < 10; i++) {
			prop.setProperty("seting.mokujiIcon" + String.valueOf(i), mokujiIcon[i]);
			System.out.println("prop>mokujiIcon[" + i + "]:" + mokujiIcon[i]);
		}
		prop.store(new FileOutputStream("seting.properties"), "seting");
	}

	void setMokujiBool(Boolean mokujiBool[]) throws IOException, FileNotFoundException {
		prop.load(new FileInputStream("seting.properties"));
		for (int i = 1; i < 10; i++) {

			prop.setProperty("seting.mokujiBool" + String.valueOf(i), mokujiBool[i].toString());
			System.out.println("prop>mokujiBool[" + i + "]:" + mokujiBool[i].toString());
		}
		prop.store(new FileOutputStream("seting.properties"), "seting");
	}

	public static Properties getPict() throws IOException, FileNotFoundException {
		Properties prop = new Properties();
		// プロパティファイルからキーと値のリストを読み込みます
		prop.load(new FileInputStream("seting.properties"));
		// "javahello.message"に設定されている値を取得します
		//String hen = prop.getProperty("seting.hen");
		return prop;
	}
}
