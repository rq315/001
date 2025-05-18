package edu.wtbu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import edu.wtbu.StuId;
import edu.wtbu.helper.MySqlHelper;
public class MemberDao {
	// 获取成员总数（带条件）
    public static int getMemberCount(String userName, String roleId) {    	
        String sql = "SELECT COUNT(*) as total FROM user_information u " +
                "LEFT JOIN community_information c ON u.user_belong_community_number = c.community_id " +
                "WHERE (u.user_name LIKE ? OR ? IS NULL) " +
                "AND (u.user_role = ? OR ? IS NULL)";
        
	   // 处理参数为空的情况
	   String nameParam = (userName == null || userName.isEmpty()) ? null : "%" + userName + "%";
	   String roleParam = (roleId == null || "0".equals(roleId)) ? null : roleId;
	   
	   // 四个参数对应SQL中的四个问号
	   List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, new Object[]{
	       nameParam, nameParam,  // 第一个条件的两个参数
	       roleParam, roleParam   // 第二个条件的两个参数
	   });
	   // 处理查询结果
	   return result.isEmpty() ? 0 : ((Number)result.get(0).get("total")).intValue();
    	
    }
    
    // 获取分页成员列表（带条件）
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
		// 处理参数为空的情况
		   String nameParam = (userName == null || userName.isEmpty()) ? null : "%" + userName + "%";
		   String roleParam = (roleId == null || "0".equals(roleId)) ? null : roleId;
		   
		return MySqlHelper.executeQuery(sql, new Object[]{
				nameParam, nameParam,
				roleParam, roleParam,
				pageSize, offset
				});
        
    }
    
	// 添加成员时 生成学号 学号唯一
    public static int addMember(String user_name, String studentId, String user_join_date,
            int user_role, String user_phone, int clubId) {
		// 验证日期格式
//		if (user_join_date == null || !user_join_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
//			throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
//		}
		
		// 检查学号唯一性（使用更可靠的方式）
		int attempt = 0;
		while (attempt < 10) { // 最多尝试10次 防止无限循环
			String checkSql = "SELECT COUNT(*) as count FROM user_information WHERE user_student_id = ?";
			List<HashMap<String, Object>> result = MySqlHelper.executeQuery(checkSql, new Object[]{studentId});
			
			// 检查结果集是否正确处理
			if (result != null && !result.isEmpty()) {
				int count = ((Number)result.get(0).get("count")).intValue();
				if (count == 0) {
					break; // 学号唯一，可以插入
				}
			}
			
			// 生成新学号
			studentId = StuId.generateStudentId();
			attempt++;
		}
		
		if (attempt >= 10) {
		throw new RuntimeException("无法生成唯一学号，请稍后再试");
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
    
    //删除成员
    public static int deleteMember(int userId) {
		String sql="DELETE FROM user_information WHERE user_id = ?";
		return MySqlHelper.executeUpdate(sql, new Object[] {userId});
	}
    //更新权限
    public static int updateRoleMember(int userId,int roleId) {
		String sql="UPDATE user_information SET user_role = ? WHERE user_id = ?";
		return MySqlHelper.executeUpdate(sql, new Object[] {roleId,userId});
	}
    
	//管理成员 关联查询社团名称
    // 按姓名搜索成员
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
//    // 按角色和姓名搜索成员
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