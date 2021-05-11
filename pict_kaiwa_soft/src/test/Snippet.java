package test;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Snippet {

	public static void main(String a[]){
		PlayWave("Windows Information Bar.wav");
	}

	public static void PlayWave(String fileName) {
		final int EXTERNAL_BUFFER_SIZE = 128000;
		try {
			// File�N���X�̃C���X�^���X�𐶐�
			File soundFile = new File(fileName);
			// �I�[�f�B�I���̓X�g���[�����擾���܂�
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			// �I�[�f�B�I�`�����擾���܂�
			AudioFormat audioFormat = audioInputStream.getFormat();

			// �f�[�^���C���̏��I�u�W�F�N�g�𐶐����܂�
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			// �w�肳�ꂽ�f�[�^���C�����Ɉ�v���郉�C�����擾���܂�
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			// �w�肳�ꂽ�I�[�f�B�I�`���Ń��C�����J���܂�
			line.open(audioFormat);
			// ���C���ł̃f�[�^���o�͂��\�ɂ��܂�
			line.start();

			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
			while (nBytesRead != -1) {
				// �I�[�f�B�I�X�g���[������f�[�^��ǂݍ��݂܂�
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0) {
					// �I�[�f�B�I�f�[�^���~�L�T�[�ɏ������݂܂�
					int nBytesWritten = line.write(abData, 0, nBytesRead);
				}
			}
			// ���C������L���[�ɓ����Ă���f�[�^��r�o���܂�
			line.drain();
			// ���C������܂�
			line.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}