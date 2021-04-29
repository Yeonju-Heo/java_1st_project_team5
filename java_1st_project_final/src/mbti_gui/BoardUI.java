package mbti_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import mbti_vo.BoardVO;

public class BoardUI implements MouseListener, ActionListener {
	// Field//

	String[] colNames = { "��ȣ", "����", "�ۼ���", "�ۼ���", "��õ/�ݴ�" };
	DefaultTableModel model = new DefaultTableModel(colNames, 0) {
		public boolean isCellEditable(int i, int c) { // ���� ���� ����
			return false;
		}
	};
	Object[] row = new Object[5];
	JTable list_table = new JTable(model);

	MbtiMainUI main;
	JTextField search_tf, title_tf;
	JTextArea content_ta;
	JButton btn_search, btn_write, btn_insert, btn_cancel, btn_list, btn_delete, btn_update;
	JLabel up_label;
	JLabel down_label;
	JLabel search_label;

	// Constructor
	public BoardUI(MbtiMainUI main) {
		
		this.main = main;
		init();
	}

	// Method
	/** �� ��� **/
	public void init() {
		main.switch_panel(MbtiMainUI.BOARD);
		main.board_panel.setLayout(new BorderLayout());
		main.board_panel.setBackground(Color.white);
		main.content_panel.setBackground(Color.white);

		Panel top_panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		Panel center_panel = new Panel(new BorderLayout());
		Panel bottom_panel = new Panel();
		Panel search_panel = new Panel(new FlowLayout(FlowLayout.RIGHT));
		Panel list_panel = new Panel();

		// ž�г�
		JLabel board_label = new JLabel("�����Խ���");
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
		createJtableData(main.system.getBoardList());
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

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ��� ����Ʈ ���� ��� ����
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = list_table.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		resizeColumnWidth(list_table); // �÷� ũ�� ����
		JScrollPane pane = new JScrollPane(list_table);
		pane.setPreferredSize(new Dimension(700, 400));
		list_panel.add(pane);

		center_panel.add(BorderLayout.NORTH, search_panel);
		center_panel.add(BorderLayout.CENTER, list_panel);

		// �����г�
		btn_write = new JButton("�۾���");
		btn_write.setFont(Commons.getFont());
		bottom_panel.add(btn_write);

		// ���̱�
		main.board_panel.add(BorderLayout.NORTH, top_panel);
		main.board_panel.add(BorderLayout.CENTER, center_panel);
		main.board_panel.add(BorderLayout.SOUTH, bottom_panel);
		main.content_panel.add(main.board_panel);
		main.secondFrame.setVisible(true);

		list_table.addMouseListener(this);
		btn_write.addActionListener(this);
		btn_search.addActionListener(this);
		search_tf.addActionListener(this);
	}

	/** �۸�� ���� **/
	public void createJtableData(ArrayList<BoardVO> list) {

		model.setNumRows(0);

		if (list.size() != 0) {
			for (BoardVO board : list) {
				row[0] = board.getB_rno();
				row[1] = board.getB_title();
				row[2] = board.getB_id();
				row[3] = board.getB_date();
				row[4] = board.getB_good() + "/" + board.getB_bad();
				model.addRow(row);
			}

		} else {
			row[0] = "";
			row[1] = "��ϵ� �Խù��� �����ϴ�.";

			model.addRow(row);
		}

		model.fireTableDataChanged();
	
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
			searchProc();
		} else if (obj == btn_write) {
			new BoardWriteUI(main);
		}
	}

	/** �˻� **/
	public void searchProc() {
		if (search_tf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, Commons.getMsg("�˻�� �Է����ּ���."));
		} else {
			ArrayList<BoardVO> list = main.system.searchBoard(search_tf.getText());
			if(list.size() == 0) {
				JOptionPane.showMessageDialog(null, Commons.getMsg(search_tf.getText() + "�� ���� �˻� ����� �����ϴ�."));
			} else {
				createJtableData(main.system.searchBoard(search_tf.getText()));
			}
		}
	}

	/** �۸�� ���콺 ������ **/
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		String rno = list_table.getValueAt(list_table.getSelectedRow(), 0).toString();

		if (obj == list_table) {
			if (!rno.equals("")) {
				int no = Integer.parseInt(rno);
				new BoardReadUI(main, no);
			} else {
				System.out.println("no post");
			}

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

}
