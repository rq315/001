package edu.wtbu.service;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.dao.FinanceDao;
import edu.wtbu.pojo.Result;

public class FinanceService {
	//经费表格分页（包含总费用）
	public static Result getFinanceList(int page, int pageSize) {
        Result result = new Result();
        try {
            // 获取总记录数
            int totalItems = FinanceDao.getFinanceCount();
            
            // 计算总页数
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            
            // 获取当前页数据
            List<HashMap<String, Object>> list = FinanceDao.getFinanceList(page, pageSize);
            
            // 获取总费用
            double totalSum = FinanceDao.getTotalFundAmount();
            
            // 构建返回结果
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("list", list);
            data.put("currentPage", page);
            data.put("totalPages", totalPages);
            data.put("totalItems", totalItems);
            data.put("totalSum", totalSum); // 添加总费用
            
            result.setFlag("success");
            result.setData(data);
        } catch (Exception e) {
            result.setFlag("fail");
            result.setData("查询经费列表失败: " + e.getMessage()+"...请刷新");
        }
        return result;
    }
	
	
	//添加经费信息
		public static Result addFinance(int fund_amount,String fund_type, String fund_record_date, String fund_description,
				int associated_activity_number) {
		Result result = new Result("fail", null, null);
		int rows = FinanceDao.addFinance(fund_amount, fund_type, fund_record_date, fund_description, associated_activity_number);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("添加成功");
		} else {
			result.setData("添加失败");
		}
		return result;
	}
		
//	//经费信息
//	public static List<HashMap<String, Object>> getFinanceList() {
//        return UsersDao.getFinanceList();
//    }
	
}
