package edu.wtbu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import edu.wtbu.StuId;
import edu.wtbu.helper.MySqlHelper;
public class MemberDao {
	// ��ȡ��Ա��������������
    public static int getMemberCount(String userName, String roleId) {    	
        String sql = "SELECT COUNT(*) as total FROM user_information u " +
                "LEFT JOIN community_information c ON u.user_belong_community_number = c.community_id " +
                "WHERE (u.user_name LIKE ? OR ? IS NULL) " +
                "AND (u.user_role = ? OR ? IS NULL)";
        
	   // �������Ϊ�յ����
	   String nameParam = (userName == null || userName.isEmpty()) ? null : "%" + userName + "%";
	   String roleParam = (roleId == null || "0".equals(roleId)) ? null : roleId;
	   
	   // �ĸ�������ӦSQL�е��ĸ��ʺ�
	   List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, new Object[]{
	       nameParam, nameParam,  // ��һ����������������
	       roleParam, roleParam   // �ڶ�����������������
	   });
	   // �����ѯ���
	   return result.isEmpty() ? 0 : ((Number)result.get(0).get("total")).intValue();
    	
    }
    
    // ��ȡ��ҳ��Ա�б���������
    public static List<HashMap<String, Object>> getMemberList(int page, int pageSize, 
            String userName, String roleId) {
        
      String sql = "SELECT u.*, c.community_name " +
	      "FROM user_information u " +
	      "LEFT JOIN community_information c ON u.user_belong_community_number = c.community_id " +
	      "WHERE (u.user_name LIKE ? OR ? IS NULL) " +
	      "AND ( u.user_role = ? OR ? IS NULL) " +
	      "ORDER BY u.user_join_date DESC " +
	      "LIMIT ? OFFSET ?";
		int offset = (page - 1) * pageSize;
		// �������Ϊ�յ����
		   String nameParam = (userName == null || userName.isEmpty()) ? null : "%" + userName + "%";
		   String roleParam = (roleId == null || "0".equals(roleId)) ? null : roleId;
		   
		return MySqlHelper.executeQuery(sql, new Object[]{
				nameParam, nameParam,
				roleParam, roleParam,
				pageSize, offset
				});
        
    }
    
	// ��ӳ�Աʱ ����ѧ�� ѧ��Ψһ
    public static int addMember(String user_name, String studentId, String user_join_date,
            int user_role, String user_phone, int clubId) {
		// ��֤���ڸ�ʽ
//		if (user_join_date == null || !user_join_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
//			throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
//		}
		
		// ���ѧ��Ψһ�ԣ�ʹ�ø��ɿ��ķ�ʽ��
		int attempt = 0;
		while (attempt < 10) { // ��ೢ��10�� ��ֹ����ѭ��
			String checkSql = "SELECT COUNT(*) as count FROM user_information WHERE user_student_id = ?";
			List<HashMap<String, Object>> result = MySqlHelper.executeQuery(checkSql, new Object[]{studentId});
			
			// ��������Ƿ���ȷ����
			if (result != null && !result.isEmpty()) {
				int count = ((Number)result.get(0).get("count")).intValue();
				if (count == 0) {
					break; // ѧ��Ψһ�����Բ���
				}
			}
			
			// ������ѧ��
			studentId = StuId.generateStudentId();
			attempt++;
		}
		
		if (attempt >= 10) {
		throw new RuntimeException("�޷�����Ψһѧ�ţ����Ժ�����");
		}
		
		String sql = "INSERT INTO user_information " +
		   "(user_name, user_student_id, user_join_date, user_role, user_phone, user_belong_community_number) " +
		   "VALUES (?, ?, ?, ?, ?, ?)";
			
		return MySqlHelper.executeUpdate(sql, new Object[]{
		user_name,
		studentId,
		user_join_date,
		user_role,
		user_phone,
		clubId
		});
	}
    
    //ɾ����Ա
    public static int deleteMember(int userId) {
		String sql="DELETE FROM user_information WHERE user_id = ?";
		return MySqlHelper.executeUpdate(sql, new Object[] {userId});
	}
    //����Ȩ��
    public static int updateRoleMember(int userId,int roleId) {
		String sql="UPDATE user_information SET user_role = ? WHERE user_id = ?";
		return MySqlHelper.executeUpdate(sql, new Object[] {roleId,userId});
	}
    
	//�����Ա ������ѯ��������
    // ������������Ա
//    public static List<HashMap<String, Object>> findUserListByName(String userName) {
//        String sql = "SELECT \r\n" + 
//        		"    user_information.*,\r\n" + 
//        		"    community_information.community_name\r\n" + 
//        		"FROM \r\n" + 
//        		"    user_information\r\n" + 
//        		"JOIN \r\n" + 
//        		"    community_information \r\n" + 
//        		"    ON user_information.user_belong_community_number = community_information.community_id\r\n" + 
//        		"WHERE \r\n" + 
//        		"    user_name LIKE ?";
//        return MySqlHelper.executeQuery(sql, new Object[]{"%"+userName+"%"});
//    }
//    
//    // ����ɫ������������Ա
//    public static List<HashMap<String, Object>> findUserListByRoleAndName(int roleId, String userName) {
//        String sql = "SELECT \r\n" + 
//        		"    user_information.*,\r\n" + 
//        		"    community_information.community_name\r\n" + 
//        		"FROM \r\n" + 
//        		"    user_information\r\n" + 
//        		"JOIN \r\n" + 
//        		"    community_information \r\n" + 
//        		"    ON user_information.user_belong_community_number = community_information.community_id\r\n" + 
//        		"WHERE \r\n" + 
//        		"    user_role = ?\r\n" + 
//        		"    AND user_name LIKE ?";
//        return MySqlHelper.executeQuery(sql, new Object[]{roleId, "%"+userName+"%"});
//    }
}