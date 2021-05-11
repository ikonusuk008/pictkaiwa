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

		// �N���`�F�b�N
		final FileOutputStream fos = new FileOutputStream(new File("a"));
		final FileChannel fc = fos.getChannel();
		final FileLock lock = fc.tryLock();

		if (lock == null) {
			// ���ɋN������Ă���̂ŏI������
			try {
				"hello".charAt(-1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "�G�L����b�\�t�g�͂��łɋN�����Ă��܂��B");
			}
			return;
		}

		// ���b�N�J��������o�^
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {
				if (lock != null && lock.isValid()) {
					try {
						lock.release();
					} catch (IOException e) {
						// TODO �����������ꂽ catch �u���b�N
						e.printStackTrace();
					}
				}
				try {
					fc.close();
				} catch (IOException e) {
					// TODO �����������ꂽ catch �u���b�N
					e.printStackTrace();
				}
				try {
					fos.close();
				} catch (IOException e) {
					// TODO �����������ꂽ catch �u���b�N
					e.printStackTrace();
				}
			}
		});

		// �����𑱍s
		JFrame frame = new JFrame("�^�C�g��");
		frame.setBounds(100, 100, 200, 160);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}