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

            new Lg(this, file.getName() + " ‚É‘‚«‚İy¬Œ÷z‚µ‚Ü‚µ‚½B");
         } else {
            new Lg(this, file.getName() + " ‚É‘‚«‚İy¸”sz‚µ‚Ü‚µ‚½B");
         }
      } catch (IOException e) {
         new Lg(this, file.getName() + " ‚É‘‚«‚İy¸”sz‚µ‚Ü‚µ‚½B(IOException)");
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
