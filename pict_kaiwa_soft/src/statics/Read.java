package statics;

import java.io.BufferedReader;
import java.io.FileReader;

import test_tool.l;

public class Read {

	boolean isFile = false;
	String file_name[] = new String[100];
	String fileName = null;
	int i = 0;

	public Read(String fileName) {
		this.fileName = fileName;
	}

	public boolean read() {
		try {
			BufferedReader b = new BufferedReader(new FileReader("./resource/table_list.txt"));
			String one_line_pict_name;
			while ((one_line_pict_name = b.readLine()) != null) {
				file_name[i] = one_line_pict_name;

				if (file_name[i].equals(fileName)) {
					// �G�L���t�@�C�����Ɠ�����s�����݂����B
					isFile = true;
					new l(this, "break");
					break;
				}
				new l(this, "i==" + i);
				i++;
			}
			new l(this, "table_list.txt�ǂݍ��݁F����");
		} catch (Exception e) {
			new l(this, "table_list.txt�ǂݍ��݁F���s");
		}
		return isFile;
	}

	public static void main(String[] args) {
		new Read("test");
	}
}
