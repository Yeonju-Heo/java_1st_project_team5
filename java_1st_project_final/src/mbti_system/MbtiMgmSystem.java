package mbti_system;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import mbti_dao.BoardDAO;
import mbti_dao.ItemDAO;
import mbti_dao.MbtiDAO;
import mbti_dao.UserDAO;
import mbti_dao.UserItemDAO;
import mbti_vo.BoardVO;
import mbti_vo.ItemVO;
import mbti_vo.MbtiVO;
import mbti_vo.MessageVO;
import mbti_vo.UserItemVO;
import mbti_vo.UserVO;

public class MbtiMgmSystem {
	// Field//
	UserDAO udao = new UserDAO();
	BoardDAO bdao = new BoardDAO();
	MbtiDAO mdao = new MbtiDAO();
	ItemDAO idao = new ItemDAO();
	UserItemDAO u_idao = new UserItemDAO();

	// login ���
	public static boolean LOGIN_RESULT = false;

	// Constructor
	public MbtiMgmSystem() {

	}

	// Method
	/** �α��� **/
	public int loginCheck(String id, String pass) { //#############################
		return udao.getLoginResult(id, pass);
	}

	/** ȸ������ **/
	public boolean join(UserVO user) {
		return udao.getJoinResult(user);
	}

	/** ȸ������ ���̵� �ߺ� **/
	public boolean idCheck(String id) {
		return udao.getIdCheck(id);
	}
	
	/** ���� ����Ʈ ����(���) **/
	public boolean subHairPoint(UserVO user) {
		return udao.getSubHairPointResult(user);
	}

	/** ���� ����Ʈ ����(����,����) **/
	public boolean subTopBottomPoint(UserVO user) {
		return udao.getSubTopBottomPointResult(user);
	}
	
	/** ȸ�� ���� ��ȸ **/
	public UserVO searchUser(String id) {
		return udao.getUserDataResult(id);
	}

	/** ȸ�� ������ ��� ��ȸ **/
	public ArrayList<UserItemVO> searchHairItem(String id) {
		return u_idao.getUserHairItem(id);
	}
	
	/** ȸ�� ������ ���� ��ȸ **/
	public ArrayList<UserItemVO> searchTopItem(String id) {
		return u_idao.getUserTopItem(id);
	}
	
	/** ȸ�� ������ ���� ��ȸ **/
	public ArrayList<UserItemVO> searchBottomItem(String id) {
		return u_idao.getUserBottomItem(id);
	}
	
	/** ȸ�� �ϼ�ĳ���� ���� (���,����,���� �� ���� ��)**/
	public int saveUserChar(String id, ImageIcon hair, ImageIcon top, ImageIcon bottom) {
		return udao.saveUserCharResult(id, hair, top, bottom);
	}
	
	/** ȸ�� �ϼ�ĳ���� ����(���,����,���� �� �ϳ��� ���� ��) **/
	public int saveUserChar(String id, ImageIcon hair, ImageIcon top) {
		return udao.saveUserCharResult(id, hair, top);
	}
	
	/** ȸ�� �ϼ�ĳ���� ����(�ʱ����) **/
	public int saveUserChar(String id) {
		return udao.saveUserCharResult(id);
	}
	
	/** ȸ�� �ϼ�ĳ���� ��ȸ **/
	public UserVO getUserChar(String id) {
		return udao.getUserChar(id);
	}

	/** ȸ�� ��й�ȣ ���� **/
	public int updateUser(UserVO user, String pass) {
		return udao.getUpdateUserResult(user, pass);
	}

	/** ��� ������ ��ȸ **/
//	public ArrayList<ItemVO> getItem() {
//		return idao.getItemResult();
//	}

	/** �� mbti ��ȸ **/
	public MbtiVO getMbti(UserVO user) {
		return mdao.getMbtiSelectResult(user);
	}

	/** ���� **/
	public void close() {
		udao.close();
		System.out.println("--- �����ͺ��̽� ���� ���� ---");
	}

	/** �Խ��� ��� **/
	public boolean insertBoard(BoardVO board) {
		return bdao.getInsertResult(board);
	}

	/** �Խ��� ��ȸ **/
	public ArrayList<BoardVO> getBoardList() {
		return bdao.getSelectResult();
	}

	/** �Խ��� �� �б� **/
	public BoardVO readBoard(int no) {
		return bdao.getReadResult(no);
	}

	/** �Խ��� �˻� **/
	public ArrayList<BoardVO> searchBoard(String title) {
		return bdao.getSearchResult(title);
	}

	/** �Խ��� ���� **/
	public int updateBoard(BoardVO board) {
		return bdao.getUpdateResult(board);
	}

	/** �Խ��� ��õ **/
	public int updateRecommend(int recommend, int no) {
		return bdao.getUpdateRecommendResult(recommend, no);
	}

	/** �Խ��� ���� **/
	public boolean deleteBoard(int no) {
		return bdao.getDeleteResult(no);
	}

	/** �Խ��� ����Ʈ �߰� **/
	public boolean addPoint(BoardVO board) {
		return bdao.getAddPointResult(board);
	}

	/** ���� ������ �߰� **/
	public boolean updateUserItem(String id, String item) {
		return u_idao.getUserItemResult(id, item);
	}

	/** ������ ���� �˻� **/
	public ArrayList<UserVO> getUserDateSelect(String id) {
		System.out.println("�˻�");
		return udao.getUserSearchAdminResult(id);
	}

	/** ���� �˻� **/
	public UserVO getUserDataSelect(String id) {
		System.out.println("���νý���");
		return udao.getUserDataResult(id);
	}
	
	/** ���� �˻� (ä��)**/
	public UserVO getChatUserDataSelect(String id) {
		System.out.println("���νý���");
		return udao.getChatUserDataResult(id);
	}
	
	/** ������ ���� ��ȸ **/
	public ArrayList<UserVO> getUserDateSelect() {
		System.out.println("��ȸ");
		return udao.getUserDataResult();
	}

	/** ������ ���� ��ȸ(nullüũ) **/
	public boolean getUserDateExsistSelect(String id) {
		System.out.println("��ȸ");
		return udao.getUserExsistResult(id);
	}

	/** ������ �Խñ� ��ȸ(nullüũ) **/
	public boolean getBoardDateExsistSelect(String title) {
		System.out.println("��ȸ");
		return bdao.getBoardExsistResult(title);
	}

	/** ���� ��ȸ **/
	public ArrayList<UserVO> getUserDataSelect() {
		System.out.println("���νý���");
		return udao.getUserDataResult();
	}

//	/** ���� ���� **/
//	public UserVO getUserDateDelete(String id){
//		System.out.println("����");
//		return udao.getUserDateResult(id);
//	public  UserVO getUserDatSelect(String id){
//		System.out.println("���νý���");
//		return udao.getUserDataResult();
//	}

	/** ������ ���� ���� **/
	public boolean deleteAdminUser(String name) {
		return udao.getDeleteUserAdmin(name);
	}

	/** ������ ���� ���� **/
	public boolean deleteAdminBoard(int bno) {
		return bdao.getDeleteResult(bno);
	}

	/** ������ �Խ��� �˻� **/
	public ArrayList<BoardVO> searchAdminBoard(String title) {
		return bdao.getSearchAdminResult(title);
	}
}
