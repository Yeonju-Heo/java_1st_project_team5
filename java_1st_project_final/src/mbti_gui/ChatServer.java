package mbti_gui;



import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import mbti_dao.MbtiDAO;
import mbti_vo.MessageVO;

public class ChatServer {
	//Field
	ServerSocket server;
	static ArrayList<ObjectOutputStream> list = new ArrayList<ObjectOutputStream>();
	static Vector<String> user_list = new Vector<String>();
	
	
	//Constructor
	public ChatServer() {
		try {
			server = new ServerSocket(9000);
			System.out.println("��������");
			
			while(true) {
				Socket s = server.accept();
				ServerThread st = new ServerThread(s);
				st.start();
				
				list.add(st.oos);
				System.out.println("Ŭ���̾�Ʈ ����");
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	//Method
	synchronized static public void broadcasting(MessageVO vo) {
		try {
			if(vo.getStatus() == MessageVO.CONNECT) {
//				user_list.add(vo.getName());
				user_list.add(vo.getName() + "(" + vo.getMbti() + ")");
				Vector<String> copy_list = (Vector<String>)user_list.clone();
				vo.setUser_list(copy_list);
				vo.setMsg("---------------->>" + 
						vo.getName() + "(" + vo.getMbti() + ")" + "���� �����ϼ̽��ϴ�.");
				
			}else if(vo.getStatus() == MessageVO.TALK) {
				Vector<String> copy_list = (Vector<String>)user_list.clone();
				vo.setUser_list(copy_list);
				vo.setMsg(vo.getName() + "(" + vo.getMbti() + ")" + " �� " + vo.getMsg());
				
			}else if(vo.getStatus() == MessageVO.EXIT) {
//				int index = user_list.indexOf(vo.getName());
				int index = user_list.indexOf(vo.getName() + "(" + vo.getMbti() + ")") ;
				System.out.println(index);
				ObjectOutputStream remove = (ObjectOutputStream)list.get(index);
				Iterator<ObjectOutputStream> ie = list.iterator();
				while(ie.hasNext()) {
					if(ie.next() == remove) ie.remove();
				}
				
				user_list.remove(vo.getName() + "(" + vo.getMbti() + ")");
				Vector<String> copy_list = (Vector<String>)user_list.clone();
				vo.setUser_list(copy_list);
				vo.setMsg("---------------->>" + 
						vo.getName() + "(" + vo.getMbti() + ")" + "���� �����̽��ϴ�.");
				
			}
			
			for(ObjectOutputStream oos : list) {
				
				oos.writeObject(vo);
			}
			
						
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

}
