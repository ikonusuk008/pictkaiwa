package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Write {
   File file = null;

   public Write(String fileName) {
      try {
         file = new File("./resource/table_list.txt");

         if (checkBeforeWritefile(file)) {
            FileWriter filewriter = new FileWriter(file, true);
            filewriter.write(fileName + "\r\n");
            filewriter.close();

            new Lg(this, file.getName() + " に書き込み【成功】しました。");
         } else {
            new Lg(this, file.getName() + " に書き込み【失敗】しました。");
         }
      } catch (IOException e) {
         new Lg(this, file.getName() + " に書き込み【失敗】しました。(IOException)");
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
