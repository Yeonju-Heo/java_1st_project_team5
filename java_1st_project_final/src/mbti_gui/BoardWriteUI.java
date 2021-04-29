package mbti_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import mbti_vo.BoardVO;

public class BoardWriteUI implements ActionListener {
	// Field

	String[] colNames = { "��ȣ", "����", "�ۼ���", "�ۼ���", "��õ/�ݴ�" };
	DefaultTableModel model = new DefaultTableModel(colNames, 0) {
		public boolean isCellEditable(int i, int c) { // ���� ���� ����
			return false;
		}
	};
	Object[] row = new Object[5];
	JTable list_table = new JTable(model);

	MbtiMainUI main;
	JTextField title_tf, img_tf;   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	JTextArea content_ta;
	JButton btn_write, btn_insert, btn_cancel, btn_img, btn_ireset;   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	int count = 1;
	JFileChooser chooser;

	// Constructor
	public BoardWriteUI(MbtiMainUI main) {
		this.main = main;
		init();
	}

	// Method
	/** �� �ۼ� ȭ�� **/
	public void init() {
		main.board_panel.removeAll();
		main.switch_panel(MbtiMainUI.BOARD);
		main.board_panel.setLayout(new BorderLayout());
		main.board_panel.setBackground(Color.white);
		main.content_panel.setBackground(Color.white);

		Panel top_panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel center_panel = new Panel(new BorderLayout());
		Panel bottom_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
		Panel title_panel = new Panel();
		Panel content_panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel img_panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		
		// ž�г�
		JLabel board_label = new JLabel("�����Խ���");
		board_label.setFont(Commons.getFont(20));
		top_panel.add(board_label);

		// �����г� - ����
		JLabel title_label = new JLabel("����  ");
		title_tf = new JTextField(45);
		title_label.setFont(Commons.getFont(15));
		title_tf.setFont(Commons.getFont(15));
		title_panel.add(title_label);
		title_panel.add(title_tf);

		// �����г� - �����ۼ�
		JLabel content_label = new JLabel("����  ");
		content_label.setFont(Commons.getFont(15));
		content_ta = new JTextArea(15, 45);
		content_ta.setFont(Commons.getFont(15));
		content_ta.setLineWrap(true);
		JScrollPane ta_pane = new JScrollPane(content_ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel img_label = new JLabel("�̹��� ");   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		img_tf = new JTextField(15);
		btn_img = new JButton("ã��");
		btn_ireset = new JButton("�����");
		img_label.setFont(Commons.getFont(13));
		img_tf.setFont(Commons.getFont(13));
		img_tf.setText("");
		img_tf.setEditable(false);
		btn_img.setFont(Commons.getFont());
		btn_ireset.setFont(Commons.getFont());
		btn_img.setPreferredSize(new Dimension(70,23));
		btn_ireset.setPreferredSize(new Dimension(70,23));
		
		img_panel.add(img_label);
		img_panel.add(img_tf);
		img_panel.add(btn_img);
		img_panel.add(btn_ireset);

		content_panel.add(content_label);
		content_panel.add(ta_pane);
		center_panel.add(BorderLayout.NORTH, title_panel);
		center_panel.add(BorderLayout.CENTER, content_panel);
		center_panel.add(BorderLayout.SOUTH, img_panel);   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		

		// �����г�
		btn_insert = new JButton("���");
		btn_cancel = new JButton("���");
		btn_insert.setFont(Commons.getFont());
		btn_cancel.setFont(Commons.getFont());
		bottom_panel.add(btn_insert);
		bottom_panel.add(btn_cancel);

		// ���̱�
		main.board_panel.add(BorderLayout.NORTH, top_panel);
		main.board_panel.add(BorderLayout.CENTER, center_panel);
		main.board_panel.add(BorderLayout.SOUTH, bottom_panel);
		main.content_panel.add(main.board_panel);
		main.secondFrame.setVisible(true);

		btn_insert.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_img.addActionListener(this);//&&&&&&&&&&&&&&&
		btn_ireset.addActionListener(this);
	}
	
	/** �׼� ������ **/
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_insert) {
			insertProc();
		} else if (obj == btn_cancel) {
			int result = JOptionPane.showConfirmDialog(null, Commons.getMsg("�Խù� �ۼ��� ����Ͻðڽ��ϱ�?"));
			if (result == 0) {
				JOptionPane.showMessageDialog(null, Commons.getMsg("�Խù� �ۼ��� ��ҵǾ����ϴ�."));
				new BoardUI(main);
			}
		} else if (obj == btn_img) {//&&&&&&&&&&&&&&&
			addImg();
		} else if (obj == btn_ireset) {
			img_tf.setText("");
		}
	}
	
	public void insertProc() {  //&&&&&&&&&&&&&&&
		if (writeCheck()) {
			BoardVO board = new BoardVO();
			board.setB_title(title_tf.getText());
			board.setB_content(content_ta.getText());
			board.setB_id(main.id_tf.getText());
			board.setB_filepath(img_tf.getText());

			if(main.system.insertBoard(board) && main.system.addPoint(board)) {
				JOptionPane.showMessageDialog(null, Commons.getMsg("�Խù��� ��ϵǾ����ϴ�."));
				JOptionPane.showMessageDialog(null, Commons.getMsg("10 ����Ʈ�� �����Ǿ����ϴ�."));
				new BoardUI(main);
			} else {
				JOptionPane.showMessageDialog(null, Commons.getMsg("�Խù� ��Ͽ� �����߽��ϴ�."));
			}
		}
	}
	
	public void addImg() {  ///&&&&&&&&&&&&&&&&
		chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("JPG, PNG �̹��� ����", "jpg", "png"));
		chooser.setMultiSelectionEnabled(false);
	
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String filepath = chooser.getSelectedFile().getPath();
			img_tf.setText(filepath);
			
		} else {
			JOptionPane.showMessageDialog(null, Commons.getMsg("�̹����� �������� �ʾҽ��ϴ�."));
		}
	}
	

	/** �� �ۼ� ��ȿ�� �˻� **/
	public boolean writeCheck() {
		boolean result = false;
		
		if (title_tf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, Commons.getMsg("������ �Է��ϼ���."));
			title_tf.requestFocus();
		} else if (content_ta.getText().equals("")) {
			JOptionPane.showMessageDialog(null, Commons.getMsg("������ �Է��ϼ���."));
			content_ta.requestFocus();
		} else {
			result = true;
		}
			
		return result;
	}


	
}
