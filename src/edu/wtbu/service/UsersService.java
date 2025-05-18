package edu.wtbu.service;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.dao.UsersDao;
import edu.wtbu.pojo.Result;

public class UsersService {	
	//登录
	public static Result login(String user_name,String user_student_id) {
		Result result=new Result("fail", null, null);
		HashMap<String, Object> user=UsersDao.findByNameAndStuId(user_name, user_student_id);
		if (user!=null) {
			result.setFlag("success");
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("user_id", user.get("user_id"));
			map.put("user_role", user.get("user_role"));
			map.put("user_student_id", user.get("user_student_id"));
			map.put("user_name", user.get("user_name"));
			result.setData(map);
		}else {
			user=UsersDao.findByName(user_name);
			if (user!=null) {
				result.setData("学号错误");
			}else {
				result.setData("姓名错误");
			}
		}
		return result;
	}
	
	
	//录入社团信息
	public static Result enterCI(int community_id,String community_name, String community_type, String founder_name,
			String founder_id,String create_date,String community_introduction,String community_status)  {
		Result result = new Result("fail", null, null);
		int rows = UsersDao.enterCI(community_id,community_name, community_type, founder_name, founder_id,
				create_date, community_introduction, community_status);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("添加成功");
		} else {
			result.setData("添加失败");
		}
		return result;
	}
	
	//社团信息
	public static List<HashMap<String, Object>> getCIList() {
        return UsersDao.getCIList();
    }
		
	//搜索社团
	public static Result searchCI(String communityName, String communityType, int page, int pageSize) {
	    Result result = new Result("fail", null, null);
	    
	    try {
	        // 参数验证
	        if (page < 1) { page = 1;}
	        if (pageSize < 1 || pageSize > 100) {pageSize = 10;}
	        
	        // 查询搜索数据
	        List<HashMap<String, Object>> items = UsersDao.searchClubsWithPagination(communityName, communityType, page, pageSize);
	        
	        // 查询分页总数  Math.ceil() → 向上取整
	        //totalItems：总记录数  pageSize：每页显示的记录数
	        int totalItems = UsersDao.countClubs(communityName, communityType);
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
	
//  // 搜索社团（按名称模糊匹配）
//  public static List<HashMap<String, Object>> findClubListByName(String community_name) {
//      String searchName = community_name == null ? "" : "%" + community_name + "%";
//      return UsersDao.findClubListByName(searchName);
//  }
//  // 搜索社团（按名称和类型）
//  public static List<HashMap<String, Object>> findClubListByTypeAndName(String community_name, String community_type) {
//      String searchName = community_name == null ? "" : "%" + community_name + "%";
//      String searchType = community_type == null ? "" : "%" + community_type + "%";
//      return UsersDao.findClubListByTypeAndName(searchName, searchType);
//  }
}
