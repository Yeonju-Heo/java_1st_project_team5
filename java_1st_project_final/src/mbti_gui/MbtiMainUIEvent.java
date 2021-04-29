package mbti_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import mbti_system.MbtiMgmSystem;

public class MbtiMainUIEvent implements ActionListener {
	// Field
	MbtiMainUI main;
	AdminMainUI adminmain;

	public static final int ADMIN = 0;
	public static final int USER = 1;

	// Constructor
	public MbtiMainUIEvent(MbtiMainUI main) {
		this.main = main;
	}

	// Method
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == main.btn_login || obj == main.id_tf || obj == main.pw_tf) {
			loginUI_proc();
		} else if (obj == main.btn_join) {
			new JoinUI(main);
		} else if (obj == main.buttonlist.get(0)) {
			new MainUI(main);
			main.secondFrame.setTitle("MBTI WORLD");
		} else if (obj == main.buttonlist.get(1)) {
			new CreateUI(main);
			main.secondFrame.setTitle("ĳ���� ����");
		} else if (obj == main.buttonlist.get(2)) {
			new ChatUI(main);
		} else if (obj == main.buttonlist.get(3)) {
			new BoardUI(main);
			main.secondFrame.setTitle("�Խ���");
		} else if (obj == main.buttonlist.get(4)) {
			new MypageUI(main);
			main.secondFrame.setTitle("����������");
		} else if (obj == main.buttonlist.get(5)) {
			int confirm = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?");
			if (confirm == 0) {
				JOptionPane.showMessageDialog(null, "������ �� ������ :)");
				System.exit(0);
			}
		}

	}

	/** �α��� �̺�Ʈ **/
	public void loginUI_proc() {
		if (main.id_tf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���");
			main.id_tf.requestFocus();
		} else if (main.pw_tf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���");
			main.pw_tf.requestFocus();
		} else {  //#############################
			// �α��� üũ : system.loginCheck(���̵�,��й�ȣ)
			int result = main.system.loginCheck(main.id_tf.getText(), main.pw_tf.getText());
			if (result == USER) {
				JOptionPane.showMessageDialog(null, "�α��� �Ǿ����ϴ�.");
				main.firstFrame.dispose();
				main.secondView();
				main.btn_login.setText("LOGOUT");
				MbtiMgmSystem.LOGIN_RESULT = true;
			} else if (result == ADMIN) {
				main.firstFrame.dispose();
				new AdminMainUI();
			} else {
				JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				main.id_tf.setText("");		main.pw_tf.setText("");
			}
		}

	}
}
