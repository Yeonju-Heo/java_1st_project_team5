package mbti_dao;

import java.util.ArrayList;

import mbti_vo.ItemVO;

public class ItemDAO extends DBConn{
	
	/** ��� ������ ��ȸ **/
	public ArrayList<ItemVO> getItemResult() {
		ArrayList<ItemVO> itemlist = new ArrayList<ItemVO>();
		
		try {
			String sql = " SELECT I_TYPE FROM ITEM_TABLE ";
			getPreparedStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				ItemVO item = new ItemVO();
				item.setI_type(rs.getString(1));
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return itemlist;
		
	}
	
	
}
