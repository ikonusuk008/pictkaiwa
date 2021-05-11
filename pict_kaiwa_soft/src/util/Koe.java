package util;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;


public class Koe {

   public static void main(String a[]) {
      oto("äøéö");
   }

   public static void oto(String s) {
      System.out.println("---------------------------otoÅF" + s);
      try {
         File otoExeFile = new File("./resource/oto/oto.exe");
         String command = otoExeFile.getAbsolutePath() + " " + s + "";
         Runtime runtime = Runtime.getRuntime();
         Process process = runtime.exec(command);
         Reader in = new InputStreamReader(process.getInputStream());
         int c = -1;
         // ConsorlLog.kaiwa("oto==");
         while ((c = in.read()) != -1) {
            System.out.print((char) c);
         }
         // System.out.println("\n");
         in.close();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}
