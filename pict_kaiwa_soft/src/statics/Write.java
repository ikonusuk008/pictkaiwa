package statics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import test_tool.l;

public class Write {
	File file=null;
	public Write(String fileName) {
		try {
			file = new File("./resource/table_list.txt");

			if (checkBeforeWritefile(file)) {
				FileWriter filewriter = new FileWriter(file, true);
				filewriter.write(fileName + "\r\n");
				filewriter.close();

				new l(this,file.getName()+" に書き込み【成功】しました。");
			} else {
				new l(this,file.getName()+" に書き込み【失敗】しました。");
			}
		} catch (IOException e) {
			new l(this, file.getName()+" に書き込み【失敗】しました。(IOException)" );
		}
	}

	private static boolean checkBeforeWritefile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canWrite()) {
				return true;
			}
		}
		return false;
	}
}
