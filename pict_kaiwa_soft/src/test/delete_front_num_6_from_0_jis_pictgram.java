package test;

import java.io.*;

public class delete_front_num_6_from_0_jis_pictgram {
	public static void main(String[] args) throws IOException {
		// (1)File�I�u�W�F�N�g�̐���
		File directory0 = new File("C:/Users/yamamoto/Desktop/�t���[�\�t�g/JIS�G�L���i�Ђ炪�ȁj/�����E�Љ�");

		String[] directory_list0 = directory0.list();

		/*
		 * �T�u�f�B���N�g���̐������w�肷��B�i�P��P��j
		 */
		File directory1 = new File(directory0.getAbsolutePath()+"/"+directory_list0[0]);

		//System.out.println("�i�[�f�B���N�g�����F" + directory1.getParent());
		System.out.println("PATH���F" + directory1.getAbsolutePath());

		// (9)�f�B���N�g�����̃t�@�C���E�f�B���N�g���ꗗ���擾
		String[] directory_list = directory1.list();
		for (int i = 0; i < directory_list.length; i++) {
			// (10)�t�@�C���E�f�B���N�g���ꗗ�̕\��
			System.out.println("�f�B���N�g���̒��g�F" + directory_list[i]);

			 //�ύX�O�t�@�C����
		      File fileA = new File(directory1.getAbsolutePath()+"/"+directory_list[i]);

		      //�ύX��̃t�@�C����
		      File fileB = new File(directory1.getAbsolutePath()+"/"+directory_list[i].substring(6,directory_list[i].length()));


		      if(fileA.renameTo(fileB)){
		         //�t�@�C�����ύX����
		         System.out.println("�t�@�C�����ύX����");
		      }else{
		         //�t�@�C�����ύX���s
		         System.out.println("�t�@�C�����ύX���s");
		      }
		}
	}
}