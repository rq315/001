package edu.wtbu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wtbu.dao.MemberDao;
import edu.wtbu.pojo.Result;

public class MemberService {
    //��Ա����ҳ
    public static Result getMemberList(int page, int pageSize, String userName, String roleId) {
        Result result = new Result();
        try {
            // ��ȡ�ܼ�¼��
            int totalItems = MemberDao.getMemberCount(userName, roleId);
            
            // ������ҳ��
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            
            // ��ȡ��ǰҳ����
            List<HashMap<String, Object>> list = MemberDao.getMemberList(
                page, pageSize, userName, roleId);
            
            // �������ؽ��
            Map<String, Object> data = new HashMap<>();
            data.put("list", list);
            data.put("currentPage", page);
            data.put("totalPages", totalPages);
            data.put("totalItems", totalItems);
            
            result.setFlag("success");
            result.setData(data);
        } catch (Exception e) {
            result.setFlag("fail");
            result.setData("��ѯ��Ա�б�ʧ��: " + e.getMessage());
        }
        return result;
    }
    
	//��ӳ�Ա
	public static Result addMember(String user_name, String studentId, String user_join_date,
            int user_role, String user_phone, int clubId) {
		Result result = new Result("fail", null, null);
		
		try {
			int rows = MemberDao.addMember(user_name, studentId, user_join_date, user_role, user_phone, clubId);
			if (rows > 0) {
			result.setFlag("success");
			result.setData("��Ա��ӳɹ�");
			// �������ɵ�ѧ��
			result.setData(new HashMap<String, Object>() {
				{
				put("message", "��Ա��ӳɹ�");
				put("studentId", studentId);
				}
				});
			} else {
				result.setData("��Ա���ʧ��");
			}
		} catch (Exception e) {
			result.setData(e.getMessage());
		}
		
		return result;
	}
    
    //ɾ����Ա
	public static Result deleteMember(int userId) {
		Result result = new Result("fail", null, null);
		int rows = MemberDao.deleteMember(userId);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("ɾ���ɹ�");
		} else {
			result.setData("ɾ��ʧ��");
		}
		return result;
	}
    
	//����Ȩ��
	public static Result updateRoleMember(int userId,int roleId) {
		Result result = new Result("fail", null, null);
		int rows = MemberDao.updateRoleMember(userId, roleId);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("���³ɹ�");
		} else {
			result.setData("����ʧ��");
		}
		return result;
	}

//    // ������Ա��������ģ��ƥ�䣩
//    public static List<HashMap<String, Object>> findUserListByName(String userName) {
//        String searchName = userName == null ? "" : "%" + userName + "%";
//        return MemberDao.findUserListByName(searchName);
//    }
//    
//    // ������Ա������ɫ��������
//    public static List<HashMap<String, Object>> findUserListByRoleAndName(String userName, int roleId) {
//        String searchName = userName == null ? "" : "%" + userName + "%";
//        return MemberDao.findUserListByRoleAndName(roleId, searchName);
//    }
    
}