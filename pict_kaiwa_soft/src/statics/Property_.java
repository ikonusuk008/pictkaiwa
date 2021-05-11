package statics;

import java.io.FileNotFoundException;
import java.io.IOException;

import main_frame.Prop;
import test_tool.l;

public class Property_ {

	public static String mokujiTex[] = new String[10];
	public static String mokujiBool[] = new String[10];

	Prop prop = null;// グローバルでインスタンス化は出来ない。

	public Property_() throws FileNotFoundException, IOException {
		// TODO 自動生成されたコンストラクター・スタブ
		prop = new Prop();

		// 目次項目
		for (int j = 0; j < 10; j++) {
			mokujiTex[j] = new String();
			mokujiTex[j] = prop.getPict().getProperty("seting.mokujiIcon" + String.valueOf(j));

			mokujiBool[j] = new String();
			mokujiBool[j] = prop.getPict().getProperty("seting.mokujiBool" + String.valueOf(j));
			//bool[j] = Boolean.parseBoolean(mokujiBool[j]);

			new l(this, "mokujiTex[" + j + "] ： mokujiBool[" + j + "] == " + mokujiTex[j] + " ： " + mokujiBool[j]);
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		new Property_();
	}
}
