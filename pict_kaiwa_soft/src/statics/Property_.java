package statics;

import java.io.FileNotFoundException;
import java.io.IOException;

import main_frame.Prop;
import test_tool.l;

public class Property_ {

	public static String mokujiTex[] = new String[10];
	public static String mokujiBool[] = new String[10];

	Prop prop = null;// �O���[�o���ŃC���X�^���X���͏o���Ȃ��B

	public Property_() throws FileNotFoundException, IOException {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		prop = new Prop();

		// �ڎ�����
		for (int j = 0; j < 10; j++) {
			mokujiTex[j] = new String();
			mokujiTex[j] = prop.getPict().getProperty("seting.mokujiIcon" + String.valueOf(j));

			mokujiBool[j] = new String();
			mokujiBool[j] = prop.getPict().getProperty("seting.mokujiBool" + String.valueOf(j));
			//bool[j] = Boolean.parseBoolean(mokujiBool[j]);

			new l(this, "mokujiTex[" + j + "] �F mokujiBool[" + j + "] == " + mokujiTex[j] + " �F " + mokujiBool[j]);
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		new Property_();
	}
}
