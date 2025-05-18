package edu.wtbu.dao;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.helper.MySqlHelper;

public class UsersDao {
	//登录 姓名学号
	public static HashMap<String, Object> findByNameAndStuId(String user_name,String user_student_id){
		String sqlString="select * from user_information where user_name=? and user_student_id=?";
		List<HashMap<String, Object>> list=MySqlHelper.executeQuery(sqlString,new Object[] {user_name,user_student_id});
		if (list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public static HashMap<String, Object> findByName(String user_name){
		String sqlString="select * from user_information where user_name=?";
		List<HashMap<String, Object>> list=MySqlHelper.executeQuery(sqlString,new Object[] {user_name});
		if (list!=null && list.size()>0) {
			return list.get(0); // 返回第一个匹配的用户
		}else {
			return null;
		}
	}
	
	//录入社团信息
	public static int enterCI(int community_id,String community_name, String community_type, String founder_name,
			String founder_id,String create_date,String community_introduction,String community_status) {
		// 验证日期格式
//		if (create_date == null || !create_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
//		throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
//		}
		String sql = "INSERT INTO community_information (community_id,community_name, community_type,founder_name,"
				+ "founder_id,create_date,community_introduction,community_status) VALUES (?, ?, ?,?,?,?,?,?)";
		return MySqlHelper.executeUpdate(sql, new Object[]{community_id,community_name, community_type,founder_name,founder_id,create_date,
				community_introduction,community_status});
	}
	
	//社团信息 
	public static List<HashMap<String, Object>> getCIList() {
        String sql = "SELECT * FROM community_information";
        return MySqlHelper.executeQuery(sql, null);
    }


	//搜索社团 获取分页社团列表
	public static List<HashMap<String, Object>> searchClubsWithPagination(String communityName, String communityType,int page, int pageSize) {
	    //按社团名称、社团类型模糊查询，同时支持参数为空时忽略该条件。
		//OR ? IS NULL实现条件可选性
//		指定每页返回的记录数（如10条）。
//		指定从第几条记录开始返回（计算方式：OFFSET = (页码 - 1) * 每页条数）。
	    String sql = "SELECT * FROM community_information " +
	                 "WHERE (community_name LIKE ? OR ? IS NULL) " +
	                 "AND (community_type LIKE ? OR ? IS NULL) " +
	                 "LIMIT ? OFFSET ?";
	    
	    // 计算偏移量
	    int offset = (page - 1) * pageSize;
	    
	    // 处理搜索条件
	    String nameParam = communityName == null ? null : "%" + communityName + "%";
	    String typeParam = communityType == null ? null : "%" + communityType + "%";
	    
	    //双重参数传递第一次用于LIKE ?条件，第二次用于? IS NULL检查
	    return MySqlHelper.executeQuery(sql, new Object[]{
	        nameParam, nameParam,
	        typeParam, typeParam,
	        pageSize, offset
	    });
	}

	//搜索社团 总数 动态条件计数功能
	public static int countClubs(String communityName, String communityType) {
	    //统计满足条件的记录总数
		String sql = "SELECT COUNT(*) as total FROM community_information " +
	                 "WHERE (community_name LIKE ? OR ? IS NULL) " +
	                 "AND (community_type LIKE ? OR ? IS NULL)";
		
		// 处理模糊查询参数
	    String nameParam = communityName == null ? null : "%" + communityName + "%";
	    String typeParam = communityType == null ? null : "%" + communityType + "%";
	    
	    // 执行查询并获取结果
	    List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, new Object[]{
	        nameParam, nameParam,
	        typeParam, typeParam
	    });
	    
	    // 处理查询结果 不同数据库JDBC驱动对聚合函数的返回类型不同，使用 Number 可兼容所有情况
	    return result.isEmpty() ? 0 : ((Number)result.get(0).get("total")).intValue();
	}
	

	
	
	//搜索社团信息
//	// 按名称搜索
//    public static List<HashMap<String, Object>> findClubListByName(String community_name) {
//        String sql = "SELECT * FROM community_information WHERE community_name LIKE ?";
//        return MySqlHelper.executeQuery(sql, new Object[]{"%"+community_name+"%"});
//    }
//    // 按名称和类型搜索
//    public static List<HashMap<String, Object>> findClubListByTypeAndName(String community_name, String community_type) {
//        String sql = "SELECT * FROM community_information WHERE community_name LIKE ? AND community_type LIKE ?";
//        return MySqlHelper.executeQuery(sql, new Object[]{"%"+community_name+"%","%"+community_type+"%"});
//    }
 

    

}
