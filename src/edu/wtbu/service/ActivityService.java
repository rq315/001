package edu.wtbu.service;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.dao.ActivityDao;
import edu.wtbu.pojo.Result;

public class ActivityService {
	public static Result searchAI(String activityName, String activityType, int page, int pageSize) {
	    Result result = new Result("fail", null, null);
	    
	    try {
	        // ������֤
	        if (page < 1) page = 1;
	        if (pageSize < 1 || pageSize > 100) pageSize = 10;
	        
	        // ��ѯ����
	        List<HashMap<String, Object>> items = ActivityDao.searchActivitiesWithPagination(
	            activityName, activityType, page, pageSize);
	        
	        // ��ѯ����
	        int totalItems = ActivityDao.countActivities(activityName, activityType);
	        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
	        
	        // �������ؽ��
	        HashMap<String, Object> data = new HashMap<String, Object>();
	        data.put("items", items);
	        data.put("currentPage", page);
	        data.put("totalPages", totalPages);
	        data.put("totalItems", totalItems);
	        data.put("pageSize", pageSize);
	        
	        result.setFlag("success");
	        result.setData(data);
	        
	    } catch (Exception e) {
	        result.setData("��ѯʧ��: " + e.getMessage()+"...��ˢ��");
	        e.printStackTrace();
	    }
	    
	    return result;
	}
	
    
    //ȫ�����Ϣ-���
  	public static List<HashMap<String, Object>> getActivityList() {
          return ActivityDao.getActivityList();
      }
    

  	
    //ָ���Ļ����-ҳ�� id
  	public static HashMap<String, Object> getActivityDetailList(int activity_id) {
  		return ActivityDao.getActivityDetailList(activity_id);
      }
  	
	//�����
	public static Result publishA(String activity_name, String activity_type, String activity_date,
            String activity_location, String expected_activity_number, String activity_description, int assocoated_community_number) {
		Result result = new Result("fail", null, null);
		int rows = ActivityDao.publishA(activity_name, activity_type, activity_date, activity_location, expected_activity_number, activity_description, assocoated_community_number);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("��ӳɹ�");
		} else {
			result.setData("���ʧ��");
		}
		return result;
	}
	//���²�������
	public static Result updateExpectedNumber(int activityId, int expectedNumber) {
		Result result = new Result("fail", null, null);
		int rows = ActivityDao.updateExpectedNumber(activityId, expectedNumber);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("���³ɹ�");
		} else {
			result.setData("����ʧ��");
		}
		return result;
	}
	//�����ܽ�
		public static Result saveActivitySummary(int activityId, String summary) {
			Result result = new Result("fail", null, null);
			int rows = ActivityDao.saveActivitySummary(activityId, summary);
			if (rows > 0) {
				result.setFlag("success");
				result.setData("����ɹ�");
			} else {
				result.setData("����ʧ��");
			}
			return result;
		}
		
		
		
//	    // �������������ģ��ƥ�䣩
//	    public static List<HashMap<String, Object>> findActivityListByName(String activity_name) {
//	        String searchName = activity_name == null ? "" : "%" + activity_name + "%";
//	        return UsersDao.findActivityListByName(searchName);
//	    }
	//    
//	    // ������������ƺ����ͣ�
//	    public static List<HashMap<String, Object>> findActivityListByTypeAndName(String activity_name, String activity_type) {
//	        String searchName = activity_name == null ? "" : "%" + activity_name + "%";
//	        String searchType = activity_type == null ? "" : "%" + activity_type + "%";
//	        return UsersDao.findActivityListByTypeAndName(searchName, searchType);
//	    }
		
//  	/**
// 	 * ��ȡ���ҳ�б�
// 	 * @param page ��ǰҳ��
// 	 * @param pageSize ÿҳ��¼��
// 	 * @return ��ҳ���
// 	 */
// 	public static Result getActivityListByPage(int page, int pageSize) {
// 	    Result result = new Result("fail", null, null);
// 	    
// 	    try {
// 	        // ������֤
// 	        if (page < 1) page = 1;
// 	        if (pageSize < 1 || pageSize > 100) pageSize = 10;
// 	        
// 	        // ��ѯ����
// 	        List<HashMap<String, Object>> items = ActivityDao.getActivityListByPage(page, pageSize);
// 	        
// 	        // ��ѯ����
// 	        int totalItems = ActivityDao.getTotalActivities();
// 	        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
// 	        
// 	        // ����ͳһ��Ӧ�ṹ
// 	        HashMap<String, Object> data = new HashMap<>();
// 	        data.put("list", items);  // ʹ��list��Ϊ�����ֶ���
// 	        data.put("currentPage", page);
// 	        data.put("totalPages", totalPages);
// 	        data.put("totalItems", totalItems);
// 	        data.put("pageSize", pageSize);
// 	        
// 	        result.setFlag("success");
// 	        result.setData(data);
// 	        
// 	    } catch (Exception e) {
// 	        result.setData("��ѯʧ��: " + e.getMessage());
// 	        e.printStackTrace();
// 	    }
// 	    
// 	    return result;
// 	}
//
// 	/**
// 	 * ��������ѯ���ҳ�б�
// 	 * @param activityName �����
// 	 * @param activityType �����
// 	 * @param communityId ��������ID
// 	 * @param page ��ǰҳ��
// 	 * @param pageSize ÿҳ��¼��
// 	 * @return ��ҳ���
// 	 */
// 	public static Result searchActivities(String activityName, String activityType, 
// 	                                    Integer communityId, int page, int pageSize) {
// 	    Result result = new Result("fail", null, null);
// 	    
// 	    try {
// 	        // ������֤
// 	        if (page < 1) page = 1;
// 	        if (pageSize < 1 || pageSize > 100) pageSize = 10;
// 	        
// 	        // ��ѯ����
// 	        List<HashMap<String, Object>> items = ActivityDao.searchActivities(
// 	            activityName, activityType, communityId, page, pageSize);
// 	        
// 	        // ��ѯ����
// 	        int totalItems = ActivityDao.countActivities(activityName, activityType, communityId);
// 	        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
// 	        
// 	        // �������ؽ��
// 	        HashMap<String, Object> data = new HashMap<>();
// 	        data.put("items", items);
// 	        data.put("currentPage", page);
// 	        data.put("totalPages", totalPages);
// 	        data.put("totalItems", totalItems);
// 	        data.put("pageSize", pageSize);
// 	        
// 	        result.setFlag("success");
// 	        result.setData(data);
// 	        
// 	    } catch (Exception e) {
// 	        result.setData("��ѯʧ��: " + e.getMessage()+"...��ˢ��");
// 	        e.printStackTrace();
// 	    }
// 	    
// 	    return result;
// 	}

 	// �޸�ԭ�е�searchAI������ʹ������µ�searchActivities����
// 	public static Result searchAI(String activityName, String activityType, int page, int pageSize) {
// 	    return searchActivities(activityName, activityType, null, page, pageSize);
// 	}
 	
 	
		
}
