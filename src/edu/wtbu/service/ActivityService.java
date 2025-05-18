package edu.wtbu.service;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.dao.ActivityDao;
import edu.wtbu.pojo.Result;

public class ActivityService {
	public static Result searchAI(String activityName, String activityType, int page, int pageSize) {
	    Result result = new Result("fail", null, null);
	    
	    try {
	        // 参数验证
	        if (page < 1) page = 1;
	        if (pageSize < 1 || pageSize > 100) pageSize = 10;
	        
	        // 查询数据
	        List<HashMap<String, Object>> items = ActivityDao.searchActivitiesWithPagination(
	            activityName, activityType, page, pageSize);
	        
	        // 查询总数
	        int totalItems = ActivityDao.countActivities(activityName, activityType);
	        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
	        
	        // 构建返回结果
	        HashMap<String, Object> data = new HashMap<String, Object>();
	        data.put("items", items);
	        data.put("currentPage", page);
	        data.put("totalPages", totalPages);
	        data.put("totalItems", totalItems);
	        data.put("pageSize", pageSize);
	        
	        result.setFlag("success");
	        result.setData(data);
	        
	    } catch (Exception e) {
	        result.setData("查询失败: " + e.getMessage()+"...请刷新");
	        e.printStackTrace();
	    }
	    
	    return result;
	}
	
    
    //全部活动信息-表格
  	public static List<HashMap<String, Object>> getActivityList() {
          return ActivityDao.getActivityList();
      }
    

  	
    //指定的活动详情-页面 id
  	public static HashMap<String, Object> getActivityDetailList(int activity_id) {
  		return ActivityDao.getActivityDetailList(activity_id);
      }
  	
	//发布活动
	public static Result publishA(String activity_name, String activity_type, String activity_date,
            String activity_location, String expected_activity_number, String activity_description, int assocoated_community_number) {
		Result result = new Result("fail", null, null);
		int rows = ActivityDao.publishA(activity_name, activity_type, activity_date, activity_location, expected_activity_number, activity_description, assocoated_community_number);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("添加成功");
		} else {
			result.setData("添加失败");
		}
		return result;
	}
	//更新参与人数
	public static Result updateExpectedNumber(int activityId, int expectedNumber) {
		Result result = new Result("fail", null, null);
		int rows = ActivityDao.updateExpectedNumber(activityId, expectedNumber);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("更新成功");
		} else {
			result.setData("更新失败");
		}
		return result;
	}
	//保存活动总结
		public static Result saveActivitySummary(int activityId, String summary) {
			Result result = new Result("fail", null, null);
			int rows = ActivityDao.saveActivitySummary(activityId, summary);
			if (rows > 0) {
				result.setFlag("success");
				result.setData("保存成功");
			} else {
				result.setData("保存失败");
			}
			return result;
		}
		
		
		
//	    // 搜索活动（按名称模糊匹配）
//	    public static List<HashMap<String, Object>> findActivityListByName(String activity_name) {
//	        String searchName = activity_name == null ? "" : "%" + activity_name + "%";
//	        return UsersDao.findActivityListByName(searchName);
//	    }
	//    
//	    // 搜索活动（按名称和类型）
//	    public static List<HashMap<String, Object>> findActivityListByTypeAndName(String activity_name, String activity_type) {
//	        String searchName = activity_name == null ? "" : "%" + activity_name + "%";
//	        String searchType = activity_type == null ? "" : "%" + activity_type + "%";
//	        return UsersDao.findActivityListByTypeAndName(searchName, searchType);
//	    }
		
//  	/**
// 	 * 获取活动分页列表
// 	 * @param page 当前页码
// 	 * @param pageSize 每页记录数
// 	 * @return 分页结果
// 	 */
// 	public static Result getActivityListByPage(int page, int pageSize) {
// 	    Result result = new Result("fail", null, null);
// 	    
// 	    try {
// 	        // 参数验证
// 	        if (page < 1) page = 1;
// 	        if (pageSize < 1 || pageSize > 100) pageSize = 10;
// 	        
// 	        // 查询数据
// 	        List<HashMap<String, Object>> items = ActivityDao.getActivityListByPage(page, pageSize);
// 	        
// 	        // 查询总数
// 	        int totalItems = ActivityDao.getTotalActivities();
// 	        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
// 	        
// 	        // 构建统一响应结构
// 	        HashMap<String, Object> data = new HashMap<>();
// 	        data.put("list", items);  // 使用list作为数据字段名
// 	        data.put("currentPage", page);
// 	        data.put("totalPages", totalPages);
// 	        data.put("totalItems", totalItems);
// 	        data.put("pageSize", pageSize);
// 	        
// 	        result.setFlag("success");
// 	        result.setData(data);
// 	        
// 	    } catch (Exception e) {
// 	        result.setData("查询失败: " + e.getMessage());
// 	        e.printStackTrace();
// 	    }
// 	    
// 	    return result;
// 	}
//
// 	/**
// 	 * 带条件查询活动分页列表
// 	 * @param activityName 活动名称
// 	 * @param activityType 活动类型
// 	 * @param communityId 关联社团ID
// 	 * @param page 当前页码
// 	 * @param pageSize 每页记录数
// 	 * @return 分页结果
// 	 */
// 	public static Result searchActivities(String activityName, String activityType, 
// 	                                    Integer communityId, int page, int pageSize) {
// 	    Result result = new Result("fail", null, null);
// 	    
// 	    try {
// 	        // 参数验证
// 	        if (page < 1) page = 1;
// 	        if (pageSize < 1 || pageSize > 100) pageSize = 10;
// 	        
// 	        // 查询数据
// 	        List<HashMap<String, Object>> items = ActivityDao.searchActivities(
// 	            activityName, activityType, communityId, page, pageSize);
// 	        
// 	        // 查询总数
// 	        int totalItems = ActivityDao.countActivities(activityName, activityType, communityId);
// 	        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
// 	        
// 	        // 构建返回结果
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
// 	        result.setData("查询失败: " + e.getMessage()+"...请刷新");
// 	        e.printStackTrace();
// 	    }
// 	    
// 	    return result;
// 	}

 	// 修改原有的searchAI方法，使其调用新的searchActivities方法
// 	public static Result searchAI(String activityName, String activityType, int page, int pageSize) {
// 	    return searchActivities(activityName, activityType, null, page, pageSize);
// 	}
 	
 	
		
}
