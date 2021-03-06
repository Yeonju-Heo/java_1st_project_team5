package mbti_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import mbti_vo.MbtiVO;
import mbti_vo.UserVO;

public class MypageUI implements ActionListener{
	//Field
	MbtiMainUI main;
	JPanel closet_panel,char_closet_panel, char_panel, inform_panel,info_mbti_panel, info_text_panel,info_panel, mbti_panel, mbti_label_panel, id_panel, pass_panel,
			point_panel, date_panel,btn_panel,update_panel, cancel_account_panel,content_panel,logout_panel;
	String[] infolist = {"mbti","mbti설명","아이디","비밀번호","포인트","가입일"};
	JLabel char_img;
	JButton btn_update_info, btn_delete_account, btn_closet, btn_logout;
	JPasswordField pwd;
	ImageIcon character, logout;
	JLabel mbti, mbti_label, id_label, id_detail_label, pwd_label, point_label, point_detail_label,
			date_label, date_detail_label;
	
	UserVO user,userch;
	MbtiVO mbtivo;
	
	//Constructor
	public MypageUI(MbtiMainUI main) {
		this.main = main;
		init();
	}
	
	//Method
	public void init() {
		main.switch_panel(MbtiMainUI.MYPAGE);
		user = main.system.searchUser(main.id_tf.getText());
		mbtivo = main.system.getMbti(user);
		
		char_closet_panel = new JPanel(new GridLayout(1,2,0,50));
		closet_panel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		btn_closet = new JButton("내 옷장");
		btn_closet.setFont(Commons.getFont());
		closet_panel.add(btn_closet);
		char_panel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,50));
//		character = new ImageIcon("images/character.png");
		
		////////////////
		userch = main.system.getUserChar(main.id_tf.getText());
		character = new ImageIcon(userch.getU_char());
		/////////////////
		
		
		char_img = new JLabel(character);
		char_panel.add(char_img);
		char_closet_panel.add(closet_panel);
		char_closet_panel.add(char_panel);
		
		
		info_mbti_panel = new JPanel(new GridLayout(2,1));
		mbti_panel = new JPanel(new FlowLayout(FlowLayout.CENTER,100,10));
		mbti_label_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mbti = new JLabel(user.getU_mbti());
		mbti_panel.add(mbti);
		mbti_label = new JLabel(mbtivo.getMbti_label());
		mbti_label.setFont(Commons.getFont());
		mbti_label_panel.add(mbti_label);
		info_mbti_panel.add(mbti_panel);
		info_mbti_panel.add(mbti_label_panel);
		
		info_text_panel = new JPanel(new GridLayout(4,1));
		id_panel = new JPanel(new GridLayout(1,2));
		id_label = new JLabel("아이디");
		id_detail_label = new JLabel(user.getU_id());
		id_label.setFont(Commons.getFont());
		id_detail_label.setFont(Commons.getFont());
		id_panel.add(id_label);
		id_panel.add(id_detail_label);
		pass_panel = new JPanel(new GridLayout(1,2));
		pwd_label = new JLabel("비밀번호");
		pwd_label.setFont(Commons.getFont());
		pwd = new JPasswordField(10);
		pass_panel.add(pwd_label);
		pass_panel.add(pwd);
		point_panel = new JPanel(new GridLayout(1,2));
		point_label = new JLabel("포인트");
		point_label.setFont(Commons.getFont());
		point_detail_label = new JLabel(user.getU_point()+" point");
		point_panel.add(point_label);
		point_panel.add(point_detail_label);
		date_panel = new JPanel(new GridLayout(1,2));
		date_label = new JLabel("가입일");
		date_label.setFont(Commons.getFont());
		date_detail_label = new JLabel(user.getU_date());
		date_panel.add(date_label);
		date_panel.add(date_detail_label);
		info_text_panel.add(id_panel);
		info_text_panel.add(pass_panel);
		info_text_panel.add(point_panel);
		info_text_panel.add(date_panel);
		
		btn_panel = new JPanel(new GridLayout(1,2));
		update_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cancel_account_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btn_update_info = new JButton("정보수정");
		btn_update_info.setFont(Commons.getFont());
		btn_delete_account = new JButton("회원탈퇴");
		btn_delete_account.setFont(Commons.getFont());
		update_panel.add(btn_update_info);
		cancel_account_panel.add(btn_delete_account);
		btn_panel.add(update_panel);
		btn_panel.add(cancel_account_panel);
		
		inform_panel = new JPanel(new FlowLayout(FlowLayout.CENTER,100,150));
		info_panel = new JPanel(new GridLayout(3,1));
		info_panel.add(info_mbti_panel);
		info_panel.add(info_text_panel);
		info_panel.add(btn_panel);
		inform_panel.add(info_panel);
		
		logout_panel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,5));
		logout = new ImageIcon("images/logout.png");
		btn_logout = new JButton("로그아웃",logout);
		btn_logout.setFont(Commons.getFont());
		btn_logout.setHorizontalTextPosition(JButton.LEFT);
		btn_logout.setBorderPainted(false);
		btn_logout.setContentAreaFilled(false);
		btn_logout.setFocusPainted(false);
		btn_logout.addActionListener(this);
		logout_panel.add(btn_logout);
		inform_panel.add(logout_panel);

		main.mypage_panel.setLayout(new BorderLayout());
		main.mypage_panel.setSize(900, 700);
		main.mypage_panel.add(BorderLayout.WEST,char_closet_panel);
		main.mypage_panel.add(BorderLayout.CENTER,inform_panel);
		main.mypage_panel.add(BorderLayout.EAST,logout_panel);
		
		closet_panel.setBackground(Color.white);
		char_closet_panel.setBackground(Color.white);
		char_panel.setBackground(Color.white);
		inform_panel.setBackground(Color.white);
		info_mbti_panel.setBackground(Color.white);
		info_text_panel.setBackground(Color.white);
		info_panel.setBackground(Color.white);
		mbti_panel.setBackground(Color.white);
		mbti_label_panel.setBackground(Color.white);
		id_panel.setBackground(Color.white);
		pass_panel.setBackground(Color.white);
		point_panel.setBackground(Color.white);
		date_panel.setBackground(Color.white);
		btn_panel.setBackground(Color.white);
		update_panel.setBackground(Color.white);
		cancel_account_panel.setBackground(Color.white);
		logout_panel.setBackground(Color.white);
		
		main.content_panel.add(main.mypage_panel);
		main.secondFrame.setVisible(true);
		
		btn_closet.addActionListener(this);
		btn_update_info.addActionListener(this);
		btn_delete_account.addActionListener(this);
		
	}
	
	@Override
	   public void actionPerformed(ActionEvent e) {
	      // TODO Auto-generated method stub
	      Object obj = e.getSource();
	      if(obj == btn_closet) {
	         
	         main.switch_panel(MbtiMainUI.MYPAGE);
	         main.mypage_panel.setLayout(new BorderLayout());
	         main.mypage_panel.setSize(900, 700);
	         main.mypage_panel.add(BorderLayout.CENTER,new ClosetUI(main));
	         
	         main.content_panel.add(main.mypage_panel);
	         main.secondFrame.setTitle("내 옷장");
	         main.secondFrame.setVisible(true);
	         
	         
	         
	      }else if(obj == btn_update_info) {
			System.out.println("정보수정");
			if(passCheck()) {
				int con = JOptionPane.showConfirmDialog(null, Commons.getMsg("회원정보를 수정하시겠습니까?"));
				if(con == 0) {
					if(main.system.updateUser(user, pwd.getText())!=0) {
						JOptionPane.showMessageDialog(null, Commons.getMsg("수정이 완료되었습니다"));
						pwd.setText("");
					}else {
						JOptionPane.showMessageDialog(null, Commons.getMsg("수정에 실패하였습니다"));
					}
				}
			}
		}else if(obj == btn_delete_account) {
			System.out.println("회원탈퇴");
			int con = JOptionPane.showConfirmDialog(null, Commons.getMsg("회원탈퇴를 진행하시겠습니까?"));
			if(con == 0) {
				if(main.system.deleteAdminUser(user.getU_id())) {
					JOptionPane.showMessageDialog(null, Commons.getMsg("탈퇴가 완료되었습니다"));
					main.secondFrame.dispose();
//					new MbtiMainUI().firstView();
					main.firstView();
				}else {
					JOptionPane.showMessageDialog(null, Commons.getMsg("탈퇴에 실패하였습니다"));
				}
			}
		}else if(obj == btn_logout) {
			System.out.println("로그아웃");
			int con = JOptionPane.showConfirmDialog(null, Commons.getMsg("로그아웃 하시겠습니까?"));
			if(con == 0) {
					JOptionPane.showMessageDialog(null, Commons.getMsg("로그아웃이 완료되었습니다"));
					main.secondFrame.dispose();
					new MbtiMainUI();
			}
		}
	
	}
	
	/** 유효성 검사 **/
	public boolean passCheck() {
		boolean result = false;
		
		if(pwd.getText().equals("")) {
			JOptionPane.showMessageDialog(null, Commons.getMsg("비밀번호를 입력해주세요"));
			pwd.requestFocus();
		}else {
			result = true;
		}
		
		return result;
	}
	
}