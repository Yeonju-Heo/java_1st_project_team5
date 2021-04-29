package mbti_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import mbti_vo.BoardVO;

public class AdminBoardUI implements ActionListener, MouseListener {
	// Field
	String[] colNames = { "��ȣ", "����", "�ۼ���", "�ۼ���", "��õ/�ݴ�", "����" };
	DefaultTableModel model = new DefaultTableModel(colNames, 0) {
		public boolean isCellEditable(int i, int c) { // ���� ���� ����
			boolean result = false;
			
			if (c == 5)
				result = true;
			return result;
		}

	};

	Object[] row;
	JTable list_table = new JTable(model);

	AdminMainUI main;
//	BoardVO bvo = new BoardVO();
//	BoardVO board;
	JTextField search_tf, title_tf;
	JTextArea content_ta;
	JButton btn_search, btn_list, btn_delete;
//	int count = 1;
	JLabel good_label, bad_label, img_label;
	int bno = 0;
	int no;
	String title, id, content, good, bad, filepath; // &&&
	BufferedImage img;
	JLabel search_label;

	// Constructor
	public AdminBoardUI(AdminMainUI main) {
		this.main = main;
		init();
	}

	// Method
	/** �� ��� **/
	public void init() {
		main.switch_panel(AdminMainUI.BOARD);
		main.board_panel.setLayout(new BorderLayout());
		// main.board_panel.setBackground(Color.white);
		// main.content_panel.setBackground(Color.white);

		Panel top_panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel center_panel = new Panel(new BorderLayout());
		Panel bottom_panel = new Panel();
		Panel search_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
		Panel list_panel = new Panel();

		// ž�г�
		JLabel board_label = new JLabel("�Խ��� ����");
		board_label.setFont(Commons.getFont(20));
		top_panel.add(board_label);

		// �����г� - �˻�
		search_label = new JLabel("���� �˻� : ");
		search_label.setFont(Commons.getFont());
		search_tf = new JTextField(20);
		btn_search = new JButton("�˻�");
		btn_search.setFont(Commons.getFont());
		search_panel.add(search_label);
		search_panel.add(search_tf);
		search_panel.add(btn_search);

		// �����г� - �۸��
		createJtableData();
		model.setColumnIdentifiers(colNames);

		list_table.setModel(model);
		list_table.setRowHeight(35);
		list_table.setAutoCreateRowSorter(false);

		list_table.setRowMargin(0);
		list_table.getColumnModel().setColumnMargin(0);
		list_table.getTableHeader().setReorderingAllowed(false); // ���콺�� �÷� �̵� �Ұ�
		list_table.getTableHeader().setResizingAllowed(false); // ���콺�� �÷� ũ�� ���� �Ұ�
		list_table.setBackground(Color.white);
		list_table.setShowVerticalLines(false); // �÷� ���м� �� ���̰�

		list_table.getColumnModel().getColumn(5).setCellRenderer(new TableCell());
		list_table.getColumnModel().getColumn(5).setCellEditor(new TableCell());

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ��� ����Ʈ ���� ��� ����
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = list_table.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount() - 1; i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		resizeColumnWidth(list_table); // �÷� ũ�� ����
		JScrollPane pane = new JScrollPane(list_table);
		pane.setPreferredSize(new Dimension(700, 400));
		list_panel.add(pane);
		center_panel.add(BorderLayout.NORTH, search_panel);
		center_panel.add(BorderLayout.CENTER, list_panel);

		// ���̱�
		main.board_panel.add(BorderLayout.NORTH, top_panel);
		main.board_panel.add(BorderLayout.CENTER, center_panel);
		main.board_panel.add(BorderLayout.SOUTH, bottom_panel);
		main.content_panel.add(main.board_panel);
		main.adminFrame.setVisible(true);

		list_table.addMouseListener(this);
		btn_search.addActionListener(this);
		search_tf.addActionListener(this);
	}

	/** �۸�� ���� **/
	public void createJtableData() {
//		BoardDAO bdao = new BoardDAO();
//		data = bdao.selectTable();
//		model.setDataVector(data, colName);

//		model.setNumRows(0);
//		model.setDataVector(dataVector, columnIdentifiers);

		model.setNumRows(0);
		for (BoardVO board : main.system.getBoardList()) {
			row = new Object[6];
			row[0] = board.getB_rno();
			row[1] = board.getB_title();
			row[2] = board.getB_id();
			row[3] = board.getB_date();
			row[4] = board.getB_good() + "/" + board.getB_bad();

			model.addRow(row);

//			list_table.getColumnModel().getColumn(5).setCellRenderer(new TableCell());
//			list_table.getColumnModel().getColumn(5).setCellEditor(new TableCell());

			model.fireTableDataChanged();
		}

	}

	/** �˻� UI �����ֱ� **/
	public void createJtableData(ArrayList<BoardVO> board) {
		model.setNumRows(0);

		for (BoardVO data : board) {
			row = new Object[6];
			row[0] = data.getB_rno();
			row[1] = data.getB_title();
			row[2] = data.getB_id();
			row[3] = data.getB_date();
			row[4] = data.getB_good() + "/" + data.getB_bad();

			model.addRow(row);

//			list_table.getColumnModel().getColumn(5).setCellRenderer(new TableCell());
//			list_table.getColumnModel().getColumn(5).setCellEditor(new TableCell());

			model.fireTableDataChanged();
		}
	}
	
	public void getInfo() {
//		board = main.system.readBoard(no);
		BoardVO board = main.system.readBoard(no);
		title = board.getB_title();
		id = board.getB_id();
		content = board.getB_content();
		good = String.valueOf(board.getB_good());
		bad = String.valueOf(board.getB_bad());
		filepath = board.getB_filepath();
		img = board.getB_img();
	}
	
	/** �� �б� ȭ�� **/
	public void readUI(int no) {
		this.no = no;
		getInfo();

		main.board_panel.removeAll();
		main.switch_panel(AdminMainUI.BOARD);
		main.board_panel.setLayout(new BorderLayout());
//		main.board_panel.setBackground(Color.white);
//		main.content_panel.setBackground(Color.white);

		Panel top_panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel center_panel = new Panel(new BorderLayout());
		Panel bottom_panel = new Panel(new BorderLayout());
		Panel title_panel = new Panel(new BorderLayout());
		Panel content_panel = new Panel(new BorderLayout()); // &&
		Panel recommend_panel = new Panel();

		// ž�г�
		JLabel board_label = new JLabel("�����Խ���");
		board_label.setFont(Commons.getFont(20));
		top_panel.add(board_label);
		top_panel.setLayout(new GridLayout(2, 1)); // �����Խ����̶� ���� ���� ���� �����

		// �����г� - ���� + �ۼ���
		JLabel title_label = new JLabel("����: " + title);
		JLabel writer_label = new JLabel("�ۼ���: " + id);
		title_label.setFont(Commons.getFont(15));
		writer_label.setFont(Commons.getFont(15));
		title_panel.add(BorderLayout.WEST, title_label);
		title_panel.add(BorderLayout.EAST, writer_label);

		// �����г� - ����
//		if (img != null) { // &&&&
//			if (img.getWidth() >= 400 && img.getHeight() >= 250) {
//				Image rimg = img.getScaledInstance(400, 250, Image.SCALE_SMOOTH);
//				img_label = new JLabel(new ImageIcon(rimg));
//			} else {
//				img_label = new JLabel(new ImageIcon(img));
//			}
//			content_panel.add(img_label, BorderLayout.NORTH);
//			img_label.addMouseListener(this);
//		}


		if (img != null) { // &&&&
			boolean flag = true;
			int width = img.getWidth();
			int height = img.getHeight();

			while (flag) {
				if (width > 500 || height > 250) {
					width = width / 2;
					height = height / 2;
				} else {
					flag = false;
				}
			}
			Image rimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			img_label = new JLabel(new ImageIcon(rimg));

			content_panel.add(img_label, BorderLayout.NORTH);
			img_label.addMouseListener(this);
		}
		
		
		JTextArea rcontent_ta = new JTextArea(15, 45);
		rcontent_ta.setEditable(false);
		rcontent_ta.setFont(Commons.getFont(15));
		rcontent_ta.setText(content);
		JScrollPane ta_pane = new JScrollPane(rcontent_ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rcontent_ta.setCaretPosition(0); // ��ũ�� �� ����

		// �����г� - ��õ��
		ImageIcon good_icon = new ImageIcon("images/up.png");
		good_label = new JLabel(good_icon);
		JLabel good_count = new JLabel(good);

		ImageIcon bad_icon = new ImageIcon("images/down.png");
		bad_label = new JLabel(bad_icon);
		JLabel bad_count = new JLabel(bad);

		recommend_panel.add(good_label);
		recommend_panel.add(good_count);
		recommend_panel.add(bad_label);
		recommend_panel.add(bad_count);

//		ImageIcon writer = new ImageIcon("images/character.png"); // 0413: �Խñۿ� ĳ���� �߰�
//		JLabel character_label = new JLabel(writer);

		content_panel.add(ta_pane, BorderLayout.CENTER); // &&&&
		content_panel.setPreferredSize(new Dimension(700, 400));
		center_panel.add(BorderLayout.NORTH, title_panel);
		center_panel.add(BorderLayout.CENTER, content_panel);
//		center_panel.add(BorderLayout.EAST, character_label); // 0413: �Խñۿ� ĳ���� �߰�
		center_panel.add(BorderLayout.SOUTH, recommend_panel);

		// �����г�
		btn_list = new JButton("�������");
		btn_delete = new JButton("����");
		btn_list.setFont(Commons.getFont());
		btn_delete.setFont(Commons.getFont());
		Panel right_panel = new Panel();
		Panel left_panel = new Panel();
		right_panel.add(btn_delete);
		left_panel.add(btn_list);
		bottom_panel.add(BorderLayout.WEST, left_panel);
		bottom_panel.add(BorderLayout.EAST, right_panel);

		// ���̱�
		main.board_panel.add(BorderLayout.NORTH, top_panel);
		main.board_panel.add(BorderLayout.CENTER, center_panel);
		main.board_panel.add(BorderLayout.SOUTH, bottom_panel);
		main.content_panel.add(main.board_panel);
		main.adminFrame.setVisible(true);

		btn_list.addActionListener(this);
		btn_delete.addActionListener(this);

	}

	public void resizeColumnWidth(JTable table) { // �� �ʺ� ����
		TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 20; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			if (width > 100)
				width = 200;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	/** �׼� ������ **/
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_search || obj == search_tf) {
			search_Proc();
		} else if (obj == btn_list) {
			new AdminBoardUI(main);
		} else if (obj == btn_delete) {
			int con = JOptionPane.showConfirmDialog(null, Commons.getMsg("���� �����Ͻðڽ��ϱ�?"));
			if (con == 0) {
				// delete process
				// ��Ÿ� �������� ��� �˷�����...
				if (main.system.deleteAdminBoard(no)) {
					JOptionPane.showMessageDialog(null, Commons.getMsg("������ �Ϸ�Ǿ����ϴ�."));
					new AdminBoardUI(main);
				} else {
					JOptionPane.showMessageDialog(null, Commons.getMsg("������ �����߽��ϴ�."));
				}
			}
			// �ش� ��ư�� ������ �Խñ� ����
		}
	}

	/** �˻� Proc **/
	public void search_Proc() {
		if (search_tf.getText().equals("")) {
			// �޼ҵ�� ���� �ͼ� �� ���� ����ִٸ�
			JOptionPane.showMessageDialog(null, Commons.getMsg("�˻��� �̸��� �Է����ּ���."));
			search_tf.requestFocus();
		} else {
			// �ƴ϶�� �˻�
			if (main.system.getBoardDateExsistSelect(search_tf.getText())) {
				ArrayList<BoardVO> board = main.system.searchAdminBoard(search_tf.getText());
				createJtableData(board);
			} else {
				JOptionPane.showMessageDialog(null, Commons.getMsg("�ش��ϴ� �����Ͱ� �����ϴ�."));
			}
		}

	}
	
//	public void showImg() {
//		JFrame img_frame = new JFrame("image");
//
//		ImageIcon origin_icon = new ImageIcon(img);
//		JLabel origin_label = new JLabel(origin_icon);
//
//		img_frame.add(origin_label);
//		img_frame.setSize(img.getWidth(), img.getHeight());
//
//		img_frame.setVisible(true);
//
//		img_frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				img_frame.dispose();
//			}
//		});
//
//	}
	
	public void showImg() {
		JFrame img_frame = new JFrame("image");
		Image rimg = img;

		Double window_width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		Double window_height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		if (img.getWidth() > window_width || img.getHeight() > window_height) {
			rimg = img.getScaledInstance(img.getWidth() / 2, img.getHeight() / 2, Image.SCALE_SMOOTH);
			img_frame.setSize(img.getWidth(), img.getHeight());
		} else {
			img_frame.setSize(img.getWidth()+100, img.getHeight()+100);
		}

		ImageIcon rimg_icon = new ImageIcon(rimg);
		JLabel rimg_label = new JLabel(rimg_icon);
		img_frame.add(rimg_label);

		img_frame.setVisible(true);

		img_frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				img_frame.dispose();
			}
		});

	}

	/** �۸�� ���콺 ������ **/
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();

		if (obj == list_table) {
			System.out.println("Ŭ��!!");
			String no = list_table.getValueAt(list_table.getSelectedRow(), 0).toString();
			readUI(Integer.parseInt(no));
			// int row = list_table.getSelectedRow();
			// int column = list_table.getSelectedColumn();
		} else if (obj == img_label) {
			showImg();
		}
	}

	/** ���콺 ������ �������̵� **/
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		JButton jb;

		public TableCell() {
			jb = new JButton("����");
			jb.addActionListener(e -> {
				if (list_table.getSelectedRow() != -1) {
					String bno = list_table.getValueAt(list_table.getSelectedRow(), 0).toString();
					int con = JOptionPane.showConfirmDialog(null, Commons.getMsg("���� �����Ͻðڽ��ϱ�?"));
					if (con == 0) {
						JOptionPane.showMessageDialog(null, Commons.getMsg("�����Ǿ����ϴ�."));
						main.system.deleteAdminBoard(Integer.parseInt(bno));
						new AdminBoardUI(main);
					}
				}
			});
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component comp = null;
			if (column == 5) {
				comp = jb;
			}
			return jb;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return jb;
		}

	}

}
