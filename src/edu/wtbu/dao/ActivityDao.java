package edu.wtbu.dao;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.helper.MySqlHelper;

public class ActivityDao {
	//�������Ϣ
	
	// ��ӷ�ҳ��ѯ��ķ��� ��ȡ������б�
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

    // ���ͳ�ƻ �������ķ���
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
    
    //����
    
    //����ȫ���-���
  	public static List<HashMap<String, Object>> getActivityList() {
          String sql = "SELECT \r\n" + 
          		"activity_information.*,community_information.community_name\r\n" + 
          		"FROM activity_information\r\n" + 
          		"JOIN community_information\r\n" + 
          		"on activity_information.assocoated_community_number=community_information.community_id";
          return MySqlHelper.executeQuery(sql, null);
      }
  	
  	
    //ָ���Ļ����-ҳ�� id
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
    
    //�����
	public static int publishA(String activity_name, String activity_type, String activity_date,
            String activity_location, String expected_activity_number, String activity_description, int assocoated_community_number) {
		// ��֤���ڸ�ʽ
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
	
	//���»��������
	public static int updateExpectedNumber(int activityId, int expectedNumber) {
	    String sql = "UPDATE activity_information SET expected_activity_number = ? WHERE activity_id = ?";
	    return MySqlHelper.executeUpdate(sql, new Object[]{expectedNumber, activityId});
	}
	//�����ܽ�
	public static int saveActivitySummary(int activityId, String summary) {
	    String sql = "UPDATE activity_information SET activity_summary = ? WHERE activity_id = ?";
	    return MySqlHelper.executeUpdate(sql, new Object[]{summary, activityId});
	}
	
	
	//���� ������ѯ��������
		 // �����������
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
//	    //�����ƺ�����ɸѡ �����
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
//	 * ��ȡ���ҳ�б�
//	 * @param page ��ǰҳ��
//	 * @param pageSize ÿҳ��¼��
//	 * @return ��б�
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
//	 * ��ȡ�����
//	 * @return �����
//	 */
//	public static int getTotalActivities() {
//	    String sql = "SELECT COUNT(*) as total FROM activity_information";
//	    List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, null);
//	    return result.isEmpty() ? 0 : ((Number)result.get(0).get("total")).intValue();
//	}
//
//	/**
//	 * ��������ѯ���ҳ�б�
//	 * @param activityName �����(ģ����ѯ)
//	 * @param activityType �����
//	 * @param communityId ��������ID
//	 * @param page ��ǰҳ��
//	 * @param pageSize ÿҳ��¼��
//	 * @return ��б�
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
//	 * ������ͳ�ƻ����
//	 * @param activityName �����(ģ����ѯ)
//	 * @param activityType �����
//	 * @param communityId ��������ID
//	 * @return �����
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
