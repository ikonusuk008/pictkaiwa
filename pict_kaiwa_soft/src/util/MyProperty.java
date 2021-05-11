package util;

import java.io.FileNotFoundException;
import java.io.IOException;

import pictkaiwa.Prop;

public class MyProperty {
   public static String mokujiTex[] = new String[10];
   public static String mokujiBool[] = new String[10];
   Prop prop = null; // �O���[�o���ŃC���X�^���X���͏o���Ȃ��B

   public MyProperty() throws FileNotFoundException, IOException {
      prop = new Prop();
      // Columun mokuji
      for (int j = 0; j < 10; j++) {
         mokujiTex[j] = new String();
         mokujiTex[j] = prop.getPict().getProperty("seting.mokujiIcon" + String.valueOf(j));
         mokujiBool[j] = new String();
         mokujiBool[j] = prop.getPict().getProperty("seting.mokujiBool" + String.valueOf(j));
         // bool[j] = Boolean.parseBoolean(mokujiBool[j]);
         new Lg(this, "mokujiTex[" + j + "] �F mokujiBool[" + j + "] == " + mokujiTex[j] + " �F " + mokujiBool[j]);
      }
   }

   /**
    * @param args
    * @throws IOException
    * @throws FileNotFoundException
    */
   public static void main(String[] args) throws FileNotFoundException, IOException {
      new MyProperty();
   }
}
