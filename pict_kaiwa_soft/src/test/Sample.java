package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Sample {

	public static void main(String[] args) throws IOException {

		// 起動チェック
		final FileOutputStream fos = new FileOutputStream(new File("a"));
		final FileChannel fc = fos.getChannel();
		final FileLock lock = fc.tryLock();

		if (lock == null) {
			// 既に起動されているので終了する
			try {
				"hello".charAt(-1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "絵記号会話ソフトはすでに起動しています。");
			}
			return;
		}

		// ロック開放処理を登録
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {
				if (lock != null && lock.isValid()) {
					try {
						lock.release();
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
				try {
					fc.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				try {
					fos.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		});

		// 処理を続行
		JFrame frame = new JFrame("タイトル");
		frame.setBounds(100, 100, 200, 160);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}