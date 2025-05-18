package edu.wtbu.dao;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.helper.MySqlHelper;

public class ActivityDao {
	//搜索活动信息
	
	// 添加分页查询活动的方法 获取活动经费列表
    public static List<HashMap<String, Object>> searchActivitiesWithPagination(String activityName, String activityType, int page, int pageSize) {
        
        String sql = "SELECT a.*, c.community_name " +
                     "FROM activity_information a " +
                     "LEFT JOIN community_information c ON a.assocoated_community_number = c.community_id " +
                     "WHERE (a.activity_name LIKE ? OR ? IS NULL) " +
                     "AND (a.activity_type LIKE ? OR ? IS NULL) " +
                     "LIMIT ? OFFSET ?";
        
        int offset = (page - 1) * pageSize;
        String nameParam = activityName == null ? null : "%" + activityName + "%";
        String typeParam = activityType == null ? null : "%" + activityType + "%";
        
        return MySqlHelper.executeQuery(sql, new Object[]{
            nameParam, nameParam,
            typeParam, typeParam,
            pageSize, offset
        });
    }

    // 添加统计活动 总数量的方法
    public static int countActivities(String activityName, String activityType) {
        String sql = "SELECT COUNT(*) as total FROM activity_information " +
                     "WHERE (activity_name LIKE ? OR ? IS NULL) " +
                     "AND (activity_type LIKE ? OR ? IS NULL)";
        
        String nameParam = activityName == null ? null : "%" + activityName + "%";
        String typeParam = activityType == null ? null : "%" + activityType + "%";
        
        List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, new Object[]{
            nameParam, nameParam,
            typeParam, typeParam
        });
        
        return result.isEmpty() ? 0 : ((Number)result.get(0).get("total")).intValue();
    }
    
    //管理活动
    
    //管理全部活动-表格
  	public static List<HashMap<String, Object>> getActivityList() {
          String sql = "SELECT \r\n" + 
          		"activity_information.*,community_information.community_name\r\n" + 
          		"FROM activity_information\r\n" + 
          		"JOIN community_information\r\n" + 
          		"on activity_information.assocoated_community_number=community_information.community_id";
          return MySqlHelper.executeQuery(sql, null);
      }
  	
  	
    //指定的活动详情-页面 id
    public static HashMap<String, Object> getActivityDetailList(int activity_id) {
        String sql = "SELECT \r\n" + 
        		"activity_information.*,community_information.community_name\r\n" + 
        		"FROM activity_information\r\n" + 
        		"JOIN community_information\r\n" + 
        		"on activity_information.assocoated_community_number=community_information.community_id\r\n" + 
		        "WHERE\r\n" + 
				"activity_id = ?";
        List<HashMap<String, Object>> list=MySqlHelper.executeQuery(sql,new Object[] {activity_id});
		if (list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
    }
    
    //发布活动
	public static int publishA(String activity_name, String activity_type, String activity_date,
            String activity_location, String expected_activity_number, String activity_description, int assocoated_community_number) {
		// 验证日期格式
//		if (activity_date == null || !activity_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
//		throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
//		}
		String sql = "INSERT INTO activity_information (" +
	            "activity_name, activity_type, activity_date, " +
	            "activity_location, expected_activity_number, " +
	            "activity_description, assocoated_community_number) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?)";
		return MySqlHelper.executeUpdate(sql, new Object[]{
	            activity_name, 
	            activity_type,
	            activity_date,
	            activity_location,
	            expected_activity_number,
	            activity_description,
	            assocoated_community_number
	        });
	}
	
	//更新活动参与人数
	public static int updateExpectedNumber(int activityId, int expectedNumber) {
	    String sql = "UPDATE activity_information SET expected_activity_number = ? WHERE activity_id = ?";
	    return MySqlHelper.executeUpdate(sql, new Object[]{expectedNumber, activityId});
	}
	//保存总结
	public static int saveActivitySummary(int activityId, String summary) {
	    String sql = "UPDATE activity_information SET activity_summary = ? WHERE activity_id = ?";
	    return MySqlHelper.executeUpdate(sql, new Object[]{summary, activityId});
	}
	
	
	//管理活动 关联查询社团名称
		 // 按名称搜索活动
//	    public static List<HashMap<String, Object>> findActivityListByName(String activity_name) {
//	        String sql = "SELECT\r\n" + 
//	        		"activity_information.*,community_information.community_name\r\n" + 
//	        		"FROM activity_information \r\n" + 
//	        		"JOIN community_information\r\n" + 
//	        		"on activity_information.assocoated_community_number=community_information.community_id\r\n" + 
//	        		"WHERE\r\n" + 
//	        		"activity_name LIKE ?";
//	        return MySqlHelper.executeQuery(sql, new Object[]{"%"+activity_name+"%"});
//	    }
	//    
//	    //按名称和类型筛选 搜索活动
//	    public static List<HashMap<String, Object>> findActivityListByTypeAndName(String activity_name, String activity_type) {
//	        String sql = "SELECT\r\n" + 
//	        		"activity_information.*,community_information.community_name\r\n" + 
//	        		"FROM activity_information \r\n" + 
//	        		"JOIN community_information\r\n" + 
//	        		"on activity_information.assocoated_community_number=community_information.community_id\r\n" + 
//	        		"WHERE\r\n" + 
//	        		"activity_name LIKE ?\r\n" + 
//	        		"AND activity_type LIKE ?";
//	        return MySqlHelper.executeQuery(sql, new Object[]{"%"+activity_name+"%","%"+activity_type+"%"});
//	    }
	
	
//	/**
//	 * 获取活动分页列表
//	 * @param page 当前页码
//	 * @param pageSize 每页记录数
//	 * @return 活动列表
//	 */
//	public static List<HashMap<String, Object>> getActivityListByPage(int page, int pageSize) {
//	    String sql = "SELECT a.*, c.community_name " +
//	                 "FROM activity_information a " +
//	                 "LEFT JOIN community_information c ON a.assocoated_community_number = c.community_id " +
//	                 "LIMIT ? OFFSET ?";
//	    
//	    int offset = (page - 1) * pageSize;
//	    return MySqlHelper.executeQuery(sql, new Object[]{pageSize, offset});
//	}
//
//	/**
//	 * 获取活动总数
//	 * @return 活动总数
//	 */
//	public static int getTotalActivities() {
//	    String sql = "SELECT COUNT(*) as total FROM activity_information";
//	    List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, null);
//	    return result.isEmpty() ? 0 : ((Number)result.get(0).get("total")).intValue();
//	}
//
//	/**
//	 * 带条件查询活动分页列表
//	 * @param activityName 活动名称(模糊查询)
//	 * @param activityType 活动类型
//	 * @param communityId 关联社团ID
//	 * @param page 当前页码
//	 * @param pageSize 每页记录数
//	 * @return 活动列表
//	 */
//	public static List<HashMap<String, Object>> searchActivities(String activityName, String activityType, 
//	                                                           Integer communityId, int page, int pageSize) {
//	    StringBuilder sql = new StringBuilder();
//	    sql.append("SELECT a.*, c.community_name ");
//	    sql.append("FROM activity_information a ");
//	    sql.append("LEFT JOIN community_information c ON a.assocoated_community_number = c.community_id ");
//	    sql.append("WHERE 1=1 ");
//	    
//	    List<Object> params = new ArrayList<>();
//	    
//	    if (activityName != null && !activityName.isEmpty()) {
//	        sql.append("AND a.activity_name LIKE ? ");
//	        params.add("%" + activityName + "%");
//	    }
//	    
//	    if (activityType != null && !activityType.isEmpty()) {
//	        sql.append("AND a.activity_type = ? ");
//	        params.add(activityType);
//	    }
//	    
//	    if (communityId != null && communityId > 0) {
//	        sql.append("AND a.assocoated_community_number = ? ");
//	        params.add(communityId);
//	    }
//	    
//	    sql.append("LIMIT ? OFFSET ?");
//	    params.add(pageSize);
//	    params.add((page - 1) * pageSize);
//	    
//	    return MySqlHelper.executeQuery(sql.toString(), params.toArray());
//	}
//
//	/**
//	 * 带条件统计活动总数
//	 * @param activityName 活动名称(模糊查询)
//	 * @param activityType 活动类型
//	 * @param communityId 关联社团ID
//	 * @return 活动总数
//	 */
//	public static int countActivities(String activityName, String activityType, Integer communityId) {
//	    StringBuilder sql = new StringBuilder();
//	    sql.append("SELECT COUNT(*) as total ");
//	    sql.append("FROM activity_information a ");
//	    sql.append("WHERE 1=1 ");
//	    
//	    List<Object> params = new ArrayList<>();
//	    
//	    if (activityName != null && !activityName.isEmpty()) {
//	        sql.append("AND a.activity_name LIKE ? ");
//	        params.add("%" + activityName + "%");
//	    }
//	    
//	    if (activityType != null && !activityType.isEmpty()) {
//	        sql.append("AND a.activity_type = ? ");
//	        params.add(activityType);
//	    }
//	    
//	    if (communityId != null && communityId > 0) {
//	        sql.append("AND a.assocoated_community_number = ? ");
//	        params.add(communityId);
//	    }
//	    
//	    List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql.toString(), params.toArray());
//	    return result.isEmpty() ? 0 : ((Number)result.get(0).get("total")).intValue();
//	}
	
}
