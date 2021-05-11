package util;

public class StringFormat {

   String massage;
   int num;

   public StringFormat(String massage, int num) {
      stringFormat(massage, num);
   }

   public static String stringFormat(String massage, int num) {

      num -= massage.length();
      for (int i = 0; i < num; i++) {
         massage += "@";
      }

      // System.out.println("massage == "+massage);
      return massage;
   }

   public static void main(String[] args) {
      new StringFormat("test", 20);
   }

}
