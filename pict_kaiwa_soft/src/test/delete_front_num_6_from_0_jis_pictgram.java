package test;

import java.io.*;

public class delete_front_num_6_from_0_jis_pictgram {
	public static void main(String[] args) throws IOException {
		// (1)Fileオブジェクトの生成
		File directory0 = new File("C:/Users/yamamoto/Desktop/フリーソフト/JIS絵記号（ひらがな）/文化・社会");

		String[] directory_list0 = directory0.list();

		/*
		 * サブディレクトリの数だけ指定する。（１回１回）
		 */
		File directory1 = new File(directory0.getAbsolutePath()+"/"+directory_list0[0]);

		//System.out.println("格納ディレクトリ名：" + directory1.getParent());
		System.out.println("PATH名：" + directory1.getAbsolutePath());

		// (9)ディレクトリ内のファイル・ディレクトリ一覧を取得
		String[] directory_list = directory1.list();
		for (int i = 0; i < directory_list.length; i++) {
			// (10)ファイル・ディレクトリ一覧の表示
			System.out.println("ディレクトリの中身：" + directory_list[i]);

			 //変更前ファイル名
		      File fileA = new File(directory1.getAbsolutePath()+"/"+directory_list[i]);

		      //変更後のファイル名
		      File fileB = new File(directory1.getAbsolutePath()+"/"+directory_list[i].substring(6,directory_list[i].length()));


		      if(fileA.renameTo(fileB)){
		         //ファイル名変更成功
		         System.out.println("ファイル名変更成功");
		      }else{
		         //ファイル名変更失敗
		         System.out.println("ファイル名変更失敗");
		      }
		}
	}
}