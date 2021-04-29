package mbti_gui;

import java.io.ObjectInputStream;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import mbti_vo.MessageVO;

public class ClientThread extends Thread{
		ObjectInputStream ois;
		JTextArea content;
		JList user_list;
		JLabel user_count;
		
		public ClientThread(ObjectInputStream ois, 
				JTextArea content, JList userlist, JLabel usercount) {
			
			try {
				this.ois = ois;
				this.content = content;
				this.user_list = userlist;
				this.user_count = usercount;
				
			} catch (Exception e) {
			}
		
		}
		
		@Override
		public void run() {
			try {
				
				//������ �����۾�
				while(true) {
					MessageVO vo = (MessageVO)ois.readObject();
					//messagevo�κ��� �޾ƿ� ������ ui�� content�� �Է�
					content.append(vo.getMsg() + "\n");
					//������ ����Ʈ
					user_list.setListData(vo.getUser_list());
					user_count.setText(String.valueOf(vo.getUser_list().size()) + "�� ������");//��� �����ߴ���
					
				}
			} catch (Exception e) {
			}
		}

}
