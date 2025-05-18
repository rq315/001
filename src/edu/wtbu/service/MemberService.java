package edu.wtbu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wtbu.dao.MemberDao;
import edu.wtbu.pojo.Result;

public class MemberService {
    //成员表格分页
    public static Result getMemberList(int page, int pageSize, String userName, String roleId) {
        Result result = new Result();
        try {
            // 获取总记录数
            int totalItems = MemberDao.getMemberCount(userName, roleId);
            
            // 计算总页数
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            
            // 获取当前页数据
            List<HashMap<String, Object>> list = MemberDao.getMemberList(
                page, pageSize, userName, roleId);
            
            // 构建返回结果
            Map<String, Object> data = new HashMap<>();
            data.put("list", list);
            data.put("currentPage", page);
            data.put("totalPages", totalPages);
            data.put("totalItems", totalItems);
            
            result.setFlag("success");
            result.setData(data);
        } catch (Exception e) {
            result.setFlag("fail");
            result.setData("查询成员列表失败: " + e.getMessage());
        }
        return result;
    }
    
	//添加成员
	public static Result addMember(String user_name, String studentId, String user_join_date,
            int user_role, String user_phone, int clubId) {
		Result result = new Result("fail", null, null);
		
		try {
			int rows = MemberDao.addMember(user_name, studentId, user_join_date, user_role, user_phone, clubId);
			if (rows > 0) {
			result.setFlag("success");
			result.setData("成员添加成功");
			// 返回生成的学号
			result.setData(new HashMap<String, Object>() {
				{
				put("message", "成员添加成功");
				put("studentId", studentId);
				}
				});
			} else {
				result.setData("成员添加失败");
			}
		} catch (Exception e) {
			result.setData(e.getMessage());
		}
		
		return result;
	}
    
    //删除成员
	public static Result deleteMember(int userId) {
		Result result = new Result("fail", null, null);
		int rows = MemberDao.deleteMember(userId);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("删除成功");
		} else {
			result.setData("删除失败");
		}
		return result;
	}
    
	//更新权限
	public static Result updateRoleMember(int userId,int roleId) {
		Result result = new Result("fail", null, null);
		int rows = MemberDao.updateRoleMember(userId, roleId);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("更新成功");
		} else {
			result.setData("更新失败");
		}
		return result;
	}

//    // 搜索成员（按姓名模糊匹配）
//    public static List<HashMap<String, Object>> findUserListByName(String userName) {
//        String searchName = userName == null ? "" : "%" + userName + "%";
//        return MemberDao.findUserListByName(searchName);
//    }
//    
//    // 搜索成员（按角色和姓名）
//    public static List<HashMap<String, Object>> findUserListByRoleAndName(String userName, int roleId) {
//        String searchName = userName == null ? "" : "%" + userName + "%";
//        return MemberDao.findUserListByRoleAndName(roleId, searchName);
//    }
    
}