package main_frame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import test_tool.l;

public class Prop {

	Properties prop = new Properties();

	public Prop() {
		super();
	}

	/***** �ݒ�^�u�i1�j *****/
	// �I�����[�h�iON�EOFF�j
	public void setRoop(String s1) throws IOException, FileNotFoundException {

		// �v���p�e�B�t�@�C������L�[�ƒl�̃��X�g��ǂݍ��݂܂�
		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.roop", s1);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// �s���i3�E4�j
	public void setGyou(String Gyou) throws IOException, FileNotFoundException {

		// �v���p�e�B�t�@�C������L�[�ƒl�̃��X�g��ǂݍ��݂܂�
		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.gyou", Gyou);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// �e�^�u�̕\���i�̒��`�A�v���j
	public void setTabBool(Boolean mokujiBool[]) throws IOException, FileNotFoundException {

		prop.load(new FileInputStream("./resource/seting.properties"));
		for (int i = 1; i < 10; i++) {

			prop.setProperty("seting.mokujiBool" + String.valueOf(i), mokujiBool[i].toString());

		}
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// �J�e�S���[���i�̒��`�A�v���j
	public void setCategoryName(String category_name[]) throws IOException, FileNotFoundException {

		prop.load(new FileInputStream("./resource/seting.properties"));
		for (int i =0; i < 10; i++) {
			prop.setProperty("seting.category_name" + String.valueOf(i), category_name[i]);

		}
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// �J�e�S���[�摜���i�̒��`�A�v���j
	public void setCategoryImgName(String category_img_name[]) throws IOException, FileNotFoundException {
		prop.load(new FileInputStream("./resource/seting.properties"));
		for (int i = 1; i < 10; i++) {
			prop.setProperty("seting.category_img_name" + String.valueOf(i), category_img_name[i]);
		}
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	// �J�e�S���[�摜���i�̒��`�A�v���j
	public void setCategoryImgName_(String category_img_name, int i) throws IOException, FileNotFoundException {
		prop.load(new FileInputStream("./resource/seting.properties"));
	   new l(this,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+category_img_name);
	   new l(this,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+i);
		prop.setProperty("seting.category_img_name" + String.valueOf(i), category_img_name);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");
	}

	/***** �ݒ�^�u�i2�j�r���ݒ�\ *****/
	// �ڍ׉�ʂ̕\�����@�i���X�g�E�G�L���E��\���j
	public void setList(String s1) throws IOException, FileNotFoundException {

		// �v���p�e�B�t�@�C������L�[�ƒl�̃��X�g��ǂݍ��݂܂�

		System.out.println("Prop()>setList(String s1)>s1==" + s1);
		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.list", s1);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");

		// ���ڕϐ��ɐݒ�F�\�t�g�N����
		Kaiwa.gyouIs = s1;
	}

	// ���[�v���x
	public void setSpeed(String s) throws IOException, FileNotFoundException {

		prop.load(new FileInputStream("./resource/seting.properties"));
		prop.setProperty("seting.speed", s);
		prop.store(new FileOutputStream("./resource/seting.properties"), "seting");

		// ���ڕϐ��ɐݒ�F�\�t�g�N����
		if (s.equals("1.5")) {
			Kaiwa.speed = 1500;
		} else if (s.equals("2")) {
			Kaiwa.speed = 2000;
		} else if (s.equals("2.5")) {
			Kaiwa.speed = 2500;
		} else if (s.equals("3")) {
			Kaiwa.speed = 3000;
		} else if (s.equals("3.5")) {
			Kaiwa.speed = 3500;
		}
	}

	public Properties getPict() throws IOException, FileNotFoundException {

		Properties prop = new Properties();
		prop.load(new FileInputStream("./resource/seting.properties"));
		return prop;
	}
}
