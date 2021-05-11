package test;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Key_event_tab_and_kakutyou_tenkey_s_botton_get_string_deta implements KeyListener {

	public static void main(String a[]) {
		new Key_event_tab_and_kakutyou_tenkey_s_botton_get_string_deta();
	}

	String typekey;
	public Key_event_tab_and_kakutyou_tenkey_s_botton_get_string_deta() {
		typekey = "";
		///all_keys_on();

		JFrame frame = new JFrame();
		frame.setFocusTraversalKeysEnabled(false);
		frame.addKeyListener(this);
		frame.setVisible(true);
	}

	public boolean all_keys_on() {
		return numlock(true) && capslock(true) && scrolllock(true);
	}

	public boolean all_keys_off() {
		return numlock(false) && capslock(false) && scrolllock(false);
	}

	public boolean numlock(boolean b) {
		Toolkit tool = Toolkit.getDefaultToolkit();
		try {
			tool.setLockingKeyState(KeyEvent.VK_NUM_LOCK, b);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean capslock(boolean b) {
		Toolkit tool = Toolkit.getDefaultToolkit();
		try {
			tool.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, b);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean scrolllock(boolean b) {
		Toolkit tool = Toolkit.getDefaultToolkit();
		try {
			tool.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK, b);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void keyTyped(KeyEvent e) {
		char keyChar = e.getKeyChar();
		int keycode = e.getKeyCode();
		e.setKeyCode(1);


		System.out.println("keyChar==" + keyChar);
		System.out.println("keycode==" + keycode);
		System.out.println("e.paramString()==" + e.paramString());

		numlock(true);
	}

	@Override
	public void keyPressed(KeyEvent keyevent) {
	}

	@Override
	public void keyReleased(KeyEvent keyevent) {
	}
}
